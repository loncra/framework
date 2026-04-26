# datasource

`datasource` 是 loncra framework 的读写分离数据源基础实现。它基于标准 `javax.sql.DataSource` 与 `java.sql.Connection` 进行包装，不依赖 Spring，可被 JDBC、连接池、Spring Boot starter 或其他上层框架复用。

该模块只负责数据源路由的基础能力：当当前线程标记为只读时，从从库列表中选择一个数据源；当当前线程标记为非只读或未设置标记时，使用主库数据源。

## 模块定位

- 提供轻量级读写分离 `DataSource` 实现：`ReadWriteSeparateDataSource`。
- 提供连接包装器：`ReadWriteSeparateConnection`，用于感知 `Connection.setReadOnly(...)` 并更新当前线程的读写状态。
- 提供从库选择策略抽象：`DataSourceObtainResolver`。
- 内置随机、轮询、加权轮询三种从库负载均衡策略。
- 当没有配置从库时，自动回退到主库，保证单数据源场景也能正常使用。

## 依赖引入

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>datasource</artifactId>
    <version>${framework.version}</version>
</dependency>
```

运行依赖说明：

- 核心依赖：`commons`、`slf4j-api`。
- 编译期不依赖 Spring，也不绑定具体连接池实现。
- 测试中使用 `HikariCP`、`H2`、`JUnit Jupiter`、`logback-classic`。

## 包结构

- `io.github.loncra.framework.datasource`
  - `ReadWriteSeparateDataSource`：读写分离数据源入口，实现标准 `DataSource`。
  - `ReadWriteSeparateConnection`：连接包装器，代理原始 `Connection` 并同步只读状态。
- `io.github.loncra.framework.datasource.resolver`
  - `DataSourceObtainResolver`：从库数据源选择策略接口。
- `io.github.loncra.framework.datasource.resolver.support`
  - `RandomDataSourceObtainResolver`：随机选择从库。
  - `RoundRobinDataSourceObtainResolver`：按顺序轮询选择从库。
  - `WeightedRoundRobinDataSourceObtainResolver`：按权重平滑轮询选择从库。

## 工作机制

`ReadWriteSeparateDataSource` 内部维护一个线程级只读上下文：

- `ReadWriteSeparateDataSource.setReadOnly(true)`：当前线程后续获取连接时路由到从库。
- `ReadWriteSeparateDataSource.setReadOnly(false)`：当前线程后续获取连接时路由到主库。
- 未设置只读状态：默认路由到主库。
- `ReadWriteSeparateDataSource.clearReadOnly()`：清除当前线程状态，避免线程复用时串状态。

路由发生在 `getConnection()` 调用时。也就是说，`Connection.setReadOnly(...)` 会更新线程上下文，并影响后续从 `ReadWriteSeparateDataSource` 获取的新连接；它不会把当前已经创建出来的物理连接迁移到另一个数据源。

## 快速开始

### 创建读写分离数据源

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;

import javax.sql.DataSource;
import java.util.List;

DataSource master = createMasterDataSource();
DataSource slave1 = createSlaveDataSource("slave-1");
DataSource slave2 = createSlaveDataSource("slave-2");

ReadWriteSeparateDataSource dataSource = new ReadWriteSeparateDataSource(
        master,
        List.of(slave1, slave2)
);
```

默认从库选择策略为 `RandomDataSourceObtainResolver`。

### 只读查询路由到从库

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

ReadWriteSeparateDataSource.setReadOnly(true);

try (Connection connection = dataSource.getConnection();
     PreparedStatement statement = connection.prepareStatement("select * from tb_user where id = ?")) {

    statement.setLong(1, 1L);

    try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            // 处理查询结果
        }
    }
}
finally {
    ReadWriteSeparateDataSource.clearReadOnly();
}
```

### 写操作路由到主库

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

ReadWriteSeparateDataSource.setReadOnly(false);

try (Connection connection = dataSource.getConnection();
     PreparedStatement statement = connection.prepareStatement(
             "insert into tb_user(username, email) values(?, ?)"
     )) {

    statement.setString(1, "admin");
    statement.setString(2, "admin@example.com");
    statement.executeUpdate();
}
finally {
    ReadWriteSeparateDataSource.clearReadOnly();
}
```

未调用 `setReadOnly(...)` 时也会走主库，因此写操作可以不显式设置；但在存在读写混合调用链时，建议明确设置并在 `finally` 中清理。

## 从库选择策略

### 随机策略

`RandomDataSourceObtainResolver` 从从库列表中随机选择一个数据源，适合从库规格一致、无需强顺序分配的场景。

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;
import io.github.loncra.framework.datasource.resolver.support.RandomDataSourceObtainResolver;

ReadWriteSeparateDataSource dataSource = new ReadWriteSeparateDataSource(
        master,
        slaves,
        new RandomDataSourceObtainResolver()
);
```

### 轮询策略

`RoundRobinDataSourceObtainResolver` 使用线程安全索引按顺序选择从库，适合从库规格相近、希望请求分布更均匀的场景。

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;
import io.github.loncra.framework.datasource.resolver.support.RoundRobinDataSourceObtainResolver;

ReadWriteSeparateDataSource dataSource = new ReadWriteSeparateDataSource(
        master,
        slaves,
        new RoundRobinDataSourceObtainResolver()
);
```

### 加权轮询策略

`WeightedRoundRobinDataSourceObtainResolver` 使用平滑加权轮询算法。权重列表与从库列表一一对应，权重越高，被选择的次数越多。

```java
import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;
import io.github.loncra.framework.datasource.resolver.support.WeightedRoundRobinDataSourceObtainResolver;

import java.util.List;

WeightedRoundRobinDataSourceObtainResolver resolver =
        new WeightedRoundRobinDataSourceObtainResolver(List.of(3, 2, 1));

ReadWriteSeparateDataSource dataSource = new ReadWriteSeparateDataSource(
        master,
        slaves,
        resolver
);
```

如果未设置权重，或者权重数量与从库数量不一致，会自动使用默认权重 `1`。

## 自定义从库选择策略

实现 `DataSourceObtainResolver` 即可接入自定义策略，例如按机房、延迟、健康状态、租户或业务标签选择从库。

```java
import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;

import javax.sql.DataSource;
import java.util.List;

public class FirstAvailableDataSourceObtainResolver implements DataSourceObtainResolver {

    @Override
    public DataSource obtain(List<DataSource> slaveDataSources, DataSource masterDataSource) {
        if (slaveDataSources == null || slaveDataSources.isEmpty()) {
            return masterDataSource;
        }
        return slaveDataSources.get(0);
    }

    @Override
    public String getName() {
        return "first-available";
    }
}
```

## 关键类说明

### `ReadWriteSeparateDataSource`

- 实现 `javax.sql.DataSource`，可作为普通数据源传给 JDBC、ORM 或上层 starter。
- 构造时必须提供主数据源，主数据源为空会抛出 `SystemException`。
- 从库为空时会使用主库作为从库回退。
- `getConnection(...)` 会根据当前线程只读状态选择目标数据源，并返回 `ReadWriteSeparateConnection`。
- 提供 `setReadOnly(...)`、`getReadOnly()`、`clearReadOnly()` 管理线程级读写状态。

### `ReadWriteSeparateConnection`

- 包装真实 `Connection`，大部分方法直接委托给原始连接。
- 调用 `setReadOnly(boolean)` 时，会同时调用原始连接的 `setReadOnly(...)` 并更新 `ReadWriteSeparateDataSource` 的线程上下文。
- 该类不解析 SQL，不根据 SQL 语句自动判断读写。

### `DataSourceObtainResolver`

- 定义从库选择方法：`obtain(List<DataSource> slaveDataSources, DataSource masterDataSource)`。
- 定义策略名称：`getName()`。
- 所有内置实现都会在从库为空时返回主库。

## 推荐实践

- 在 Web 请求、RPC 调用、消息消费、定时任务等入口处设置读写状态，并在 `finally` 中调用 `clearReadOnly()`。
- 查询方法可设置 `setReadOnly(true)`，写方法设置 `setReadOnly(false)` 或保持默认主库路由。
- 如果上层接入 Spring 事务，建议由 starter 或切面根据事务 `readOnly` 属性设置本模块的线程上下文。
- 从库规格一致时使用随机或轮询；从库规格不一致时使用加权轮询。
- 自定义策略中应处理从库为空的情况，推荐回退到主库。

## 注意事项

- 本模块不提供 Spring Boot 自动配置，也不创建具体连接池；连接池由上层模块或业务工程提供。
- 路由在 `getConnection()` 时发生，已经获取到的连接不会因为后续切换只读状态而更换底层数据源。
- `READ_ONLY_CONTEXT` 是 `ThreadLocal`，线程池环境必须清理，否则可能影响同线程后续任务。
- 模块不会解析 SQL，也不会阻止在只读连接上执行写 SQL；实际行为取决于数据库、驱动和连接池对只读连接的支持。
- 主从数据一致性由数据库复制机制保证，本模块不处理复制延迟。
- `WeightedRoundRobinDataSourceObtainResolver` 的权重应使用正整数，并与从库列表顺序保持一致。

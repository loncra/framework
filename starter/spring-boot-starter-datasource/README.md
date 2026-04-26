# spring-boot-starter-datasource

`spring-boot-starter-datasource` 是 **`datasource` 读写分离** 能力在 **Spring Boot** 中的 **自动配置**封装：在应用启动时根据配置创建 **主库 / 多从库** 的 `javax.sql.DataSource`，并装配为框架实现的 **`io.github.loncra.framework.datasource.ReadWriteSeparateDataSource`**，再作为 Spring 容器的 **主 `DataSource` Bean**（`@Primary`、Bean 名 **`dataSource`**）供 `JdbcTemplate`、JPA、MyBatis 等使用。

底层路由语义（`Connection` 的只读状态、`ReadWriteSeparateDataSource` 的从库选择策略、ThreadLocal 等）**完全以 `datasource` 模块为准**，请参阅仓库内 **[`datasource/README.md`](../../datasource/README.md)**。本文只描述 **与 Spring Boot 的衔接方式** 和 **配置约定**。

## 模块定位

- 用 **`loncra.framework.datasource.*`** 声明 **主、从** 多套 `DataSourceProperties`，与 **`spring.datasource` 单数据源** 配置**并列**，由本 starter 专责读写分离场景。
- 在 **`DataSourceBuilder`** 上复用 **Spring Boot 原生的** `DataSourceProperties#initializeDataSourceBuilder()`，因此支持 **HikariCP、Tomcat Pool、DBCP2** 等 Spring Boot 文档中列出的连接池及各自 `spring.datasource.*` 风格子属性（在 **`master` / `slaves[i]`** 下书写）。
- 通过 **`@AutoConfigureBefore(org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class)`** 优先于 Spring Boot 默认的 JDBC 数据源自动配置，避免两套机制抢占。
- 若**未**配置从库，会在**日志中告警**，此时所有连接仍走主库，与 `datasource` 实现一致。

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-datasource</artifactId>
    <version>${framework.version}</version>
</dependency>
```

传递 / 主要依赖：

| 依赖 | 作用 |
| --- | --- |
| `datasource` | 提供 `ReadWriteSeparateDataSource` 及从库选择解析器实现 |
| `spring-boot-starter` | 自动配置基础设施 |
| `spring-boot-starter-jdbc` | `DataSource`、JDBC 抽象、`DataSourceProperties` |

**不包含** 具体数据库驱动，请按主从库实际类型在工程中自行增加（如 MySQL 驱动）。

## 自动配置

- 注册类：`io.github.loncra.framework.datasource.DataSourceAutoConfiguration`  
- 资源：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- 开关： **`loncra.framework.datasource.enabled`**，**默认 `true`。** 为 **`false`** 时**整段自动配置不生效**，应用可回退到 Spring Boot 默认的 `spring.datasource` 等配置（需自行处理 Bean 与冲突）。

Bean 条件：

- **`@ConditionalOnMissingBean(name = "dataSource")`**：若你已在配置类中**自定义** 名为 `dataSource` 的 `DataSource` Bean，则**不会**再创建读写分离实现。

主 Bean：

- **名称**：`dataSource`（显式在代码中通过 `@Bean` 的 name 与 `@ConditionalOnMissingBean` 对齐的是「名为 dataSource 的 Bean」的缺失判断，与通常的按类型 `DataSource` 的 MissingBean 不同，便于保留自定义名）。

装配逻辑摘要（见源码 `DataSourceAutoConfiguration#dataSource`）：

1. 校验 **`LoncraDataSourceProperties#master`** 非空，且 `url`、**`username` 非空**（密码未在框架层强制，与当前实现一致）。
2. 分别对 `master` 与 `slaves` 中每一项调用 **`DataSourceProperties#initializeDataSourceBuilder().build()`** 创建实际连接池。
3. 使用 **`new ReadWriteSeparateDataSource(masterDataSource, slaveDataSources)`** 包装；从库为**空**时仅主库、并打 `warn` 日志。

## 与 `datasource` 实现的关系

- 自动配置**不**在 YAML 中暴露从库选择策略，当前与 **`ReadWriteSeparateDataSource(主, 从列表)`** 一致时，**从库**使用 `datasource` 包内**默认的** `RandomDataSourceObtainResolver`（随机）。若需 **轮询、权重轮询** 等，可不在此 starter 里配置，改为**自行**编程式构造 `ReadWriteSeparateDataSource` 与对应 `DataSourceObtainResolver`（并自行注册为 `DataSource` Bean，且勿与本 starter 的 `dataSource` Bean 冲突），详见 **`datasource`** 说明。
- 读写判断仍依赖 **`ReadWriteSeparateConnection`、ThreadLocal 与连接只读标志**；与 **Spring `@Transactional(readOnly = true)`** 的配合请见 **[`datasource/README.md`](../../datasource/README.md)**。

## 配置结构

| 属性前缀 | 含义 |
| --- | --- |
| `loncra.framework.datasource.enabled` | 是否启用本自动配置，默认 `true` |
| `loncra.framework.datasource.master` | **主库**，`org.springframework.boot.autoconfigure.jdbc.DataSourceProperties` 结构 |
| `loncra.framework.datasource.slaves` | **从库列表**，元素类型同上，**可省略** |

`master` / `slaves[]` 下可使用的键与 **Spring Boot 关于 `DataSourceProperties` 的说明** 一致，例如（节选）：

- `url` / `username` / `password` / `driver-class-name`（或 Spring Boot 3 推荐的 `driver-class-name` 书写方式）
- `type`：连接池实现类，如 `com.zaxxer.hikari.HikariDataSource`
- `hikari.*` / 其他实现前缀：按各连接池官方与 Spring Boot 支持情况填写

## 配置示例

```yaml
loncra:
  framework:
    datasource:
      enabled: true
      master:
        url: jdbc:mysql://db-master:3306/app?useSSL=false&characterEncoding=utf-8
        username: app
        password: secret
        driver-class-name: com.mysql.cj.jdbc.Driver
        type: com.zaxxer.hikari.HikariDataSource
        hikari:
          maximum-pool-size: 20
          pool-name: master-pool
      slaves:
        - url: jdbc:mysql://db-slave-1:3306/app?useSSL=false&characterEncoding=utf-8
          username: app
          password: secret
          driver-class-name: com.mysql.cj.jdbc.Driver
          type: com.zaxxer.hikari.HikariDataSource
        - url: jdbc:mysql://db-slave-2:3306/app?useSSL=false&characterEncoding=utf-8
          username: app
          password: secret
          driver-class-name: com.mysql.cj.jdbc.Driver
          type: com.zaxxer.hikari.HikariDataSource
```

## 常见场景

- **本 starter + 多数据源/路由插件**：需要第二个非读写分离的 `DataSource` 时，为第二个 Bean 使用**另外的名称**；避免覆盖名为 `dataSource` 的主 Bean，除非明确想替换本自动配置。
- **关闭读写分离、改用官方单源**：将 **`loncra.framework.datasource.enabled`** 置为 **`false`**，并改用 **`spring.datasource`（或 3.x 的常规写法）** 配置，由 Spring Boot 标准 JDBC 自动配置提供 `DataSource`。
- **与 Flyway / Liquibase**：与常规 Spring DataSource 使用方式相同，通常指向**主库** URL；注意在迁移任务中**不要**错误依赖只读路由，详见业务与 `datasource` 模块文档。

## 推荐实践

- 主从的 **JDBC URL、用户权限** 按实际复制或中间件情况配置；从库账号建议**仅**授予业务所需**读**权限。
- 生产环境为 **`master` / 各 slave** 调优连接池与超时，与监控、限流方案配套。
- 在请求结束或异步边界处注意 **`ReadWriteSeparateDataSource.clearReadOnly()`** 等 ThreadLocal 清理，避免池化线程上残留只读状态（`datasource` 模块已说明，此处仅作集成提醒）。

## 注意事项

- 自动配置在 **`master` 缺失、或 master 的 `url` / `username` 为空** 时会**抛出** `SystemException` 并阻止启动，便于快速发现问题。
- 元数据文件 `additional-spring-configuration-metadata.json` 中 `groups[0].type` 如与 IDE 提示不完全一致，以实际 **`LoncraDataSourceProperties`** 与 **`DataSourceProperties`** 为准；不影响运行时。
- 若同一线程、同一套连接在业务上**混用** 未正确声明只读/写，路由可能与预期不符，这是 **`datasource` 包语义**，而非本 starter 单独引入的限制。

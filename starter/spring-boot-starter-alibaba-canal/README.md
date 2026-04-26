# spring-boot-starter-alibaba-canal

`spring-boot-starter-alibaba-canal` 是 loncra framework 对 Alibaba Canal 的 Spring Boot 集成模块。它提供 Canal 实例订阅、binlog 行变更解析、变更通知分发、HTTP 通知发送、Canal Admin API 封装，以及基于 Actuator 的可通知表元数据端点。

该模块适合用于把 MySQL binlog 变更转换为应用内事件或 HTTP 回调事件，常见场景包括缓存刷新、搜索索引同步、异步通知、数据订阅和跨系统数据变更感知。

## 模块定位

- 提供 Spring Boot 自动配置入口：`CanalAutoConfiguration`。
- 根据 `loncra.framework.canal.instances` 自动启动一个或多个 Canal 实例订阅。
- 将 Canal 原始 `Message` 转换为 Alibaba Canal `FlatMessage` 列表，并封装为 `CanalMessage`。
- 提供行数据变更解析扩展点：`CanalRowDataChangeResolver`。
- 提供通知规则服务抽象：`CanalRowDataChangeNoticeService`。
- 内置 HTTP 通知发送器：`HttpCanalRowDataChangeNoticeResolver`。
- 提供 Canal Admin API 客户端：`CanalAdminService`，可查询节点、实例、日志并启动/停止本地订阅。
- 提供 `@NotifiableTable` 注解和 Actuator Endpoint，用于暴露可通知表及字段元数据。

## 依赖引入

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-alibaba-canal</artifactId>
    <version>${framework.version}</version>
</dependency>
```

运行依赖说明：

- 核心依赖：`canal.common`、`canal.protocol`、`canal.client`、`spring-boot-starter-web`、`spring-boot-actuator`、`commons`。
- Canal Admin 支持依赖 `redisson-spring-boot-starter`，用于缓存 Canal Admin 登录 token。
- `NotifiableTableEndpoint` 需要业务工程提供 `DataSource`，用于读取 `information_schema.COLUMNS` 表字段元数据。

## 自动配置

自动配置类通过以下文件注册：

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

默认生效条件：

- `loncra.framework.canal.enabled` 缺省为启用。
- `loncra.framework.canal.notice.enabled` 缺省为启用。
- `loncra.framework.canal.admin.enabled` 在源码条件中缺省为启用；如果业务不需要 Canal Admin API 封装，建议显式关闭。

自动配置默认创建：

- `CanalInstanceManager`
- `HttpCanalRowDataChangeNoticeResolver`
- `CanalRowDataChangeNoticeService` 默认实现 `InMemoryCanalRowDataChangeNoticeService`
- `SimpleCanalRowDataChangeResolver`
- `NotifiableTableEndpoint`
- `CanalAdminService`，在 Canal Admin 开启时创建

## 配置示例

### 直接订阅 Canal Server

```yaml
loncra:
  framework:
    canal:
      enabled: true
      batch-size: 5120
      exception-retry-time:
        value: 1
        unit: MINUTES
      instance-option-delay-time:
        value: 15
        unit: SECONDS
      instances:
        - id: 1
          name: example
          host: 127.0.0.1
          tcp-port: 11111
          username: canal
          password: canal
      notice:
        enabled: true
        database-name: demo_db
        base-packages:
          - com.example.demo.domain
      admin:
        enabled: false
```

启动后，`CanalInstanceManager.afterPropertiesSet()` 会遍历 `instances` 并提交 `CanalSubscribeRunner` 到内部线程池。

### 开启 Canal Admin 支持

```yaml
loncra:
  framework:
    canal:
      admin:
        enabled: true
        uri: http://localhost:9097
        username: admin
        password: 123456
        token-param-name: token
        login-token-cache:
          name: loncra:framework:alibaba:canal:admin-login-token
          expires-time:
            value: 5
            unit: MINUTES
```

Canal Admin 登录 token 会缓存在 Redisson `RBucket` 中。开启该能力时，需要业务工程提供可用的 `RedissonClient`。

## 包结构

- `io.github.loncra.framework.canal`
  - `CanalAutoConfiguration`：自动配置入口。
  - `CanalInstanceManager`：Canal 订阅实例管理器。
  - `CanalSubscribeRunner`：单个 Canal 实例的订阅执行器。
  - `CanalAdminService`：Canal Admin API 客户端封装。
  - `CanalConstants`：Canal Admin API 路径和配置键常量。
  - `MysqlUtils`：读取 MySQL 表字段元数据工具。
- `config`
  - `CanalProperties`、`CanalInstanceProperties`、`CanalAdminProperties`、`CanalNoticeProperties`。
- `resolver`
  - `CanalRowDataChangeResolver`：处理 Canal 行变更消息的扩展点。
  - `CanalRowDataChangeNoticeResolver`：发送通知的扩展点。
  - `SimpleCanalRowDataChangeResolver`：默认行变更通知解析器。
  - `HttpCanalRowDataChangeNoticeResolver`：HTTP 通知发送实现。
- `service`
  - `CanalRowDataChangeNoticeService`：通知规则、ACK 记录保存和通知发送服务抽象。
  - `InMemoryCanalRowDataChangeNoticeService`：内存实现，适合测试或二次扩展示例。
- `domain`
  - Canal Admin DTO、Canal 消息 DTO、通知规则实体、ACK 消息实体、表元数据模型。
- `annotation`
  - `@NotifiableTable`：声明某个 Java 类型对应一张可通知的表。
- `endpoint`
  - `NotifiableTableEndpoint`：Actuator Endpoint，端点 ID 为 `notifiableTables`。

## 工作流程

1. 应用启动后，`CanalInstanceManager` 根据 `CanalProperties.instances` 创建 `CanalSubscribeRunner`。
2. `CanalSubscribeRunner` 使用 `CanalConnectors.newSingleConnector(...)` 连接 Canal Server，并调用 `connector.subscribe()`。
3. Runner 循环调用 `getWithoutAck(batchSize)` 拉取消息。
4. 只处理 `ROWDATA` 类型的 DML 事件：`INSERT`、`UPDATE`、`DELETE`。
5. 原始 Canal `Entry` 被转换为 `FlatMessage`，再封装为 `CanalMessage`。
6. `CanalInstanceManager` 将 `CanalMessage` 交给所有 `CanalRowDataChangeResolver`。
7. 默认 `SimpleCanalRowDataChangeResolver` 根据数据库名和表名查找通知规则，并创建 `CanalRowDataChangeAckMessage`。
8. `CanalRowDataChangeNoticeService` 保存 ACK 消息并调用匹配的 `CanalRowDataChangeNoticeResolver` 发送通知。
9. `HttpCanalRowDataChangeNoticeResolver` 发送 HTTP POST，成功后写入 ACK 响应；失败时标记重试状态。
10. 消息处理完成后，Runner 调用 `connector.ack(batchId)`；异常时回滚并按 `exceptionRetryTime` 延迟重连。

## 快速开始

### 标记可通知表

```java
import io.github.loncra.framework.canal.annotation.NotifiableTable;

@NotifiableTable(value = "tb_user", comment = "用户表")
public class UserEntity {
}
```

配合：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: notifiableTables

loncra:
  framework:
    canal:
      notice:
        database-name: demo_db
        base-packages:
          - com.example.demo.domain
```

访问 Actuator 端点：

```text
GET /actuator/notifiableTables
```

端点会扫描 `base-packages` 下标注 `@NotifiableTable` 的类，并通过 `information_schema.COLUMNS` 读取表字段与字段备注。

### 自定义行变更处理器

如果你只需要消费 Canal 变更事件，不走通知规则体系，可以直接提供一个 `CanalRowDataChangeResolver` Bean。

```java
import com.alibaba.otter.canal.protocol.FlatMessage;
import io.github.loncra.framework.canal.domain.CanalMessage;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeResolver;
import org.springframework.stereotype.Component;

@Component
public class UserCanalChangeResolver implements CanalRowDataChangeResolver {

    @Override
    public void change(CanalMessage message) {
        for (FlatMessage flatMessage : message.getFlatMessageList()) {
            if (!"demo_db".equals(flatMessage.getDatabase())) {
                continue;
            }
            if (!"tb_user".equals(flatMessage.getTable())) {
                continue;
            }

            switch (flatMessage.getType()) {
                case "INSERT" -> handleInsert(flatMessage);
                case "UPDATE" -> handleUpdate(flatMessage);
                case "DELETE" -> handleDelete(flatMessage);
                default -> {
                }
            }
        }
    }
}
```

### 自定义通知规则服务

默认 `InMemoryCanalRowDataChangeNoticeService` 不适合作为生产持久化实现。实际项目通常需要自定义 `CanalRowDataChangeNoticeService`，从数据库、配置中心或管理后台读取通知规则，并保存 ACK 记录。

```java
import io.github.loncra.framework.canal.domain.CanalRowDataChangeNotice;
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;
import io.github.loncra.framework.canal.service.support.AbstractCanalRowDataChangeNoticeService;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeNoticeResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseCanalNoticeService extends AbstractCanalRowDataChangeNoticeService {

    public DatabaseCanalNoticeService(List<CanalRowDataChangeNoticeResolver> resolvers) {
        super(resolvers);
    }

    @Override
    public List<CanalRowDataChangeNotice> findEnableByDestinations(List<String> destinations) {
        // destinations 的格式为 database.table
        return loadEnabledNoticeRules(destinations);
    }

    @Override
    public CanalRowDataChangeAckMessage saveAckMessage(CanalRowDataChangeAckMessage ackMessage) {
        return saveToDatabase(ackMessage);
    }
}
```

因为自动配置使用 `@ConditionalOnMissingBean(CanalRowDataChangeNoticeService.class)`，业务侧声明该 Bean 后会替换默认内存实现。

## HTTP 通知规则

HTTP 通知由 `HttpCanalRowDataChangeNoticeResolver` 处理。通知实体需要满足：

- `protocol = Protocol.HTTP_OR_HTTPS`
- `metadata` 中包含 `httpEntity`
- `httpEntity` 可以是多个 `HttpCanalRowDataChangeNoticeMetadata`

通知元数据结构：

```java
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeNoticeEntity;
import io.github.loncra.framework.canal.domain.meta.HttpCanalRowDataChangeNoticeMetadata;
import io.github.loncra.framework.canal.service.CanalRowDataChangeNoticeService;
import io.github.loncra.framework.commons.enumerate.basic.Protocol;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;

import java.util.List;
import java.util.Map;

HttpCanalRowDataChangeNoticeMetadata http = new HttpCanalRowDataChangeNoticeMetadata();
http.setUrl("https://example.com/canal/notify");
http.setHeaders(Map.of("X-Source", "canal"));
http.setQueryParams(Map.of("tenantId", "demo"));
http.setBody(Map.of("source", "canal"));

CanalRowDataChangeNoticeEntity notice = new CanalRowDataChangeNoticeEntity();
notice.setEnabled(YesOrNo.Yes);
notice.setDatabaseName("demo_db");
notice.setTableName("tb_user");
notice.setProtocol(Protocol.HTTP_OR_HTTPS);
notice.setProtocolMeta(Map.of(
        CanalRowDataChangeNoticeService.HTTP_ENTITY_FIELD,
        List.of(http)
));
```

HTTP 发送行为：

- 请求方式固定为 `POST`。
- 请求头默认 `Content-Type: application/json`。
- Canal 消息体会作为请求 JSON 主体。
- `HttpCanalRowDataChangeNoticeMetadata.body` 会被放入请求体的 `appendBody` 字段。
- 2xx 响应会标记为成功；非 2xx 响应会进入重试状态。
- 如果响应体能转换为 `AckResponseBody`，会作为 ACK 响应保存；否则包装为 `AckStatus.ACKNOWLEDGED`。

## 字段过滤与映射

`SimpleCanalRowDataChangeResolver` 支持两类通知前处理。

### 正则过滤

当 `filterRegularExpressionsMessage = Yes` 时，只保留能命中 `regularExpressions` 的 `FlatMessage`。

```java
notice.setFilterRegularExpressionsMessage(YesOrNo.Yes);
notice.setRegularExpressions(List.of("demo_db\\.tb_user"));
```

正则匹配目标格式为：

```text
database.table
```

### 字段映射

`fieldMappings` 用于把 Canal 字段名映射为业务字段名，同时会处理 `data`、`old` 和主键字段名。

```java
notice.setFieldMappings(Map.of(
        "demo_db.tb_user",
        Map.of(
                "user_id", "userId",
                "user_name", "username"
        )
));
```

## Canal Admin API

`CanalAdminService` 封装了 Canal Admin 的常用 API：

- `findNodeServer(...)`：分页查询节点服务。
- `getNodeServerConfig(clusterId, id)`：获取节点服务配置。
- `findActiveInstances(serverId)`：查询运行中的实例。
- `findInstances(name, pageRequest)`：分页查询实例。
- `getInstance(id)`：获取实例详情。
- `subscribe(instance, nodeServer)`：根据 Canal Admin 实例信息启动本地订阅。
- `unsubscribe(id)`：停止本地订阅。
- `getClusters()`：获取集群信息。
- `getInstanceLog(instanceId, serverId)`：获取实例日志。

使用示例：

```java
import io.github.loncra.framework.canal.CanalAdminService;
import io.github.loncra.framework.canal.domain.CanalInstance;
import io.github.loncra.framework.canal.domain.CanalNodeServer;
import io.github.loncra.framework.commons.page.PageRequest;
import io.github.loncra.framework.commons.page.TotalPage;

TotalPage<CanalInstance> page = canalAdminService.findInstances(
        "example",
        PageRequest.of(1, 20)
);

CanalInstance instance = page.getElements().get(0);
CanalNodeServer nodeServer = instance.getNodeServer();

canalAdminService.subscribe(instance, nodeServer);
```

## 关键类说明

### `CanalInstanceManager`

- 负责管理本地运行中的 `CanalSubscribeRunner`。
- 启动时订阅 `CanalProperties.instances` 中配置的实例。
- 支持通过 `subscribe(...)` 动态订阅实例，通过 `unsubscribe(id)` 停止订阅。
- 内部维护静态 `RUNNERS` 列表，按实例 ID 避免重复订阅。

### `CanalSubscribeRunner`

- 单个 Canal 实例的订阅循环。
- 使用 `getWithoutAck(batchSize)` 拉取消息。
- 只处理 DML 事件：`INSERT`、`UPDATE`、`DELETE`。
- 忽略事务开始/结束事件、DDL 事件和非 DML 事件。
- 转换后的 `FlatMessage` 保留 `database`、`table`、`type`、`data`、`old`、`pkNames`、`mysqlType`、`sqlType` 等信息。
- 处理成功后 `ack(batchId)`；异常时 `rollback()` 并延迟重试。

### `CanalRowDataChangeNoticeService`

- 定义通知规则查询、ACK 消息创建、ACK 保存和发送行为。
- 默认实现是内存服务，不负责生产级持久化。
- 生产环境建议自定义实现，至少持久化通知规则和发送结果。

### `HttpCanalRowDataChangeNoticeResolver`

- 负责发送 HTTP/HTTPS 通知。
- 支持 headers、query params 和额外 body。
- 根据响应状态和响应体更新 `CanalRowDataChangeAckMessage`。
- 失败时会按 ACK 消息里的重试字段进行补发。

### `NotifiableTableEndpoint`

- Actuator Endpoint ID 为 `notifiableTables`。
- 扫描 `CanalNoticeProperties.basePackages`。
- 读取 `@NotifiableTable` 注解中的表名和注释。
- 通过 `MysqlUtils.getTableColumns(...)` 读取 MySQL 表字段名、字段备注，并生成驼峰字段 ID。

## 推荐实践

- 生产环境不要直接依赖默认内存通知服务，建议自定义 `CanalRowDataChangeNoticeService` 并持久化 ACK 记录。
- Canal 消息处理逻辑应尽量幂等，因为异常重连、HTTP 失败重试或应用重启都可能导致重复处理。
- 如果只需要本地消费事件，优先实现 `CanalRowDataChangeResolver`；如果需要对外分发事件，再接入通知规则体系。
- `CanalSubscribeRunner` 当前使用单机 Canal Connector；集群或 HA 场景建议在上层配置 Canal Server 高可用，或扩展 Runner。
- 开启 Canal Admin 能力时，确保 Canal Admin、Redisson 和网络访问都可用。
- 暴露 `notifiableTables` 端点前，请确认 Actuator 暴露范围和访问权限。

## 注意事项

- `loncra.framework.canal.enabled=false` 会关闭整个 Canal 自动配置。
- `CanalInstanceProperties.id` 用于本地去重订阅，同一个 ID 不会重复启动 Runner。
- `CanalSubscribeRunner` 在异常后会根据 `exception-retry-time` 延迟重连；期间会回滚 Canal batch。
- `NotifiableTableEndpoint` 会缓存第一次扫描结果，运行期新增注解类或表字段变更不会自动刷新。
- `NotifiableTableEndpoint` 会读取数据库元数据，需要配置正确的 `DataSource` 和 `database-name`。
- `HttpCanalRowDataChangeNoticeResolver` 当前使用异步发送和定时补发，业务侧保存 ACK 时应考虑并发更新。
- HTTP 通知接收方建议返回 `AckResponseBody`，便于发送方判断 ACK 状态。

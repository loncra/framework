# spring-boot-starter-basic-security

`spring-boot-starter-basic-security` 是 loncra framework 的基础安全扩展模块，聚焦两件事：

- 统一“审计事件”的存储与查询抽象，提供内存 / Elasticsearch / Mongo 三种实现。
- 提供请求返回结果的“字段忽略 + 脱敏”能力，便于快速满足常见安全合规要求。

如果你是第一次接触这个模块，可以先按本文的“快速开始”把功能跑通，再看“扩展开发”章节。

## 模块作用

- 提供统一审计仓库接口 `ExtendAuditEventRepository`，支持 `find` / `findPage` / `count` / `get`。
- 提供统一审计事件模型：`IdAuditEvent`、`StoragePositioningAuditEvent`。
- 基于配置自动选择审计存储类型（`Memory`、`Elasticsearch`、`Mongo`）。
- 提供审计写入与查询拦截器接口，方便扩展多租户、数据清洗、权限过滤等逻辑。
- 提供 `@Plugin` 注解及 `PluginInfo` 模型，统一插件/资源元数据描述结构。
- 提供 Web 返回值字段忽略、脱敏上下文持有器（`IgnoreOrDesensitizeResultHolder`）。

## 核心能力结构

- `io.github.loncra.framework.security`
  - 自动配置：`AuditConfiguration`、`SecurityConfiguration`
  - 配置属性：`AuditProperties`、`WebProperties`、`StoragePositionProperties`
- `audit`
  - 抽象与模型：`ExtendAuditEventRepository`、`AbstractExtendAuditEventRepository`、`FindMetadata`
  - 审计事件：`IdAuditEvent`、`StoragePositioningAuditEvent`、`IdStoragePositioningAuditEvent`
  - 扩展点：`AuditEventRepositoryWriteInterceptor`、`AuditEventRepositoryQueryInterceptor<T>`
  - 定位器：`StoragePositioningGenerator`、`SpringElStoragePositioningGenerator`
- `audit.memory`
  - `CustomInMemoryAuditEventRepository`、`CustomInMemoryAuditConfiguration`
- `audit.elasticsearch`
  - `ElasticsearchAuditEventRepository`、`ElasticsearchAuditConfiguration`
  - 查询生成：`ElasticsearchQueryGenerator` + 多种 wildcard parser
- `audit.mongo`
  - `MongoAuditEventRepository`、`MongoAuditConfiguration`
- `filter.result`
  - `IgnoreOrDesensitizeResultFilter`
  - `IgnoreOrDesensitizeResultHolder` + `ThreadLocalIgnoreOrDesensitizeResultHolderStrategy`
- `entity`
  - `SecurityPrincipal`、`SimpleSecurityPrincipal`
  - `RoleAuthority`、`ResourceAuthority`
- `plugin`
  - `@Plugin`、`PluginInfo`、`TargetObject`

## 快速开始

## 1. 引入依赖

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-basic-security</artifactId>
    <version>${framework.version}</version>
</dependency>
```

## 2. 选择审计类型

模块会自动加载 `AuditConfiguration`，默认审计类型是 `Memory`。

```yaml
loncra:
  framework:
    security:
      audit:
        enabled: true
        type: memory # memory / elasticsearch / mongo
```

说明：

- `enabled` 默认 `true`。
- `type` 默认 `memory`。
- 当 `type=elasticsearch` 且类路径存在 ES 依赖时，启用 ES 审计实现。
- 当 `type=mongo` 且类路径存在 Mongo 依赖时，启用 Mongo 审计实现。

## 3. 配置审计存储定位（ES / Mongo 推荐）

审计数据会根据 `StoragePositionProperties` 计算“存储位置”（ES 索引 / Mongo 集合）。

```yaml
loncra:
  framework:
    security:
      audit:
        storage-position:
          prefix: audit_event
          separator: _
          spring-expression:
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#timestamp,'yyyy_MM_dd')"
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#creationTime,'yyyy_MM_dd')"
```

上面配置会产生类似：`audit_event_2026_03_27` 的存储位置。

## 4. 开启返回值字段忽略 / 脱敏

```yaml
loncra:
  framework:
    security:
      web:
        ignore-result-map:
          userInfo: [password, salt]
          getPrincipal:
            - "$.data.authorities"
        desensitize-result-map:
          userInfo: [mobile, email]
          findSystemUsers:
            - "$.data[*].value[*].realName"
            - "$.data[*].value[*].email"
            - "$.data[*].value[*].phoneNumber"
```

`userInfo` 这个 key 对应的是请求 URI 转换后的驼峰名称，例如：

- `/user/info` -> `userInfo`
- `/api/user/info` -> `apiUserInfo`

表达式语法说明：

- `ignore-result-map` / `desensitize-result-map` 的 value 是 `JsonPath` 表达式列表。
- 这些表达式由 `commons` 中的对象处理能力执行（底层依赖 `JsonPath`），用于定位响应对象中的字段。
- 常用写法：
  - `$.data.authorities`：匹配 `data` 下的 `authorities` 字段。
  - `$.data[*].value[*].realName`：匹配数组中每个元素下继续嵌套数组的 `realName` 字段。

示例 1（忽略字段）：

```yaml
loncra:
  framework:
    security:
      web:
        ignore-result-map:
          getPrincipal:
            - "$.data.authorities"
```

当访问对应 `getPrincipal` 路径时，响应体中 `data.authorities` 将被移除，不再返回。

示例 2（批量脱敏字段）：

```yaml
loncra:
  framework:
    security:
      web:
        desensitize-result-map:
          findSystemUsers:
            - "$.data[*].value[*].realName"
            - "$.data[*].value[*].email"
            - "$.data[*].value[*].phoneNumber"
```

当访问 `find/system/users`（映射后的 key 通常是 `findSystemUsers`）时，会对 `data` 下所有 `value` 元素中的 `realName`、`email`、`phoneNumber` 做脱敏处理。

`JsonPath` 参考资料：

- 官方主页：<https://github.com/json-path/JsonPath>
- 语法文档：<https://github.com/json-path/JsonPath/blob/master/README.md>
- 在线调试（可选）：<https://jsonpath.com/>

## 常见用法

## 1. 写入审计事件

```java
import audit.io.github.loncra.framework.security.IdAuditEvent;

Map<String, Object> data = new LinkedHashMap<>();
data.

put("ip","127.0.0.1");
data.

put("action","login");

auditEventRepository.

add(new
    IdAuditEvent("admin", "USER_LOGIN",
    data
    ));
```

## 2. 查询审计事件（扩展仓库）

```java
import page.io.github.opc.studio.framework.commons.PageRequest;

Map<String, Object> query = new LinkedHashMap<>();
query.

put("filter_[data->action_eq]","login");
query.

put("filter_[data->ip_eq]","127.0.0.1");

var page = repository.findPage(PageRequest.of(1, 20), Instant.now().minusSeconds(86400), query);
```

## 3. 使用 Elasticsearch 条件语法（重点）

`ElasticsearchQueryGenerator` 解析 `filter_...` 参数，格式来自 `SimpleConditionParser`：

- 单条件：`filter_[字段_通配符]`
- 多条件（同一个参数内 OR）：`filter_[a_eq]_or_[b_like]`
- 别名路径：`filter_[data->name_eq]`（`data` 是别名，`name` 是字段）

常用通配符：

- `eq`（等于）
- `ne`（不等于）
- `like`（包含匹配）
- `llike`（左模糊，`*value`）
- `rlike`（右模糊，`value*`）
- `gt` / `gte` / `lt` / `lte`
- `between`（范围）
- `in` / `nin`
- `eqn`（为 null）
- `nen`（不为 null）

示例：

```java
Map<String, Object> query = new LinkedHashMap<>();
query.put("filter_[data->name_like]", "test");
query.put("filter_[data->age_between]", List.of(18, 60));
query.put("filter_[data->status_in]", List.of("active", "pending"));
```

## 4. 扩展写入拦截器

```java
import audit.io.github.loncra.framework.security.AuditEventRepositoryWriteInterceptor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

@Component
public class DemoAuditWriteInterceptor implements AuditEventRepositoryWriteInterceptor {
    @Override
    public boolean preAddHandle(AuditEvent auditEvent) {
        // 返回 false 可阻止落库
        return true;
    }

    @Override
    public void postAddHandle(AuditEvent auditEvent) {
        // 写入后处理，如异步通知
    }
}
```

## 5. 扩展查询拦截器

```java
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import audit.io.github.loncra.framework.security.AuditEventRepositoryQueryInterceptor;
import audit.io.github.loncra.framework.security.FindMetadata;
import org.springframework.stereotype.Component;

@Component
public class DemoAuditQueryInterceptor implements AuditEventRepositoryQueryInterceptor<BoolQuery.Builder> {
    @Override
    public boolean preFind(
            Instant after,
            Map<String, Object> query
    ) {
        // 可在这里追加租户参数、权限参数等
        return true;
    }

    @Override
    public void postCreateQuery(FindMetadata<BoolQuery.Builder> metadata) {
        // 可对已经生成的查询对象再加工
    }
}
```

## 6. 使用 `@Plugin` 统一描述资源元信息

```java
import plugin.io.github.loncra.framework.security.Plugin;

@Plugin(
        id = "user:list",
        parent = "user:root",
        name = "用户列表",
        authority = {"sys:user:list"},
        audit = true,
        operationDataTrace = true,
        remark = "用户模块查询入口"
)
public class UserQueryController {
}
```

## 关键接口说明

## 1. `ExtendAuditEventRepository`

- 在 Spring 原生 `AuditEventRepository` 基础上扩展了分页、计数、按 ID 查询能力。
- 建议业务查询统一使用该接口，避免和底层存储强耦合。

## 2. `AuditEventRepositoryWriteInterceptor`

- `preAddHandle`：写入前拦截，返回 `false` 直接中断写入。
- `postAddHandle`：写入后回调。

## 3. `AuditEventRepositoryQueryInterceptor<T>`

- `preFind` / `preCount`：查询前拦截，可做参数控制或短路。
- `postCreateQuery`：查询对象创建后再加工，适合注入通用过滤条件。

## 4. `StoragePositioningGenerator`

- 用于计算审计存储位置（索引/集合）。
- 默认实现 `SpringElStoragePositioningGenerator`，通过 SpEL 动态计算后缀。

## 5. `IgnoreOrDesensitizeResultHolder`

- 持有当前请求线程的“忽略字段 / 脱敏字段”配置。
- `IgnoreOrDesensitizeResultFilter` 会在请求进入时注入配置，在请求结束后自动清理。

## 配置项速查

```yaml
loncra:
  framework:
    security:
      audit:
        enabled: true
        type: memory
        storage-position:
          prefix: audit_event
          separator: _
          spring-expression: []
      web:
        ignore-result-map: {}
        desensitize-result-map: {}
```

## 扩展建议

- 业务写审计时优先写入结构化 `data`（Map），便于后续多条件检索。
- 多业务线场景建议通过 `AuditEventRepositoryWriteInterceptor` 统一补充租户、应用名、链路 ID。
- 查询侧建议封装统一查询参数构造器，避免散落硬编码 `filter_[...]` 字符串。
- 需要分表/分索引策略时，优先通过 `storage-position.spring-expression` 管控。
- 敏感字段建议双保险：返回值脱敏 + 审计写入前脱敏。

## 注意事项

- 自动装配入口在 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`，包含：
  - `io.github.loncra.framework.security.AuditConfiguration`
  - `io.github.loncra.framework.security.SecurityConfiguration`
- `IgnoreOrDesensitizeResultFilter` 只负责注入上下文，不会直接改写响应体；需要结合上层响应处理逻辑调用 `IgnoreOrDesensitizeResultHolder.convert(...)`。
- `StoragePositionProperties.spring-expression` 至少应有一个能成功计算结果的表达式，否则会触发断言异常。
- Elasticsearch 首次写入会自动创建索引并加载 `elasticsearch/plugin-audit-mapping.json`。
- `AuditConfiguration` 通过 `loncra.framework.security.audit.type` 决定启用哪种仓库实现，未配置时默认 `Memory`。


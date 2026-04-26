# spring-boot-starter-basic-security

`spring-boot-starter-basic-security` 是 loncra framework 的基础安全支撑模块，主要提供审计事件存储、查询扩展、请求返回结果字段忽略/脱敏上下文，以及安全主体、权限、插件资源描述等通用模型。

需要注意：该模块当前不是完整的登录认证或 Spring Security 过滤链实现，它不负责用户名密码登录、Token 校验、访问控制决策等业务认证流程，而是为上层安全 starter 或业务系统提供基础模型和审计能力。

## 模块定位

- 提供 Spring Boot Actuator `AuditEventRepository` 的自动配置扩展。
- 支持三种审计存储类型：`Memory`、`Elasticsearch`、`Mongo`。
- 为 Elasticsearch / Mongo 审计仓库提供分页、统计、按 ID 查询等扩展能力。
- 提供审计写入拦截器和查询拦截器，便于补充租户、应用、链路、权限过滤等公共逻辑。
- 提供基于 SpEL 的审计存储位置生成能力，用于按日期或业务字段拆分 ES 索引 / Mongo 集合。
- 提供 Web 返回结果字段忽略与脱敏上下文，配合响应包装或序列化阶段统一处理敏感字段。
- 提供安全主体、角色权限、资源权限、插件元数据等基础模型。

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-basic-security</artifactId>
    <version>${framework.version}</version>
</dependency>
```

核心依赖：

- `commons`：提供通用工具、分页模型、查询条件解析、树结构、基础枚举和对象转换能力。
- `spring-boot-starter-actuator`：提供 Spring Boot 原生 `AuditEvent` 与 `AuditEventRepository`。

可选 / Provided 依赖：

- `spring-boot-starter-web`：存在 Web 环境时启用返回结果忽略/脱敏过滤器。
- `spring-boot-starter-data-elasticsearch`：当选择 `Elasticsearch` 审计类型时使用。
- `spring-boot-starter-data-mongodb`：当选择 `Mongo` 审计类型时使用。

## 自动配置

自动配置入口位于：

```text
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

包含两个自动配置类：

- `io.github.loncra.framework.security.AuditConfiguration`
- `io.github.loncra.framework.security.SecurityConfiguration`

`AuditConfiguration` 受 `loncra.framework.security.audit.enabled` 控制，默认启用；它会根据 `loncra.framework.security.audit.type` 选择审计实现。`SecurityConfiguration` 在类路径存在 `OncePerRequestFilter` 时启用，用于注册 `IgnoreOrDesensitizeResultFilter`。

## 包结构

```text
io.github.loncra.framework.security
├── AuditConfiguration                         # 审计自动配置入口
├── SecurityConfiguration                      # Web 安全辅助自动配置入口
├── AuditProperties                            # 审计配置属性
├── WebProperties                              # 返回结果忽略/脱敏配置属性
├── StoragePositionProperties                  # 审计存储位置配置属性
├── audit
│   ├── ExtendAuditEventRepository             # 扩展审计仓库接口
│   ├── AbstractExtendAuditEventRepository     # 扩展仓库抽象实现
│   ├── IdAuditEvent                           # 带 id 的审计事件
│   ├── StoragePositioningAuditEvent           # 指定存储位置的审计事件
│   ├── IdStoragePositioningAuditEvent         # 带 id 且指定存储位置的审计事件
│   ├── AuditEventRepositoryWriteInterceptor   # 审计写入拦截器
│   ├── AuditEventRepositoryQueryInterceptor   # 审计查询拦截器
│   ├── StoragePositioningGenerator            # 存储位置生成器
│   ├── SpringElStoragePositioningGenerator    # SpEL 存储位置生成器
│   ├── AuditPrincipal / SimpleAuditPrincipal  # 审计当事人模型
│   └── Auditable                              # 审计标记注解
├── audit.memory
│   ├── CustomInMemoryAuditConfiguration       # 内存审计自动配置
│   └── CustomInMemoryAuditEventRepository     # 带写入拦截的内存审计仓库
├── audit.elasticsearch
│   ├── ElasticsearchAuditConfiguration        # Elasticsearch 审计自动配置
│   ├── ElasticsearchAuditEventRepository      # Elasticsearch 审计仓库
│   ├── ElasticsearchQueryGenerator            # ES 查询条件生成器
│   └── wildcard                               # eq/ne/like/between/in 等条件解析器
├── audit.mongo
│   ├── MongoAuditConfiguration                # Mongo 审计自动配置
│   └── MongoAuditEventRepository              # Mongo 审计仓库
├── filter.result
│   ├── IgnoreOrDesensitizeResultFilter        # 请求级字段处理配置注入过滤器
│   ├── IgnoreOrDesensitizeResultHolder        # 字段处理上下文持有器
│   └── ThreadLocalIgnoreOrDesensitizeResultHolderStrategy
├── entity
│   ├── SecurityPrincipal                      # 安全主体接口
│   ├── SimpleSecurityPrincipal                # 简单安全主体实现
│   ├── RoleAuthority                          # 角色权限模型
│   └── ResourceAuthority                      # 资源权限模型
└── plugin
    ├── Plugin                                 # 插件/资源元数据注解
    ├── PluginInfo                             # 插件/资源元数据模型
    └── TargetObject                           # 插件扫描目标对象描述
```

## storage-position 存储定位

`loncra.framework.security.audit.storage-position` 只在 **Elasticsearch / Mongo** 审计实现中使用；`Memory` 不依赖本配置。它的设计目的，是在审计数据**量级较大**时，用 **应用层可预测的命名规则** 把数据拆到多个**物理目标**上（在 ES 中体现为**多个按名称区分的索引**，在 Mongo 中体现为**多个集合**），避免单表/单索引无限膨胀、便于按时间做冷热分离、归档与检索。

> 说明：这里说的「分片」是**业务/命名维度的分桶**（例如按天、按月拆索引或集合），不是去改 Elasticsearch 集群的 `number_of_shards` 分片数配置。后者仍由 ES 集群与索引模板/策略在运维侧管理。

实现类为 `SpringElStoragePositioningGenerator`：把要定位的对象先转成 `Map` 再作为 SpEL 变量，并额外提供 `className` 变量，然后**按 `spring-expression` 列表中的顺序**依次求值，**第一个成功求出非空字符串** 的表达式作为**后缀**；若全部失败则触发断言。最终名称为：

```text
{prefix}{separator}{suffix}
```

默认 `prefix` 为 `audit_event`，`separator` 为下划线。仓库实现里会再对结果做 `toLowerCase()` 作为实际索引名/集合名。

下面两条表达式是典型「按**当天日期**分桶」的**兜底链**：写入时优先用审计事件时间戳 `timestamp` 格式化成 `yyyy_MM_dd`；在只有 `StringIdEntity` 等**带 `creationTime`、未必有 `timestamp` 键** 的对象上参与定位时，第二条可继续尝试用 `creationTime`：

```text
T(io.github.loncra.framework.commons.DateUtils).dateFormat(#timestamp,'yyyy_MM_dd')
T(io.github.loncra.framework.commons.DateUtils).dateFormat(#creationTime,'yyyy_MM_dd')
```

在「按天」分桶的示例下，**当日期（自然日）变化时，计算出的后缀会变**，于是一次写入会落到一个**新的**索引/集合名上。此时：

- **Elasticsearch**：`ElasticsearchAuditEventRepository#doAdd` 在写入前会调用 `createIndexIfNotExists`：若**目标索引尚不存在**则**创建**该索引并加载 `elasticsearch/plugin-audit-mapping.json` 中的 mapping，再写入审计文档。因此你看到的，就是「**当天第一次写入**某命名规则下的索引时，**按需自动建索引**；同一天后续写入复用已存在的同一索引」。
- **Mongo**：`MongoAuditEventRepository#doAdd` 使用 `mongoTemplate.save(idAuditEvent, collectionName)`；集合名由存储定位结果决定，**首次写入不存在的集合名**时，Mongo 会在**首次落库**时创建该集合（无额外 mapping 文件）。

**查询侧**：扩展仓库的列表/统计查询会用 `createFind` 中传入的 `after` 时间，通过 `getIndexName(Instant) / getCollectionName(Instant)` 同样走一遍存储定位，决定**一次查询落在哪一个**索引/集合上（与写入、按 `StringIdEntity#get` 查询时的分桶规则需保持一致）。**跨多天的范围检索** 不在本实现里自动合并多索引/多集合，需要业务自行扩展或多次查询。

**手动指定**目标：若 `AuditEvent` 是 `StoragePositioningAuditEvent`，可绕过 SpEL 计算，直接使用其 `getStoragePositioning()` 作为索引名/集合名（需自行保证命名符合 ES/ Mongo 要求）。

## 配置示例

### 1. 使用内存审计

默认审计类型为 `Memory`，未配置时会注册 `CustomInMemoryAuditEventRepository`。

```yaml
loncra:
  framework:
    security:
      audit:
        enabled: true
        type: Memory
```

内存实现继承 Spring Boot 原生 `InMemoryAuditEventRepository`，容量固定为 `1000`，并额外支持 `AuditEventRepositoryWriteInterceptor`。它适合本地开发、测试或简单场景，不提供 `ExtendAuditEventRepository` 的分页、统计和按 ID 查询能力。

### 2. 使用 Elasticsearch 审计

```yaml
spring:
  elasticsearch:
    uris: http://localhost:9200

loncra:
  framework:
    security:
      audit:
        enabled: true
        type: Elasticsearch
        storage-position:
          prefix: audit_event
          separator: _
          spring-expression:
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#timestamp,'yyyy_MM_dd')"
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#creationTime,'yyyy_MM_dd')"
```

索引命名与「按天拆索引、不存在则创建」等行为见上文 **《storage-position 存储定位》**。`ElasticsearchAuditEventRepository` 首次在**某个**计算出的索引名上写入时，会加载内置 mapping 创建该索引：

```text
elasticsearch/plugin-audit-mapping.json
```

### 3. 使用 Mongo 审计

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/demo

loncra:
  framework:
    security:
      audit:
        enabled: true
        type: Mongo
        storage-position:
          prefix: audit_event
          separator: _
          spring-expression:
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#timestamp,'yyyy_MM_dd')"
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#creationTime,'yyyy_MM_dd')"
```

存储定位对 Mongo 的集合名作用方式与上节一致；`mongoTemplate.save` 写入 `IdAuditEvent`。**首次**写入**此前不存在**的集合名时，Mongo 会在**首次落库**时建集合。

### 4. 配置返回结果忽略 / 脱敏

```yaml
loncra:
  framework:
    security:
      web:
        ignore-result-map:
          userInfo:
            - "$.data.password"
            - "$.data.salt"
          getPrincipal:
            - "$.data.authorities"
        desensitize-result-map:
          userInfo:
            - "$.data.mobile"
            - "$.data.email"
          findSystemUsers:
            - "$.data[*].value[*].realName"
            - "$.data[*].value[*].email"
            - "$.data[*].value[*].phoneNumber"
```

`IgnoreOrDesensitizeResultFilter` 会把请求 URI 转为配置 key：

- `/user/info` -> `userInfo`
- `/api/user/info` -> `apiUserInfo`
- `/find/system/users` -> `findSystemUsers`

过滤器只负责把当前请求匹配到的 JsonPath 列表写入 `IgnoreOrDesensitizeResultHolder`，不会直接改写 HTTP 响应体。真正执行字段忽略或脱敏时，需要在响应包装、返回值处理或统一结果转换阶段调用 `IgnoreOrDesensitizeResultHolder.convert(source)`。

## 常见用法

下面示例中的 `auditEventRepository` 或 `repository` 均表示已由 Spring 注入的仓库 Bean。

### 1. 写入审计事件

```java
import io.github.loncra.framework.security.audit.IdAuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;

import java.util.LinkedHashMap;
import java.util.Map;

Map<String, Object> data = new LinkedHashMap<>();
data.put("ip", "127.0.0.1");
data.put("action", "login");

auditEventRepository.add(new IdAuditEvent("admin", "USER_LOGIN", data));
```

如果当前使用 Elasticsearch / Mongo，也可以指定本次事件的存储位置：

```java
import io.github.loncra.framework.security.audit.StoragePositioningAuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;

import java.time.Instant;
import java.util.Map;

auditEventRepository.add(new StoragePositioningAuditEvent(
        "audit_event_2026_04_25",
        Instant.now(),
        "admin",
        "USER_LOGIN",
        Map.of("ip", "127.0.0.1")
));
```

### 2. 分页查询审计事件

`ExtendAuditEventRepository` 只由 Elasticsearch / Mongo 审计实现提供。内存实现请使用 Spring Boot 原生 `AuditEventRepository#find(...)`。

```java
import io.github.loncra.framework.commons.page.Page;
import io.github.loncra.framework.commons.page.PageRequest;
import io.github.loncra.framework.security.audit.ExtendAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

Map<String, Object> query = new LinkedHashMap<>();
query.put("filter_[data->action_eq]", "login");
query.put("filter_[data->ip_eq]", "127.0.0.1");

Page<AuditEvent> page = repository.findPage(
        PageRequest.of(1, 20),
        Instant.now().minusSeconds(86400),
        query
);
```

### 3. 按 ID 查询审计事件

按 ID 查询同样依赖存储定位。由于 ES 索引 / Mongo 集合可能按日期拆分，`StringIdEntity` 需要携带可用于计算存储位置的时间字段。

```java
import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.security.audit.ExtendAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.time.Instant;

StringIdEntity idEntity = StringIdEntity.of("audit-id", Instant.now());

AuditEvent event = repository.get(idEntity);
```

### 4. Elasticsearch 条件查询语法

`ElasticsearchQueryGenerator` 基于 `commons` 的 `SimpleConditionParser` 解析 `filter_...` 参数，并把条件转为 ES `BoolQuery`。

常见格式：

- 单条件：`filter_[字段_通配符]`
- 多条件 OR：`filter_[a_eq]_or_[b_like]`
- 别名路径：`filter_[data->name_eq]`，其中 `data` 是对象字段，`name` 是嵌套字段

支持的通配符：

| 通配符 | 含义 |
| --- | --- |
| `eq` | 等于 |
| `ne` | 不等于 |
| `like` | 包含匹配 |
| `llike` | 左模糊 |
| `rlike` | 右模糊 |
| `gt` / `gte` | 大于 / 大于等于 |
| `lt` / `lte` | 小于 / 小于等于 |
| `between` | 范围匹配 |
| `in` / `nin` | 在列表中 / 不在列表中 |
| `eqn` | 字段为 null |
| `nen` | 字段不为 null |

示例：

```java
import java.util.List;
import java.util.Map;

Map<String, Object> query = Map.of(
        "filter_[data->name_like]", "test",
        "filter_[data->age_between]", List.of(18, 60),
        "filter_[data->status_in]", List.of("active", "pending")
);
```

## 扩展开发

### 1. 审计写入拦截器

所有审计实现都会使用 `AuditEventRepositoryWriteInterceptor`。返回 `false` 可以阻止本次审计写入。

```java
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

@Component
public class DemoAuditWriteInterceptor implements AuditEventRepositoryWriteInterceptor {

    @Override
    public boolean preAddHandle(AuditEvent auditEvent) {
        return true;
    }

    @Override
    public void postAddHandle(AuditEvent auditEvent) {
        // 写入后可进行异步通知、日志增强等处理
    }
}
```

### 2. 审计查询拦截器

查询拦截器只作用于 `AbstractExtendAuditEventRepository`，也就是 Elasticsearch / Mongo 扩展仓库。

Elasticsearch 示例：

```java
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import io.github.loncra.framework.security.audit.AuditEventRepositoryQueryInterceptor;
import io.github.loncra.framework.security.audit.FindMetadata;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class DemoElasticsearchAuditQueryInterceptor
        implements AuditEventRepositoryQueryInterceptor<BoolQuery.Builder> {

    @Override
    public boolean preFind(Instant after, Map<String, Object> query) {
        query.putIfAbsent("filter_[data->tenantId_eq]", "tenant-a");
        return true;
    }

    @Override
    public void postCreateQuery(FindMetadata<BoolQuery.Builder> metadata) {
        // 可继续修改 metadata.getTargetQuery()
    }
}
```

Mongo 示例：

```java
import io.github.loncra.framework.security.audit.AuditEventRepositoryQueryInterceptor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class DemoMongoAuditQueryInterceptor
        implements AuditEventRepositoryQueryInterceptor<Criteria> {

    @Override
    public boolean preCount(Instant after, Map<String, Object> query) {
        return true;
    }
}
```

### 3. 使用 `@Plugin` 描述资源元信息

`@Plugin` 可以标记在类或方法上，用于给上层资源扫描、权限菜单、审计配置等功能提供元数据。本模块只提供注解和 `PluginInfo` 模型，不包含自动扫描器。

```java
import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.security.plugin.Plugin;

@Plugin(
        id = "user:list",
        parent = "user:root",
        name = "用户列表",
        authority = {"sys:user:list"},
        audit = true,
        operationDataTrace = true,
        sources = {"admin"},
        sort = 10,
        remark = "用户模块查询入口",
        metadata = {
                @Metadata(key = "module", value = "user")
        }
)
public class UserQueryController {
}
```

手动转换为 `PluginInfo`：

```java
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.security.plugin.PluginInfo;

Plugin plugin = UserQueryController.class.getAnnotation(Plugin.class);
PluginInfo pluginInfo = new PluginInfo(plugin);
```

### 4. 安全主体与权限模型

```java
import io.github.loncra.framework.commons.enumerate.security.UserStatus;
import io.github.loncra.framework.security.entity.ResourceAuthority;
import io.github.loncra.framework.security.entity.RoleAuthority;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;

SimpleSecurityPrincipal principal = new SimpleSecurityPrincipal(
        1L,
        "credential-value",
        "admin",
        UserStatus.Enabled
);

String principalName = principal.getName(); // 1:admin
boolean locked = !principal.isNonLocked();

RoleAuthority role = new RoleAuthority("管理员", RoleAuthority.DEFAULT_ROLE_PREFIX + "ADMIN");
ResourceAuthority resource = new ResourceAuthority(
        ResourceAuthority.getPermissionValue("system:user:list"),
        "用户列表",
        "/system/users"
);
```

## 关键类说明

### `AuditConfiguration`

审计自动配置入口。它读取 `loncra.framework.security.audit.type`，并按 `AuditType` 导入 `CustomInMemoryAuditConfiguration`、`ElasticsearchAuditConfiguration` 或 `MongoAuditConfiguration`。未配置类型时默认使用 `Memory`。

### `ExtendAuditEventRepository`

在 Spring Boot 原生 `AuditEventRepository` 基础上扩展：

- `findPage(PageRequest, Instant, Map<String, Object>)`
- `find(Instant, Map<String, Object>)`
- `count(Instant, Map<String, Object>)`
- `get(StringIdEntity)`

目前由 Elasticsearch / Mongo 实现提供。

### `AbstractExtendAuditEventRepository<T>`

扩展仓库抽象基类，统一处理：

- 写入前后拦截器调用。
- 审计事件 `data` 转换为 `Map`。
- 查询前拦截、查询对象创建、查询后执行。
- 分页参数注入和总数查询。

### `SpringElStoragePositioningGenerator`

`StoragePositionProperties` 的运行时实现，将 SpEL 表达式与 `prefix` / `separator` 组合为最终存储名。分桶设计目的、按天示例、`timestamp` 与 `creationTime` 兜底、以及写/查与 ES 建索引行为，见上文 **《storage-position 存储定位》**。

### `IgnoreOrDesensitizeResultHolder`

请求级字段处理上下文。默认策略是 `ThreadLocalIgnoreOrDesensitizeResultHolderStrategy`，可通过系统属性 `ignore.or.desensitize.result.holder.strategy` 指定自定义策略实现类。

常用方法：

- `setIgnoreProperties(List<String>)`
- `setDesensitizeProperties(List<String>)`
- `convert(Object)`
- `clear()`

### `SecurityPrincipal`

安全主体基础接口，继承 `Principal` 与 `BasicIdentification<Object>`，定义用户 id、用户名、凭证和账号状态判断方法。`SimpleSecurityPrincipal#getName()` 默认返回 `{id}:{username}`。

## 配置项速查

```yaml
loncra:
  framework:
    security:
      audit:
        enabled: true
        type: Memory # Memory / Elasticsearch / Mongo
        storage-position:
          prefix: audit_event
          separator: _
          spring-expression: []
      web:
        ignore-result-map: {}
        desensitize-result-map: {}
```

## 推荐实践

- 生产环境不建议使用 `Memory` 审计，内存仓库容量有限且应用重启后数据丢失。
- 审计 `data` 建议使用结构化 `Map`，避免后续 ES 条件查询只能做弱匹配。
- 多租户系统建议通过 `AuditEventRepositoryWriteInterceptor` 在写入前补充 `tenantId`、`applicationName`、`traceId` 等公共字段。
- 查询侧建议封装统一查询参数构造器，减少业务代码中散落 `filter_[data->xxx_eq]` 字符串。
- ES / Mongo 使用按日期拆分存储时，`storage-position.spring-expression` 应同时兼容写入事件和按 ID 查询对象，例如同时配置 `#timestamp` 与 `#creationTime`。
- 数据量增长快时，通过 `storage-position` 做**时间维度的索引/集合分桶**（如按天），可控制单索引或单集合体积、便于按日归档与检索；分桶规则变化前需评估历史数据与查询方式。
- 敏感字段建议在审计写入前先清洗，并在响应阶段继续使用忽略/脱敏机制作为补充保护。

## 注意事项

- `AuditType` 枚举值为 `Memory`、`Elasticsearch`、`Mongo`，建议配置时使用与枚举一致的写法。
- `spring-expression` 默认是空列表；当使用 Elasticsearch / Mongo 时，如果没有任何表达式能成功计算后缀，写入或查询会触发断言异常。`storage-position` 的设计说明见 **《storage-position 存储定位》** 一节。
- `ElasticsearchAuditEventRepository` 写入时会把自动生成的索引名转为小写；在按天等规则下，**落在某一索引名上的第一次写入**若该索引尚不存在，会通过 `createIndexIfNotExists` 创建；同一天后续写入该索引时不再重复创建。使用 `StoragePositioningAuditEvent` 手动指定存储位置时，需自行保证名称符合 ES 规则。
- `MongoAuditEventRepository` 当前只内置 `principal`、`type`、`timestamp` 条件构造；复杂业务过滤建议通过查询拦截器扩展。
- `IgnoreOrDesensitizeResultFilter` 的顺序为 `Ordered.HIGHEST_PRECEDENCE`，请求结束时会自动清理 ThreadLocal 上下文。
- `IgnoreOrDesensitizeResultHolder.convert(...)` 依赖 `commons` 中的 JsonPath 对象处理能力，配置表达式应与实际响应结构保持一致。
- `@Auditable` 和 `@Plugin` 当前只提供元数据声明，不会单独触发 AOP 审计或自动资源扫描；需要上层模块或业务系统消费这些注解。

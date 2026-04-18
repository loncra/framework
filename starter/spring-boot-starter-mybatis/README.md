# spring-boot-starter-mybatis

`spring-boot-starter-mybatis` 是 loncra framework 的 MyBatis 基础扩展模块，核心目标是：

- 增强 MyBatis 的类型映射能力（JSON、枚举）。
- 提供数据库写操作（INSERT/UPDATE/DELETE）的统一“数据留痕”拦截机制。
- 与 `spring-boot-starter-basic-security` 的审计体系打通，把数据库变更记录推送到 Spring 审计仓库。

如果你是新手，可以先理解一句话：  
这个模块负责“拦截并提取变更数据”，你只需要提供 `OperationDataTraceResolver`（或使用 MyBatis-Plus 的默认实现）即可自动产生日志审计事件。

## 模块作用

- 自动注册 `JacksonJsonCollectionPostInterceptor`，修复 JSON 集合泛型字段映射问题。
- 提供 `OperationDataTraceInterceptor`，拦截 MyBatis `Executor#update`。
- 抽象 `OperationDataTraceResolver`，定义“如何生成留痕记录、如何保存记录”。
- 提供基础实现 `AbstractOperationDataTraceResolver`，内置 SQL 类型识别与基本记录构建。
- 提供类型处理器：
  - `JacksonJsonTypeHandler<T>`：JSON 字段序列化/反序列化。
  - `NameValueEnumTypeHandler<E>`：`NameEnum` / `ValueEnum` 枚举持久化。

## 核心能力结构

- `io.github.loncra.framework.mybatis`
  - 自动配置：`MybatisAutoConfiguration`
- `config`
  - `OperationDataTraceProperties`
- `interceptor.audit`
  - `OperationDataTraceInterceptor`
  - `OperationDataTraceResolver`
  - `AbstractOperationDataTraceResolver`
  - `OperationDataTraceRecord`
- `interceptor.json`
  - `AbstractJsonCollectionPostInterceptor`
  - `support.JacksonJsonCollectionPostInterceptor`
- `handler`
  - `JacksonJsonTypeHandler`
  - `NameValueEnumTypeHandler`
- `enumerate`
  - `OperationDataType`（INSERT / UPDATE / DELETE）

## 快速开始

## 1. 引入依赖

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-mybatis</artifactId>
    <version>${framework.version}</version>
</dependency>
```

说明：

- 该模块已经依赖 `spring-boot-starter-basic-security`，可直接使用 Spring 审计体系。
- 若你使用 MyBatis-Plus，推荐再引入 `spring-boot-starter-mybatis-plus`，可开箱即用默认审计解析器。

## 2. 开启模块

```yaml
loncra:
  framework:
    mybatis:
      enabled: true
      operation-data-trace:
        enabled: true
```

默认行为：

- `loncra.framework.mybatis.enabled` 默认 `true`
- `loncra.framework.mybatis.operation-data-trace.enabled` 默认 `true`（但前提是容器中存在 `OperationDataTraceResolver`）

## 3. 配置留痕基础参数

```yaml
loncra:
  framework:
    mybatis:
      operation-data-trace:
        audit-prefix-name: OPERATION_DATA_AUDIT
        date-format: yyyy-MM-dd HH:mm:ss
        storage-position:
          prefix: operation_audit
          separator: _
          spring-expression:
            - "T(io.github.loncra.framework.commons.DateUtils).dateFormat(#creationTime,'yyyy_MM_dd')"
```

配置含义：

- `audit-prefix-name`：审计事件 `type` 的前缀。
- `date-format`：备注字段中的时间格式。
- `storage-position`：可选，开启后可为记录生成存储定位（供 `StoragePositioningAuditEvent` 使用）。

## 审计链路是怎么打通的

以一次 `updateById` 为例：

1. `OperationDataTraceInterceptor` 拦截 MyBatis `update(...)` 调用。
2. 判断影响行数 `> 0` 且 SQL 类型为 `INSERT/UPDATE/DELETE`。
3. 使用 JSqlParser 解析 SQL AST。
4. 调用 `OperationDataTraceResolver#createOperationDataTraceRecord(...)` 生成留痕记录。
5. 调用 `OperationDataTraceResolver#saveOperationDataTraceRecord(...)` 保存记录。
6. 在保存逻辑中，把记录转换为 `AuditEvent` / `IdAuditEvent` / `StoragePositioningAuditEvent`，发布为 `AuditApplicationEvent`，最终进入 Spring 的 `AuditEventRepository`。

这就是你提到的“数据库操作留痕 -> 推送 Spring 审计仓库”的完整路径。

## 两种接入方式（重点）

## 方式 A：纯 MyBatis（你自己实现 Resolver）

`spring-boot-starter-mybatis` 不内置具体 `OperationDataTraceResolver`，需要你实现。

```java
import config.io.github.loncra.framework.mybatis.OperationDataTraceProperties;
import audit.interceptor.io.github.loncra.framework.mybatis.AbstractOperationDataTraceResolver;
import audit.interceptor.io.github.loncra.framework.mybatis.OperationDataTraceRecord;
import audit.io.github.loncra.framework.security.IdAuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class DemoOperationDataTraceResolver extends AbstractOperationDataTraceResolver {

  private final ApplicationEventPublisher publisher;

  public DemoOperationDataTraceResolver(
          OperationDataTraceProperties properties,
          ApplicationEventPublisher publisher
  ) {
    super(properties);
    this.publisher = publisher;
  }

  @Override
  public void saveOperationDataTraceRecord(List<OperationDataTraceRecord> records) {
    for (OperationDataTraceRecord record : records) {
      Map<String, Object> data = new LinkedHashMap<>();
      data.put(OperationDataTraceRecord.SUBMIT_DATA_FIELD, record.getSubmitData());
      data.put(OperationDataTraceRecord.REMARK_FIELD, record.getRemark());

      IdAuditEvent event = new IdAuditEvent(
              UUID.randomUUID().toString(),
              record.getCreationTime(),
              String.valueOf(record.getPrincipal()),
              getOperationDataTraceProperties().getAuditPrefixName()
                      + "_"
                      + record.getTarget()
                      + "_"
                      + record.getType(),
              data
      );
      publisher.publishEvent(new AuditApplicationEvent(event));
    }
  }
}
```

你也可以在 `saveOperationDataTraceRecord` 中，根据 `record.getStoragePositioning()` 组装 `StoragePositioningAuditEvent`，实现分索引/分集合审计落库。

## 方式 B：MyBatis-Plus（推荐）

如果引入 `spring-boot-starter-mybatis-plus`，框架会自动注入 `MybatisPlusOperationDataTraceResolver`（当你没有自定义 `OperationDataTraceResolver` 时）。

这个默认实现已经帮你完成：

- 解析实体 ID（包括 `Wrapper` 条件里的 ID）。
- 生成 `OperationDataTraceRecord` / `EntityIdOperationDataTraceRecord`。
- 构造审计事件并发布：
  - 普通：`IdAuditEvent`
  - 带存储定位：`IdStoragePositioningAuditEvent`（内部包含 `StoragePositioningAuditEvent`）
- 自动发布到 Spring `AuditEventRepository`。

## 常见用法

## 1. JSON 字段映射（`JacksonJsonTypeHandler`）

```java
@TableField(typeHandler = JacksonJsonTypeHandler.class)
private List<DeviceInfo> devices;
```

当你的 JSON 列是集合且存在泛型擦除问题，可配合 `@JsonCollectionGenericType` 使用。

## 1.1 `@JsonCollectionGenericType`（重点）

这个注解用于解决一个非常常见的问题：

- 实体字段是 `List<T>` / `Set<T>`，数据库里存的是 JSON。
- 查询出来后，Jackson 在缺失泛型上下文时，常把元素反序列化成 `Map`，导致你实际拿到的是 `List<Map>`。
- 后续业务代码一旦按 `List<T>` 使用，就容易出现类型转换问题。

`@JsonCollectionGenericType` 的作用就是：  
告诉框架“这个集合里每个元素的真实类型是什么”，然后在 `JacksonJsonCollectionPostInterceptor` 中二次强制转换为目标类型集合。

执行机制（基于 `JacksonJsonCollectionPostInterceptor`）：

1. MyBatis 查询返回对象后，拦截器扫描结果对象属性。
2. 找到标注了 `@JsonCollectionGenericType` 的字段或 getter。
3. 读取注解 `value()` 作为目标元素类型。
4. 将当前集合值（可能是 `List<Map>`）转换为 `List<目标类型>` / `Set<目标类型>`。
5. 回写到实体属性中，业务层拿到的就是强类型集合。

### 使用示例（普通对象）

```java
import annotation.io.github.opc.studio.framework.commons.JsonCollectionGenericType;
import handler.io.github.loncra.framework.mybatis.JacksonJsonTypeHandler;

public class UserEntity {

  @JsonCollectionGenericType(DeviceInfo.class)
  @TableField(typeHandler = JacksonJsonTypeHandler.class)
  private List<DeviceInfo> devices;

  // getter / setter
}
```

### 使用示例（枚举集合）

```java
import annotation.io.github.opc.studio.framework.commons.JsonCollectionGenericType;
import handler.io.github.loncra.framework.mybatis.JacksonJsonTypeHandler;

public class UserEntity {

  @JsonCollectionGenericType(ExecuteStatus.class)
  @TableField(typeHandler = JacksonJsonTypeHandler.class)
  private List<ExecuteStatus> executes;
}
```

对于 `ValueEnum` / `NameEnum` 类型，拦截器会结合 `NameValueEnumTypeHandler` 做值到枚举的转换，避免你手工遍历转型。

## 2. 枚举字段映射（`NameValueEnumTypeHandler`）

```java
@TableField(typeHandler = NameValueEnumTypeHandler.class)
private DisabledOrEnabled status;
```

支持 `ValueEnum` / `NameEnum` 自动转换，避免手写重复映射逻辑。

## 3. 自定义留痕记录结构

你可以继承 `OperationDataTraceRecord` 增加业务字段（租户、链路、请求来源等），再在 Resolver 中转换为审计事件 data。

## 关键接口说明

## 1. `OperationDataTraceInterceptor`

- 拦截 `Executor#update`。
- 仅处理 `INSERT/UPDATE/DELETE` 且影响行数 `> 0` 的 SQL。
- SQL 会先做清洗，再交给 JSqlParser 解析。

## 2. `OperationDataTraceResolver`

- `createOperationDataTraceRecord(...)`：把 SQL + 参数转换为留痕记录。
- `saveOperationDataTraceRecord(...)`：把记录保存或转发（常见是发布审计事件）。

## 3. `AbstractOperationDataTraceResolver`

- 提供通用 INSERT/UPDATE/DELETE 记录构建逻辑。
- 默认把参数对象转成 `Map` 放入 `submitData`。
- 可选启用 `storagePosition` 动态生成 `storagePositioning`。

## 4. `OperationDataTraceRecord`

核心字段：

- `creationTime`：创建时间
- `principal`：操作者（默认是本机 IP）
- `target`：目标表名
- `type`：操作类型（INSERT / UPDATE / DELETE）
- `submitData`：提交数据快照
- `storagePositioning`：存储定位（可选）
- `remark`：备注

## 配置项速查

```yaml
loncra:
  framework:
    mybatis:
      enabled: true
      operation-data-trace:
        enabled: true
        audit-prefix-name: OPERATION_DATA_AUDIT
        date-format: yyyy-MM-dd HH:mm:ss
        storage-position:
          prefix: operation_audit
          separator: _
          spring-expression: []
```

## 与 basic-security 的关系

- `basic-security` 提供统一审计事件模型与审计仓库抽象（如 `IdAuditEvent`、`StoragePositioningAuditEvent`、`ExtendAuditEventRepository`）。
- `mybatis` 负责在数据库写操作点提取“操作数据留痕”。
- `mybatis-plus` 的默认 Resolver 把留痕记录转换为审计事件，并通过 Spring 事件机制推送到审计仓库。

可理解为：

- `mybatis` = 留痕拦截框架
- `basic-security` = 审计存储框架
- `mybatis-plus` = 两者的默认落地桥接实现

## 扩展建议

- 生产环境建议重写 `principal` 获取逻辑，优先使用当前登录用户，而不是机器 IP。
- 建议在 `submitData` 中移除敏感字段（密码、密钥、身份证号）。
- 建议为不同业务线设置不同 `audit-prefix-name`，便于查询与统计。
- 若 ES/Mongo 作为审计存储，建议开启 `storage-position` 做按日分片。

## 注意事项

- 只有注册了 `OperationDataTraceResolver`，`OperationDataTraceInterceptor` 才会生效。
- 影响行数 `<= 0` 的 SQL 不会触发留痕。
- 非 `INSERT/UPDATE/DELETE`（例如 `SELECT`）不会触发留痕。
- SQL 解析依赖 `jsqlparser`，复杂 SQL 场景建议在自定义 Resolver 中做兜底处理。
- 模块自动装配入口：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` -> `io.github.loncra.framework.mybatis.MybatisAutoConfiguration`。


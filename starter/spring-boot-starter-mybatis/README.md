# spring-boot-starter-mybatis

`spring-boot-starter-mybatis` 是 loncra framework 对 **MyBatis 3** 的薄封装：在**不**替代你选用 `mybatis-spring-boot-starter` / 自建 `SqlSessionFactory` 的前提下，提供 **TypeHandler**（JSON、按名/值枚举）、**ResultSet 后处理拦截器**（解决 JSON 集合**泛型擦除**问题），以及**写操作**（`INSERT/UPDATE/DELETE`）的 **JSQLParser** 级留痕**拦截器** + **可插拔的 `OperationDataTraceRepository` 契约**。

> **依赖关系**：`JsonCollectionGenericType` 等注解在 **`commons`** 包；**存储定位**相关（`StoragePositionProperties`、`SpringElStoragePositioningGenerator`）在 **`spring-boot-starter-basic-security`（传递依赖）**；`AbstractOperationDataTraceRepository` 直接依赖 `security` 的审计/定位类。`OperationDataTraceProperties` **未**在 `MybatisAutoConfiguration` 中 `Enable`；若**仅**使用本模块 + 手写 `Repository`，需在应用或配置类上 **`@EnableConfigurationProperties(io.github.loncra.framework.mybatis.config.OperationDataTraceProperties.class)`**。

> **与 MyBatis-Plus**：默认注册 `MybatisPlusOperationDataTraceRepository` 的是 `spring-boot-starter-mybatis-plus`，**不是**本 JAR。纯 MyBatis 下需**自行实现** `OperationDataTraceRepository`（可继承 `AbstractOperationDataTraceRepository`）。

## 模块定位

| 能力 | 作用 |
| --- | --- |
| `JacksonJsonTypeHandler` | 以 **Jackson**（`CastUtils.getObjectMapper()`）读写 JSON 列；`Collection` 时优先把 `NameEnum` / `ValueEnum` 元素**展开**为**可存库**的标量列表再 `writeValueAsString`，否则整体序列化 |
| `NameValueEnumTypeHandler` | 基于 `ValueEnum` / `NameEnum` 的 DB 值与 Java 枚举互转；读库走 `ValueEnum.ofEnum` / `NameEnum.ofEnum`；写库在 `jdbcType` 为 null 时对标量做字符串或 ordinal 等分支（见源码） |
| `AbstractJsonCollectionPostInterceptor` / `JacksonJsonCollectionPostInterceptor` | 在 `Executor#query` 返回**之后**，对「JSON 列 → `List`/`Set`」常见的 **`List<Map>` 误当 `List<POJO>`** 问题做二次映射；详见下文《`@JsonCollectionGenericType`：要解决什么问题》 |
| `OperationDataTraceInterceptor` | 在 `Executor#update` 执行**成功**后：仅当**返回为 `Integer` 且 &gt; 0** 且为 `INSERT`/`UPDATE`/`DELETE` 时，解析 SQL、生成 `OperationDataTraceRecord` 列表；再按表名对每条记录调用匹配 Hook 的 **`preSaveOperationDataTraceRecord`**，最后 **`saveOperationDataTraceRecord`** |
| `AbstractOperationDataTraceRepository` | 按 `Insert/Update/Delete` 生成 `OperationDataTraceRecord`：业务字段在嵌套 **`OperationDataTraceMetadata`**（`target`＝表名、`type`、`data`＝参数 Map、可选 `remark`）；`principal` 缺省为本机 IP。**框架不在底层自动填充 `remark`**。若配置 `OperationDataTraceProperties#storagePosition` 则通过 `SpringElStoragePositioningGenerator` 为每条**基础**记录**再**生成带 `storagePositioning` 的**复制**行（基础行 + 复制行均进入 `save`，见下文《storagePosition 分桶》）。构造可传入 `List<OperationDataTraceRecordHook>`，或仅 `OperationDataTraceProperties` 单参构造（等价于 Hook 列表**为空**） |
| `OperationDataTraceRecordHook` | **可选扩展**：构建阶段 `preCreate…` / `postCreate…` 对命中表名**各取第一个** Hook（`findFirst`）；**保存前** `preSave…` 在 `OperationDataTraceInterceptor` 里、**写库成功后**且调用 `saveOperationDataTraceRecord` **之前**触发，**同一表**上所有 `isSupport` 为 true 的 Hook **全部**执行，且对同表每条记录各调用一次 |

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-mybatis</artifactId>
    <version>${framework.version}</version>
</dependency>
```

| 依赖 | 作用 |
| --- | --- |
| `commons` | `CastUtils`、`@JsonCollectionGenericType`、异常与枚举基类等 |
| `mybatis` 3.5.16、`mybatis-spring` 3.0.4 | 见上文：不含 `mybatis-spring-boot-starter`，需自行提供 `SqlSessionFactory` 与 `Interceptor` 挂链方式 |
| `spring-boot-starter` | 自动配置基础设施 |
| `spring-boot-starter-basic-security` | 仓库对 `StoragePositioningGenerator` 的依赖等 |
| `jsqlparser` 5.0 | 留痕拦截中 `CCJSqlParserUtil.parse` |

## 自动配置

- 入口：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 仅登记 `io.github.loncra.framework.mybatis.MybatisAutoConfiguration`。
- 开关：`loncra.framework.mybatis.enabled`（`matchIfMissing` 为 `true`）。
- 本类 `@Bean`：
  - 始终：Bean 名 `jacksonCollectionPostInterceptor`，类型为 `JacksonJsonCollectionPostInterceptor`。
  - 当容器中存在 `OperationDataTraceRepository` 且 `loncra.framework.mybatis.operation-data-trace.enabled` 为 `true`（**默认**）时：再注册 `OperationDataTraceInterceptor`。
- **不会**注册**默认的** `OperationDataTraceRepository`，**也不会**对该模块单独 `@EnableConfigurationProperties(OperationDataTraceProperties.class)`。
- 若**额外**引入 `spring-boot-starter-mybatis-plus`：其 `MybatisPlusAutoConfiguration` 在「**无**自定义 `OperationDataTraceRepository`」时注册 `MybatisPlusOperationDataTraceRepository`，并 `@EnableConfigurationProperties` 包含 `OperationDataTraceProperties`，在 MP 的开关为 `true` 时，留痕仓库**开箱**可用。

## 包结构（概要）

| 位置 | 说明 |
| --- | --- |
| `MybatisAutoConfiguration` | 上节 |
| `config/OperationDataTraceProperties` | 前缀 `loncra.framework.mybatis.operation-data-trace`：`auditPrefixName`、`storagePosition`（`StoragePositionProperties` 见 security 模块） |
| `domain/metadata/OperationDataTraceMetadata` | 留痕业务载荷：`target`、`type`、`data`、`remark`；常量 `OPERATION_DATA_TRACE_DATA_FIELD` = `"operationTrace"` |
| `interceptor/audit` | `OperationDataTraceInterceptor`、`OperationDataTraceRepository`、`AbstractOperationDataTraceRepository`、`OperationDataTraceRecord`、`OperationDataTraceRecordHook` |
| `interceptor/json`、`support` | `AbstractJsonCollectionPostInterceptor`；`JacksonJsonCollectionPostInterceptor` 在 `@Intercepts` 中固定 `Executor#query` 四参签名 |
| `handler` | `JacksonJsonTypeHandler`、`NameValueEnumTypeHandler` |
| `enumerate/OperationDataType` | 与 `INSERT`/`UPDATE`/`DELETE` 对应的中文名等 |

## 写操作留痕：链路与实现细节

1. 插件拦截 `Executor#update(MappedStatement, Object)`，执行 `proceed` 得 `result`。
2. **若** `!Integer.class.isAssignableFrom(result.getClass())`，**原样返回**不解析（**注意**：与驱动/链路上游返回类型**不一致**时可能**整段**不生效）。
3. 若 `(Integer) result <= 0`，不处理。
4. 根据 `MappedStatement#getSqlCommandType` 过滤，仅 `INSERT` / `UPDATE` / `DELETE`。
5. `getBoundSql` 取可执行 SQL，以正则 `REMOVE_ESCAPE_REG`（`\\.|\\n|\\t`）**替换**为**空格**后 `CCJSqlParserUtil.parse`。
6. 调用 `createOperationDataTraceRecord(mappedStatement, statement, parameter)`。
7. **非**空时：按 **`record.getData().getTarget()`（表名）** 分组，对所有 **`isSupport(表名)`** 的 `OperationDataTraceRecordHook` 依次调用 **`preSaveOperationDataTraceRecord`**；再调用 `saveOperationDataTraceRecord(records)`。

`AbstractOperationDataTraceRepository` 对 `Insert/Update/Delete` 仅取 `getTable().getName()` 作为 **metadata.target**；**metadata.data** 为全参转 `Map`。`principal` 为 `InetAddress.getLocalHost().getHostAddress()`。若需**用户身份**或**与 HTTP 审计合并**，应使用 `MybatisPlusOperationDataTraceRepository`、`SecurityPrincipalOperationDataTraceRepository`（见 mybatis-plus / spring-security-core）。

### `OperationDataTraceRecord` 与 `OperationDataTraceMetadata`

| 层级 | 字段 | 说明 |
| --- | --- | --- |
| `OperationDataTraceRecord` | `creationTime`、`principal`、`storagePositioning` | 审计「信封」 |
| `OperationDataTraceRecord.data` | `OperationDataTraceMetadata` | 业务载荷 |
| `OperationDataTraceMetadata` | `target`、`type`、`data`、`remark` | 表名、操作类型、参数 Map、**可选**备注（框架不自动填） |

### storagePosition 分桶

配置 `operation-data-trace.storage-position` 后：

1. 每条**基础** record 经 `SpringElStoragePositioningGenerator.generatePositioning(record)` 预计算分桶名，克隆为带 `storagePositioning` 的**复制行**。
2. 返回列表 = **基础行 + 复制行**（均为预期，会各发一次审计事件）。
3. **复制行**：`save` 时包装为 `StoragePositioningAuditEvent`，ES/Mongo **直接使用** `storagePositioning` 作为索引/集合名。
4. **基础行**：无 `storagePositioning`，落库时由 **`loncra.framework.security.audit.storage-position`** 对 `IdAuditEvent` 再算默认桶。

两套 `storage-position` 的 SpEL 变量可能不同（如 record 上 `#creationTime`、审计事件上 `#timestamp`），业务侧应对齐表达式。详见 `spring-boot-starter-basic-security` README《storage-position 存储定位》。

### 留痕记录钩子：`OperationDataTraceRecordHook`

| 阶段 | 调用时机 | 与多个 Bean 的关系 |
|------|----------|-------------------|
| `preCreateOperationDataTraceRecord` | `AbstractOperationDataTraceRepository#createBasicOperationDataTraceRecord` 内，填充 Metadata 之前 | 对 `data` Map **每个方法各自** `findFirst` |
| `postCreateOperationDataTraceRecord` | 默认 `principal` 等填完后、storagePosition 复制与 `save` 之前 | 同上 |
| `preSaveOperationDataTraceRecord` | 数据库 update 已成功且已生成完整 `records` 后、`saveOperationDataTraceRecord` **之前** | **所有** `isSupport(表名)` 的 Hook **都会**执行 |

- **纯 MyBatis**：自定义 `AbstractOperationDataTraceRepository` 子类时，向父类构造传入 `List<OperationDataTraceRecordHook>`。
- **MyBatis-Plus / Security-Core**：`ObjectProvider<OperationDataTraceRecordHook>` 自动聚合 Bean；`OperationDataTraceRepository#getOperationDataTraceRecordHooks()` 供拦截器调用 `preSave`。

## 迁移说明（Resolver → Repository / 扁平 Record → Metadata）

| 旧 | 新 |
| --- | --- |
| `OperationDataTraceResolver` | `OperationDataTraceRepository` |
| `AbstractOperationDataTraceResolver` | `AbstractOperationDataTraceRepository` |
| `MybatisPlusOperationDataTraceResolver` | `MybatisPlusOperationDataTraceRepository` |
| `SecurityPrincipalOperationDataTraceResolver` | `SecurityPrincipalOperationDataTraceRepository` |
| `record.getTarget()` / `getSubmitData()` / `getRemark()` | `record.getData().getTarget()` / `getData()` / `getRemark()` |
| `EntityIdOperationDataTraceRecord` | `EntityIdOperationDataTraceMetadata`（extends `OperationDataTraceMetadata` + `id`） |
| `AuditEvent.data` 键 `submitData` / `SUBMIT_DATA_FIELD` | `operationTrace` / `OperationDataTraceMetadata.OPERATION_DATA_TRACE_DATA_FIELD` |
| 框架自动 `remark`（IP+时间+操作名） | **已移除**；由 `@Auditable` / `@OperationDataTrace` 或 Hook 填写 |

## 配置项速查

### `loncra.framework.mybatis`

| 属性 | 说明 | 默认 |
| --- | --- | --- |
| `enabled` | 是否注册本模块自动配置中的 Bean | `true` |

### `loncra.framework.mybatis.operation-data-trace`

| 属性 | 说明 | 默认 |
| --- | --- | --- |
| `enabled` | 是否注册 `OperationDataTraceInterceptor`（仍要求容器内已存在 `OperationDataTraceRepository`） | `true` |
| `audit-prefix-name` | 与审计事件 type 拼接的业务前缀（MP 实现中常用） | `OPERATION_DATA_AUDIT` |
| `storage-position` | 非空时对每条基础 record SpEL 生成 `storagePositioning` **复制行** | 可选 |
| `date-format` | `OperationDataTraceProperties` 上存在该字段，**当前留痕链路未读取**；配置无效，请勿依赖 | 默认 `yyyy-MM-dd HH:mm:ss` |

## `@JsonCollectionGenericType`：要解决什么问题

在**持久化实体**里，常见模式是：Java 字段写成 `List<某个业务类型>`（或 `Set<…>`），数据库里对应一列 **JSON** 文本；读写时通过 `JacksonJsonTypeHandler`（或等价方式）把列与字段绑在一起。

**问题出在「反序列化后的真实类型」与「源码里写的泛型」不一致：**

- 源码里你写的是 `List<DeviceInfo> devices` 这类**强类型**声明。
- 从 JDBC 读出的是字符串，经 Jackson 反序列化成集合时，在**缺少完整泛型上下文**（或 TypeHandler 只按「外层」类型处理）的情况下，运行时集合里每个元素往往是 **`LinkedHashMap`**（或其它 `Map`），整体类型表现为 **`List<Map<String,Object>>`**，而**不是** `List<DeviceInfo>`。
- MyBatis 把结果填进实体字段时，**引用**可以塞进 `List` 字段里，但**元素**仍是 `Map`。编译期看是「`List<DeviceInfo>`」，运行期却是「`List` 里装着 `Map`」。
- 业务代码在拿到实体后若写：`devices.get(0).getSerialNumber()`，会在运行时报错，因为 `get(0)` 实际返回的是 **`Map`**，不是 `DeviceInfo`。

**`@JsonCollectionGenericType` 的作用**：在查询结果映射完成后，把 `Map` 转成注解 `value()` 声明的元素类型。

- 注解在 **`commons`**：`io.github.loncra.framework.commons.annotation.JsonCollectionGenericType`。
- **`JacksonJsonCollectionPostInterceptor`** 在 `Executor#query` 返回后扫描带注解的属性并 `convertValue` 写回实体。

## 在 Mapper / 实体上使用 TypeHandler

- **纯 MyBatis**：在 `@Result`、Mapper XML 的 `typeHandler`，或**全局** `type-handlers-package` 中注册 handler。
- **MyBatis-Plus**：`@TableField(typeHandler = ...)`。

```java
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.mybatis.handler.JacksonJsonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;

public class UserEntity {

  @JsonCollectionGenericType(DeviceInfo.class)
  @TableField(typeHandler = JacksonJsonTypeHandler.class)
  private List<DeviceInfo> devices;
}
```

## 纯 MyBatis 实现 `OperationDataTraceRepository`

```java
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OperationDataTraceProperties.class)
/* @Component
public class DemoOperationDataTraceRepository extends AbstractOperationDataTraceRepository {
    // super(properties) 或 super(properties, hookList)
    // 实现 saveOperationDataTraceRecord：发布 AuditApplicationEvent 或写入审计仓库
} */
```

## 与 `spring-boot-starter-mybatis-plus` 的衔接

- 引入 MP 时在 `@ConditionalOnMissingBean(OperationDataTraceRepository.class)` 下**自动**注册 `MybatisPlusOperationDataTraceRepository`。
- 该实现发布 Spring 审计事件、解析实体与 `Wrapper`、使用 `EntityIdOperationDataTraceMetadata`；细节见 mybatis-plus 子模块 README。

## 注意事项

- 无 `OperationDataTraceRepository` **Bean** → 无 `OperationDataTraceInterceptor` → 无写操作自动留痕；`JacksonJsonCollectionPostInterceptor` **仍**会注册。
- **JSQLParser** 无法解析的 SQL 会在 `parse` 时**抛错**；复杂 SQL 宜自定义兜底或关闭 `operation-data-trace.enabled`。
- 纯 MyBatis + `AbstractOperationDataTraceRepository` 时需 **`@EnableConfigurationProperties(OperationDataTraceProperties.class)`**（MP 自动配置会代启用）。
- 生产环境建议用 Security 子类或 Hook 将 `principal` 换为登录用户，并对 `record.getData().getData()` 脱敏。

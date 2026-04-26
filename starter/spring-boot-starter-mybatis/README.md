# spring-boot-starter-mybatis

`spring-boot-starter-mybatis` 是 loncra framework 对 **MyBatis 3** 的薄封装：在**不**替代你选用 `mybatis-spring-boot-starter` / 自建 `SqlSessionFactory` 的前提下，提供 **TypeHandler**（JSON、按名/值枚举）、**ResultSet 后处理拦截器**（解决 JSON 集合**泛型擦除**问题），以及**写操作**（`INSERT/UPDATE/DELETE`）的 **JSQLParser** 级留痕**拦截器** + **可插拔的 `OperationDataTraceResolver` 契约**。

> **依赖关系**：`JsonCollectionGenericType` 等注解在 **`commons`** 包；**存储定位**相关（`StoragePositionProperties`、`SpringElStoragePositioningGenerator`）在 **`spring-boot-starter-basic-security`（传递依赖）**；`AbstractOperationDataTraceResolver` 直接依赖 `security` 的审计/定位类。`OperationDataTraceProperties` **未**在 `MybatisAutoConfiguration` 中 `Enable`；若**仅**使用本模块 + 手写 `Resolver`，需在应用或配置类上 **`@EnableConfigurationProperties(io.github.loncra.framework.mybatis.config.OperationDataTraceProperties.class)`**。

> **与 MyBatis-Plus**：默认注册 `MybatisPlusOperationDataTraceResolver` 的是 `spring-boot-starter-mybatis-plus`，**不是**本 JAR。纯 MyBatis 下需**自行实现** `OperationDataTraceResolver`（可继承 `AbstractOperationDataTraceResolver`）。

## 模块定位

| 能力 | 作用 |
| --- | --- |
| `JacksonJsonTypeHandler` | 以 **Jackson**（`CastUtils.getObjectMapper()`）读写 JSON 列；`Collection` 时优先把 `NameEnum` / `ValueEnum` 元素**展开**为**可存库**的标量列表再 `writeValueAsString`，否则整体序列化 |
| `NameValueEnumTypeHandler` | 基于 `ValueEnum` / `NameEnum` 的 DB 值与 Java 枚举互转；读库走 `ValueEnum.ofEnum` / `NameEnum.ofEnum`；写库在 `jdbcType` 为 null 时对标量做字符串或 ordinal 等分支（见源码） |
| `AbstractJsonCollectionPostInterceptor` / `JacksonJsonCollectionPostInterceptor` | 在 `Executor#query` 返回**之后**，对「JSON 列 → `List`/`Set`」常见的 **`List<Map>` 误当 `List<POJO>`** 问题做二次映射；详见下文《`@JsonCollectionGenericType`：要解决什么问题》 |
| `OperationDataTraceInterceptor` | 在 `Executor#update` 执行**成功**后：仅当**返回为 `Integer` 且 &gt; 0** 且为 `INSERT`/`UPDATE`/`DELETE` 时，清洗 SQL 字符串后用 **JSqlParser** 建 AST，交给 `OperationDataTraceResolver` 生成/保存 `OperationDataTraceRecord` 列表 |
| `AbstractOperationDataTraceResolver` | 按 `Insert/Update/Delete` 生成 `OperationDataTraceRecord`：`target`＝表名；`submitData`＝`CastUtils` 将参数转 `Map`；`principal` 缺省为本机 IP；`remark` 为「IP + 时间 + 操作中文名」。若配置 `OperationDataTraceProperties#storagePosition` 则通过 `SpringElStoragePositioningGenerator` 为每条**基础**记录**再**生成**带** `storagePositioning` 的**复制**行 |

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
| `spring-boot-starter-basic-security` | 解析器对 `StoragePositioningGenerator` 的依赖等 |
| `jsqlparser` 5.0 | 留痕拦截中 `CCJSqlParserUtil.parse` |

## 自动配置

- 入口：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 仅登记 `io.github.loncra.framework.mybatis.MybatisAutoConfiguration`。
- 开关：`loncra.framework.mybatis.enabled`（`matchIfMissing` 为 `true`）。
- 本类 `@Bean`：
  - 始终：Bean 名 `jacksonCollectionPostInterceptor`，类型为 `JacksonJsonCollectionPostInterceptor`。
  - 当容器中存在 `OperationDataTraceResolver` 且 `loncra.framework.mybatis.operation-data-trace.enabled` 为 `true`（**默认**）时：再注册 `OperationDataTraceInterceptor`。
- **不会**注册**默认的** `OperationDataTraceResolver`，**也不会**对该模块单独 `@EnableConfigurationProperties(OperationDataTraceProperties.class)`。
- 若**额外**引入 `spring-boot-starter-mybatis-plus`：其 `MybatisPlusAutoConfiguration` 在「**无**自定义 `OperationDataTraceResolver`」时注册 `MybatisPlusOperationDataTraceResolver`，并 `@EnableConfigurationProperties` 包含 `OperationDataTraceProperties`，在 MP 的开关为 `true` 时，留痕解析器**开箱**可用。

## 包结构（概要）

| 位置 | 说明 |
| --- | --- |
| `MybatisAutoConfiguration` | 上节 |
| `config/OperationDataTraceProperties` | 前缀 `loncra.framework.mybatis.operation-data-trace`：`auditPrefixName`、`dateFormat`、`storagePosition`（`StoragePositionProperties` 见 security 模块） |
| `interceptor/audit` | `OperationDataTraceInterceptor`、`OperationDataTraceResolver`、`AbstractOperationDataTraceResolver`、`OperationDataTraceRecord` |
| `interceptor/json`、`support` | `AbstractJsonCollectionPostInterceptor`；`JacksonJsonCollectionPostInterceptor` 在 `@Intercepts` 中固定 `Executor#query` 四参签名 |
| `handler` | `JacksonJsonTypeHandler`、`NameValueEnumTypeHandler` |
| `enumerate/OperationDataType` | 与 `INSERT`/`UPDATE`/`DELETE` 对应的中文名等，供 `remark` 等使用 |

## 写操作留痕：链路与实现细节

1. 插件拦截 `Executor#update(MappedStatement, Object)`，执行 `proceed` 得 `result`。
2. **若** `!Integer.class.isAssignableFrom(result.getClass())`，**原样返回**不解析（**注意**：与驱动/链路上游返回类型**不一致**时可能**整段**不生效）。
3. 若 `(Integer) result <= 0`，不处理。
4. 根据 `MappedStatement#getSqlCommandType` 过滤，仅 `INSERT` / `UPDATE` / `DELETE`。
5. `getBoundSql` 取可执行 SQL，以正则 `REMOVE_ESCAPE_REG`（`\\.|\\n|\\t`）**替换**为**空格**后 `CCJSqlParserUtil.parse`。
6. 调用 `createOperationDataTraceRecord(mappedStatement, statement, parameter)`；**非**空时 `saveOperationDataTraceRecord`。

`AbstractOperationDataTraceResolver` 对 `Insert/Update/Delete` 仅取 `getTable().getName()` 作为**表名**；`submitData` 为**全参**转 `Map`（复杂 Mapper 入参**结构**以 `CastUtils` 实际行为为准）。`principal` 为 `InetAddress.getLocalHost().getHostAddress()`。若需**用户身份**，应**在子类**覆盖记录构造，或**换用** `MybatisPlusOperationDataTraceResolver`、安全子类增强解析器（见 `spring-boot-starter-spring-security-core` 等模块）。

## 配置项速查

### `loncra.framework.mybatis`

| 属性 | 说明 | 默认 |
| --- | --- | --- |
| `enabled` | 是否注册本模块自动配置中的 Bean | `true` |

### `loncra.framework.mybatis.operation-data-trace`

| 属性 | 说明 | 默认 |
| --- | --- | --- |
| `enabled` | 是否注册 `OperationDataTraceInterceptor`（仍要求容器内已存在 `OperationDataTraceResolver`） | `true` |
| `audit-prefix-name` | 与审计事件类型等拼接的**业务前缀**（在 MP 等实现中常用） | `OPERATION_DATA_AUDIT` |
| `dateFormat` | 抽象类中 `SimpleDateFormat` 使用的模式 | 与 `DateUtils.DEFAULT_DATE_TIME_FORMATTER_PATTERN` 默认一致 |
| `storagePosition` | 非空时创建 `SpringElStoragePositioningGenerator`；`record` 作为 SpEL 根，生成 `storagePositioning` **额外**记录行 | 可选 |

## `@JsonCollectionGenericType`：要解决什么问题

在**持久化实体**里，常见模式是：Java 字段写成 `List<某个业务类型>`（或 `Set<…>`），数据库里对应一列 **JSON** 文本；读写时通过 `JacksonJsonTypeHandler`（或等价方式）把列与字段绑在一起。

**问题出在「反序列化后的真实类型」与「源码里写的泛型」不一致：**

- 源码里你写的是 `List<DeviceInfo> devices` 这类**强类型**声明。
- 从 JDBC 读出的是字符串，经 Jackson 反序列化成集合时，在**缺少完整泛型上下文**（或 TypeHandler 只按「外层」类型处理）的情况下，运行时集合里每个元素往往是 **`LinkedHashMap`**（或其它 `Map`），整体类型表现为 **`List<Map<String,Object>>`**，而**不是** `List<DeviceInfo>`。
- MyBatis 把结果填进实体字段时，**引用**可以塞进 `List` 字段里，但**元素**仍是 `Map`。编译期看是「`List<DeviceInfo>`」，运行期却是「`List` 里装着 `Map`」。
- 业务代码在拿到实体后若写：`devices.get(0).getSerialNumber()`（或任意只在 `DeviceInfo` 上存在的方法），会在运行时报错（典型为 **`ClassCastException`**，或表现为「`Map` 上没有该方法」），因为 `get(0)` 实际返回的是 **`Map`**，不是 `DeviceInfo`。

**`@JsonCollectionGenericType` 的作用就是：在框架里把「集合元素应该是哪种具体类型」说清楚，并在查询结果映射完成后，再做一步「把 `Map`（或中间形态）转成真正的元素类型」**，使运行期集合里的对象**确实是**你在注解里声明的类型，业务层按 `List<业务类型>` 使用字段、调用方法时**与编译期一致**，不再踩 `Map` 陷阱。

- 注解定义在 **`commons`**：`io.github.loncra.framework.commons.annotation.JsonCollectionGenericType`，成员 **`value()`** 即**集合元素**的 Class（例如 `DeviceInfo.class`）。
- **`JacksonJsonCollectionPostInterceptor`**（挂在 `Executor#query` 上）在**一次查询执行完毕、得到结果对象之后**，扫描 `ResultMap` 对应实体上、标注了 `@JsonCollectionGenericType` 的**可读属性**（字段或 getter 上的注解均可）；若元素类型是 `ValueEnum` / `NameEnum`，则与 `NameValueEnumTypeHandler` 的取值逻辑配合转成枚举；否则用 Jackson 的 **`CollectionType`** 对当前集合做一次 **`convertValue`**，把元素从 `Map` 等**还原**成 `value()` 指定的类型，再**写回**实体属性（优先 setter，无 setter 则写字段）。
- **只作用**在**本次查询的返回值为 `Collection`** 的场景：若 Mapper 返回的是**单条**实体（非 `List`），拦截器首行即不进入集合后处理逻辑。
- 集合字段在源码中须声明为 **`List` 或 `Set`**；其它 `Collection` 子类会按源码抛 `SystemException`（见 `JacksonJsonCollectionPostInterceptor#doMappingResult`）。

## 在 Mapper / 实体上使用 TypeHandler

- **纯 MyBatis**：在 `@Result`、Mapper XML 的 `typeHandler`，或**全局** `type-handlers-package` 中注册 `JacksonJsonTypeHandler` 或 `NameValueEnumTypeHandler`（`JacksonJsonTypeHandler(Class<T> type)` 的泛型在 MyBatis 3 中常通过**子类**或**注册工厂**指定，与项目既有 TypeHandler 写法**一致**即可）。
- **MyBatis-Plus**：可用 `@TableField(typeHandler = ...)`（见 MP 文档）。

**示例**（**MP** 注解，仅作**风格**说明；**包名**以**实际**类为准）：

```java
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.mybatis.handler.JacksonJsonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField; // 使用 MP 时

public class UserEntity {

  @JsonCollectionGenericType(DeviceInfo.class)
  @TableField(typeHandler = JacksonJsonTypeHandler.class) // 仅 MyBatis-Plus
  private List<DeviceInfo> devices;
}
```

## 纯 MyBatis 实现 `OperationDataTraceResolver`（摘抄需对齐 import）

```java
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceResolver;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OperationDataTraceProperties.class)
/* @Component
public class DemoOperationDataTraceResolver extends AbstractOperationDataTraceResolver { ... } */
// 在 @Component 类中：构造器 super(properties)；实现 save... 中发布 AuditApplicationEvent 或写入你方审计表。
```

> 上例仅说明**配置属性启用**与**类继承关系**；`IdAuditEvent`、`AuditApplicationEvent` 等**实际**包名与**构造**以你工程中的 `spring-boot-actuate` 与**安全/审计**模块 API 为准；在实现 `saveOperationDataTraceRecord` 时自行选用事件或落库。发布前在 IDE 中**核对 import** 与编译通过。

## 与 `spring-boot-starter-mybatis-plus` 的衔接

- 引入 `spring-boot-starter-mybatis-plus` 时，在 `@ConditionalOnMissingBean(OperationDataTraceResolver.class)` 下**自动**注册 `MybatisPlusOperationDataTraceResolver`（**并**启用 `OperationDataTraceProperties`）。
- 该实现会发布 Spring 审计相关事件、解析实体与 `Wrapper` 等，**比**「`AbstractOperationDataTraceResolver` + 全参转 `Map`」**更**多一层业务信息；细节见 mybatis-plus 子模块与 `MybatisPlusOperationDataTraceResolver` 源码。

## 注意事项

- 无 `OperationDataTraceResolver` **Bean** → 无 `OperationDataTraceInterceptor` **Bean** → 无**写操作**自动留痕，但 `JacksonJsonCollectionPostInterceptor` **仍**会注册；二者都需 `SqlSessionFactory` **插件链**中挂上对应 `Interceptor`（通常由 `mybatis-spring-boot-starter` 自动收集）。
- **JSQLParser** 无法解析的 SQL 会在 `parse` 时**抛错**；复杂 SQL 宜在**自定义** `createOperationDataTraceRecord` 中**兜底**或**关闭** `operation-data-trace.enabled` / **不**提供 Resolver。
- `REMOVE_ESCAPE_REG` 只去掉反斜杠转义/换行/制表，**不**代表与数据库**最终**执行计划完全一致。
- **仅**使用本 starter、且使用 `AbstractOperationDataTraceResolver` 时，需在应用侧 **显式** `@EnableConfigurationProperties(OperationDataTraceProperties.class)` 以便构造注入**生效**（mybatis-plus 的自动配置会代你启用**一次**）。
- 生产环境建议在子类/替代实现中把 `principal` 从**本机 IP** 换为**当前**登录用户，并在落库/审计前对 `submitData` 做**脱敏**（密码、Token 等）。

# spring-boot-starter-mybatis-plus

`spring-boot-starter-mybatis-plus` 是 loncra framework 对 MyBatis-Plus 的增强 Starter，目标是把常用能力一次性打包好：

- CRUD 基础服务封装（`BasicService`）。
- 条件查询表达式生成（`MybatisPlusQueryGenerator`）。
- 字段自动加密/解密（注解 + 拦截器）。
- 自动更新时间填充（`@LastModifiedDate`）。
- 数据库变更留痕并推送到 Spring 审计仓库（基于 `OperationDataTraceResolver`）。

如果你是新手，可以先按本文“快速开始”跑通，再逐步加上加密和审计能力。

## 模块作用

- 自动装配 `MybatisPlusInterceptor` 内部插件链（`MybatisPlusAutoConfiguration` 中**按此顺序**添加入 `addInnerInterceptor`）：
  1. 乐观锁（`OptimisticLockerInnerInterceptor`）
  2. 分页（`PaginationInnerInterceptor`，**默认** `DbType.MYSQL`）
  3. 防全表更新/删除（`BlockAttackInnerInterceptor`）
  4. 自动更新时间（`LastModifiedDateInnerInterceptor`）
  5. 写入加密与条件中的加密（`EncryptInnerInterceptor`）
  6. **多租户**（`TenantLineInnerInterceptor` + `TenantEntityHandler`）：从已注册 Mapper 解析出「泛型实体实现 `io.github.loncra.framework.commons.tenant.TenantEntity` 且带 `@TableName`」的**表名集合**，对这类表在 SQL 中追加租户列条件；是否追加由 `TenantLinePolicy` 决定，默认注册 `TenantLinePolicy.ALWAYS`（**总是**从 `TenantContextHolder` 取租户并拼条件）。运营后台等需**跨租户**时，可**自注册**同名 `TenantLinePolicy` Bean 在适当时机返回不拼条件。
- 同时注册**独立的** MyBatis 插件 `DecryptInterceptor`（`@Intercepts` 为 `Executor#query`）：在 `MybatisPlusInterceptor` **之外**，对**查询结果**做字段解密；与框架内其他 `org.apache.ibatis.plugin.Interceptor` Bean 一样，由 **MyBatis Spring Boot** 的自动配置**收集**后挂到 `SqlSessionFactory`（以所用版本行为为准）。
- 提供默认操作留痕解析器（`MybatisPlusOperationDataTraceResolver`），直接把 DB 变更推送到 Spring 审计仓库。
- 提供统一查询生成器（`filter_[字段_通配符]`）+ JSON 字段查询扩展（`jin/jeq/jso/jsa`）。
- 提供 `BasicService` 泛型服务基类，封装大量常见 CRUD 与分页/链式操作。
- 提供逻辑删除与版本号实体基类接口/实现（`LogicDeleteEntity`、`VersionEntity` 等）。

## 核心能力结构

- `io.github.loncra.framework.mybatis.plus`
  - 自动配置：`MybatisPlusAutoConfiguration`（`@AutoConfigureBefore` `MybatisAutoConfiguration`）
  - 查询生成：`MybatisPlusQueryGenerator`
  - 基础服务：`service.BasicService`
  - 加解密 SPI：`EncryptService`、`DecryptService`、`CryptoService`
  - 配置：`CryptoProperties`（`loncra.framework.mybatis.plus.crypto`；缺省 AES/RSA Bean 名见 `CryptoProperties` 中 `MYBATIS_PLUS_DATA_*` 常量）
- `tenant`（`TenantLinePolicy`、`TenantEntityHandler`）：与 `commons` 的 `TenantContextHolder`、`TenantEntity` 等配合
- `annotation`
  - 字段级：`@Encryption`、`@Decryption`、`@LastModifiedDate`
  - 类级批量：`@EncryptProperties`、`@DecryptProperties`（可重复注解）
- `interceptor`
  - MP 内链：`OptimisticLockerInnerInterceptor`、`PaginationInnerInterceptor`、`BlockAttackInnerInterceptor`、`LastModifiedDateInnerInterceptor`、`EncryptInnerInterceptor`、`TenantLineInnerInterceptor`
  - 独立插件：`DecryptInterceptor`（`Executor#query` 结果解密）
- `crypto`
  - `DataAesCryptoService`、`DataRsaCryptoService`
- `audit`
  - `MybatisPlusOperationDataTraceResolver`
  - `EntityIdOperationDataTraceRecord`
- `wildcard`
  - 基础通配符：`eq/ne/like/llike/rlike/gt/gte/lt/lte/in/nin/between/eqn/nen`
  - JSON 通配符：`jin/jeq/jso/jsa`
- `baisc`（原包名如此）
  - `LogicDeleteEntity`、`VersionEntity`
  - `support.*`：常用 `Integer/Long` 版本、逻辑删除组合基类

## 快速开始

## 1. 引入依赖

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-mybatis-plus</artifactId>
    <version>${framework.version}</version>
</dependency>
```

该 Starter 已**传递**依赖（**不含**被标为 `optional` 的项）：

- `spring-boot-starter-mybatis`
- `spring-boot-starter-access-crypto`（与字段加解密算法、Base64 等**配套**）
- `mybatis-plus-spring-boot3-starter`
- `mybatis-plus-jsqlparser`

`spring-boot-starter-basic-security` 在 POM 中为 `optional=true`，**不会**随本模块自动加入你的工程；需审计/安全模型时请**自己**加依赖，见下文《POM 说明》。

## 2. 开启模块

```yaml
loncra:
  framework:
    mybatis:
      plus:
        enabled: true
```

默认 `enabled=true`。

## 3. 最小配置示例

```yaml
mybatis-plus:
  configuration:
    default-enum-type-handler: io.github.loncra.framework.mybatis.handler.NameValueEnumTypeHandler

loncra:
  framework:
    mybatis:
      operation-data-trace:
        enabled: true
        audit-prefix-name: OPERATION_DATA_AUDIT
    security:
      audit:
        enabled: true
        type: memory
```

这样就能跑通：

- MyBatis-Plus 基础能力
- 操作数据留痕（依赖默认 `MybatisPlusOperationDataTraceResolver` 发布审计事件）
- 审计事件入内存仓库

> **POM 说明**：`spring-boot-starter-basic-security` 在本 artifact 的 `pom.xml` 中声明为 `<optional>true</optional>`，**不会**随本 starter 自动进入你的工程。`MybatisPlusOperationDataTraceResolver` 直接依赖 `io.github.loncra.framework.security.audit.*` 与 `org.springframework.boot.actuate.audit.listener.AuditApplicationEvent`。使用**默认**留痕时，请在应用 POM 中**显式**加入 `spring-boot-starter-basic-security`（该 starter 自身**通常**会传递 `spring-boot-starter-actuator`，满足 `AuditApplicationEvent` 的类路径）。若**不**要审计、或**避免**引 security，可**自行**实现并注册 `OperationDataTraceResolver`（或**排除**自动配置中的相关 Bean，视你的 Spring 能力而定），使默认解析器**不要**被实例化。

## 自动装配了什么

`MybatisPlusAutoConfiguration` 在满足 `ConditionalOnClass(MybatisPlusInterceptor)` 与 `loncra.framework.mybatis.plus.enabled`（默认 `true`）时注册下列 Bean，是否生效另受 `@ConditionalOnMissingBean` / `ConditionalOnProperty` 约束，**以源码为准**：

- `MybatisPlusQueryGenerator`
- `TenantLinePolicy`：未自定义时，返回 `TenantLinePolicy.ALWAYS`
- `MybatisPlusOperationDataTraceResolver`：未定义其他 `OperationDataTraceResolver` 且 `loncra.framework.mybatis.operation-data-trace.enabled` 为 `true` 时
- `MybatisPlusInterceptor`：内含 **6** 段 Inner 链（**顺序**见上节）
- `DecryptInterceptor`
- `DataAesCryptoService` / `DataRsaCryptoService`：在 `loncra.framework.mybatis.plus.crypto.enabled` 为 `true`（`matchIfMissing` 为 `true`）且无同类型 Bean 时；Bean 名为 `mybatisPlusDataAesCryptoService`、`mybatisPlusDataRsaCryptoService`（`CryptoProperties` 常量），与 `@Encryption` / `@EncryptProperties` 等 `beanName` 一致
- `@EnableConfigurationProperties`：`CryptoProperties` 与 `OperationDataTraceProperties`（后者由本配置**启用**后，可与 YAML 的 `operation-data-trace` 节绑定，**补充**了仅引 `spring-boot-starter-mybatis` 时未在自动配置里 `Enable` 的缺口）

## 常见用法

## 1. 使用 `BasicService` 快速写业务服务

```java
@Service
public class UserService extends BasicService<UserMapper, UserEntity> {
}
```

你可以直接使用：

- `save/insert/updateById/deleteById`
- `find/findOne/findPage/findTotalPage`
- `count/exist`
- `lambdaQuery/lambdaUpdate`

## 2. 使用 `filter_` 条件语法（重点）

`MybatisPlusQueryGenerator` 解析 `filter_...` 参数，格式来自 `SimpleConditionParser`：

- 单条件：`filter_[字段_通配符]`
- 多条件（同一个参数内 OR）：`filter_[a_eq]_or_[b_like]`
- 别名路径：`filter_[device->city.name_jeq]`（`device` 是别名，`city.name` 是 JSON 路径）

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

JSON 常用通配符（MySQL）：

- `jin`：`JSON_CONTAINS`
- `jeq`：JSON 路径等于
- `jso`：`JSON_SEARCH(..., 'one', ...)`
- `jsa`：`JSON_SEARCH(..., 'all', ...)`

示例：

```java
MultiValueMap<String, Object> filter = new LinkedMultiValueMap<>();
filter.add("filter_[name_eq]", "test");
filter.put("filter_[age_between]", List.of(18, 35));
filter.put("filter_[status_in]", List.of(1, 2));
filter.add("filter_[device->os_jin]", "windows");
filter.add("filter_[device->city.name_jeq]", "nanning");

QueryWrapper<UserEntity> wrapper = queryGenerator.createQueryWrapperFromMap(filter);
List<UserEntity> list = userService.find(wrapper);
```

说明：JSON 通配符基于 MySQL JSON 函数拼接 SQL，建议在 MySQL/H2(MySQL 模式)场景使用。

## 4. 字段级加密/解密

```java
@TableName(value = "tb_crypto_entity", autoResultMap = true)
public class CryptoEntity implements BasicIdentification<Integer> {

    private Integer id;

    @Decryption(serviceClass = DataAesCryptoService.class, beanName = "mybatisPlusDataAesCryptoService")
    @Encryption(serviceClass = DataAesCryptoService.class, beanName = "mybatisPlusDataAesCryptoService")
    private String cryptoValue;
}
```

执行效果：

- `insert/update` 时自动加密
- `select` 返回结果时自动解密
- 使用 `lambdaQuery().eq(CryptoEntity::getCryptoValue, "明文")` 时，查询条件也会自动按同策略加密后匹配

## 5. 类级批量加密配置

```java
@EncryptProperties(value = {"mobile", "idCard"}, beanName = "mybatisPlusDataAesCryptoService")
@DecryptProperties(value = {"mobile", "idCard"}, beanName = "mybatisPlusDataAesCryptoService")
public class UserEntity {
    // ...
}
```

适合同一个实体多个字段共用同一种加解密服务。

## 6. 自动更新时间

```java
public class UserEntity {
    @LastModifiedDate
    private Instant lastModifiedTime;
}
```

在 `UPDATE` 场景中会自动填充，支持字段类型：

- `Date`
- `LocalDateTime`
- `LocalDate`
- `LocalTime`
- `Long`
- `Instant`

## 7. 操作数据留痕推送审计仓库

`MybatisPlusOperationDataTraceResolver` 是该模块与 `basic-security` 的关键桥接：

- 识别 `INSERT/UPDATE/DELETE` 的实体与条件
- 尝试提取实体 ID（含 Wrapper 条件中的 ID）
- 生成 `OperationDataTraceRecord` / `EntityIdOperationDataTraceRecord`
- 封装审计事件并发布：
  - 普通：`IdAuditEvent`
  - 带存储定位：`IdStoragePositioningAuditEvent`
- 通过 `AuditApplicationEvent` 推入 Spring `AuditEventRepository`

## 关键接口说明

## 1. `MybatisPlusQueryGenerator<T>`

- 从 `filter_` 参数生成 `QueryWrapper<T>`。
- 支持 `SimpleConditionParser` 语法。
- 内置基础 + JSON 通配符。

## 2. `BasicService<M, T>`

- 面向业务层的通用 Service 基类。
- 把 Mapper 常见操作、分页、链式调用封装为统一 API。

## 3. `EncryptInnerInterceptor` / `DecryptInterceptor`

- 前者处理写入加密与条件加密。
- 后者处理查询结果解密。
- 都基于注解扫描字段并路由到对应 `EncryptService`/`DecryptService`。

## 4. `MybatisPlusOperationDataTraceResolver`

- `spring-boot-starter-mybatis` 中 `OperationDataTraceResolver` 的默认实现。
- `createAuditEvent(OperationDataTraceRecord)` 先把 `record` 的 `submitData` / `remark` 放入 `data`，再调用重载 `createAuditEvent(record, data)`，得到**底层**的 `org.springframework.boot.actuate.audit.AuditEvent`（或子类）。
- `saveOperationDataTraceRecord` 对**每条**记录用**同一** `batchUuid` 再包一层，最后 `ApplicationEventPublisher.publishEvent(new AuditApplicationEvent(...))`（**参见源码 `saveOperationDataTraceRecord` 中分支**）。

### 4.1 `createAuditEvent` 分支与 `AuditEvent` 具体类型

`AuditEvent` 的 **type** 字符串在两种分支中相同：`{auditPrefixName}_{target}_{OperationDataType}`，其中 `auditPrefixName` 来自 `OperationDataTraceProperties`（如 `OPERATION_DATA_AUDIT`），`target` 一般为表名，`OperationDataType` 为 `INSERT` / `UPDATE` / `DELETE` 等。

| 条件（`OperationDataTraceRecord`） | `createAuditEvent` 直接构造的**具体**类型 | 说明 |
|-----------------------------------|---------------------------------------------|------|
| `getStoragePositioning()` **非**空 | `io.github.loncra.framework.security.audit.StoragePositioningAuditEvent` | 构造参数：`storagePositioning`、`getCreationTime()`、`getPrincipal().toString()`、上式 **type**、`data` |
| 否则（空或未配置） | `org.springframework.boot.actuate.audit.AuditEvent` | 无存储定位，为标准 Actuate 审计事件，其余字段与上一行**同一**套 **timestamp / principal / type / data** |

`data` 在入口重载中至少含：`submitData`（`OperationDataTraceRecord.SUBMIT_DATA_FIELD`）、`remark`（`OperationDataTraceRecord.REMARK_FIELD`）；`EntityIdOperationDataTraceRecord` 等子类**不改变**上表**分支**（仅影响 `OperationDataTraceRecord` 的字段，从而改变 `data` 内容），**不**会单独产生第三种 `AuditEvent` 子类。

### 4.2 发布前包装与 `AuditApplicationEvent`

`saveOperationDataTraceRecord` 在发布前**统一**为每条事件加**业务** `id`（**与 4.1 中** `AuditEvent` **类型一一对应**；同一次 `save` 内多条记录**共用**方法开头生成的**同一个** `batchUuid` 作为 `id`）：

| 4.1 中**底层**事件类型 | 再包装为（发布**载荷**的 `AuditEvent` 实际类型） |
|-------------------------|--------------------------------------------------|
| `StoragePositioningAuditEvent` | `io.github.loncra.framework.security.audit.IdStoragePositioningAuditEvent`（`new IdStoragePositioningAuditEvent(batchUuid, (StoragePositioningAuditEvent) auditEvent)`） |
| `org.springframework.boot.actuate.audit.AuditEvent`（**非**上一行子类时） | `io.github.loncra.framework.security.audit.IdAuditEvent`（`new IdAuditEvent(batchUuid, auditEvent)`） |

监听器收到的是 `AuditApplicationEvent`，`getAuditEvent()` 为表**第二列**类型。消费侧要区分是否带**存储定位**：`getAuditEvent() instanceof IdStoragePositioningAuditEvent`，或对 `IdStoragePositioningAuditEvent` 调 `getStoragePositioning()`。

## 配置项速查

```yaml
loncra:
  framework:
    mybatis:
      plus:
        enabled: true
        crypto:
          enabled: true
          data-aes-crypto-key: ""
          data-ras-crypto-public-key: ""
          data-ras-crypto-private-key: ""
      operation-data-trace:
        enabled: true
        audit-prefix-name: OPERATION_DATA_AUDIT
        date-format: yyyy-MM-dd HH:mm:ss
        storage-position:
          prefix: operation_audit
          separator: _
          spring-expression: []
```

## 与其他模块关系

- `spring-boot-starter-mybatis-plus` **强依赖** `spring-boot-starter-mybatis`：复用 `JacksonJsonTypeHandler` / `NameValueEnumTypeHandler`、留痕 `OperationDataTraceInterceptor` 等。
- `spring-boot-starter-basic-security`：**不**被本模块**自动**拉取（`optional`），但其 `io.github.loncra.framework.security.audit` 包是**默认** `MybatisPlusOperationDataTraceResolver` 发审计事件所**必需**的；需要完整审计链时由应用**显式**依赖。
- `spring-boot-starter-access-crypto`：**会**被拉入，为 `DataAesCryptoService` / `DataRsaCryptoService` 与加解密拦截器**提供**算法实现。

**角色可概括为**：

- `mybatis`：TypeHandler、留痕**协议**与**拦截**入口
- `mybatis-plus`：MP 插件链、查询生成、**默认** `MybatisPlusOperationDataTraceResolver`、服务基类、租户等
- `basic-security`（可选但审计常用）：`IdAuditEvent`、存储定位类审计事件、审计仓库

## 扩展建议

- 生产环境优先提供你自己的 `EncryptService/DecryptService` Bean，并在注解里显式 `beanName`。
- 若数据库不是 MySQL，谨慎使用 JSON 通配符（`jin/jeq/jso/jsa`）。
- 建议在实体层统一规划加密字段，避免明文/密文混用。
- 审计数据建议开启 `storage-position` 做分索引/分集合归档。

## 注意事项

- `PaginationInnerInterceptor` 在自动配置里默认使用 `DbType.MYSQL`，非 MySQL 场景建议自定义 `MybatisPlusInterceptor`。
- `loncra.framework.mybatis.plus.crypto.enabled` 默认按 `matchIfMissing=true` 处理，未配置时也会尝试创建默认 AES/RSA 服务 Bean；若你不需要，建议显式设为 `false`。
- 加密拦截器在部分路径下会**跳过**已表现为 **Base64** 的字符串，避免**重复**加密（见 `EncryptInnerInterceptor` 中 `Base64.isBase64` 判断）。
- 多租户：需业务在请求/任务上下文中正确设置 `TenantContextHolder`；`TenantEntityHandler` 从 **Mapper Bean** 收集租户表，若某表**未**被解析进集合，**不会**自动加租户条件（见类注释中关于**表前缀**与 `TableName` 的约定）。
- 自动装配入口：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 注册 `io.github.loncra.framework.mybatis.plus.MybatisPlusAutoConfiguration`（**在** `MybatisAutoConfiguration` **之前**装配，由 `@AutoConfigureBefore` 指定）。
- **YAML 键** `loncra.framework.mybatis.plus.crypto.*` 与 `CryptoProperties` 字段**对应**；`data-ras-crypto-*` 的命名在源码中写作 **Ras**（非 Rsa），**绑定时**以 `CryptoProperties` **setter 为准**。
- `CryptoProperties` **类**内**未**声明 `enabled` 字段；`crypto.enabled` 仅**用于** `@ConditionalOnProperty` 控制是否创建**默认** AES/RSA 服务 Bean。


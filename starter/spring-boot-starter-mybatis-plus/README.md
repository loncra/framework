# spring-boot-starter-mybatis-plus

`spring-boot-starter-mybatis-plus` 是 loncra framework 对 MyBatis-Plus 的增强 Starter，目标是把常用能力一次性打包好：

- CRUD 基础服务封装（`BasicService`）。
- 条件查询表达式生成（`MybatisPlusQueryGenerator`）。
- 字段自动加密/解密（注解 + 拦截器）。
- 自动更新时间填充（`@LastModifiedDate`）。
- 数据库变更留痕并推送到 Spring 审计仓库（基于 `OperationDataTraceResolver`）。

如果你是新手，可以先按本文“快速开始”跑通，再逐步加上加密和审计能力。

## 模块作用

- 自动装配 MyBatis-Plus 插件链：
  - 乐观锁（`OptimisticLockerInnerInterceptor`）
  - 分页（`PaginationInnerInterceptor`，默认 MySQL）
  - 防全表更新/删除（`BlockAttackInnerInterceptor`）
  - 自动更新时间（`LastModifiedDateInnerInterceptor`）
  - 写入加密（`EncryptInnerInterceptor`）
- 自动注册查询结果解密拦截器（`DecryptInterceptor`）。
- 提供默认操作留痕解析器（`MybatisPlusOperationDataTraceResolver`），直接把 DB 变更推送到 Spring 审计仓库。
- 提供统一查询生成器（`filter_[字段_通配符]`）+ JSON 字段查询扩展（`jin/jeq/jso/jsa`）。
- 提供 `BasicService` 泛型服务基类，封装大量常见 CRUD 与分页/链式操作。
- 提供逻辑删除与版本号实体基类接口/实现（`LogicDeleteEntity`、`VersionEntity` 等）。

## 核心能力结构

- `io.github.loncra.framework.mybatis.plus`
  - 自动配置：`MybatisPlusAutoConfiguration`
  - 查询生成：`MybatisPlusQueryGenerator`
  - 基础服务：`service.BasicService`
  - 加解密 SPI：`EncryptService`、`DecryptService`、`CryptoService`
  - 配置：`CryptoProperties`
- `annotation`
  - 字段级：`@Encryption`、`@Decryption`、`@LastModifiedDate`
  - 类级批量：`@EncryptProperties`、`@DecryptProperties`（可重复注解）
- `interceptor`
  - `EncryptInnerInterceptor`、`DecryptInterceptor`、`LastModifiedDateInnerInterceptor`
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

该 Starter 已依赖：

- `spring-boot-starter-mybatis`
- `mybatis-plus-spring-boot3-starter`
- `mybatis-plus-jsqlparser`

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
    default-enum-type-handler: handler.io.github.loncra.framework.mybatis.NameValueEnumTypeHandler

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
- 操作数据留痕
- 审计事件入库（内存仓库）

## 自动装配了什么

`MybatisPlusAutoConfiguration` 在满足条件时会自动创建：

- `MybatisPlusQueryGenerator`
- `MybatisPlusOperationDataTraceResolver`（当你未自定义 `OperationDataTraceResolver`）
- `MybatisPlusInterceptor`（内含 5 个 inner interceptor）
- `DecryptInterceptor`
- `DataAesCryptoService` / `DataRsaCryptoService`（按属性开关）

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

    @Decryption(serviceClass = DataAesCryptoService.class, beanName = "aesEcbCryptoService")
    @Encryption(serviceClass = DataAesCryptoService.class, beanName = "aesEcbCryptoService")
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

- `spring-boot-starter-mybatis` 抽象接口的默认落地实现。
- 负责把 DB 变更变成审计事件并发布。

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

- `spring-boot-starter-mybatis-plus` 依赖 `spring-boot-starter-mybatis`：
  - 复用 JSON/枚举 TypeHandler
  - 复用留痕拦截抽象
- `spring-boot-starter-basic-security` 提供审计仓库模型与存储实现：
  - 本模块把留痕数据桥接成审计事件推送过去

可以理解为：

- `mybatis`：基础拦截与抽象协议
- `mybatis-plus`：默认实现 + 高阶能力（加解密、查询生成、服务封装）
- `basic-security`：审计存储与查询框架

## 扩展建议

- 生产环境优先提供你自己的 `EncryptService/DecryptService` Bean，并在注解里显式 `beanName`。
- 若数据库不是 MySQL，谨慎使用 JSON 通配符（`jin/jeq/jso/jsa`）。
- 建议在实体层统一规划加密字段，避免明文/密文混用。
- 审计数据建议开启 `storage-position` 做分索引/分集合归档。

## 注意事项

- `PaginationInnerInterceptor` 在自动配置里默认使用 `DbType.MYSQL`，非 MySQL 场景建议自定义 `MybatisPlusInterceptor`。
- `loncra.framework.mybatis.plus.crypto.enabled` 默认按 `matchIfMissing=true` 处理，未配置时也会尝试创建默认 AES/RSA 服务 Bean；若你不需要，建议显式设为 `false`。
- 加密拦截器会跳过看起来已是 Base64 的值，避免重复加密。
- 自动装配入口：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` -> `plus.io.github.loncra.framework.mybatis.MybatisPlusAutoConfiguration`。


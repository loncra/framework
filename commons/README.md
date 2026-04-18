# commons

`commons` 是 loncra framework 的基础公共库，提供通用工具、枚举体系、分页与树形结构、实体基类、异常体系、REST 结果封装、Jackson 扩展、雪花 ID 生成器与通用领域模型能力，供各 Starter 与业务模块复用。

## 模块作用

- 提供对象/类型/日期/URL/命名/版本/请求参数等通用工具能力。
- 提供统一枚举模型（`ValueEnum`、`NameEnum`、`NameValueEnum`）及序列化支持。
- 提供分页模型（滚动分页 + 页码分页）和树形结构组装能力。
- 提供主键实体抽象、ID 元数据对象和异常基类体系。
- 提供标准 REST 返回体 `RestResult`。
- 提供 ACK 消息、Token、重试元数据、MinIO DTO 等通用领域模型。
- 提供基于 Twitter Snowflake 的字符串 ID 生成器。
- 提供查询条件抽象（`Condition`、`ConditionParser`、`QueryGenerator`、`WildcardParser`）。

## 核心能力结构

- `io.github.loncra.framework.commons`
  - 工具类：`ObjectUtils`、`CastUtils`、`DateUtils`、`UrlUtils`、`NamingUtils`、`VersionUtils`、`MetadataUtils`、`HttpRequestParameterMapUtils`、`EnumUtils`
  - 通用模型：`RestResult`、`TimeProperties`、`CacheProperties`、`BigDecimalScaleProperties`
- `annotation`
  - `@Description`、`@Metadata`、`@MetadataElements`、`@IgnoreField`、`@Time`、`@GetValueStrategy`、`@JsonCollectionGenericType`
- `enumerate`
  - `ValueEnum`、`NameEnum`、`NameValueEnum`
  - 内置枚举：`YesOrNo`、`ExecuteStatus`、`Protocol`、`DisabledOrEnabled`、`AckStatus`、`UserStatus`
- `page`
  - `ScrollPageRequest`、`PageRequest`、`ScrollPage<T>`、`Page<T>`、`TotalPage<T>`
- `tree`
  - `Tree<P, C>`、`TreeUtils`
- `id`
  - `BasicIdentification<T>`、`IdEntity<T>`、`StringIdEntity`、`IntegerIdEntity`、`LongIdEntity`
  - 元数据：`IdNameMetadata`、`IdValueMetadata`、`IdNameValueMetadata`、`TypeIdNameMetadata`、`TreeIdNameMetadata`
- `exception`
  - `SystemException`、`ServiceException`、`ErrorCodeException`、`StatusErrorCodeException`、`EnumException`、`ValueEnumNotFoundException`、`NameEnumNotFoundException`
- `jackson`
  - 序列化/反序列化：`NameEnum*`、`ValueEnum*`、`NameValueEnum*`
  - 脱敏：`DesensitizeSerializer`
- `domain`
  - `AckMessage` / `AbstractAckMessage`、`AccessToken`、`RefreshToken`、`ExpiredToken`
  - `domain.body`：`AckResponseBody`
  - `domain.metadata`：`DescriptionMetadata`、`TreeDescriptionMetadata`、`RetryMetadata`、`CloudSecretMetadata`、`RefreshAccessTokenMetadata`
- `generator`
  - `IdGenerator<T>`、`generator.twitter.SnowflakeIdGenerator`、`SnowflakeProperties`
- `query`
  - `Property`、`Condition`、`ConditionType`、`ConditionParser`、`SimpleConditionParser`
  - `QueryGenerator<T>`、`WildcardParser<Q>`
- `minio`
  - `Bucket`、`FileObject`、`CopyFileObject`、`MoveFileObject`、`FilenameObject`、`ObjectWriteResult`、`VersionFileObject`

## 快速开始

## 1. 引入依赖

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>commons</artifactId>
    <version>${framework.version}</version>
</dependency>
```

## 2. 运行依赖说明

- 核心依赖：`slf4j-api`、`commons-lang3`、`commons-collections4`、`commons-beanutils`、`jackson-databind`、`jackson-datatype-jsr310`、`json-path`、`json-smart`、`jakarta.servlet-api`
- 可选依赖：`spring-core`、`spring-beans`（`MetadataUtils`、`CastUtils` 等能力会用到）

## 常见用法

## 1. 对象与类型处理

```java
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.ObjectUtils;

import java.util.Map;

Map<String, Object> map = ObjectUtils.ignoreObjectFieldToMap(source, "$.password", "$.secret");
DemoDto dto = CastUtils.of(map, DemoDto.class);
```

## 2. 枚举模型与映射

```java
import enumerate.io.github.opc.studio.framework.commons.ValueEnum;
import basic.enumerate.io.github.opc.studio.framework.commons.YesOrNo;

Map<String, Integer> enumMap = ValueEnum.ofMap(YesOrNo.class);
YesOrNo value = ValueEnum.ofEnum(1, YesOrNo.class);
```

## 3. 分页结构

```java
import page.io.github.opc.studio.framework.commons.PageRequest;
import page.io.github.opc.studio.framework.commons.TotalPage;

PageRequest request = PageRequest.of(1, 20);
TotalPage<String> page = new TotalPage<>(request, List.of("a", "b"), 200L);
```

## 4. 树形组装

```java
import tree.io.github.opc.studio.framework.commons.TreeUtils;

List<MenuTree> tree = TreeUtils.buildGenericTree(flatMenus);
```

## 5. REST 返回体

```java
import io.github.loncra.framework.commons.RestResult;

RestResult<Object> success = RestResult.ofSuccess("ok", data);
RestResult<Object> failure = RestResult.ofException(throwable);
```

## 6. 雪花 ID

```java
import twitter.generator.io.github.opc.studio.framework.commons.SnowflakeIdGenerator;
import twitter.generator.io.github.opc.studio.framework.commons.SnowflakeProperties;

SnowflakeProperties properties = new SnowflakeProperties(1, 1, "001");
SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(properties);
String id = idGenerator.generateId();
```

## 7. 查询条件解析

```java
import support.condition.query.io.github.opc.studio.framework.commons.SimpleConditionParser;

SimpleConditionParser parser = new SimpleConditionParser();
boolean support = parser.isSupport("filter_name_eq");
```

## 关键接口说明

## 1. `BasicIdentification<T>`

- 统一主键抽象：`getId()` / `setId(T id)`。
- `ofNew(ignoreProperties)`：创建新对象，复制 `id` 及指定属性。
- `ofIdData(ignoreProperties)`：仅保留 `id`，其余写属性置空（忽略列表除外）。

## 2. `Tree<P, C>`

- 定义树节点 `getChildren()`、`getParent()` 与父子关系判断。
- `ROOT_VALUE = "root"`，可用于定义根节点。

## 3. `Retryable`

- 统一重试模型：重试次数、最大重试数、重试时间、是否可重试。
- `RetryMetadata` 已实现该接口，可直接作为重试任务元数据对象。

## 注解说明

- `@Description`：描述类/方法/字段，配合 `MetadataUtils.convertDescriptionMetadata(...)` 生成描述树。
- `@Metadata` / `@MetadataElements`：键值元数据定义与容器。
- `@IgnoreField`：对象映射时忽略字段（`ObjectUtils.getIgnoreField`）。
- `@Time`：时间配置声明，可转换为 `TimeProperties`。
- `@GetValueStrategy`：枚举取值策略（`Value` / `Name` / `ToString`）。
- `@JsonCollectionGenericType`：声明集合泛型类型，用于 JSON 转换场景。

## 扩展建议

- 业务状态枚举优先实现 `NameValueEnum<Integer>`，便于前后端统一“展示名 + 码值”。
- 分页入参推荐 `PageRequest`，返回 `Page/TotalPage`，再统一封装 `RestResult`。
- 树形实体实现 `Tree<P, T>`，使用 `TreeUtils.buildTree`/`buildGenericTree` 组装。
- 业务异常推荐抛 `ServiceException` 或 `ErrorCodeException`，在全局异常处理中统一转换为 `RestResult.ofException(...)`。
- 对敏感字段优先使用 `DesensitizeSerializer`，或在对象转 Map 场景用 `ObjectUtils.desensitizeObjectFieldToMap(...)`。

## 注意事项

- `CastUtils` 持有独立 `ObjectMapper`，如需与 Spring 全局配置一致，可调用 `CastUtils.setObjectMapper(...)`。
- `MetadataUtils` 对 Spring 工具类有依赖，建议在 Spring 环境中使用（`spring-core`、`spring-beans` 为 optional）。
- `SnowflakeIdGenerator` 在检测到时钟回拨时会抛出 `SystemException`。
- `SimpleConditionParser` 的默认格式为 `filter_[property]_[wildcard]`（可通过解析器扩展）。
- `RestResult.isSuccess()`/`isProcessing()`/`isUnknown()` 的判断基于内部状态码字符串比对，使用时建议统一通过工厂方法构造结果对象。

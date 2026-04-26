# commons

`commons` 是 loncra framework 的顶级基础工具依赖，也是各类 starter 和业务模块共同复用的公共契约层。它不绑定具体业务场景，主要沉淀通用工具、统一枚举、分页/树形模型、实体基类、异常体系、REST 返回体、Jackson 扩展、租户上下文、查询条件抽象、雪花 ID 生成器以及 MinIO 领域 DTO。

## 模块定位

- 为上层 starter 提供稳定的基础类型和工具方法，避免每个 starter 重复定义通用模型。
- 统一框架内枚举、异常、分页、树形、主键实体、REST 响应、租户上下文等基础规范。
- 提供可复用的领域载体，例如 ACK 消息、访问令牌、重试元数据、云密钥元数据、MinIO 文件对象。
- 提供低侵入的工具能力，例如对象映射、JsonPath 字段忽略/脱敏、HTTP 参数转换、URL 路径变量填充、命名转换、版本号比较。

## 依赖引入

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>commons</artifactId>
    <version>${framework.version}</version>
</dependency>
```

运行依赖说明：

- 核心依赖：`slf4j-api`、`commons-lang3`、`commons-collections4`、`commons-beanutils`、`jackson-databind`、`jackson-datatype-jsr310`、`json-path`、`json-smart`、`jakarta.servlet-api`。
- 可选依赖：`spring-core`、`spring-beans`。`CastUtils`、`MetadataUtils`、`TenantContextHolder` 等能力会使用 Spring 的工具类，Spring 环境中可直接复用。
- 测试依赖：`spring-boot-starter-test`，仅用于本模块测试。

## 包结构

- `io.github.loncra.framework.commons`：通用工具与基础配置模型，包括 `CastUtils`、`ObjectUtils`、`DateUtils`、`UrlUtils`、`NamingUtils`、`VersionUtils`、`MetadataUtils`、`HttpRequestParameterMapUtils`、`EnumUtils`、`RestResult`、`TimeProperties`、`CacheProperties`、`BigDecimalScaleProperties`。
- `annotation`：公共注解，包括 `@Description`、`@Metadata`、`@MetadataElements`、`@IgnoreField`、`@Time`、`@GetValueStrategy`、`@JsonCollectionGenericType`。
- `enumerate`：统一枚举接口，包括 `ValueEnum`、`NameEnum`、`NameValueEnum`；内置枚举位于 `enumerate.basic` 与 `enumerate.security`。
- `page`：分页模型，包括 `ScrollPageRequest`、`PageRequest`、`ScrollPage<T>`、`Page<T>`、`TotalPage<T>`。
- `tree`：树形结构契约，包括 `Tree<P, C>`、`TreeUtils`。
- `id`：主键实体与元数据，包括 `BasicIdentification<T>`、`IdEntity<T>`、`StringIdEntity`、`IntegerIdEntity`、`LongIdEntity`、`NumberIdEntity`、`IdNameMetadata`、`IdValueMetadata`、`IdNameValueMetadata`、`IdValueRecordMetadata`、`TypeIdNameMetadata`、`TreeIdNameMetadata`。
- `tenant`：租户基础契约，包括 `TenantEntity<T>`、`TenantContext`、`SimpleTenantEntity`、`SimpleTenantContext`、`TenantContextHolder`、`TenantContextHolderStrategy`、`ThreadLocalTenantContextHolderStrategy`。
- `exception`：异常体系，包括 `SystemException`、`ServiceException`、`ErrorCodeException`、`StatusErrorCodeException`、`EnumException`、`ValueEnumNotFoundException`、`NameEnumNotFoundException`。
- `jackson`：枚举序列化/反序列化与字段脱敏，包括 `NameEnum*`、`ValueEnum*`、`NameValueEnum*`、`DesensitizeSerializer`。
- `domain`：通用领域模型，包括 `AckMessage`、`AbstractAckMessage`、`AccessToken`、`RefreshToken`、`ExpiredToken`、`AckResponseBody`、`DescriptionMetadata`、`TreeDescriptionMetadata`、`RetryMetadata`、`ProtocolMetadata`、`CloudSecretMetadata`、`RefreshAccessTokenMetadata`。
- `generator`：ID 生成器抽象与实现，包括 `IdGenerator<T>`、`SnowflakeIdGenerator`、`SnowflakeProperties`。
- `query`：查询条件抽象，包括 `Property`、`Condition`、`ConditionType`、`ConditionParser`、`SimpleConditionParser`、`QueryGenerator<T>`、`WildcardParser<Q>`。
- `retry`：重试契约，包括 `Retryable`。
- `minio`：MinIO 文件与桶 DTO，包括 `Bucket`、`ExpirableBucket`、`FileObject`、`FilenameObject`、`VersionFileObject`、`CopyFileObject`、`MoveFileObject`、`ObjectWriteResult`。

## 常见用法

### 对象与类型转换

`CastUtils` 用于类型转换、对象复制、Map 转对象、Jackson `convertValue` 等场景；`ObjectUtils` 用于对象转 Map 后按 JsonPath 忽略或脱敏字段。

```java
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.ObjectUtils;

import java.util.List;
import java.util.Map;

Map<String, Object> map = ObjectUtils.ignoreObjectFieldToMap(
        source,
        List.of("$.password", "$.secret")
);

DemoDto dto = CastUtils.ofMap(map, DemoDto.class);
```

字段脱敏示例：

```java
import io.github.loncra.framework.commons.ObjectUtils;

import java.util.List;
import java.util.Map;

Map<String, Object> result = ObjectUtils.desensitizeObjectFieldToMap(
        user,
        List.of("$.mobile", "$.email")
);
```

### 枚举模型与映射

框架内推荐业务状态枚举实现 `NameValueEnum<V>`，同时具备展示名称和持久化值。`ValueEnum`、`NameEnum` 会自动支持 Jackson 反序列化，并提供通用查找、Map 转换、元数据转换方法。

```java
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;

import java.util.Map;

Map<String, Integer> enumMap = ValueEnum.ofMap(YesOrNo.class);
YesOrNo value = ValueEnum.ofEnum(YesOrNo.class, 1);
```

```java
import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum OrderStatus implements NameValueEnum<Integer> {

    CREATED("已创建", 1),
    PAID("已支付", 2);

    private final String name;
    private final Integer value;

    OrderStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
```

### 分页模型

`ScrollPageRequest`/`ScrollPage<T>` 适合滚动加载；`PageRequest`/`Page<T>` 适合页码分页；`TotalPage<T>` 在页码分页基础上增加总数。

```java
import io.github.loncra.framework.commons.page.PageRequest;
import io.github.loncra.framework.commons.page.TotalPage;

import java.util.List;

PageRequest request = PageRequest.of(1, 20);
TotalPage<String> page = new TotalPage<>(request, List.of("a", "b"), 200L);
```

### 树形结构

实体实现 `Tree<P, C>` 后，可用 `TreeUtils` 在平铺列表与树形列表之间转换。

```java
import io.github.loncra.framework.commons.tree.TreeUtils;

import java.util.List;

List<MenuTree> tree = TreeUtils.buildGenericTree(flatMenus);
List<MenuTree> flat = TreeUtils.unBuildGenericTree(tree);
```

### REST 返回体

`RestResult<T>` 是框架内统一响应结构，包含 `message`、`status`、`executeCode`、`data`、`timestamp`、`metadata`。

```java
import io.github.loncra.framework.commons.RestResult;

RestResult<Object> success = RestResult.ofSuccess(data);
RestResult<Object> processing = RestResult.ofProcessing(RestResult.DEFAULT_PROCESSING_MESSAGE, data);
RestResult<Object> failure = RestResult.ofException(throwable);
```

### 异常与断言

`SystemException` 是基础运行时异常，并提供断言与受检异常转换工具；业务异常可使用 `ServiceException`、`ErrorCodeException`、`StatusErrorCodeException`，由上层统一转换为 `RestResult`。

```java
import io.github.loncra.framework.commons.exception.ServiceException;
import io.github.loncra.framework.commons.exception.SystemException;

SystemException.isTrue(order != null, () -> new ServiceException("订单不存在"));

String value = SystemException.convertSupplier(
        () -> remoteClient.getValue(),
        "调用远程服务失败"
);
```

### 雪花 ID

`SnowflakeIdGenerator` 基于 Twitter Snowflake 算法生成字符串 ID。`SnowflakeProperties` 需要提供 worker、datacenter、序列号补齐前缀等配置。

```java
import io.github.loncra.framework.commons.generator.twitter.SnowflakeIdGenerator;
import io.github.loncra.framework.commons.generator.twitter.SnowflakeProperties;

SnowflakeProperties properties = new SnowflakeProperties(1, 1, "001");
SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(properties);
String id = idGenerator.generateId();
```

### 查询条件解析

`SimpleConditionParser` 默认识别 `filter_[字段_条件]` 格式；多个字段条件会解析为 `Or`，单个字段条件默认为 `And`。字段支持别名语法 `source->target`。

```java
import io.github.loncra.framework.commons.query.condition.Condition;
import io.github.loncra.framework.commons.query.condition.support.SimpleConditionParser;

import java.util.List;

SimpleConditionParser parser = new SimpleConditionParser();

String name = "filter_[username_eq]";
boolean support = parser.isSupport(name);
List<Condition> conditions = parser.getCondition(name, List.of("admin"));
```

数组参数可使用 `[]` 后缀：

```java
List<Condition> conditions = parser.getCondition(
        "filter_[role_in][]",
        List.of("admin", "operator")
);
```

### HTTP 请求参数转换

`HttpRequestParameterMapUtils` 用于 query string、`MultiValueMap`、Servlet 参数 Map 之间转换，适合 starter 处理代理请求、签名参数、过滤条件等场景。

```java
import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import org.springframework.util.MultiValueMap;

MultiValueMap<String, String> map = HttpRequestParameterMapUtils.castRequestBodyMap("a=1&b=2");
String body = HttpRequestParameterMapUtils.castRequestBodyMapToString(map);
```

### 租户上下文

`TenantContextHolder` 默认使用 `ThreadLocalTenantContextHolderStrategy` 保存当前线程租户上下文。上层 starter 可在过滤器、拦截器或消息消费入口设置租户，再在后续链路中读取。

```java
import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;

SimpleTenantContext context = TenantContextHolder.create();
context.setId("tenant-001");

TenantContextHolder.set(context);

try {
    SimpleTenantContext current = TenantContextHolder.get();
}
finally {
    TenantContextHolder.clear();
}
```

也可以通过系统属性 `loncra.framework.tenant.holder.strategy` 指定自定义 `TenantContextHolderStrategy` 实现类。

### 元数据与描述树

`@Description` 可描述类、字段、方法；`MetadataUtils.convertDescriptionMetadata(...)` 会生成 `TreeDescriptionMetadata`，常用于接口文档、字段配置、可视化元数据等场景。

```java
import io.github.loncra.framework.commons.MetadataUtils;
import io.github.loncra.framework.commons.annotation.Description;
import io.github.loncra.framework.commons.domain.metadata.TreeDescriptionMetadata;

@Description(value = "用户信息", name = "user")
class UserInfo {

    @Description("用户名")
    private String username;
}

TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(UserInfo.class);
```

`@Metadata` / `@MetadataElements` 可在类、字段、方法上定义键值元数据，并通过 `MetadataUtils.parseMetadata(...)` 解析为 `Map<String, String>`。

### 时间、缓存与重试

`TimeProperties` 是统一时间配置模型，可从 `@Time` 注解创建，也可直接通过工厂方法创建。`RetryMetadata` 实现了 `Retryable`，适合作为重试任务的通用元数据。

```java
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.metadata.RetryMetadata;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;

TimeProperties timeout = TimeProperties.ofSeconds(30);

RetryMetadata metadata = new RetryMetadata();
metadata.setExecuteStatus(ExecuteStatus.Failure);
metadata.setRetryCount(1);
metadata.setMaxRetryCount(3);
```

### MinIO DTO

`minio` 包只提供通用数据载体，不直接依赖 MinIO 客户端。对象存储 starter 可复用这些 DTO 表达桶、文件、版本文件、复制、移动、写入结果和临时过期配置。

```java
import io.github.loncra.framework.commons.minio.Bucket;
import io.github.loncra.framework.commons.minio.FilenameObject;

Bucket bucket = Bucket.of("public-files", "cn-east-1");
FilenameObject file = FilenameObject.of(bucket, "avatars/u001.png", "u001.png");
```

## 关键接口说明

### `BasicIdentification<T>`

- 统一主键抽象：`getId()` / `setId(T id)`。
- `ofNew(ignoreProperties)`：创建同类型新对象，保留 `id` 和指定忽略属性。
- `ofIdData(ignoreProperties)`：创建同类型新对象，除 `id` 和忽略属性外，其余可写属性置空。

### `Tree<P, C>`

- 定义树节点的子节点、父节点和父子关系判断。
- `ROOT_VALUE = "root"` 可作为根节点标识。
- 配合 `TreeUtils.buildTree(...)`、`TreeUtils.unBuildTree(...)` 完成树形组装和扁平化。

### `TenantEntity<T>` 与 `TenantContext`

- `TenantEntity<T>` 统一租户实体字段名为 `tenantId`。
- `TenantContext` 提供租户 ID 与扩展明细 `details`。
- `TenantContextHolder` 负责保存当前执行上下文中的租户信息。

### `Retryable`

- 统一重试模型：重试次数、最大重试次数、重试时间、是否可重试。
- `RetryMetadata` 已实现该接口，可作为通用重试任务元数据对象。

### `IdGenerator<T>`

- 定义 `generateId()`，用于屏蔽具体 ID 生成算法。
- 当前内置实现为 `SnowflakeIdGenerator`。

## 注解说明

- `@Description`：描述类、方法、字段，配合 `MetadataUtils.convertDescriptionMetadata(...)` 生成描述树。
- `@Metadata` / `@MetadataElements`：定义键值元数据，配合 `MetadataUtils.parseMetadata(...)` 使用。
- `@IgnoreField`：标记对象映射时应忽略的字段，可通过 `ObjectUtils.getIgnoreField(...)` 读取。
- `@Time`：声明时间配置，可转换为 `TimeProperties`。
- `@GetValueStrategy`：声明枚举取值策略，支持 `Value`、`Name`、`ToString`。
- `@JsonCollectionGenericType`：声明集合泛型类型，用于 JSON 转换场景。

## 内置枚举

- `YesOrNo`：是/否状态，并支持 `toBoolean()`、`ofBoolean(...)`。
- `ExecuteStatus`：执行状态，适合任务、消息、重试等流程状态。
- `Protocol`：协议类型元数据。
- `DisabledOrEnabled`：禁用/启用状态。
- `AckStatus`：ACK 响应状态。
- `UserStatus`：用户状态。

## 推荐实践

- 业务状态枚举优先实现 `NameValueEnum<Integer>`，便于前后端统一“展示名 + 码值”。
- starter 暴露分页接口时，入参优先使用 `PageRequest`，返回 `Page<T>` 或 `TotalPage<T>`，最外层再由 Web 层封装为 `RestResult<T>`。
- 树形实体优先实现 `Tree<P, T>`，使用 `TreeUtils` 组装，避免各模块重复维护树构建逻辑。
- 业务异常推荐抛出 `ServiceException` 或 `ErrorCodeException`，由全局异常处理统一转换为 `RestResult.ofException(...)`。
- 涉及敏感字段时，JSON 序列化可使用 `DesensitizeSerializer`；对象转 Map 场景可使用 `ObjectUtils.desensitizeObjectFieldToMap(...)`。
- 需要保存当前租户时，入口处设置 `TenantContextHolder`，并在 `finally` 中调用 `clear()`，避免线程复用时租户串号。
- 对象存储 starter 应复用 `minio` 包 DTO，不在业务模块重新定义 Bucket、FileObject、CopyFileObject 等结构。

## 注意事项

- `CastUtils` 持有独立 `ObjectMapper`。如需与 Spring 全局配置一致，可在启动阶段调用 `CastUtils.setObjectMapper(...)`。
- `ObjectUtils.ignoreObjectFieldToMap(...)` 与 `desensitizeObjectFieldToMap(...)` 使用 JsonPath，字段路径需要使用 `$.field`、`$.items[*].name` 等格式。
- `MetadataUtils` 对 Spring 工具类有依赖，建议在 Spring 环境中使用。
- `TenantContextHolder` 默认策略为 ThreadLocal，在线程池、异步任务、消息消费等场景中必须主动清理。
- `SnowflakeIdGenerator` 检测到时钟回拨时会抛出 `SystemException`。
- `SimpleConditionParser` 的默认格式为 `filter_[property_wildcard]`，不是 `filter_property_wildcard`；格式可通过 setter 调整。
- `RestResult.isSuccess()`、`isProcessing()`、`isUnknown()` 的判断基于 `status` 字段字符串比对，建议统一通过工厂方法创建响应对象。

# spring-boot-starter-web-mvc

在 **Spring Boot WebMVC** 之上，把本框架里常用的 **`RestResult` 统一响应**、**按异常类型解析错误**、**Jackson 对枚举/MimeType 的扩展**、**User-Agent 设备信息**、**Actuator 枚举清单** 等做成默认可用能力；**验证码**与 **幂等异常** 走**可选/提供范围**依赖，避免用不到的工程被硬拉依赖。

适合作为业务 **HTTP API** 的基座 starter（与 **spring-security** 等组合时，安全相关 filter 在别的 starter 里）。

## 1. 本模块解决什么问题（设计意图）

- **前后端约定一种 JSON 结构**：成功时可用 `RestResponseBodyAdvice` 把「普通返回值」包成 `RestResult`；失败时由 `RestResultErrorAttributes` 把 **/error** 上的错误属性也收敛成 **同一套** `RestResult` 形态，便于前端与**网关**消费。  
- **与「安全/脱敏」协同**：成功体在写出前会走 `IgnoreOrDesensitizeResultHolder`（来自 **basic-security** 体系），与下游数据脱敏、忽略字段等能力对齐。  
- **在 Boot 默认错误与 Jackson 之前插入扩展**：`SpringWebMvcAutoConfiguration` 使用 `@AutoConfigureBefore(ErrorMvcAutoConfiguration.class, JacksonAutoConfiguration.class)`，让本框架的**错误属性**、**带扩展模块的 ObjectMapper** 先参与合并策略（具体以 Spring Boot 的 Bean 覆盖规则为准）。  
- **设备信息用于业务**：用 **YAUAA** 在**极早**的 Filter 里解析 UA，把 `UserAgent` 放进 request 属性，后续 Controller/工具类可读（与 `DeviceHandlerMethodArgumentResolver` 等配合时，**见下文「需自己注册」**）。  
- **给 RestTemplate 一条「自己的拦截器链」**：通过**标记接口** `CustomClientHttpRequestInterceptor` 与默认 `RestTemplate` Bean 组装，避免和 **LoadBalancer** 等其它 `ClientHttpRequestInterceptor` 混在一个 list 里难以区分（见接口注释）。  
- **便于联调/后台展示枚举值**：`EnumerateEndpoint` 在 Actuator 上暴露**可扫描包内**的 `NameEnum` / `NameValueEnum` / `ValueEnum` 清单（需**暴露**该端点并配置 `enumerate-endpoint-base-packages`）。

## 2. Maven 依赖与传递关系

- **强依赖**：`commons`、`spring-boot-starter-basic-security`（**脱敏/安全结果**与 `RestResponseBodyAdvice` 等）、`spring-boot-starter-web`、`spring-boot-starter-validation`、`spring-boot-starter-actuator`、YAUAA、Caffeine 等。  
- **可选**：`spring-boot-starter-captcha`（`optional`）— 有验证码 starter 时才会加载 `CaptchaExtendAutoConfiguration`。  
- **provided**：`spring-boot-starter-idempotent` — 仅当**运行时** classpath 存在时，才注册与 `IdempotentException` 相关的 `ErrorResultResolver`（见 `SpringWebMvcAutoConfiguration#idempotentErrorResultResolver`）。

## 3. 主开关

- **`loncra.framework.mvc.enabled`**：Servlet 应用下，为 `true`（默认，**未写配置即开启**）时注册 `SpringWebMvcAutoConfiguration`；置 `false` 可整体关闭本 starter 中**该自动配置**声明的 Bean（**不含**你业务里自己写的 `@ControllerAdvice` 等）。

## 4. 自动配置入口

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 包含：

| 类 | 条件摘要 |
|----|----------|
| `SpringWebMvcAutoConfiguration` | Servlet + `loncra.framework.mvc.enabled` |
| `CaptchaExtendAutoConfiguration` | Servlet + classpath 存在 `CaptchaAutoConfiguration`（即一般需**显式**依赖 captcha） |

## 5. 核心能力说明

### 5.1 成功响应是否包成 `RestResult`（`RestResponseBodyAdvice`）

在 **JSON** 且**允许格式化**时，将 Controller 返回值包成 `RestResult`（若已是 `RestResult` 则做元数据/脱敏等处理）。是否「包一层」由配置决定：

- **`all-rest-result-format: true`**：所有支持的请求都包（仍受「不格式化」开关影响，见下）。  
- 否则：请求头 **`X-REQUEST-CLIENT`** 的值须落在 **`rest-result-format-support-clients`** 列表中（默认仅包含 `SPRING_GATEWAY`），用于**标识来自网关/指定客户端**、需要统一包装。

**跳过包装**的常用方式：请求属性或头 **`X-REST-RESULT-NOT-FORMAT`**（与 `RestResponseBodyAdvice` 中常量一致）。错误流程里会设置 `RestResultErrorAttributes` 中的请求属性，避免与成功包装逻辑**重复冲突**。

### 5.2 错误响应（`RestResultErrorAttributes` + `ErrorResultResolver`）

替换/增强默认 `ErrorAttributes`：按 **HTTP 状态**、**异常类型** 和已注册的 **`ErrorResultResolver`** 链，生成 `RestResult` 再转 Map 给 **BasicErrorController** 使用。  

默认内置解析器（均为 `@Bean`）：`BindingResultErrorResultResolver`、`MissingServletRequestParameterResolver`、`ErrorCodeResultResolver`；**若 classpath 有幂等模块** 则多一个 `IdempotentErrorResultResolver`。

可通过 **`loncra.framework.mvc.support-exception` / `support-http-status`** 控制「哪些异常/哪些状态**默认把 `message` 用异常**消息或短语填充」（见 `SpringWebMvcProperties` 与 `RestResultErrorAttributes` 默认值）。  

另可**自行**实现并注册 `ErrorResultResolver` Bean 扩展解析顺序（`List<ErrorResultResolver>` 注入顺序**以 Spring 的排序/列表顺序为准，建议看源码如何收集**）。

### 5.3 Jackson `ObjectMapper` 扩展

`loncraFrameworkJacksonJsonObjectMapper` 在 **`Jackson2ObjectMapperBuilder` 上** 注册本框架 `commons` 中的 **Name/Value/NameValue 枚举**序列化/反序列化，以及 **`MimeType`** 的反序列化，并执行 **`CastUtils.setObjectMapper`** 供全框架静态工具使用。  

**说明**：本 Bean **未**使用 `@ConditionalOnMissingBean(ObjectMapper.class)`，可能与 Boot/其它模块再注册的 `ObjectMapper` **并存**；在需要「唯一主 `ObjectMapper`」注入的场景，请用 **`@Primary`** 或 **`@Qualifier`** 明确选 Bean，并确认 **`CastUtils`** 是否应指向你期望的那一份。

### 5.4 设备解析（`DeviceResolverRequestFilter`）

在 Filter 中调用 `DeviceUtils.getDevice(request)`（YAUAA），将当前 **`UserAgent`** 放入 **`DeviceUtils.CURRENT_DEVICE_ATTRIBUTE`**。`FilterRegistrationBean` 为 **`/*`**。  

- **`loncra.framework.mvc.enabled-device-filter`**：为 `false` 时**不**注册该 Filter；未配置时默认 `true`（与 `SpringWebMvcProperties` 一致），对应 `@ConditionalOnProperty(..., matchIfMissing = true)`。  
- **`loncra.framework.mvc.device-filter-order-value`**：同时用于 **`FilterRegistrationBean#setOrder`** 与 **`DeviceResolverRequestFilter` 的 `Ordered#getOrder()`**；默认值为 **`Ordered.HIGHEST_PRECEDENCE + 60`**（见 `SpringWebMvcProperties`）。若你本地曾手写 `new DeviceResolverRequestFilter()` 无参构造，仍使用 **`HIGHEST_PRECEDENCE`** 作为顺序。

### 5.5 默认 `RestTemplate`

在**没有**其他 `RestTemplate` Bean 时，注册一个并只收集 **`CustomClientHttpRequestInterceptor`** 实现，用于与 LoadBalancer 等**其它**拦截器**区分**（见接口 `CustomClientHttpRequestInterceptor` 的 Javadoc）。

### 5.6 验证码（`CaptchaExtendAutoConfiguration`）

当 **captcha 相关类在 classpath**（即通常增加 **`spring-boot-starter-captcha`** 且满足各 `@ConditionalOnProperty`）时：

- 可提供 **`ControllerTianaiCaptchaService`** 作为默认 **`TianaiCaptchaService`**（`@ConditionalOnMissingBean(TianaiCaptchaService.class)`）；  
- 在 **`loncra.framework.captcha.controller.enabled` 为 true**（默认）时注册 **`CaptchaController`**。

**与** `spring-boot-starter-spring-security` 里的 `CaptchaExtendAutoConfiguration` 类似但**分属不同依赖路径**；本 **web-mvc** 模块的验证码配置**在 classpath 有 captcha 时**由 **imports 第二行** 加载。实际是否启用以 **`@ConditionalOnClass` / 属性** 为准。

### 5.7 Actuator `EnumerateEndpoint`（`id = "enumerate"`）

在 **`enumerate-endpoint-base-packages`** 中扫描 **`NameValueEnum` / `ValueEnum` / `NameEnum`** 实现类，为前端/联调提供**枚举可选项**聚合信息；会合并已注册的 `InfoContributor` 输出。需在 **Management** 中**暴露**该端点并配置**包路径**，否则**无数据或空**属预期。

## 6. 包内提供、但未在自动配置里注册的类（需业务接入）

以下类**随 jar 提供**，但 **SpringWebMvcAutoConfiguration 不会**为它们调用 `addArgumentResolvers`：

- **`GenericsListHandlerMethodArgumentResolver`** + **`@GenericsList`**：把查询参数**绑定**为**带泛型**的 `List`；  
- **`DeviceHandlerMethodArgumentResolver`**：把 **`UserAgent`** 注入到方法参数。

**用法**：在任意 `@Configuration` 实现 `WebMvcConfigurer`，在 `addArgumentResolvers` 里**手动** `resolvers.add(...)`（必要时传入 `Validator` 等，见各解析器构造说明）。

## 7. 配置前缀速查（`loncra.framework.mvc`）

| 项 | 含义（摘要） |
|----|----------------|
| `enabled` | 总开关。 |
| `all-rest-result-format` | 是否**全部**走成功体 `RestResult` 包装。 |
| `rest-result-format-support-clients` | 与头 `X-REQUEST-CLIENT` 搭配的白名单。 |
| `support-exception` / `support-http-status` | 错误 JSON 中**何时**用异常信息或短语文案。 |
| `enumerate-endpoint-base-packages` | 枚举端点**扫描**包。 |
| `enabled-device-filter` | 为 `false` 时不注册设备 Filter。 |
| `device-filter-order-value` | 设备 Filter 的 **Servlet 注册顺序**与 **`Ordered` 值**（见 5.4）。 |

其余键以 **`SpringWebMvcProperties`** 及**编译**生成的 `spring-configuration-metadata` 为准。

## 8. 与其它 starter 的衔接

- **idempotent（provided）**：只有引入**幂等 starter 且**有 `IdempotentException` 类时，多注册一条错误解析。  
- **netty-socketio** 等：若**要在 socket 中复用** `RestResponseBodyAdvice` 的同类逻辑，会**自行** `AutoConfigureBefore(SpringWebMvcAutoConfiguration.class)` 并**定制** `ResponseBodyAdvice`；属**组合场景**，**以对应模块源码为准**。

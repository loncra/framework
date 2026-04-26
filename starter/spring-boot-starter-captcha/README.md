# spring-boot-starter-captcha

`spring-boot-starter-captcha` 在 **Spring Boot + Servlet** 环境下，提供可插拔的验证码领域模型与协议：统一 **Token（绑定/拦截）**、**生成**、**校验**、**存储** 与部分 **受保护 URL 的过滤器**。当前内置的图形/行为验证码能力基于开源组件 **[tianai-captcha](https://github.com/dromara/tianai-captcha)**（`cloud.tianai.captcha`），在工程内由 `TianaiCaptchaService` 做了契约封装与资源装配。

> 本模块**不包含** MVC `Controller` 的 HTTP 映射。典型 Web 项目会再引入 `spring-boot-starter-web-mvc`（或自行编写控制器）对接浏览器与移动端；见下文《与 Spring MVC 的配套关系》。

## 模块定位

- 以 `**CaptchaService`** 为核心契约，可扩展多种实现；当前已集成 **tianai 行为/图形验证码**（滑块、旋转、滑动还原、文字点选等，具体以 `cloud.tianai.captcha` 的 `CaptchaTypeConstant` 为准）。
- 通过 `**DelegateCaptchaService`** 按请求头/参数中的验证码类型，路由到具体实现。
- 提供 **绑定 Token**、**拦截 Token** 两级结构，与 `**CaptchaStorageManager`**（内存 / Redisson）一起支撑分布式部署。
- 提供 `**CaptchaVerificationFilter**`：对配置的 URL 在业务逻辑**之前**做验证码校验（适用于登录、敏感操作前二次确认等场景）。
- 内置与 tianai 配套的前端静态资源：`classpath:tianai/js/tianai-captcha.js`、 `classpath:static/tianai-captcha.css`；Spring MVC 下可通过控制器暴露为可访问的 HTTP JS（见后文）。

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-captcha</artifactId>
    <version>${framework.version}</version>
</dependency>
```

主要依赖：


| 依赖                             | 作用                                                    |
| ------------------------------ | ----------------------------------------------------- |
| `commons`                      | `RestResult`、`CacheProperties`、`TimeProperties`、对象转换等 |
| `spring-boot-starter-web`      | Servlet、过滤器、`HttpServletRequest` 绑定等                  |
| `tianai-captcha` 1.5.5         | 行为验证码的生成、校验、资源/模板模型                                   |
| `redisson-spring-boot-starter` | **optional**，存在且完成自动配置时，可用 Redis 做 Token/验证码状态存储      |
| `commons-text` / `commons-io`  | 工具类                                                   |


多实例或需要跨 JVM 共享验证码状态时，建议引入 **Redisson** 并让 `**RedissonStorageAutoConfiguration`** 生效；否则使用 `**InMemoryCaptchaStorageManager**`（单进程、容量约 1000 条，适合本地开发）。

## 自动配置

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 中（顺序有含义）：

- `io.github.loncra.framework.captcha.RedissonStorageAutoConfiguration`：类路径存在 Redisson 自动配置且**未**自定义 `CaptchaStorageManager` 时，注册 `**RedissonCaptchaStorageManager`**。
- `io.github.loncra.framework.captcha.CaptchaAutoConfiguration`：在 `loncra.framework.captcha.enabled` 为 `true`（默认）时，注册 `TianaiCaptchaService`、`DelegateCaptchaService`、拦截器、内存存储（若上一步未注册 Redisson 存储）、`TianaiCaptchaVerificationService` 与 `**CaptchaVerificationFilter**` 等。

开关：`loncra.framework.captcha.enabled`（默认 `true`）。

## 包结构（概要）


| 包 / 类                                       | 说明                                                                                           |
| ------------------------------------------- | -------------------------------------------------------------------------------------------- |
| `CaptchaService` / `DelegateCaptchaService` | 生成 Token、生成验证码、校验的对外协议与多实现路由                                                                 |
| `AbstractCaptchaService`                    | 通用：参数绑定、Token 与缓存、`verify` 流程；子类实现 `doGenerateCaptcha` 等                                     |
| `tianai.TianaiCaptchaService`               | 对接 tianai 的 `ImageCaptchaGenerator` / `ImageCaptchaValidator`、实现 `CrudResourceStore` 注册资源与模板 |
| `tianai.config.TianaiCaptchaProperties`     | 前缀 `loncra.framework.captcha.tianai`                                                         |
| `tianai.body.TianaiRequestBody`             | 生成验证码时绑定请求参数，如 `generateImageType`（验证码子类型）                                                   |
| `token.*`                                   | `BuildToken` / `InterceptToken` 及简单实现类                                                       |
| `storage.*`                                 | `CaptchaStorageManager`；`InMemoryCaptchaStorageManager` / `RedissonCaptchaStorageManager`    |
| `filter.*`                                  | `CaptchaVerificationFilter`、`CaptchaVerificationService`；`TianaiCaptchaVerificationService`  |
| `intercept.*`                               | 生成验证码**前**可追加一次「拦截验证码」校验，防刷（见下文）                                                             |


## 核心协议说明

### 1. 验证码类型与「是否支持本次请求」

- 请求头 `X-CAPTCHA-TYPE` 或参数 `captchaType`（可配置，见 `CaptchaProperties`）声明类型。
- 默认 `defaultCaptchaType` 为 `tianai`。
- `TianaiCaptchaService#getType()` 固定返回 `"tianai"`。

### 2. 绑定 Token 与参数名

- 对类型 `tianai`，绑定 Token 的 HTTP 参数名为 `**_tianaiCaptchaToken`**（由 `_` + `getType()` + 首字母大写后的 `captchaToken` 后缀组成，见 `AbstractCaptchaService#getTokenParamName`）。
- tianai 提交/校验用的业务参数字段名由 `**TianaiCaptchaProperties.captchaParamName**` 决定，**默认** `_tianaiCaptcha`（滑动结束后客户端将该字段作为**校验摘要**等提交，见 tianai 与 `matchesCaptcha` 实现）。

### 3. 拦截器（防刷、二次验证）

`AbstractCaptchaService#generateToken` 在生成绑定 Token 时，若未通过 `ignoreInterceptor` 关闭，则会按委托拦截器为当前会话再生成**拦截类 Token**（`Interceptor#generateCaptchaIntercept`）。`generateCaptcha` 入口在 MVC 的 `CaptchaController` 中会先 `interceptor.verifyCaptcha`：在真正拉取 tianai 图之前，可要求先通过上一轮验证码。详见 `io.github.loncra.framework.captcha.intercept.Interceptor` 的注释。

## tianai 行为验证码：实现与设计要点

本仓库中的 `**TianaiCaptchaService`** 是 **tianai 官方能力** 与 **本框架 Token/存储协议** 的桥接层，主要职责如下。

### 1. 资源与生成管线

- 使用 `**MultiImageCaptchaGenerator`** 与 `**Base64ImageTransform**`，在构造时从配置加载 `**TianaiCaptchaProperties#resourceMap**`、`**templateMap**` 到内存中的 `CrudResourceStore`（`addResource` / `addTemplate` 等实现了 tianai 的 `CrudResourceStore` 接口）。
- 调用 `**ImageCaptchaGenerator#generateCaptchaImage(type)**` 按类型生成图片/轨迹结构；`type` 来自 `TianaiRequestBody#generateImageType`。
  - 当请求里 `generateImageType` 等于 Spring Boot 的 `**RandomValuePropertySource.RANDOM_PROPERTY_SOURCE_NAME`（即字符串 `random`）** 时，在 `**randomCaptchaType`** 中**随机**选一种子类型。源码中该列表**默认**含 **滑块、旋转、滑动还原** 等，可按需改为含 `WORD_IMAGE_CLICK` 等，类型名以 `cloud.tianai.captcha` 的 `CaptchaTypeConstant` 为准。
- `**tolerantMap`** 按**模板图 tag**（`imageCaptchaInfo.getTemplateImageTag()`）为本次验证码设置**容错**（`setTolerant`），用于与滑块/旋转等校验算法配合。

### 2. 存什么：校验数据 + 与前端约好的两次交互

- **首次生成**时，除返回给前端的 `ImageCaptchaInfo` 结构外，会将 `ImageCaptchaValidator#generateImageCaptchaValidData` 产出的 **校验用 Map** 经 JSON 写入 `SimpleCaptcha#value` 并缓存，供后续服务端校验**滑动轨迹**使用。
- `**clientVerify`（MVC 暴露为 `POST /captcha/clientVerify`）**：请求体为 `**ImageCaptchaTrack` JSON**；**绑定 token** 仍通过 `**TianaiCaptchaService#getTokenParamName()`**（如 `_tianaiCaptchaToken`）传入。实现流程：用 token 取缓存中的 `SimpleCaptcha` 后，**先 `deleteCaptcha` 删除缓存中的验证码副本**（与「二次提交」的并发语义有关，见源码 `TianaiCaptchaService#clientVerify`）；用 `**BasicCaptchaTrackValidator#valid`** 将轨迹与生成阶段写入的**校验用 Map**（`SimpleCaptcha#value` 反序列化为 `AnyMap`）做比对。成功后把 `**ImageCaptchaTrack` 的 JSON 串** 写回 `captcha#value` 并 `**saveCaptcha`**，响应 `**data` 为 `MD5(utf8 轨迹 JSON)` 的十六进制串**，供后续 `**verifyCaptcha` / 过滤器** 中 `_tianaiCaptcha` 等参数与此摘要比对。
- **最终 `verifyCaptcha`（`POST`）** 与过滤器里的校验，走子类重写的 `**matchesCaptcha`**：请求参数 `captchaParamName` 的值须等于 **当前 `SimpleCaptcha#getValue` 字节**的 **MD5 十六进制**；并解析为 `**ImageCaptchaTrack`**，用 **起止时间差** 必须 **小于** `**serverVerifyTimeout`**（**默认 2 分钟**），作为「行为在合理时间窗内完成」的约束。

### 3. 与「规范」类图验证码的差异

- 本实现**不是**简单的「客户端提交字符串与 Session 中明文比对」，而是 tianai 的 **图 + 轨迹 + 服务器侧校验数据** 模型；最终一步仍落在框架统一的 `**verify`** 与 `**RestResult**` 上，便于和登录、过滤器等接在一起。

## 与 Spring MVC 的配套关系

若使用 `**spring-boot-starter-web-mvc**`，在 Servlet Web 下会配置 `**io.github.loncra.framework.spring.web.CaptchaExtendAutoConfiguration**`（需存在 `CaptchaAutoConfiguration` 类于 classpath；且 `**loncra.framework.captcha.controller.enabled` 为 true 时，默认**）：

- 提供 `**/captcha`** 下的 `**CaptchaController**`：
  - `GET /captcha/generateToken`：生成绑定 Token；可省略 `type`（走 `defaultCaptchaType`）、`deviceIdentified`（由工具类或 Session 兜底）。
  - `GET|POST /captcha/generateCaptcha`：在 `**Interceptor#verifyCaptcha` 可能要求前置拦截通过** 后，调用 `DelegateCaptchaService#generateCaptcha`。
  - `POST /captcha/verifyCaptcha`：与通用 `verify` 一致。
  - `POST /captcha/clientVerify`：**行为验证码客户端轨迹校验**（`ImageCaptchaTrack` body + `_tianaiCaptchaToken`）。
  - `GET /captcha/tianai.js`：读取配置的 JS 内容，把 `**TianaiCaptchaProperties.JS_BASE_URL_TOKEN`** 占位符替换为 `**apiBaseUrl**`，使前端以当前站点为 API 根（见 `TianaiCaptchaProperties` 常量与 `CaptchaController#tianaiJs`）。

`CaptchaExtendAutoConfiguration` 在 `**@ConditionalOnMissingBean(TianaiCaptchaService.class)**` 为真时会先注册 `**ControllerTianaiCaptchaService**`；随后 `CaptchaAutoConfiguration` 中默认的 `TianaiCaptchaService` Bean 因**已存在子类实现**而**不会**再注册。子类在 `**createGenerateArgs`** 里将 `**jsUrl**` 设为 `**{apiBaseUrl}/captcha/tianai.js**` 的 **HTTP 地址**（`CaptchaController` 对外提供该 JS，并对 `**JS_BASE_URL_TOKEN`** 做占位符替换），便于浏览器直连。

**仅**引入本模块、**不**走上述 MVC 扩展时，由 `CaptchaAutoConfiguration` 提供 `**TianaiCaptchaService`** 基类 Bean，其 `createGenerateArgs` 中 `**jsUrl` 为 `TianaiCaptchaProperties#jsPath**` 所指的 `**classpath:.../tianai-captcha.js` 资源**（或你自定义的 classpath 路径），需由应用自行通过静态资源等方式暴露，或自写 `Controller` 做同等替换。

> 构造用元数据里 `args.generate` 会带 `**jsUrl`** 等键，与前端初始化脚本引用方式一致；具体键名以 `TianaiCaptchaProperties` 中常量为准。

## 原生 JavaScript 与 `tianai-captcha.js`

以下说明对应当前随模块分发的 `classpath:tianai/js/tianai-captcha.js`（MVC 下经 `/captcha/tianai.js` 输出，服务端会把 `**$baseUrlToken$**` 替换为 `loncra.framework.captcha.tianai.api-base-url`）。脚本末尾将构造函数挂在 `**window.TianaiCaptcha**` 上，且已**内联** HTTP 客户端（**无需**再单独引入 axios）。

### 1. 页面资源


| 资源  | 典型地址                              | 说明                                                                                                                                                        |
| --- | --------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 主脚本 | `{apiBaseUrl}/captcha/tianai.js`  | 在业务页面用 `<script src="...">` 引入，放在业务脚本**之前**                                                                                                               |
| 样式  | `{apiBaseUrl}/tianai-captcha.css` | 由组件在 `show()` 时**动态插入** `<link>`（`href = baseUrl + "/tianai-captcha.css"`），需保证应用可对外提供 `**/tianai-captcha.css`**（如 Spring Boot 默认的 `static` 资源，或网关转发到同一前缀） |


`baseUrl` 与脚本内 Axios 的 `**baseURL` 一致**；其下相对路径为 `**/captcha/generateToken`**、`**/captcha/generateCaptcha**`、`**/captcha/clientVerify**`，因此 `**apiBaseUrl` 必须与本页访问后端的**协议、主机、端口、上下文根**一致**（有网关时填对外统一入口）。

### 2. 构造函数与常用配置

通过 `**new TianaiCaptcha(config)`** 创建实例，再调用 `**show()**` 弹出层并拉取首帧验证码。`config` 中与后端约定最相关的字段包括：

- `**baseUrl**`：API 根地址；未传时脚本内为占位 `**$baseUrlToken$**`，**必须由服务端**替换，浏览器里应是完整 origin（如 `http://localhost:8080`），**不要**以 `/` 结尾。
- `**token`**：绑定令牌的**值**（`CacheProperties#name`），与 `**GET /captcha/generateToken?type=tianai&deviceIdentified=...`** 返回体里 `**token.name**` 一致。未设置时若首次 `generateCaptcha` 失败，脚本在收到 `**10404**` 业务码时会**自动**再请求 `generateToken` 并重试（见下节）。
- `**generateType`**：子类型，对应 `TianaiRequestBody#generateImageType`；不填时脚本默认 `**random**`，与后端的 `random` 特殊值一致。也可显式传 `**SLIDER` / `ROTATE` / `CONCAT**` 等（小写会由服务转成小写，与 tianai 行为一致）。
- `**postConfig**`：默认 `**captchaParamName: "_tianaiCaptcha"**`、`**tokenParamName: "_tianaiCaptchaToken"**`，与 `**TianaiCaptchaProperties#captchaParamName**` 及 `AbstractCaptchaService#getTokenParamName()` 的默认约定一致；**若你改了后端配置，这里必须同步**。
- `**success` / `fail`**：轨迹提交 `**clientVerify**` 后根据返回 `**executeCode === "200"**` 走成功/失败；成功时**服务端**在 `data` 中返回 **MD5 十六进制串**，你应在**登录/敏感操作**的表单中把它写入**隐藏域**并随 `**_tianaiCaptchaToken`** 一起提交，或再调 `**POST /captcha/verifyCaptcha**` 完成「最终一次校验」。

其他 UI 向字段如 `**title`、`width`、`loadingText`、`validText`、各类型 `slider`/`rotate`/`concat` 的 `prompt`/`onMove**` 等可保持默认或按需覆写（见脚本的默认配置与 `doGenerateHtml` 分支逻辑）。

### 3. 与后端的请求顺序（脚本内置）

下列路径均以 `**this.config.baseUrl` 为基址** 拼接，与 `**CaptchaController`** 一致（无前缀时即为应用根路径 + `/captcha/...`）：

1. `**GET /captcha/generateCaptcha?` +**
  `tokenParamName=...&captchaType=tianai&generateImageType=...`  
   用于拉取 `**ImageCaptchaInfo` JSON**（在统一包装体中），再渲染底图、模板图、滑块等。
2. 若上述请求失败且响应中 `**executeCode === "10404"`**，会 `**GET /captcha/generateToken?type=tianai**`，将返回的 `**token.name**` 赋给 `**this.config.token**` 并**重新**请求 `generateCaptcha`。
3. 用户松手后 `**POST /captcha/clientVerify?` +** `tokenParamName=...`，**Body 为行为轨迹 JSON**（含 `startTime`/`stopTime`/`trackList` 等，与 tianai 的 `ImageCaptchaTrack` 结构一致），对应 `**TianaiCaptchaService#clientVerify`**。
4. 返回 `**executeCode` 为 `200**` 时，在 `**config.success` 中可取到摘要**；随后业务请求需携带 `**_tianaiCaptcha`**、`**_tianaiCaptchaToken**`，并在需要时带 `**captchaType=tianai**` 或头 `**X-CAPTCHA-TYPE: tianai**`，以通过 `**verifyCaptcha` 或 `CaptchaVerificationFilter**`。

### 4. 最简页面示例（原生 JS）

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>天爱行为验证码</title>
</head>
<body>
<button type="button" id="open">打开验证</button>
<!-- 通过全局脚本引入：地址需与 loncra.framework.captcha.tianai.api-base-url 一致 -->
<script src="http://localhost:8080/captcha/tianai.js"></script>
<script>
  document.getElementById('open').addEventListener('click', function () {
    // 1）先取绑定 token（与脚本自动 10404 拉 token 二选一；此处便于控制 deviceIdentified）
    fetch('http://localhost:8080/captcha/generateToken?type=tianai&deviceIdentified=demo-native-js')
        .then(function (r) { return r.json(); })
        .then(function (body) {
          // 2）按项目统一 RestResult 结构解包；下标需与你方全局响应包装一致
          var name = (body.data && body.data.token && body.data.token.name) || body.token.name;
          var t = new window.TianaiCaptcha({
            baseUrl: 'http://localhost:8080',
            token: name,
            generateType: 'SLIDER', // 或 'random' / 'ROTATE' 等
            success: function (res) {
              // clientVerify 成功：res.data 为 _tianaiCaptcha 应提交的 MD5
              console.log('tianai digest', res.data);
            },
            fail: function (e) { console.error(e); }
          });
          t.show();
        });
  });
</script>
</body>
</html>
```

说明：

- 上例中 `**body.data.token.name` 的读取路径** 取决于应用是否用 `**RestResult`** 再包一层；若控制器直接返回 `**BuildToken**` 而无外层 `data`，应改为与真实 JSON 一致。
- **登录表单** 提交时除用户名密码外，请增加隐藏域 `**_tianaiCaptcha`**（值为成功回调里拿到的 **MD5**）与 `**_tianaiCaptchaToken`**（与本次 `**generateToken` / 实例上的 `config.token` 相同**），否则 `**verifyCaptcha`** 或过滤器会校验失败。

## 受保护 URL：`CaptchaVerificationFilter`

- 配置项 `**verifyUrls**`：Ant 风格路径（见 `AntPathMatcher`，大小写是否敏感由 `**filterAntPathMatcherCaseSensitive**` 控制）。
- 命中的请求必须在请求中携带**验证码类型**，并交给 `**CaptchaVerificationService`** 列表中匹配 `type` 的实现；tianai 的 `**TianaiCaptchaVerificationService.getType()**` 返回**仅含** `tianai`。
- 可注册多个 `**CaptchaVerificationInterceptor`** 做 `preVerify`；若**全部**返回成功，**可跳过**本过滤器内的验证码强校验，直接放行（用于已登录、白名单等策略）。
- 验证成功后可选择删除服务端缓存，行为由 `**verifySuccessDelete` 参数** 与 `**alwaysVerifySuccessDelete`** 等共同决定（以源码与请求参数为准）。

> 注意：`CaptchaProperties` 中存在 `**filterOrderValue` 字段**；`CaptchaAutoConfiguration` 中过滤器注册**当前**使用 `**Ordered.HIGHEST_PRECEDENCE`**。若你依赖的排序与配置不一致，请以**源码实际注册顺序**为准，或在业务侧用自定义 `FilterRegistrationBean` 调整。

## 配置示例

### 1. 全局

```yaml
loncra:
  framework:
    captcha:
      enabled: true
      default-captcha-type: tianai
      captcha-type-header-name: X-CAPTCHA-TYPE
      captcha-type-param-name: captchaType
      # 受保护 URL（登录、短信发送等）
      verify-urls:
        - /api/auth/login
        - /api/sensitive/**
      build-token-cache:
        name: loncra:framework:captcha:build-token:
        expires-time:
          value: 5
          unit: MINUTES
      interceptor-token-cache:
        name: loncra:framework:captcha:interceptor-token:
        expires-time:
          value: 5
          unit: MINUTES
      controller:
        enabled: true
```

> `controller.enabled` 由 **web-mvc** 模块的 `CaptchaExtendAutoConfiguration` 使用；**仅**引入 `spring-boot-starter-captcha` 时该配置项无效果。

### 2. tianai 专用

```yaml
loncra:
  framework:
    captcha:
      tianai:
        # 与 CaptchaController 中替换 JS 占位符、前端请求 API 基址一致
        api-base-url: http://localhost:8080
        # 滑动轨迹与服务端时间窗（秒，见 TianaiCaptchaService#matchesCaptcha）
        server-verify-timeout:
          value: 2
          unit: MINUTES
        captcha-expire-time:
          value: 5
          unit: MINUTES
        # 可选项：为不同 template tag 设容错
        tolerant-map:
          default: 0.0
        # 可选项：按验证码类型 name 提供资源/模板，类型名与 tianai 的 CaptchaTypeConstant 一致
        # resource-map:
        #   SLIDER: [ { type: ..., data: "...", tag: "default" } ]
        # template-map:
        #   ROTATE: [ { tag: "default", activeImage: { ... } } ]
```

## 主要扩展点


| 接口 / 类                                                              | 作用                                                                                          |
| ------------------------------------------------------------------- | ------------------------------------------------------------------------------------------- |
| 自定义 `**CaptchaService**` 实现                                         | 新的验证码类型：实现并注册为 Spring Bean 即可被 `DelegateCaptchaService` 收集；**勿**与已有实现 **重复** 同一 `getType()` |
| `**Interceptor`**                                                   | 默认 `DelegateCaptchaInterceptor` 可换；在生成/校验链路上插入自己的逻辑                                         |
| `**CaptchaStorageManager**`                                         | 自定义持久化/加密存储                                                                                 |
| `**CaptchaVerificationService` / `CaptchaVerificationInterceptor**` | 扩展过滤器支持的校验方式与放行策略                                                                           |


## 推荐实践

- 生产环境优先 **Redisson** 存储，并合理设置 `buildTokenCache` / `interceptorTokenCache` 过期时间。
- `**apiBaseUrl` / `TianaiCaptchaProperties.JS_BASE_URL_TOKEN`** 应与对外的网关或真实浏览器访问地址一致，否则前端的 `tianai.js` 内联请求会指向错误 host。
- 为滑块/旋转等类型配置 **合适的 `tolerantMap`** 与 **合理的 `serverVerifyTimeout`**，平衡体验与安全。
- 在 `**verifyUrls**` 上**仅**挂载真正需要人机校验的接口，避免全站每请求都跑验证码链。

## 注意事项

- 本模块依赖 tianai 的 **1.5.5** 版本行为与 API，升级 tianai 大版本时需在集成侧回归生成、轨迹校验与资源加载。
- 元数据里的 `**constrooler` 拼写** 问题出现在 `additional-spring-configuration-metadata.json` 中，实际代码使用的是 `**controller`** 前缀，请以 `**loncra.framework.captcha.controller.enabled**` 为准。
- 若同时引入多个会注册 `TianaiCaptchaService` 的扩展，需留意 `**@ConditionalOnMissingBean(TianaiCaptchaService.class)**` 与 `**ControllerTianaiCaptchaService**` 的互斥关系，保证 Bean 唯一、路径与 `jsUrl` 一致。


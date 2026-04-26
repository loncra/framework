# spring-boot-starter-spring-security

**怎么读这份文档？** 若你刚学 Java/Spring，不必一次读完。先记住三件事即可：本库帮你**装好 Spring Security 的默认行为**（很多项目都要配半天）；**YAML 里改 `loncra.framework` 下前缀** 就能调行为；多出来的 **oauth2、redis、session 子依赖按需要加，不必全加**。

本工程是 **多模块聚合 POM**（父工程不写业务代码、只把子模块打包在一起）。整体目标：在 **Spring Security 6 / Spring Boot 3** 上，为 **前后端分离** 与（可选的）**多认证方式、审计、OAuth2、Redis 缓存、Spring Session** 给出一套默认实现；业务要扩展时，尽量通过**小接口/适配器**改，而不用从第一个 Filter 起全部手写。

## 1. 模块与职责

| 模块 | Artifact | 用一句话说 |
|------|------------|------------|
| **core** | `spring-boot-starter-spring-security-core` | 最常用：安全过滤链、登录失败/没权限/未登录**统一成 JSON**、多类型用户登录的骨架、和 **basic-security** 审计相关的 Bean、有 Feign 时多一个异常解析 等。 |
| **oauth2** | `spring-boot-starter-spring-security-oauth2` | 在依赖齐全时，**内嵌 OAuth2 授权服务 + 用 JWT 当资源服**，并和 core 的**同一套**成功/失败 JSON 对齐。 |
| **redis** | `spring-boot-starter-spring-security-redis` | 有 **Redisson** 时，用 Redis 当**安全相关缓存**；和 oauth2 一起用时，可让 **授权数据存 Redis**（不重启丢）。 |
| **session** | `spring-boot-starter-spring-security-session` | 有 **Spring Session** 时：Session、记住我、**accessToken 与 session 怎么配合** 等（见子模块里自动配置类名字）。 |

依赖关系：**core 是基础**；其它模块都是在它上面**加能力**。

## 2. 设计思路（为什么多封装一层？）

- **统一 JSON 响应**：默认不启用「浏览器表单登录取 HTML」那套，接口返回 JSON 方便接前端/移动端。  
- **多认证类型可插**：同一套「用户名+密码+类型」的入口，实际找用户、校密码，由**你自己实现的若干小 Service** 分工（类型 SecurityPrincipal 相关）。  
- **OAuth2 不另搞一套错误格式**：授权端点/Token 端点的成功与失败，尽量走与 core 相同的 **Json…Handler**，避免**同一项目里**两种风格。  
- **不想抄一整份 Security 配置**：用 **WebSecurityConfigurerAfterAdapter** 这种「在已有链**后面/旁边**加几行」的方式，而不是自己建一个新的 `SecurityFilterChain` 复制粘贴几十行。  
- **有 Mybatis-Plus 时**：在「有登录用户、且本请求允许留痕」时，用 **SecurityPrincipal 增强版**留痕实现，避免**后台批任务**没有用户却误记审计。

## 3. 主开关与相关条件

- 整栈（Servlet 应用、多数 core 类）：`loncra.framework.authentication.spring.security.enabled`（默认不写字段多为 **true**，**彻底关掉**就显式设 `false`，具体以源码 `@ConditionalOnProperty` 为准）。  
- **只关 OAuth2 子能力**（在 classpath 仍有授权服相关类时仍建议显式关）：`loncra.framework.authentication.spring.security.oauth2.enabled`。  
- 另外：`loncra.framework.security.audit.enabled`、`loncra.framework.captcha.*`、`loncra.framework.mybatis.operation-data-trace.enabled` 等，分别管**控制层审计、验证码、数据库留痕实现**等。

## 4. OAuth2 在 YAML 里写哪儿？（你拍板后的统一规则）

**所有** OAuth2 的键（含开关与 RSA 等）都在同一棵树下，前缀为：

`loncra.framework.authentication.spring.security.oauth2`

例如（字段名**随版本以 IDE 对 `OAuth2Properties` 的补全为准**；下面仅示意**层级**）：

```yaml
loncra:
  framework:
    authentication:
      spring:
        security:
          enabled: true          # 总安全栈
          oauth2:
            enabled: true         # 仅 OAuth2 子能力
            public-key: "..."     # 示例：Base64
            private-key: "..."    # 示例
            key-id: "my-key"      # 示例
            issuer: "http://localhost:20080"   # 需要则写
#            authorization-cache:  # 需要时覆盖默认；集群升级见下文「迁移说明」
```

> **注意**：`loncra.framework.authentication` 下还有**别的**子前缀（如 `captcha-verification`、`permit-uri-ant-matcher` 对应字段、session 子模块 等），**不要**和 `spring.security` 那一层**搞混**；`AuthenticationProperties` 是 **authentication** 直接下边的字段，与 **spring.security.oauth2** 是**平行的两段配置树**（都在 `loncra.framework.authentication` 大前缀下，YAML 里**并列**写即可，见本仓库子模块的 `src/test/resources/application.yml` 示例）。

### 从旧 key 迁过来（迁移说明）

若你以前写的是 **`loncra.framework.authentication.oauth2.*`**，请**整体挪到**上面的 **`...authentication.spring.security.oauth2.*`**。  
**Redis 里 OAuth2 授权**若曾经用**默认的缓存 key 前缀**未在配置里改写过：新版默认的 **key 名前缀**与旧版**可能不同**，**升级后**以前缓存在机器上的 token/授权，若**读不到**属预期，需**重登**或在配置里把 `authorization-cache` 的 `name` **显式写回旧名**以兼容老数据（见 `OAuth2Properties` 源码与注释）。

## 5. 自动配置从哪儿进来？

Spring Boot 3 从 **`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`** 里读一行一个类名：

- **core** 有 Feign/验证码/主安全/留痕/默认 Web 安全 等。  
- **oauth2、redis、session** 各有一个自己的 `imports` 文件。  

顺序上，**redis、oauth2、session** 里**部分**类会 `@AutoConfigureBefore` **core** 里的 `SpringSecurityAutoConfiguration`，目的是：先准备 **JWK/缓存/Session 仓库** 等，再装主安全。精确顺序可用 **`--debug` 看报告**或 **Actuator conditions**（这里不展开，避免和新手第一遍阅读抢注意力）。

## 6. 默认的「安全链」在干什么（只记大方向）

- 配置里**放行列表**中的路径不登录可访问，其它路径要**已认证**。  
- 使用 **HTTP Basic** 的某种细节配合 **可审计的 details**（为后面的多类型登录用）。  
- 关闭默认表单登出/缓存重定向等常见「浏览器应用」行为，**偏 API 用法**。  
- 在部分 Filter 前加上 **IP、租户** 等扩展 Filter（**具体类名**见 `DefaultWebSecurityAutoConfiguration`）。  
- 若你注册了 **WebSecurityConfigurerAfterAdapter**，会分**两阶段**被调用，OAuth2/Session 子模块就是用它在**不复制整条链**的前提下**改装**的。

## 7. 你以后最可能要改的扩展点

| 扩展点 | 人话 |
|--------|------|
| `TypeSecurityPrincipalService` 多个 | 「不同登录方式」时**去哪里查用户、怎么验密码** |
| `WebSecurityConfigurerAfterAdapter` | 在**默认**链上**再加**几条规则/Filter，不从头写 `SecurityFilterChain` |
| `JsonAuthentication…Response` | 登录**成功/失败** JSON 长什么样 |
| `OAuth2AuthorizationConfigurerAdapter`（有 oauth2 时）| 授权服各端点、资源服 JWT 的**再定制** |
| 带 `@Plugin` 的**方法** + `PluginSourceAuthorizationManager` | 按**插件/菜单**等做**方法级**是否允许执行 |

## 8. Maven 怎么引？

在应用里至少要有 **core** 对应坐标（**版本**由你方 BOM/父 POM 决定）：

```xml
<dependency>
  <groupId>io.github.loncra.framework</groupId>
  <artifactId>spring-boot-starter-spring-security-core</artifactId>
</dependency>
```

需要 OAuth2 时再加 `spring-boot-starter-spring-security-oauth2`；需要 Redis 时再加 `…-redis`；需要 Session 集成时再加 `…-session`。**oauth2 的 starter 会带上 core，不必再写一遍 core。**

## 9. 其它配置前缀速查

| 前缀 / 类 | 说明 |
|-----------|------|
| `loncra.framework.authentication` | `AuthenticationProperties`：白名单 URL、测试用内存用户、各 token/缓存 名称等。 |
| `loncra.framework.authentication.spring.security` | 子字段 `enabled` 作为**本套** Security 总开关。 |
| `loncra.framework.authentication.spring.security.oauth2` | **全部** `OAuth2Properties`（**含**子开关 `enabled`、**RSA、issuer、authorization-cache 等**）。见上文「统一规则」。 |
| `loncra.framework.authentication.plugin` | 插件信息（Actuator 上暴露的树等）。 |
| `loncra.framework.authentication.captcha-verification` | 验证码。 |
| `loncra.framework.authentication.controller.audit` | 控制层审计。 |
| `loncra.framework.authentication.data-owner` | 数据属主/留痕主体等。 |
| `loncra.framework.authentication.session` 及 `…remember-me` | 仅**session 子模块** 场景。 |
| `loncra.framework.authentication.access-token` | 例如只控制**是否**注册刷新 AccessToken 的 Controller（`enable-refresh-access-token: true` 等）。 |


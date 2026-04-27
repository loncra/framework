# spring-boot-starter-wechat

对**微信小程序**与**微信公众号**开放接口做轻量封装：在 **稳定版 `access_token`（`stable_token`）**、**Redisson 缓存**、**Nacos 定时刷新** 与**并发锁**（与 `AbstractWechatBasicService` 内 `@NacosCronScheduled` + `@Concurrent` 一致）之上，提供常用 **HTTP API** 方法。适合与现有 `commons` 的 `AccessToken`、业务异常体系一起用。

## 主开关

| 键 | 说明 |
|----|------|
| `loncra.framework.wechat.enabled` | 总开关，默认 `true`；`false` 时本 starter 自动配置不生效。 |
| `loncra.framework.wechat.official.enabled` | 是否注册 **公众号** `WechatOfficialService`，默认 **`false`**；与仅使用小程序的部署兼容。 |

## 配置树（摘要）

- `loncra.framework.wechat`：`errcode` / `errmsg` 等字段名与成功值（与微信返回一致，默认 `errcode=0` 为成功）。  
- `loncra.framework.wechat.applet`：小程序 `access-token`（`secretId`=`appid`，`secretKey`=`secret`）及缓存名等。  
- `loncra.framework.wechat.official`：公众号**独立**一套 `access-token` 与缓存名（**勿与小程序混用同一缓存 key**）。  
- `loncra.framework.wechat.refresh-access-token-cron`：刷新 token 的 Nacos 定时表达式（见 `AbstractWechatBasicService`）。

## 已封装能力

### 小程序 `WechatAppletService`

- `login`：code2Session（`jscode2session`）  
- `getPhoneNumber`：获取手机号（`getuserphonenumber`）  
- `createAppletQrcode`：无限制码（`getwxacodeunlimit`）；若微信返回 **JSON 错误** body，会按 `errcode` 抛业务异常，避免把错误 JSON 当图片用  
- `sendSubscribeMessage`：订阅消息  
- `generateWxaUrlScheme` / `generateWxaUrlLink`：加密 scheme、short link，用于 H5/短信等打开小程序  

### 公众号 `WechatOfficialService`（需 `official.enabled=true`）

- `getUserList`：关注者 openid 列表  
- `createQrcode`：带参数二维码（`param` 同官方文档的 `action_name` / `action_info`）  
- `createMenu` / `getMenu` / `deleteMenu`：菜单  
- `sendTemplateMessage`：模板消息  
- `getOAuth2AccessToken` / `getSnsUserInfo`：网页授权 code 换 token、拉取用户信息（需已获 `snsapi_userinfo` 等）  
- `sendKfText`：客服文本消息（易扩展为其它 `msgtype`）  

**说明**：各方法入参/出参为 `Map` 或与现有元数据类配合，**以微信当前文档为准**；模板/类目、订阅消息等会随平台调整，请自行在业务层校验字段。

## 依赖前提

- `spring-boot-starter-alibaba-nacos`（定时任务）、`spring-boot-starter-idempotent`（Redisson + 并发）、`spring-boot-starter-web-mvc` 等已在本 `pom.xml` 声明；运行环境需能连 **微信 API** 且已配置 **appid/secret** 与 **Redis/Redisson** 等 idempotent 所需能力。

## 测试建议

本地在 `application.yml` 中配置 `loncra.framework.wechat.applet.access-token.secret-id` / `secret-key`（与公众平台一致），调用 `wechatAppletService.refreshAccessToken()` 或启动后观察 `WechatAppletService#afterPropertiesSet` 的日志。公众号需额外 `loncra.framework.wechat.official.enabled: true` 与 **另一套** appid/secret（若同主体仅一种应用，可只开小程序或只开公众号之一）。

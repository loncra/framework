# Loncra Framework

<div align="center">

![Java](https://img.shields.io/badge/Java-21+-orange.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-green.svg)

**基于 Spring Boot 3 的 Java 框架扩展库：通用契约与工具 + 可组合的 Spring Boot Starters**

[项目结构](#-仓库结构与构建) • [模块索引](#-模块索引) • [快速开始](#-快速开始) • [文档](#-文档)

</div>

---

## 📖 项目简介

本仓库是 **多模块 Maven 工程**（根 `parent` 聚合版本与依赖管理），在 **`commons`** 统一契约之上，提供大量 **Spring Boot Starter**，覆盖 Web API 形态、安全与审计、访问加密、读写分离数据源、MyBatis / MyBatis-Plus、幂等与并发、Nacos（配置与可选服务发现事件）、MinIO、Canal、验证码、微信开放平台、Socket.IO 等常见企业场景。

设计取向：**可单独引入某一 Starter**，按业务需要组合；各模块详细行为以 **各子目录下的 `README.md`** 为准（篇幅较长，根文档只做导航与总览）。

---

## 🧱 仓库结构与构建

| 路径 | 说明 |
|------|------|
| [`parent/pom.xml`](parent/pom.xml) | 聚合父 POM：`groupId`=`io.github.loncra.framework`，当前版本 **`3.0.1-SNAPSHOT`**，统一 Java **21**、Spring Boot 3.x 等 |
| [`commons/`](commons/) | 无 Spring 强绑定的通用工具、异常、分页、租户、REST 包装、Jackson 扩展、领域模型等 |
| [`datasource/`](datasource/) | 无 Spring 绑定的读写分离 `DataSource` 实现（主从路由、从库策略） |
| [`starter/`](starter/) | 各 `spring-boot-starter-*` 与自动配置 |

---

## ✨ 能力总览（按领域）

| 领域 | 内容摘要 |
|------|----------|
| **基础与契约** | [`commons`](commons/README.md)：枚举、分页、异常、`RestResult`、租户、`CastUtils`、Snowflake、查询抽象、MinIO DTO 等 |
| **数据源** | [`datasource`](datasource/README.md)：读写分离；[`spring-boot-starter-datasource`](starter/spring-boot-starter-datasource/README.md)：Boot 自动配置主从 `DataSource` |
| **HTTP API** | [`spring-boot-starter-web-mvc`](starter/spring-boot-starter-web-mvc/README.md)：`RestResult` 包装、错误收敛、UA 设备、枚举 Actuator、`RestTemplate` 拦截器标记等 |
| **安全与审计** | [`spring-boot-starter-basic-security`](starter/spring-boot-starter-basic-security/README.md)：审计存储扩展、脱敏/忽略上下文等；[`spring-boot-starter-spring-security`](starter/spring-boot-starter-spring-security/README.md)：Security 6 多模块（core / oauth2 / redis / session） |
| **加解密** | [`spring-boot-starter-access-crypto`](starter/spring-boot-starter-access-crypto/README.md)：AES/RSA/SM 等与算法门面（当前以工具库形态为主，见该 README） |
| **幂等与并发** | [`spring-boot-starter-idempotent`](starter/spring-boot-starter-idempotent/README.md)：`@Concurrent`、`@Idempotent` + Redisson |
| **注册配置中心** | [`spring-boot-starter-alibaba-nacos`](starter/spring-boot-starter-alibaba-nacos/README.md)：`@NacosCronScheduled` 动态 cron；可选 Naming → Spring 事件 |
| **对象存储** | [`spring-boot-starter-minio`](starter/spring-boot-starter-minio/README.md)：异步 MinIO 模板、Console API、过期删除等 |
| **持久层** | [`spring-boot-starter-mybatis`](starter/spring-boot-starter-mybatis/README.md)、[`spring-boot-starter-mybatis-plus`](starter/spring-boot-starter-mybatis-plus/README.md) |
| **数据变更** | [`spring-boot-starter-alibaba-canal`](starter/spring-boot-starter-alibaba-canal/README.md)：Canal 订阅与通知链 |
| **验证码** | [`spring-boot-starter-captcha`](starter/spring-boot-starter-captcha/README.md)：验证码契约 + tianai 实现 |
| **即时通信** | [`spring-boot-starter-netty-socketio`](starter/spring-boot-starter-netty-socketio/README.md)：聚合 POM；业务依赖 **`-core`**，契约依赖 **`-api`** |
| **微信** | [`spring-boot-starter-wechat`](starter/spring-boot-starter-wechat/README.md)：小程序/公众号 HTTP 封装、`stable_token`、独立缓存与可选公众号 Bean |

---

## 📦 模块索引

下列 **Artifact** 均在父工程 `io.github.loncra.framework` 下，版本与 [`parent/pom.xml`](parent/pom.xml) 一致。**详细说明请点击对应 README**（仓库根目录下 **无** 统一的 `docs/` 目录，请以各模块文档为准）。

| Artifact | 一句话说明 | 文档 |
|----------|------------|------|
| **commons** | 框架公共契约与工具（非 Boot Starter） | [commons/README.md](commons/README.md) |
| **datasource** | 读写分离数据源核心实现（非 Boot） | [datasource/README.md](datasource/README.md) |
| **spring-boot-starter-access-crypto** | 访问加解密与国密等算法门面 | [starter/…/README.md](starter/spring-boot-starter-access-crypto/README.md) |
| **spring-boot-starter-alibaba-canal** | Canal 订阅、binlog 解析与通知 | [starter/…/README.md](starter/spring-boot-starter-alibaba-canal/README.md) |
| **spring-boot-starter-alibaba-nacos** | Nacos 动态 cron + 可选服务发现事件桥接 | [starter/…/README.md](starter/spring-boot-starter-alibaba-nacos/README.md) |
| **spring-boot-starter-basic-security** | 审计仓库扩展、脱敏/忽略上下文、安全领域模型 | [starter/…/README.md](starter/spring-boot-starter-basic-security/README.md) |
| **spring-boot-starter-captcha** | 验证码协议与 tianai 集成 | [starter/…/README.md](starter/spring-boot-starter-captcha/README.md) |
| **spring-boot-starter-datasource** | 读写分离 DataSource 自动配置 | [starter/…/README.md](starter/spring-boot-starter-datasource/README.md) |
| **spring-boot-starter-idempotent** | `@Concurrent` / `@Idempotent` | [starter/…/README.md](starter/spring-boot-starter-idempotent/README.md) |
| **spring-boot-starter-minio** | MinIO 异步模板与可选 Console / 定时清理 | [starter/…/README.md](starter/spring-boot-starter-minio/README.md) |
| **spring-boot-starter-mybatis** | MyBatis TypeHandler、JSON 集合拦截、写操作留痕扩展点 | [starter/…/README.md](starter/spring-boot-starter-mybatis/README.md) |
| **spring-boot-starter-mybatis-plus** | MP 插件链、加密、多租户、审计留痕等 | [starter/…/README.md](starter/spring-boot-starter-mybatis-plus/README.md) |
| **spring-boot-starter-netty-socketio** | Socket.IO 聚合工程（请依赖 **-core** / **-api**） | [starter/…/README.md](starter/spring-boot-starter-netty-socketio/README.md) |
| **spring-boot-starter-spring-security** | Spring Security 多子模块封装 | [starter/…/README.md](starter/spring-boot-starter-spring-security/README.md) |
| **spring-boot-starter-web-mvc** | 统一 REST、错误、设备信息、枚举端点等 | [starter/…/README.md](starter/spring-boot-starter-web-mvc/README.md) |
| **spring-boot-starter-wechat** | 微信小程序 / 公众号 API 封装 | [starter/…/README.md](starter/spring-boot-starter-wechat/README.md) |

> **spring-security** 下还有 **core / oauth2 / redis / session** 等子模块，结构见 [spring-boot-starter-spring-security/README.md](starter/spring-boot-starter-spring-security/README.md)。  
> **netty-socketio** 下还有 **api / core** 子模块，见 [spring-boot-starter-netty-socketio/README.md](starter/spring-boot-starter-netty-socketio/README.md)。

---

## 🚀 快速开始

### 环境要求

- **JDK 21+**
- **Maven 3.6+**
- 目标运行时：**Spring Boot 3.x**（与父 POM 管理的依赖版本一致）

### Maven 依赖示例

在业务工程的 `pom.xml` 中引用父 BOM（推荐）或直接写版本：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-web-mvc</artifactId>
    <version>3.0.1-SNAPSHOT</version>
</dependency>
```

具体传递依赖、可选依赖与 **关闭开关**（如 `loncra.framework.mvc.enabled`）见各 Starter 的 README。

---

## 📚 文档

- **权威文档**：各模块目录下的 **`README.md`**（上表已列链接）。  
- 历史上若存在指向 `docs/*.md` 的旧链接，**当前仓库未包含该目录**；请以模块 README 为准。

---

## 🔧 配置说明

各 Starter 使用 **`loncra.framework.*`**、**`spring.cloud.nacos.*`** 等前缀；细则见对应 README 与模块内 `META-INF/additional-spring-configuration-metadata.json`（若有）。

---

## 🤝 贡献

欢迎提交 Issue 与 Pull Request。

---

## 📄 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

---

## 👨‍💻 作者

- **maurice.chen** — [maurice.chen@foxmail.com](mailto:maurice.chen@foxmail.com)

---

## 🙏 致谢

感谢所有为本项目做出贡献的开发者。

---

<div align="center">

**若本项目对你有帮助，欢迎 Star**

</div>

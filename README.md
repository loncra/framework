# Framework Support

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-green.svg)

**Java 框架扩展库，为日常开发提供基础工具类和 Spring Boot Starter 集成**

[功能特性](#-功能特性) • [快速开始](#-快速开始) • [模块说明](#-模块说明) • [使用示例](#-使用示例) • [文档](#-文档)

</div>

---

## 📖 项目简介

`framework-support` 是一个功能丰富的 Java 框架扩展库，提供了大量实用的工具类和 Spring Boot Starter，帮助开发者快速构建企业级应用。本项目基于 Spring Boot 3.x 开发，提供了包括加密解密、服务发现、权限管理、文件存储、幂等控制、数据同步等在内的完整解决方案。

## ✨ 功能特性

- 🔐 **安全加密** - 完整的加解密解决方案，支持 AES、RSA、Hash 等多种算法
- 🚀 **服务发现** - 基于 Nacos 的服务注册与发现，支持动态配置和事件监听
- 🔒 **权限管理** - 完整的 Spring Security 扩展，支持多种认证方式和审计功能
- 📁 **文件存储** - MinIO 客户端封装，简化文件上传下载操作
- 🔄 **幂等控制** - 基于分布式锁的幂等性和并发控制
- ✅ **验证码** - 统一的验证码服务接口，支持多种验证码类型
- 💾 **数据同步** - 基于 Canal 的 MySQL 数据变更监听和同步
- 💬 **即时通讯** - 基于 Netty Socket.IO 的实时推送；拆分为 **api**（消息模型 / 轻量契约）与 **core**（服务端 Starter 与自动配置）
- 🔍 **数据审计** - 完整的操作日志和数据变更追踪

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- Spring Boot 3.x

### Maven 依赖

在您的 `pom.xml` 中添加：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-web-mvc</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

## 📦 模块说明

本项目包含以下模块：

| 模块 | 说明 | 文档 |
|------|------|------|
| **commons** | 通用工具类库，包含反射、类型转换、枚举工具等 | [查看文档](docs/commons.md) |
| **access-crypto** | 加解密工具，支持 AES、RSA、Hash 等算法 | [查看文档](docs/access-crypto.md) |
| **alibaba-nacos** | Nacos 集成，支持动态配置和服务发现事件 | [查看文档](docs/alibaba-nacos.md) |
| **basic-security** | 基础安全模块，提供审计和权限管理功能 | [查看文档](docs/basic-security.md) |
| **minio** | MinIO 客户端封装，简化文件存储操作 | [查看文档](docs/minio.md) |
| **idempotent** | 幂等性和并发控制，基于分布式锁实现 | [查看文档](docs/idempotent.md) |
| **spring-security** | Spring Security 扩展，支持多种认证方式 | [查看文档](docs/spring-security.md) |
| **spring-web-mvc** | Spring MVC 扩展，统一 REST 接口规范 | [查看文档](docs/spring-web-mvc.md) |
| **captcha** | 验证码服务，支持多种验证码类型 | [查看文档](docs/captcha.md) |
| **alibaba-canal** | Canal 数据同步，MySQL 变更监听 | [查看文档](docs/alibaba-canal.md) |
| **netty-socketio** | Socket.IO 服务端：根目录 `spring-boot-starter-netty-socketio` 为 **Maven 聚合（pom）**；**`spring-boot-starter-netty-socketio-core`** 为运行时 Starter（一般业务引入）；**`spring-boot-starter-netty-socketio-api`** 为消息模型与 DTO（如 Feign 仅需契约时引入） | [查看说明](starter/spring-boot-starter-netty-socketio/README.md) |
| **mybatis** | MyBatis 扩展，提供 JSON 类型处理器和审计拦截器 | [查看文档](docs/mybatis.md) |
| **mybatis-plus** | MyBatis-Plus 扩展，提供加密解密和数据追踪 | [查看文档](docs/mybatis-plus.md) |

## 📚 文档

详细的模块文档和使用示例请访问：

- [Commons 工具类文档](docs/commons.md)
- [Access Crypto 加解密文档](docs/access-crypto.md)
- [Alibaba Nacos 文档](docs/alibaba-nacos.md)
- [Basic Security 文档](docs/basic-security.md)
- [MinIO 文档](docs/minio.md)
- [Idempotent 幂等控制文档](docs/idempotent.md)
- [Spring Security 文档](docs/spring-security.md)
- [Spring Web MVC 文档](docs/spring-web-mvc.md)
- [Captcha 验证码文档](docs/captcha.md)
- [Alibaba Canal 文档](docs/alibaba-canal.md)
- [Netty Socket.IO 说明](starter/spring-boot-starter-netty-socketio/README.md)
- [MyBatis 文档](docs/mybatis.md)
- [MyBatis-Plus 文档](docs/mybatis-plus.md)

## 🔧 配置说明

各模块的详细配置说明请参考对应模块的文档。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

## 👨‍💻 作者

- **maurice.chen** - *主要开发者* - [maurice.chen@foxmail.com](mailto:maurice.chen@foxmail.com)

## 🙏 致谢

感谢所有为本项目做出贡献的开发者！

---

<div align="center">

**⭐ 如果这个项目对您有帮助，请给它一个星标 ⭐**

</div>

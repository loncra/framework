# spring-boot-starter-netty-socketio

本目录为 **Maven 多模块聚合**（`packaging=pom`），基于 [netty-socketio](https://github.com/mrniko/netty-socketio) 提供 **Socket.IO 服务端**能力，并与 Spring Web MVC 返回体、访问令牌/安全上下文、**Redisson** 存储、**Nacos**（由 `core` 传递）等完成集成。

## 子模块与依赖方式

| 子模块 | ArtifactId | 作用 |
|--------|------------|------|
| **api** | `spring-boot-starter-netty-socketio-api` | 消息模型与元数据（如 `SocketResult`、`UnicastMessageMetadata` 等），依赖 `spring-boot-starter-spring-security-core`，适合 **Feign 契约 / 跨服务只共享类型** |
| **core** | `spring-boot-starter-netty-socketio-core` | 自动配置、`SocketServerManager`、AOP、消息发送解析器；**依赖 api**，是**真正启动** Socket 服务的 **Starter** |

- **在业务应用中启动 Socket 服务**：请依赖 **`spring-boot-starter-netty-socketio-core`**（会**传递**引入 `api`）。
- **仅需要 DTO/契约、不跑 Socket 进程**：可**只**依赖 `spring-boot-starter-netty-socketio-api`。
- 根 POM 坐标 `spring-boot-starter-netty-socketio` **无**可装配类，**不要**当作带实现的单一 Starter 依赖；请**固定**到 `-core` 或 `-api`。

**包名**：

- **api**：`io.github.loncra.framework.socketio.api` 及子包如 `io.github.loncra.framework.socketio.api.metadata`。
- **core**：`io.github.loncra.framework.socketio.core` 及子包如 `io.github.loncra.framework.socketio.core.interceptor`、`io.github.loncra.framework.socketio.core.holder`。

## 模块作用

### core（运行时）

- **自动配置** `SocketClientAutoConfiguration`：在 `loncra.framework.socketio.enabled` 为 `true`（`matchIfMissing` 默认 `true`）时生效；`@AutoConfigureBefore(SpringWebMvcAutoConfiguration.class)`；`@EnableConfigurationProperties` 包含 `SocketProperties`、`AuthenticationProperties`、`SpringWebMvcProperties`（与 `SocketResultResponseBodyAdvice` 等配合）。
- **`SocketServerManager` Bean**（`@ConditionalOnMissingBean`）构造依赖：
  - **`RedissonClient`**
  - **`AccessTokenContextRepository`**（与握手、令牌、以及 `RedissonStoreFactory` 等配合）
  - 容器中所有 `MessageSenderResolver` / `AckMessageSenderResolver` 实现，以及**可选**的 `SocketServerInterceptor`、`AuthorizationInterceptor` 列表
  - 应用**必须**能成功装配 `RedissonClient` 与上述令牌仓库等，否则**无法**创建**管理器**。
- 实现 `CommandLineRunner#run` 中**启动** Socket、`DisposableBean#destroy` 中**停止**；配置使用 `SocketProperties`（**继承** `com.corundumstudio.socketio.Configuration`），可同时配置父类中 **hostname、port、ping 超时**等原生项与下文扩展项。
- **本类注册的** `MessageSenderResolver` 相关 Bean：`UnicastMessageSender`、`BroadcastMessageSenderResolver`、`MultipleUnicastMessageSender`。三者**均**为 `MessageSenderResolver` 实现；单播/广播/多路单播的解析类**还**作为 `AckMessageSenderResolver` 被收集，供 `ackSendMessage` 路由使用。
- **`@SocketMessage` + `SocketResultHolder`（AOP，由 `SocketMessagePointcutAdvisor` 管理）**与 **`SocketResultResponseBodyAdvice`** 支持在**方法**或 **Controller 返回值**上聚合 `SocketResult` 并推送消息。
- **`userCache` 默认**：`SocketProperties` 中缺省为 `CacheProperties.of("loncra:framework:socketio:socket-user", TimeProperties.ofHours(2))`，即**约 2 小时**；下文 YAML 示例中 7 天**仅为**展示如何覆盖，**非**代码默认值。

### api（契约与模型）

- 单播/多播/广播元数据、`SocketResult`、`ReturnValueSocketResult` 等，可在**不**强依赖 `netty-socketio` 的模块中**引用**。

## `SocketClientAutoConfiguration` 中注册的 Bean（以源码为准）

- `getSocketServerManager` → `SocketServerManager`
- `socketResultResponseBodyAdvice` → `SocketResultResponseBodyAdvice`
- `socketMessageInterceptor` → `SocketMessageInterceptor`
- `socketMessagePointcutAdvisor` → `SocketMessagePointcutAdvisor`（`ROLE_INFRASTRUCTURE`）
- `unicastMessageSender` / `broadcastMessageSenderResolver` / `multipleUnicastMessageSender`

> **`AuthorizationInterceptor`、`SocketServerInterceptor`** **未**在自动配置里 `@Bean`；需业务侧 `@Component` 或显式 `@Bean` 提供，再被 `ObjectProvider` 收集注入 `SocketServerManager`。

> `loncra.framework.socketio.enabled` 在 `additional-spring-configuration-metadata.json` 中**单独**补充，与 `SocketClientAutoConfiguration` 的 `@ConditionalOnProperty` 一致（**非** `SocketProperties` 声明字段，故**不**在编译生成的 `spring-configuration-metadata.json` 中）。`SocketProperties` 与父类 `Configuration` 的其余键**见**主 metadata（编译**产物**）。

## `SocketProperties`（`loncra.framework.socketio`）要点

- 继承 `com.corundumstudio.socketio.Configuration`，**同一前缀**下可写 netty-socketio 原生命名项。
- **`userCache`**：`CacheProperties`；**源码**缺省**约 2h** 过期、固定默认 name 前缀，见上。
- **`singleEnded`**：同账号**是否**仅**允许**一**端**在线；为 `true` 时，新端上线会**依据** `SocketServerManager` 中逻辑处理旧端并可能下发**被动断开**事件，事件常量为 `SocketServerManager.SERVER_DISCONNECT_EVENT_NAME`（`"socket_server_disconnect"`）。
- **`ackSendMessageTimeout`**：ACK 未在元数据里**单独**指定**超时**时的**默认**值（`TimeProperties`，**默认 10s**）。
- **`ignoreResultMap`（YAML：`ignore-result-map`）**：键为**事件名**，值为 JsonPath/字段表达式**列表**；在 `MessageSenderResolver#postMessageToJson` 中通过 `ObjectUtils.ignoreObjectFieldToMap` 在**序列化下发前**剔除。
- **`desensitizeResultMap`（YAML：`desensitize-result-map`）**：配置类有 setter，**但**在 `MessageSenderResolver#postMessageToJson` 的**默认**实现**中** **未**读取。发送路径上的**脱敏**走 `io.github.loncra.framework.security.filter.result.IgnoreOrDesensitizeResultHolder`（与 `basic-security` 等过滤器配合）。若**按**事件**静态**映射脱敏，需在**自研**的 `MessageSenderResolver` 或**覆盖** `postMessageToJson` 中**接入**；**以后续源码**为准。

## `spring-boot-starter-netty-socketio-core` 传递依赖（主要）

| 依赖 | 作用（概要） |
|------|--------------|
| `spring-boot-starter-netty-socketio-api` | 上表 |
| `spring-boot-starter-web-mvc` | `SocketResultResponseBodyAdvice`、MVC 配置等 |
| `spring-boot-starter-alibaba-nacos` | 与本框架 Nacos/服务发现**集成**（Nacos 自身 yml 见业务工程） |
| `com.corundumstudio:netty-socketio` | Socket 服务端**核心** |
| `spring-boot-starter-aop` | `@SocketMessage` 切面 |
| `redisson-spring-boot-starter` | `RedissonClient`、`RedissonStoreFactory` 等 |

## 快速开始

### 1. 引入依赖

**Socket 服务端**（**推荐**）：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-netty-socketio-core</artifactId>
    <version>${framework.version}</version>
</dependency>
```

**仅**契约/模型：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-netty-socketio-api</artifactId>
    <version>${framework.version}</version>
</dependency>
```

### 2. 基础配置（`application.yml`）

```yaml
loncra:
  framework:
    socketio:
      enabled: true
      hostname: 0.0.0.0
      port: 9092
      singleEnded: true
      ackSendMessageTimeout:
        value: 10
        unit: SECONDS
      ignore-result-map:
        "order:updated":
          - "$.data.secret"
      user-cache:
        name: loncra:framework:socketio:socket-user
        expires-time:
          value: 7
          unit: DAYS
```

- `ignore-result-map` 的**键**须与**下发**事件名**一致**（如 `order:updated`）；**值**为**忽略**规则列表，语义与 `ObjectUtils`/`JsonPath` 约定一致。上例表示：发送 `order:updated` 时**不再**将 `data.secret` 随 JSON 下行。
- `ack-send-message-timeout` 对应 `TimeProperties` 绑定（`value`+`unit` 或项目既有写法）。
- `user-cache` 的 `name`、`expires-time` 对应 `CacheProperties`；示例 7 天**覆盖**了源码默认 2h。

### 3. 实现 `AuthorizationInterceptor`（可选、常用）

握手与连接/断开**流程**中，框架会结合 `SecurityContext`、`AccessTokenContextRepository` 等恢复 `AuditAuthenticationToken`，并对你注册的 `AuthorizationInterceptor` 用 `isSupport` 选择。请使用**正确**的 import，**不要**使用文档里**虚构的** `import ...io.github...` 反序**路径**。

```java
import com.corundumstudio.socketio.SocketIOClient;
import io.github.loncra.framework.socketio.core.interceptor.AuthorizationInterceptor;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
public class DemoSocketAuthorizationInterceptor implements AuthorizationInterceptor {

    @Override
    public boolean isSupport(SecurityContext securityContext) {
        return securityContext != null
                && securityContext.getAuthentication() instanceof AuditAuthenticationToken;
    }

    // 可选 @Override onConnect / onDisconnect
}
```

**AccessToken** 的 Header / Query 约定见 `SocketServerManager` 握手**逻辑**与项目中的安全/`DeviceUtils` 等配置。

## 消息发送

### 方式一：直接 `SocketServerManager`

元数据类型位于包 **`io.github.loncra.framework.socketio.api.metadata`**：

```java
import io.github.loncra.framework.socketio.api.metadata.BroadcastMessageMetadata;
import io.github.loncra.framework.socketio.api.metadata.MultipleUnicastMessageMetadata;
import io.github.loncra.framework.socketio.api.metadata.UnicastMessageMetadata;
import io.github.loncra.framework.socketio.core.SocketServerManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoSocketPushService {

    private final SocketServerManager socketServerManager;

    public DemoSocketPushService(SocketServerManager socketServerManager) {
        this.socketServerManager = socketServerManager;
    }

    public void pushOne(String deviceId, Object data) {
        socketServerManager.sendMessage(
                UnicastMessageMetadata.of(deviceId, "order:updated", data)
        );
    }

    public void pushMany(List<String> deviceIds, Object data) {
        socketServerManager.sendMessage(
                MultipleUnicastMessageMetadata.of(deviceIds, "notice:batch", data)
        );
    }

    public void pushAll(Object data) {
        socketServerManager.sendMessage(
                BroadcastMessageMetadata.of("notice:broadcast", data)
        );
    }
}
```

**ACK 单次**超时**覆盖**（在元数据 `getMetadata()` 中放入以 `TimeProperties.class.getName()` 为**键**的 `TimeProperties` 实例，与**源码**一致）：

```java
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.socketio.api.metadata.UnicastMessageMetadata;

UnicastMessageMetadata<Object> meta = UnicastMessageMetadata.of(deviceId, "order:confirm", payload);
meta.getMetadata().put(TimeProperties.class.getName(), TimeProperties.ofSeconds(3));
Object ack = socketServerManager.ackSendMessage(meta);
```

### 方式二：`@SocketMessage` + `SocketResultHolder`

- 注解：`io.github.loncra.framework.socketio.core.holder.annotation.SocketMessage`
- 持有者：`io.github.loncra.framework.socketio.core.holder.SocketResultHolder`

```java
import io.github.loncra.framework.socketio.api.SocketResult;
import io.github.loncra.framework.socketio.api.metadata.UnicastMessageMetadata;
import io.github.loncra.framework.socketio.core.holder.SocketResultHolder;
import io.github.loncra.framework.socketio.core.holder.annotation.SocketMessage;
import org.springframework.stereotype.Service;

@Service
public class DemoBusinessService {

    @SocketMessage
    public void handle(String deviceId) {
        SocketResult socketResult = SocketResultHolder.create();
        socketResult.getMessages().add(
                UnicastMessageMetadata.of(deviceId, "task:done", "ok")
        );
    }
}
```

## Controller 返回 `SocketResult`

`ReturnValueSocketResult` 位于 `io.github.loncra.framework.socketio.api`：

```java
import io.github.loncra.framework.socketio.api.ReturnValueSocketResult;
import io.github.loncra.framework.socketio.api.metadata.BroadcastMessageMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo/socket-result")
    public ReturnValueSocketResult<String> test() {
        ReturnValueSocketResult<String> result = new ReturnValueSocketResult<>("ok");
        result.getMessages().add(BroadcastMessageMetadata.of("demo:event", "hello"));
        return result;
    }
}
```

## 扩展与注意事项

- 可实现 `SocketServerInterceptor` 在 Socket 服务**启停**时扩展**逻辑**（见**接口**文档）。
- **生产**环境建议显式配置**监听**、**端口**、**父类** `Configuration` 中的**超时/线程**等，避免**盲目**用默认。
- **`core` 强依赖** `RedissonClient` 与 `AccessTokenContextRepository` 等，请保证**启动**时**能**成功装配；仅引 `api` 时**不会**启动上述**服务端**链。
- **`api` 传递** `spring-boot-starter-spring-security-core`，**不是**与 Spring 无关的**纯** POJO jar。
- 自动配置注册文件：`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 一行 `io.github.loncra.framework.socketio.core.SocketClientAutoConfiguration`。
- **`SocketResultHolder` 策略**：系统属性 `loncra.framework.socket.client.holder.strategy` 可指向**全限定类**名，默认**等价**为 ThreadLocal 实现（`ThreadLocalSocketResultHolderStrategy`），见**源码**。
- 若历史曾混用不区分 `-core` 的单一 jar，请改依赖为 `spring-boot-starter-netty-socketio-core`，并用上文包名修正 import。

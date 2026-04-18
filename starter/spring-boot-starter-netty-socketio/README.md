# spring-boot-starter-netty-socketio

本目录为 **Maven 多模块聚合工程**（`packaging=pom`），基于 `netty-socketio` 提供 Socket.IO 服务端能力，并与 loncra framework 框架中的安全体系、Web 返回体处理、Redisson 缓存等完成集成。

## 子模块与依赖方式

| 子模块 | ArtifactId | 作用 |
|--------|------------|------|
| **api** | `spring-boot-starter-netty-socketio-api` | 消息模型与 DTO（如 `SocketResult`、`UnicastMessageMetadata` 等），依赖较轻，适合 **Feign 契约 / 跨服务复用类型** |
| **core** | `spring-boot-starter-netty-socketio-core` | 自动配置、Socket 运行时、`SocketServerManager`、AOP 与拦截器等；**依赖 api**，适合 **实际启动 Socket 服务的应用** |

- **启动 Socket 服务的业务工程**：请依赖 **`spring-boot-starter-netty-socketio-core`**（会传递引入 api）。
- **仅需契约类型（例如定义 Feign 接口体）**：可只依赖 **`spring-boot-starter-netty-socketio-api`**，避免拉取 netty-socketio、Redisson、Nacos 等运行时依赖。
- 根坐标 **`spring-boot-starter-netty-socketio`** 仅为聚合 POM，**不要**作为带类的 Starter 直接依赖。

**包名约定**：api 中类型位于 `io.github.loncra.framework.socketio.api`（及 `api.metadata`、`api.enumerate` 等）；core 中类型位于 `io.github.loncra.framework.socketio.core`（及 `core.interceptor`、`core.holder` 等子包）。

## 模块作用

### core（运行时）

- 自动装配 Socket.IO 服务端，应用启动时启动服务，关闭时释放资源。
- 内置握手鉴权与连接生命周期管理，支持通过扩展拦截器接入自定义鉴权逻辑。
- 基于 Redisson 保存连接用户认证信息，实现跨节点共享连接态数据。
- 提供消息发送解析器，支持单播、多目标单播、广播/房间广播（类型定义在 **api**）。
- 支持 `ACK` 应答消息发送（`SocketServerManager#ackSendMessage`）。
- 支持通过 `@SocketMessage` + `SocketResultHolder` 在业务方法中聚合消息并自动发送。
- 支持 Controller 返回 `SocketResult` 时自动推送（通过 `SocketResultResponseBodyAdvice`）。

### api（契约与模型）

- 统一消息元数据与结果容器，例如单播（`UnicastMessageMetadata`）、多目标单播（`MultipleUnicastMessageMetadata`）、广播（`BroadcastMessageMetadata`）、`SocketResult` 等。

## 核心能力结构（core）

- `SocketClientAutoConfiguration`：自动配置入口，注册 `SocketServerManager`、消息发送器、AOP 拦截器等 Bean。
- `SocketServerManager`：Socket 服务核心管理器，负责鉴权、连接管理、消息派发、ACK 发送。
- `SocketProperties`：`loncra.framework.socketio` 配置绑定对象（继承 netty-socketio `Configuration`）。
- `AuthorizationInterceptor`（`core.interceptor`）：握手鉴权/上下文恢复扩展点。
- `SocketServerInterceptor`（`core.interceptor`）：Socket 服务启动与销毁生命周期扩展点。

## 快速开始

## 1. 引入依赖

**Socket 服务端应用**（推荐）：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-netty-socketio-core</artifactId>
</dependency>
```

**仅需要消息模型 / Feign DTO**（不启动 Socket 服务）：

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-netty-socketio-api</artifactId>
</dependency>
```

## 2. 添加基础配置

在 `application.yml` 中配置 `loncra.framework.socketio`：

```yaml
loncra:
  framework:
    socketio:
      enabled: true
      hostname: 0.0.0.0
      port: 9092
      single-ended: true
      ack-send-message-timeout: 10s
      ignore-result-map:
        order:updated:
          - "$.data.secret"
      user-cache:
        name: loncra:framework:socketio:socket-user
        expires-time: 
          value: 7
          unit: DAYS
```

说明：

- `enabled`：是否启用自动配置（默认 `true`）。
- 其余如 `hostname`、`port`、`pingTimeout`、`pingInterval` 等参数来自 `netty-socketio Configuration`。
- `single-ended`：是否同账号单端在线，默认 `true`。
- `ack-send-message-timeout`：ACK 消息默认超时时间。
- `ignore-result-map`：按 **Socket 事件名** 配置消息字段忽略规则（表达式语法同 `JsonPath`）。
- `user-cache`：连接用户认证信息缓存配置。
  - `name`：缓存名称前缀。
  - `expires-time`：缓存超时时间（对应 `CacheProperties.expiresTime`）。

`ignore-result-map` 示例说明：

- 配置 `order:updated -> $.data.secret` 后，发送 `order:updated` 事件时，`data.secret` 字段会被移除再下发。

## 3. 实现鉴权扩展（建议）

通过实现 `AuthorizationInterceptor` 接入鉴权扩展与连接生命周期回调：

- 框架在握手阶段会根据 token 还原 **Spring Security 上下文**（这一步你不需要手动实现）。
  - 支持从认证配置对应的 **header** 读取 token。
  - 若 header 未命中，会回退到 URL 参数读取 token。
- 当客户端连接成功或断开时，框架会遍历拦截器，用 `isSupport(securityContext)` 选择“支持当前上下文”的拦截器，并触发 `onConnect/onDisconnect`。

```java
import interceptor.io.github.loncra.framework.socketio.core.AuthorizationInterceptor;
import token.authentication.io.github.loncra.framework.spring.security.core.AuditAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
public class DemoSocketAuthorizationInterceptor implements AuthorizationInterceptor {

    @Override
    public boolean isSupport(SecurityContext securityContext) {
        // 让拦截器“告诉框架：我支持这种上下文”。
        // 新手建议先直接返回 true，后续再按业务(角色/类型/tenant等)细化。
        return securityContext != null
                && securityContext.getAuthentication() instanceof AuditAuthenticationToken;
    }

    // 可选：连接成功回调（例如发送欢迎消息、上报连接状态等）
    // @Override
    // public void onConnect(SocketIOClient client, AuditAuthenticationToken socketAuthenticationToken) {}

    // 可选：断开连接回调（例如清理资源、上报断连原因等）
    // @Override
    // public void onDisconnect(SocketIOClient client) {}
}
```

## 消息发送方式

可通过 `SocketServerManager` 直接发送，也可使用 `@SocketMessage` 进行聚合发送。

## 方式一：直接调用 `SocketServerManager`

```java
import metadata.io.github.loncra.framework.socketio.api.BroadcastMessageMetadata;
import metadata.io.github.loncra.framework.socketio.api.MultipleUnicastMessageMetadata;
import metadata.io.github.loncra.framework.socketio.api.UnicastMessageMetadata;
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

ACK 发送示例：

```java
Object ackResult = socketServerManager.ackSendMessage(
        UnicastMessageMetadata.of(deviceId, "order:confirm", payload)
);
```

按消息覆盖 ACK 超时（可选）：

```java
UnicastMessageMetadata<Object> metadata = UnicastMessageMetadata.of(deviceId, "order:confirm", payload);
metadata.getMetadata().put(
        io.github.loncra.framework.commons.TimeProperties.class.getName(),
        io.github.loncra.framework.commons.TimeProperties.ofSeconds(3)
);
Object ackResult = socketServerManager.ackSendMessage(metadata);
```

## 方式二：`@SocketMessage` + `SocketResultHolder`

在业务方法中聚合待发送消息，方法执行后由 AOP 自动发送：

```java
import io.github.loncra.framework.socketio.api.SocketResult;
import metadata.io.github.loncra.framework.socketio.api.UnicastMessageMetadata;
import holder.io.github.loncra.framework.socketio.core.SocketResultHolder;
import annotation.holder.io.github.loncra.framework.socketio.core.SocketMessage;
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

## 在 Controller 中返回 `SocketResult`

当接口返回 `SocketResult`（或其子类）时，框架会在响应阶段自动发送其中的消息：

```java
import io.github.loncra.framework.socketio.api.ReturnValueSocketResult;
import metadata.io.github.loncra.framework.socketio.api.BroadcastMessageMetadata;
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

## 扩展建议

- 通过自定义 `AuthorizationInterceptor` 统一处理连接/断连回调（`onConnect/onDisconnect`）；通过 `isSupport(securityContext)` 选择是否接管当前认证上下文。
- 通过实现 `SocketServerInterceptor` 在启动/销毁阶段接入监控或资源初始化逻辑。
- 按业务类型约定事件名（如 `order:*`、`notice:*`），便于前后端协同与治理。

## 注意事项

- **core** 默认依赖 Redisson，请确保 Redis 与 Redisson 配置可用。
- **api** 当前依赖 `spring-boot-starter-spring-security-core`（例如 `SocketPrincipal` 等类型链），引入 api 时仍会带入该安全核心模块，并非“零 Spring Security”的纯 POJO jar。
- 若开启 `single-ended=true`，同账号新设备登录会触发旧连接下线通知事件 `socket_server_disconnect`。
- 生产环境建议显式配置 `hostname`、`port`、超时参数与线程参数，避免使用默认值。
- 若此前依赖单一的 `spring-boot-starter-netty-socketio` jar，请改为依赖 **`spring-boot-starter-netty-socketio-core`**，并按上文 **api / core 包名** 调整 import。
- `ignore-result-map` 的 key 必须与发送事件名完全一致（如 `order:updated`）。

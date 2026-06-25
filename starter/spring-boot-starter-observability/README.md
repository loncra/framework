# spring-boot-starter-observability

`spring-boot-starter-observability` 为 loncra framework 提供基于 **Spring Boot 3 Observability**（Micrometer Tracing + OpenTelemetry）的可观测性能力，并与现有 **ES 业务审计**（`@Auditable` / `@OperationDataTrace`）通过 `traceId` 关联。

## 模块定位

| 能力 | 说明 | 存储 |
|------|------|------|
| **链路追踪** | HTTP / JDBC / Security / 幂等 / DB 留痕 Span | Tempo（OTLP） |
| **日志关联** | 自动 MDC 注入 `traceId` / `spanId` | Loki |
| **指标** | HTTP、`loncra.*` 框架指标 | Prometheus |
| **业务审计** | 保留现有机制，自动补 `traceId` | Elasticsearch |
| **API 响应** | `RestResult.metadata` 回传 `traceId` | 前端可见 |

**审计 vs 可观测性**：审计记录「谁做了什么敏感操作」；可观测性记录「请求经过了哪些组件、耗时多少」。两者通过 `traceId` 在 Grafana 中互跳。

## 依赖引入

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-observability</artifactId>
</dependency>
```

通常与 `spring-boot-starter-web-mvc`、`spring-boot-starter-basic-security` 一起使用。

## 最小配置

```yaml
spring:
  application:
    name: order-service

loncra:
  framework:
    observability:
      enabled: true
      audit-correlation:
        enabled: true          # AuditEvent.data 写入 traceId
      response-metadata:
        enabled: true          # RestResult.metadata 回传 traceId

management:
  tracing:
    sampling:
      probability: 1.0         # 开发环境；生产建议 0.1
  otlp:
    tracing:
      endpoint: http://localhost:4318/v1/traces
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true

logging:
  pattern:
    correlation: "[${spring.application.name:},%X{traceId:-},%X{spanId:-}] "
```

## RestResult.metadata 响应示例

```json
{
  "message": "ok",
  "status": 200,
  "executeCode": "200",
  "data": {},
  "metadata": {
    "url": "/api/order/create",
    "traceId": "4bf92f3577b34da6a3ce929d0e0e4736",
    "spanId": "00f067aa0ba902b7",
    "applicationName": "order-service"
  }
}
```

异常路径（`RestResultErrorAttributes`）同样会写入上述 metadata 字段。

## 业务开发者：标记关键 Service

```java
import io.micrometer.observation.annotation.Observed;

@Observed(name = "order.create", contextualName = "create-order")
public Order createOrder(CreateOrderCommand command) {
    // ...
}
```

需启用 AOP（本 starter 已引入 `spring-boot-starter-aop`）。

## 框架自动增强点

| 组件 | Span / 指标 |
|------|-------------|
| Spring MVC | HTTP Root Span（Spring Boot 自动） |
| `RestResponseBodyAdvice` | metadata traceId + `loncra.rest.format.applied` |
| `RestResultErrorAttributes` | 异常 Observation + 错误码指标 |
| `TraceContextAuditWriteInterceptor` | ES 审计 data 注入 traceId |
| `@Concurrent` | `loncra.concurrent.check` |
| `@Idempotent` | `loncra.idempotent.check` |
| `@OperationDataTrace` | `loncra.operation.trace` |
| `@Auditable` | 控制器审计 Observation 属性 |

## 本地验证（Grafana 栈）

```bash
docker compose -f docs/docker-compose-observability.yml up -d
```

- Grafana: http://localhost:3000
- 应用 OTLP 端点: `http://localhost:4318/v1/traces`（或经 otel-collector `4319`）

## 排查手册

1. **用户报错** → 前端拿到 `metadata.traceId`
2. **Grafana Tempo** → 搜索 traceId，查看完整 Span 树
3. **Grafana Loki** → 搜索 `{traceId="..."}` 查看关联日志
4. **ES 审计** → 按 `data.traceId` 查询 `@Auditable` 记录

## 配置项

| 配置键 | 默认值 | 说明 |
|--------|--------|------|
| `loncra.framework.observability.enabled` | `true` | 总开关 |
| `loncra.framework.observability.tracing.enabled` | `true` | 链路追踪 |
| `loncra.framework.observability.logging.correlation-enabled` | `true` | 日志 MDC 关联 |
| `loncra.framework.observability.audit-correlation.enabled` | `true` | 审计 traceId 注入 |
| `loncra.framework.observability.response-metadata.enabled` | `true` | RestResult metadata 回传 |
| `loncra.framework.observability.metrics.enabled` | `true` | 框架自定义指标 |

## 相关文档

- [spring-boot-starter-basic-security 审计文档](../spring-boot-starter-basic-security/README.md)
- [Spring Boot Observability 官方文档](https://docs.spring.io/spring-boot/reference/actuator/observability.html)

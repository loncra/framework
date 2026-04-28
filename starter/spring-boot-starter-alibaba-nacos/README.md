# spring-boot-starter-alibaba-nacos

在 **Spring Cloud Alibaba Nacos**（**配置中心** + **服务发现**）之上，提供两类与本框架协同的能力：

1. **`@NacosCronScheduled`**：把 **Nacos Config** 里配置的 **cron / 时区** 与本地 **Spring 调度任务**绑定，配置变更后**无需重启**即可重绑定时任务（依赖 `spring.cloud.nacos.config` 的刷新与监听）。
2. **（可选）服务发现事件桥接**：在开启开关时，将 Nacos **Naming** 侧的服务实例变更回调，转换为 **Spring `ApplicationEvent`**，便于业务用 `@EventListener` 做进程内「全局」响应。

> 说明：**配置驱动的调度**与**实例变更通知**分别依赖 Nacos 的 **Config** 与 **Naming** 两套客户端能力，彼此独立。

---

## 1. Maven 依赖与传递能力

- **直接传递**：`spring-cloud-starter-alibaba-nacos-discovery`、`spring-cloud-starter-alibaba-nacos-config`、`spring-cloud-starter-bootstrap`、`spring-boot-starter-actuator`，以及本仓库 `commons`、`spring-boot-starter-web-mvc`（测试 scope）。  
- 使用本 starter 前，应用需按 Spring Cloud Alibaba 惯例配置 **Nacos Server 地址**、**命名空间**、**group** 等（见官方文档）。

---

## 2. 自动配置入口

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 注册：

| 类 | 生效条件（摘要） |
|----|------------------|
| `NacosAutoConfiguration` | `spring.cloud.nacos.discovery.enabled` 未关闭（默认视为开启） |

### 2.1 `NacosCronScheduledListener`（动态 cron）

| 条件 | 说明 |
|------|------|
| `spring.cloud.nacos.config.schedule.enabled` 为 `true`（默认 **true**） | 扫描 Bean 上带 `@NacosCronScheduled` 的无参方法，注册调度 |
| 容器中存在自定义 `NacosCronScheduledListener` 类型 Bean | **不**再注册默认实例（`@ConditionalOnMissingBean`） |

### 2.2 `NacosSpringEventManager`（Naming → Spring 事件）

| 条件 | 说明 |
|------|------|
| `spring.cloud.nacos.discovery.event.enabled` **显式为 `true`** | 注册事件管理器；未开启则不加载 |

---

## 3. `@NacosCronScheduled`：Nacos 配置变更驱动调度刷新

### 3.1 行为概要

- 启动时对命中 `@NacosCronScheduled` 的方法构建 **`CronScheduledInfo` / `NacosCronScheduledInfo`**，其中 **cron / zone 使用占位符** `${property}` 时，会把属性名写入缓存，与 **Nacos 下发配置**中的键关联。
- `afterPropertiesSet` 中根据 `NacosConfigProperties`（含是否 `refreshEnabled`、默认 `dataId`、profile、`extensionConfigs` / `sharedConfigs` 且 `refresh=true` 等）为对应配置 **添加 Config 监听器**。
- 配置推送触发 `configReceive`：解析 `dataId` 对应内容，匹配缓存中「属性是否变化」，若变化则 **取消旧 `ScheduledTask`**，按新 cron / 时区 **重新调度**；若表达式为 Spring 约定的禁用值（与 `ScheduledTaskRegistrar.CRON_DISABLED` 一致），则停止该任务。

### 3.2 使用注意

- **必须无参方法**（见 `NacosCronScheduledListener#createRunnable` 断言）。
- AOP 基础设施 Bean、`ScheduledExecutorService`、`TaskScheduler` 等 Bean **不参与**注解扫描。
- 依赖 **`spring.cloud.nacos.config.refresh-enabled=true`**（或等价行为）才会对默认/扩展配置注册监听；否则可能根本不监听配置变更。

---

## 4. `NacosSpringEventManager`：服务发现事件（可选）

### 4.1 设计意图

Nacos Java Client 通过 **`NamingService.subscribe(serviceName, group, listener)`** 可在**实例列表变更**时收到回调（如 `NamingChangeEvent`）。Spring 本身不提供「全局微服务上下线」的统一应用事件；本模块把回调 **封装为 Spring `ApplicationEvent`**，便于与现有 `@EventListener` 体系一致。

### 4.2 生命周期（阅读源码时的顺序）

1. 当前应用 **完成向 Nacos 的实例注册**后，触发 **`InstanceRegisteredEvent`**，`NacosSpringEventManager#onInstanceRegisteredEvent` 调用 **`subscribeService()`**（无参）：拉取当前客户端 **`NacosDiscoveryProperties` 所用 group** 下的服务名列表（`NamingService#getServicesOfServer`），再对每个名称调用 **`subscribeService(serviceName, group, namespace)`**。
2. **`subscribeService(String serviceName, ...)`** 内：若尚未为该三元组建立监听，则创建 **`NacosServiceEventListener`**，通过校验器（可选）后加入缓存，调用 **`NamingService.subscribe`**，并发布 **`NacosServiceSubscribeEvent`**（载荷为 **`NamingEvent`**）。
3. **实例变更**：监听器 **`onEvent`** 中若收到 **`NamingChangeEvent`**，则发布 **`NacosInstancesChangeEvent`**（载荷为 **`NamingChangeEvent`**）。
4. **定时任务**：`subscribeService` / `unsubscribeService` 方法本身带有 **`@NacosCronScheduled`**，占位符分别为 **`${spring.cloud.nacos.discovery.event.subscribe-service-cron:30 0/1 * * * ?}`** 与 **`${spring.cloud.nacos.discovery.event.unsubscribe-service-cron:0 0/1 * * * ?}`**（即未配置时：每分钟第 **30** 秒扫描订阅、第 **0** 秒处理过期取消）；过期时间 **`expire-unsubscribe-time`**（默认约 1 小时未访问监听器则视为过期）配合 **`unsubscribeService`** 取消订阅并发布 **`NacosServiceUnsubscribeEvent`**。  
   > 说明：`NacosDiscoveryEventProperties` 类里另有常量默认值字符串，与上述占位符默认值**不一致**；以 **`@NacosCronScheduled` 上 `${...:...}`** 为准。
5. **容器销毁**：`DisposableBean#destroy` 中会标记监听过期并执行取消订阅逻辑。

### 4.3 Spring 事件一览

| 事件类型 | 触发时机 | `getSource()` / 载荷类型 |
|----------|----------|---------------------------|
| `NacosServiceSubscribeEvent` | 首次对某服务建立订阅并成功 `subscribe` 后 | `NamingEvent` |
| `NacosServiceUnsubscribeEvent` | 过期清理时取消订阅 | `NamingEvent` |
| `NacosInstancesChangeEvent` | Nacos 推送 **`NamingChangeEvent`** 时 | `NamingChangeEvent` |

业务侧示例：`@EventListener NacosInstancesChangeEvent`，从事件中取出 `NamingChangeEvent` 再解析实例列表（需查阅 Nacos Client 类型 API）。

### 4.4 扩展：`NacosServiceListenerValidator`

实现 **`NacosServiceListenerValidator`** 并注册为 Spring Bean，可按 **`NamingEvent`** 决定是否 **`subscribeValid`**，用于过滤不需要订阅的服务。

### 4.5 实现细节（阅读源码时请注意）

- **`listenerCache`** 按 **`discoveryProperties.getGroup()`** 分组存放 **`NacosServiceEventListener`**；监听器的唯一键由 **`serviceName + group + namespace`** 拼接而成（见 `NacosSpringEventManager#toUniqueValue`）。
- **`subscribeService(String serviceName, ...)`** 中 **`NamingService.subscribe` 传入的服务名为 `NacosDiscoveryProperties#getService()`**（一般为当前应用注册使用的服务名），与循环变量 **`serviceName`**（来自 **`getServicesOfServer` 返回列表）在源码层面并不一致；若你希望「按扫描到的每一个远端服务名单独订阅」，需要结合业务自行核对是否与当前实现意图一致。

---

## 5. 配置项速查

| 属性 | 默认 | 含义 |
|------|------|------|
| `spring.cloud.nacos.config.schedule.enabled` | `true` | 是否启用 `@NacosCronScheduled` 扫描与 Config 监听刷新调度 |
| `spring.cloud.nacos.discovery.event.enabled` | 未默认开启 | 必须为 **`true`** 才启用 `NacosSpringEventManager` |
| `spring.cloud.nacos.discovery.event.subscribe-service-cron` | 未配置时为 **`30 0/1 * * * ?`**（见 `NacosSpringEventManager` 注解占位符） | 定时扫描并订阅服务 |
| `spring.cloud.nacos.discovery.event.unsubscribe-service-cron` | 未配置时为 **`0 0/1 * * * ?`** | 定时取消过期订阅 |
| `spring.cloud.nacos.discovery.event.expire-unsubscribe-time` | 约 1 小时 | 多久未访问视为监听过期 |

IDE 中另有 **`META-INF/additional-spring-configuration-metadata.json`** 中为 **`spring.cloud.nacos.config.schedule.enabled`** 提供的提示；其余 discovery.event 项可在 `NacosDiscoveryEventProperties` 上查看绑定字段名。

---

## 6. 与「仅使用 Spring Cloud Alibaba」的差异小结

| 能力 | 仅用官方 starter | 本模块额外提供 |
|------|-------------------|----------------|
| Nacos 配置刷新 | 有 | 将刷新结果映射到 **具体 cron 任务** 的取消/重建 |
| 实例列表变更 | Client **`subscribe`** 回调 | 转为 **`ApplicationEvent`**，并带可选 **订阅扫描 / 过期回收** |

---

## 7. 版本与兼容性说明

行为以当前仓库依赖的 **Spring Cloud Alibaba / nacos-client** 为准；升级 **Nacos Server 或客户端大版本** 时，请关注 **`NamingService`、`NamingChangeEvent`、`NamingEvent`** 等 API 是否有破坏性变更，并在联调环境验证 **`subscribe` 与配置监听** 是否正常。

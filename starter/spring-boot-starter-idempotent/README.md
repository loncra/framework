# spring-boot-starter-idempotent

`spring-boot-starter-idempotent` 在 **Spring AOP** 下提供两类与 **Redis** 协同的能力：方法级 **并发控制**（基于 Redisson `RLock` 的可重入锁 / 公平锁）与 **防重复提交式幂等**（基于 Redisson `RBucket` 与「窗口内值比对」约定）。通过 **`@Concurrent` / `@Idempotent`** 声明在**方法**上，由已注册的 `PointcutAdvisor` 在调用前拦截。

> **前置条件**：类路径中需存在 **`RedissonClient`** 且 Redisson 自动配置已生效（如引入 `redisson-spring-boot-starter` 并完成必要配置）。未满足时本 starter 的自动配置类**不会**装配。

## 模块定位

- **并发（`@Concurrent`）**：对给定业务 **key**（可含 SpEL 插值）执行 `tryLock` → 执行业务方法 → 在 `finally` 中按**当前持有线程**释放；多个 `@Concurrent`（`@Repeatable(ConcurrentElements)`）或同一容器注解上的**多个**子注解会**按配置顺序**依次加锁、统一释放，用于降低热点资源上的写冲突、保证临界区互斥。
- **幂等（`@Idempotent`）**：在**过期时间窗口**内，按 Redis 中存储的「**业务指纹**」判断是否为重复请求；若判定为重复，抛出 `**IdempotentException**`（消息来自注解的 `exception()`）。
- **键生成**：`**SpelExpressionValueGenerator**` 实现 `ValueGenerator`；`key` / `value` / `condition` 中通过 **`[与]`** 包一层 Spel 子表达式，在**方法实参**上下文中求值后替换进模板，并在最前拼上**可配置的前缀**（与并发、幂等各一套前缀，见下）。`@Concurrent` 的 `value` 若**留空**，模板在替换后为空串，经 `prependIfMissing` 后 Redis 上实际 key 为**仅前缀**（如 `concurrent:`），**所有**未配 key 的方法会抢同一把「全局」锁，生产环境**务必**为热点方法写上带业务维度的 `value`。

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-idempotent</artifactId>
    <version>${framework.version}</version>
</dependency>
```

| 依赖 | 作用 |
| --- | --- |
| `commons` | `TimeProperties`、`CastUtils` 等，以及 `@Time` 时间元数据 |
| `redisson-spring-boot-starter` | 提供 `**RedissonClient**`，与分布式锁 / 桶存取能力 |
| `spring-boot-starter-aop` | 启用 AOP 基础设施；切面**仅**匹配带 `@Concurrent` 或 `@ConcurrentElements` 或 `@Idempotent` 的方法 |

## 自动配置

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 注册 `**IdempotentAutoConfiguration**`，在以下条件同时满足时生效：

- `@ConditionalOnClass(RedissonClient.class)`
- `@AutoConfigureAfter(RedissonAutoConfigurationV4.class)`（在 Redisson 之后装配）
- `**loncra.framework.idempotent.enabled**` 为 `true`（`matchIfMissing` 为 `true`，即**默认开启**）
- 注册两个独立的 `**SpelExpressionValueGenerator**` 实例，分别将前缀设为 `IdempotentProperties#getConcurrentKeyPrefix` 与 `**getIdempotentKeyPrefix**`，再创建 `**ConcurrentInterceptor**` / `**IdempotentInterceptor**` 与对应的 `**ConcurrentPointcutAdvisor**` / `**IdempotentPointcutAdvisor**`（`@Role(BeanDefinition.ROLE_INFRASTRUCTURE)`）。
- `**ConcurrentInterceptor**`、`**IdempotentInterceptor**` 在**不存在**同名 Bean 时由本配置创建（`@ConditionalOnMissingBean`），便于在应用中替换实现；切面对象本身始终由本类注册，用于统一应用 `*PointcutAdvisorOrderValue`。

## 包结构（概要）

| 包 / 类 | 说明 |
| --- | --- |
| `config.IdempotentAutoConfiguration` | 上述 Bean 装配、前缀与切面顺序 |
| `config.IdempotentProperties` | 前缀 `**loncra.framework.idempotent.*`** 下的属性；开关见 `**loncra.framework.idempotent.enabled**` |
| `advisor.concurrent.ConcurrentInterceptor` / `**ConcurrentPointcutAdvisor**` | 并发切点与拦截逻辑，含大量 **编程式** `invoke(...)` 重载，可在业务代码中**手动**按 key 加锁（不仅限于注解） |
| `advisor.IdempotentInterceptor` / `**IdempotentPointcutAdvisor**` | 幂等切点与拦截逻辑 |
| `generator.SpelExpressionValueGenerator` | `[expr]` 片段的 Spel 求值、前缀、条件断言 |
| `annotation.Concurrent` / `**ConcurrentElements**` | 方法级；`Concurrent` 可重复 |
| `annotation.Idempotent` | 方法级 |
| `ConcurrentConfig` | 从 `Concurrent` 解出的可编程配置，支持 `**ofSuffix**` 派生子 key |
| `LockType` | `Lock`（`getLock`）与 `FairLock`（`getFairLock`） |
| `exception.IdempotentException` / `**ConcurrentException**` | `ConcurrentException` 继承自 `**IdempotentException**` |

## SpEL 与键格式

- `**SpelExpressionValueGenerator**` 将模板字符串中 **`[和]` 之间的子串**视为 Spel，在**仅含方法形参名 → 实参**的 `StandardEvaluationContext` 中计算；结果以字符串形式替换到模板中，空值写为 `null`；若模板中**没有**任何 `[...]` 片段，则整段字符串按字面量再拼**前缀**。
- **`condition` 断言**：对 `condition` 先做**同样的片段替换**得到**最终** Spel 串，再解析为 `Boolean`；**只有**在显式提供 `condition` 且**非**空、且**替换后**表达式求值不为 `true` 时，本次**不**执行并发/幂等控制（**直接** `proceed`）。
- **注意**：`key` 为空时，`@Idempotent` 在 `IdempotentInterceptor#isIdempotent` 中默认使用 `**ClassName** + . + **methodName**` 再交给生成器，因此**仍**会受前缀与可选 `[]` 影响。

## `@Concurrent`

| 成员 | 含义 | 默认 |
| --- | --- | --- |
| `value` | 锁的 key 模板（`[]` 内为 Spel） | `""`（若与其他约束组合后仍无有效 key，加锁无意义，应在业务中保证有键） |
| `condition` | 为**空**则恒参与；否则为 Spel 条件，**非 true** 时**跳过**本段并发逻辑 | 空 |
| `exception` | 获取锁失败时抛 `**ConcurrentException**` 的消息 | `**ConcurrentInterceptor.DEFAULT_EXCEPTION**`（`"请不要重复操作"`） |
| `waitTime` | 等待持锁的时长（`@Time`） | `@Time(1000)`，**毫秒**（同 `Time` 默认 `TimeUnit.MILLISECONDS`） |
| `leaseTime` | 上锁后租约/自动释放时间，与 `waitTime` 一起走 `**RLock#tryLock(wait, lease, unit)**` 语义；仅 `waitTime` 有值时走 `**tryLock(wait, unit)**` 重载；二者皆空则 `**tryLock()**` | `@Time(5000)` 毫秒 |
| `type` | `Lock` 或 `FairLock` | `**Lock**` |

同一方法上多个 `@Concurrent` 通过 `@ConcurrentElements` 聚合，过滤 `condition` 后顺序加锁，全部成功才执行 `invocation.proceed()`。方法**抛**受检异常时，拦截器内会包装为 `**SystemException**`（`RuntimeException` 原样抛出），锁仍会在 `finally` 中尝试释放；仅当 `lock.isLocked()` 为真时 `unlock`（**多锁**分支不校验 `isHeldByCurrentThread`，以 Redisson 语义为准）。

**编程式**：注入 `**ConcurrentInterceptor**`，可调用其 `**invoke(ConcurrentConfig, Supplier/Runnable, ...)`** 或更简化的 `invoke(String key, ...)` 系列，在**非**注解场景复用同一把「锁与异常」规则。

## `@Idempotent`

| 成员 | 含义 | 默认 |
| --- | --- | --- |
| `key` | 存储幂等态的 **Redis 键**模板；**空**时用 `**类全名.方法名**` | 空 |
| `value` | 若**非**空，每一项为 Spel，求值后组成**列表**作为「**业务值**」参与幂等比较；**若为空**，则用方法参数在排除 `**ignore**` 名、**null**、及 `**IdempotentProperties#ignoreClasses**` 中声明的类型后，对**剩余**实参**数组**做 `**Arrays#hashCode**` 的单一整数，包装成**一项** | `{}` |
| `condition` | 同 `Concurrent`：非空且**不为 true** 时**不**做幂等，直接 `proceed` | 空 |
| `exception` | 判为**重复/过快**时抛出 `**IdempotentException**` 的消息 | 同 `**ConcurrentInterceptor#DEFAULT_EXCEPTION**`（`"请不要重复操作"`，与 `Idempotent` 上注释「过快」的措辞略有不同，以源码常量为准） |
| `ignore` | 未使用 `**value**` 时，按**形参名**排除不参与 hash 的参数 | `{}` |
| `expirationTime` | 桶的 TTL，控制「窗口」；`@Time(3000)` 表示 **3000 毫秒** | `@Time(3000)` |

`IdempotentInterceptor#isIdempotent` 中实际使用 Redisson 的 `**RBucket**<`List`>`，通过 `**getAndDelete**` 与 `**setIfAbsent**` 在并发下对「**上一窗口/当前**」的列表值做比对；语义与「简单 setnx 一次即永远禁止」不同，**强依赖**在窗口内、相同 `key` 上业务值的覆盖与去重行为，**部署与压测**时建议结合业务验证是否符合「防连点/防重复」预期。

`ignoreClasses` 可通过 `application.yaml` 与 `**IdempotentProperties**` 正常绑定；也可在注解 `**ignore**` 中按**参数名**排除。YAML 中类名通常写为**全限定类名**（如 `jakarta.servlet.http.HttpServletRequest`）。

## 配置项（`loncra.framework.idempotent`）

| 属性 | 类型 | 说明 | 默认 |
| --- | --- | --- | --- |
| `enabled` | `boolean` | 是否装配本模块 | `true` |
| `concurrentKeyPrefix` | `String` | 仅作用于 **`SpelExpressionValueGenerator`** 用于**并发** key 的前缀 | `concurrent:` |
| `idempotentKeyPrefix` | `String` | 仅作用于**幂等** key 的前缀 | `idempotent:` |
| `concurrentPointcutAdvisorOrderValue` | `int` | 并发切面的 `**Advisor#order**` | `**Ordered#LOWEST_PRECEDENCE**` |
| `idempotentPointcutAdvisorOrderValue` | `int` | 幂等切面的 `**Advisor#order**` | `**Ordered#LOWEST_PRECEDENCE**` |
| `ignoreClasses` | `List<Class<?>>` | 在 `**@Idempotent**` 未显式 `**value**` 时，参与**默认** hash 时要忽略的**实参**类型 | 空列表 |

> IDE 的 `**additional-spring-configuration-metadata.json**` 中已登记 `**loncra.framework.idempotent.enabled**` 的提示说明。

## 与切面顺序的配套关系

- 若同一方法**同时**标有 `@Concurrent` 与 `@Idempotent`（或其它切面），**执行顺序**由 `**Advisor#order**` 与 Spring AOP 规则共同决定。二者默认**同为** `LOWEST_PRECEDENCE`，**相对顺序不保证**；需要「**先**串行化、**后**防重复」或相反时，请**显式**调大/调小 `**concurrentPointcutAdvisorOrderValue**` 与 `**idempotentPointcutAdvisorOrderValue**`。
- 与事务（`@Transactional`）等组合的先后顺序亦依赖全局 `Order`，建议在集成测试中验证。

## 使用示例

```java
// 1. 并发：按订单加互斥，公平锁 + 等锁 1s、租约 5s
@Concurrent(
    value = "order:[#orderId]",
    type = LockType.FairLock
)
@Transactional
public void pay(Long orderId) { /* ... */ }
```

```java
// 2. 幂等：多条件 key + 非空时启用；3 秒窗口
@Idempotent(
    key = "pay:[#userId]:[#orderId]",
    value = { "[#orderId]" },
    condition = "[#userId] != null",
    expirationTime = @Time(3000)
)
public void createOrder(User user, Long orderId) { /* ... */ }
```

```java
// 3. 编程式加锁（与注解共用同一 `ConcurrentInterceptor` 规则时，注入即可）
service.getBean(ConcurrentInterceptor.class).invoke(
    new ConcurrentConfig("manual:key"),
    () -> { /* ... */ }
);
```

## 注意事项

- 分布式能力**强依赖** Redis/Redisson 的可用与键空间隔离；**多**环境、**多**服务实例须保证 **key 前缀 + 业务维度** 不会与无关业务冲突。
- 幂等实现是**时间窗口**内的值比对，**不**保证跨窗口、不保证与数据库唯一约束的等价，重要业务应仍以**数据库约束**与**业务幂等键**为最终判据。
- 锁的 `leaseTime` 过短会导致业务未完成即释锁，过长会拉长故障下的阻塞时间，请按接口延迟与**幂等/重试**策略调参。

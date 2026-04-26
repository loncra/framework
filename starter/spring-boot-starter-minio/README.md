# spring-boot-starter-minio

`spring-boot-starter-minio` 在 **Spring Boot** 下提供 **MinIO 异步 Java SDK**（`MinioAsyncClient`）的封装 Bean `MinioAsyncTemplate`：在 **S3 协议** 侧完成桶与对象的常用读写、**分片上传**、**JSON 序列化**存取；并可选对接 MinIO **Console 管理端 HTTP API**（`RestTemplate` + 登录 **Cookie**），以及基于 **Quartz 风格 Cron** 的**按最后修改时间**的**过期对象自动删除**任务（需启用 Spring 调度，见下）。

> **依赖关系**：`Bucket`、`FileObject`、`ExpirableBucket` 等**领域模型在** `commons` 的 `io.github.loncra.framework.commons.minio` **包中**；本 starter 在之上实现模板与自动配置。业务侧请优先用 `commons` 的模型描述桶与对象，再组合 `MinioAsyncTemplate` 的方法。

## 模块定位

- **统一入口**：`MinioAsyncTemplate` 继承 `ConsoleApiMinioAsyncClient`，而父类**又继承** `MinioAsyncClient`，因此**注入的 Bean 既是**高层模板，**也是**可向下转型使用的异步客户端子类，便于在特殊场景直接调用 `MinioAsyncClient` 上尚未封装的 API。
- **S3 侧能力**（`MinioAsyncTemplate`）：`makeBucketIfNotExists` / `isBucketExist` / `deleteBucket`；`upload`（`size > 5MB` 走分片，否则单次 `putObject`）；`getObject`；`deleteObject`（支持**前缀/目录**形式时列举后批量删）；`moveObject`（`copy` + 删源）；`copyObject`；`readJsonValue` / `writeJsonValue`；`ObjectItem` 对 `Item` 做可序列化 DTO 封装。分片流程会把流按 **5MiB** 切分、落入 `uploadPartTempFilePath` 下的**临时子目录**再上传，结束后清理目录；内部分片与批量操作使用**固定**大小线程池，见下节《实现细节》。
- **Console 侧能力**（`ConsoleApiMinioAsyncClient`）：`buckets` 等经 `exchangeConsoleApi` 访问 `console-endpoint` + `consoleApiPrefix`（如 `/api/v1`）的 REST 接口。启动时 `InitializingBean#afterPropertiesSet` 向 `login` 接口用 JSON 体（`accessKeyBodyName` / `secretKeyBodyName`）换 **Set-Cookie**；`SchedulingConfigurer` 中按 `refreshCookieCron` 在 cookie **剩余可刷新窗口**内重登。若仅使用 S3 上传下载、**不需要** Console API，仍需注意**调用**依赖 Cookie 的 Console 方法时 `Assert.notNull(cookie)` 与 `endpoint` 非空 等**前置**条件，见《注意事项》与《核心行为》第 4 节。
- **自动删除**（`MinioAutoDeleteConfiguration`）：`@EnableScheduling` + 实现 `SchedulingConfigurer`：按 `loncra.framework.minio.auto-delete.cron` 扫描 `expiration` 中各 `ExpirableBucket`，以对象 **lastModified + expirationTime** 与**当前时间**比较，超期则 `deleteObject(..., true)`（`true` 时若桶空会尝试删桶）。`expiration` 为 **null** 时任务**立即返回**（仍注册调度，**无**实质删除动作）。

## 依赖说明

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-minio</artifactId>
    <version>${framework.version}</version>
</dependency>
```

| 依赖 | 作用 |
| --- | --- |
| `commons` | `Bucket` / `FileObject` / `ExpirableBucket` / `TimeProperties`、工具类等 |
| `minio`（`io.minio:minio`） | `MinioAsyncClient` 及 `GetObjectResponse`、`ListObjectsArgs` 等 |
| `okhttp3` | MinIO 客户端所依赖的 HTTP 栈（随官方传递） |
| `**spring-boot-starter-web**` | `**RestTemplate**`、**HTTP** 消息转换（Console 登录与 API） |
| `**spring-boot-actuator**` | 测试工程常配合暴露端点，非**业务强依赖**；你可在纯后台任务中改为更轻的 starter |
| `commons-io` | 分片临时目录清理等 |
| `slf4j-api` | 显式记录日志 |

## 自动配置

`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 顺序如下：

1. `io.github.loncra.framework.minio.MinioAutoConfiguration`：在 `loncra.framework.minio.enabled` 为 `true`（`matchIfMissing` 为 `true`）时注册 `MinioAsyncTemplate`；若**不存在**同名 Bean 才创建。使用 `MinioAsyncClient.builder()` 的 `endpoint`、`accessKey`、`secretKey`（来自 `MinioProperties`）构建客户端，并注入**唯一**的 `ObjectMapper`（`ObjectProvider` 无**唯一**实例时用 `new ObjectMapper()`）。
2. `io.github.loncra.framework.minio.MinioAutoDeleteConfiguration`：在**同一** `enabled` 条件、`@AutoConfigureAfter(MinioAutoConfiguration.class)` 之后，装配**过期清理**的 `SchedulingConfigurer` 任务；`@EnableScheduling` 在**本类**上。若**排除**该自动配置但仍需 `MinioAsyncTemplate` 基类中 **Cookie 定时刷新** 或你自定义的 `Scheduled`/`SchedulingConfigurer` Bean 生效，请在**应用**上自行添加 `@EnableScheduling`（本 starter 默认**通过**上一条与这一条自动配置**一并**打开调度，一般**无需**在主类重复标注）。

## 包结构（概要）

| 类 / 包 | 说明 |
| --- | --- |
| `**MinioAutoConfiguration**` / `**MinioAutoDeleteConfiguration**` | 主配置与自动删过期对象 |
| `**MinioProperties**` | 前缀 `**loncra.framework.minio**`（Console、Cookie、大文件临时目录、IO 等） |
| `**AutoDeleteProperties**` | 前缀 `**loncra.framework.minio.auto-delete**` |
| `**MinioAsyncTemplate**` | 异步 S3/JSON 等封装 |
| `**ConsoleApiMinioAsyncClient**` | Console 登录、Cookie 刷新、REST 封装；继承自 `**MinioAsyncClient**` |
| `**UserMetadataFileObject**` | 继承 `**FileObject**`，`putObject` 时带 `**userMetadata**`；**分片**路径下若元数据在 `**extraHeaders**` 中会被合并到 header 参与上传 |
| `**ObjectItem**` | 把 `**Item**` 暴露为便于 JSON 的**包装**类 |

## 配置项

### `loncra.framework.minio`（`**MinioProperties**`）

| 属性 | 说明 | 默认/备注 |
| --- | --- | --- |
| `**enabled**` | 由 `@ConditionalOnProperty` 使用；类中**可**不声明字段，用于开关**两套**自动配置 | 默认**开启** |
| `**endpoint**` | S3 API 基址（`MinioAsyncClient`） | **必填**于正常客户端调用 |
| `**accessKey**` / `**secretKey**` | 访问/秘密密钥 | **必填**于客户端与（默认）Console 登录体 |
| `**console-endpoint**` | Console（管理台）**根** URL，用于 `**getConsoleApiAddress**` 拼接；**登录**、刷新 Cookie 均依赖**有效**的 Console 地址与**网络** | 使用 `**buckets**` 等 Console API 时需配 |
| `**consoleApiPrefix**` | API 路径前缀，与 `**console-endpoint**` 拼接，如 `**/api/v1**` | `/api/v1` |
| `**accessKeyBodyName**` / `**secretKeyBodyName**` | 登录 JSON 的字段名 | `**accessKey**` / `**secretKey**`（见源码常量） |
| `**cookieMinRemainingBeforeRefresh**` | `**TimeProperties**`：在 `**refreshCookie**` 中与 cookie 过期时间一起参与判断 | 默认 `**5` 分钟**（见 `**TimeUnit**` 绑定方式） |
| `**refreshCookieCron**` | 刷新 Cookie 的 **Cron**（6 位 Spring 形式） | `0 1 * * * ?`（示例：每小时 1 分） |
| `**uploadPartTempFilePath**` | 分片上传**临时**目录**父路径**（会再建带纳秒的子目录） | `./**upload_part**` |
| `**ioExecutorNumberOfThreads**` | 设计意图：限制分片/批量删等**专用**线程池规模 | **当前 `**MinioAsyncTemplate**` 构造中未读取该值**，分片/IO 池**固定**为 `**Runtime.getRuntime().availableProcessors() * 2**`；若你依赖该键，以源码实际行为为准，或提 PR 接线 |

### `loncra.framework.minio.auto-delete`（`**AutoDeleteProperties**`）

| 属性 | 说明 | 默认 |
| --- | --- | --- |
| `**cron**` | 扫描**过期**任务的 Cron | `0 1 * * * ?` |
| `**expiration**` | `**List<**ExpirableBucket**>**`：`**bucketName**` / `**region**` 继承自 `**Bucket**`，`**expirationTime**` 为 `**TimeProperties**`；若某项缺 `**expirationTime**` 会打** WARN** 并跳过 | `**null**` 时不做删除 |

`**ExpirableBucket**` 的 YAML 可写成（示例：桶 `**temp**` 里对象在「最后修改时间 + 1 天」**之前**仍保留，**之后**的调度周期内会被删掉——逻辑见 `**MinioAutoDeleteConfiguration**` 内 `**lastModified** **.plus(...)**` 与 `**isAfter**` 比较）：

```yaml
loncra:
  framework:
    minio:
      auto-delete:
        cron: "0 0 2 * * ?"   # 每天 2 点
        expiration:
          - bucket-name: "temp"
            region: "us-east-1"   # 可省略，视 MinIO/网关配置
            expiration-time:
              value: 1
              unit: DAYS
```

> `**TimeProperties**` 的 `**value**` / `**unit**` 与 `TimeUnit` 对应；若绑定失败，对照 `commons` 中 `**TimeProperties**` 的 setter 与元数据。

## 实现细节

- **分片阈值**：`MinioAsyncTemplate#PART_SIZE` 为 `5 * 1024 * 1024`（5MiB），与上文中「大于 5MB 走分片」一致（精确为 5MiB，不是 5×1000²）。
- **IO 线程池**：`MinioAsyncTemplate` 内 `ioExecutor` 为 `Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2)`；`MinioProperties#ioExecutorNumberOfThreads` **未**在构造中参与计算，与配置项说明需对照源码。
- **大文件临时目录**：`createUploadPartTempDir` 在 `uploadPartTempFilePath` 后拼接 `_{nanoTime}` 子目录，每分片在子目录中写**临时**文件，上传后删除单文件、整体结束后删目录（失败会打 error 日志）。

## 核心行为说明

### 1. 删除对象 `deleteObject(FileObject, boolean deleteBucketIfEmpty)`

- **objectName 以** `/` **或** `**.**` **结尾**（视为**目录/前缀**）时，先 `**listObject**` 成批 `**removeObject**`。
- 否则**单对象**删：先 `**statObject**`；异常时**仅**打 WARN 并**返回** `**false**` 的**已完成** Future（不抛错）。
- `**deleteBucketIfEmpty**` 为真且桶内**再无**对象时，**删除**桶（见 `**afterDeleteObject**`）。

### 2. 分片与大小阈值

- `**upload**` 内以 `**PART_SIZE = 5 * 1024 * 1024**` 为界：大于则 `**putObjectMultipart**`（`**createMultipartUpload**` → 读流写临时文件 → `**uploadPart**` → `**complete**`），否则 `**putObject**`。

### 3. 复制与移动

- `**moveObject**`：`**copy**` 目标后 `**deleteObject**` 源；`**copyObject**` 会先对**目标**做 `**deleteObject(target, false)**` 再拷贝（覆盖语义）。

### 4. 控制台与 Cookie

- 若 `**getEndpoint**()` 为**空**，`**afterPropertiesSet**` 中 `**setCookieValue**` **早退**，**cookie** 保持 **null**，后续 `**exchangeConsoleApi**` 在 `**Assert**` 上失败。实际部署请同时提供 **`endpoint`** 与**可用的** `**console-endpoint**`（以及 Console 的 login 与 API 与 MinIO 版本、反向代理**路径**是否一致）。源码中 `**setCookie**` 使用 `**HttpUrl.parse**(**`minioProperties.getEndpoint()`**)` 解析域，**Cookie 域**与 S3 端点一致，请与运维约定**同一主机名**等。

### 5. 自动删过期

- 使用 **最后修改时间** 作为**起点**，加 **`ExpirableBucket#expirationTime`** 得到**过期时刻**；**当前时间晚于**该时刻则删除。这是**非** S3/MinIO 原生**生命周期**策略的**应用层轮询**实现，**大桶**、**高 QPS** 时需注意**列举**与**调度**的负载。源码有 `**FIXME 怎么做成可动态的配置**`，Cron 与列表**不**在运行时**热**更新（除非刷新上下文）。

## 与 Spring 调度的关系

- `**@EnableScheduling**` 在 `**MinioAutoDeleteConfiguration**` 中；`**MinioAsyncTemplate**` 作为 Bean 的基类也实现了 `**SchedulingConfigurer**`（注册 **Cookie 刷新**）。**同一**个应用内只要加载了本 starter 的**上述**类，**调度**即生效，无需在 `**@SpringBootApplication**` 上**重复** `@EnableScheduling`（**除非**你排除了**相关**自动配置后仍自行注册等效 Bean）。

## 使用示例

```java
@Autowired
private MinioAsyncTemplate minioAsyncTemplate;

// 建桶、上传
Bucket bucket = Bucket.of("app-files");
minioAsyncTemplate.makeBucketIfNotExists(bucket)
        .thenCompose(ignored -> {
            FileObject file = FileObject.of(bucket, "a/b/photo.png");
            try (InputStream in = ...) {
                return minioAsyncTemplate.upload(
                        file, in, in.available(), "image/png");
            }
        });
```

```java
// 仅 S3 操作、不用 Console
// 请确保启动阶段不会因“未用 Console 却调用了基类中依赖 cookie 的方法”而失败；
// 若只使用 S3 封装方法，不调用 buckets 等，仍建议配置好 endpoint/密钥。
```

## 注意事项

- **安全**：`**access-key**`、`**secret-key**` 与生产 Console 应通过**环境变量/密钥管理**注入，**勿**入仓库。
- **Console API** 的 URL、路径前缀、登录**请求体**字段与 MinIO **大版本**相关；与官方/部署文档**不一致**会导致启动登录失败。
- 自动删除是**全量列举** + 比较时间，**不是** S3 生命周期**内置**规则；**海量**小文件桶请评估**清单 API** 成本，必要时改为**带前缀/分段**等策略或上游生命周期。
- 若不需要自动删除，可将 `**auto-delete.expiration**` 留**空**（**不要**在 YAML 中误写空 list 为大量规则），或**排除** `**MinioAutoDeleteConfiguration**` 并自行管理调度与删除逻辑。

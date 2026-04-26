# spring-boot-starter-access-crypto

`spring-boot-starter-access-crypto` 是 loncra framework 中的访问加解密与通用密码算法模块。它提供 AES、DES、RSA、SM2、SM4 加解密能力，MD5、SHA、SM3 哈希能力，以及访问加解密配置模型、访问 token、签名 token、过期 token 等通用对象。

虽然模块名称中包含 `spring-boot-starter`，但当前源码没有 Spring Boot 自动配置、`spring.factories` / `AutoConfiguration.imports` 或 `@ConfigurationProperties` 绑定。它更接近一个可被上层 Web/Spring Boot starter 复用的加密基础库。

## 模块定位

- 提供统一的加解密接口：`CipherService`、`EncryptService`、`DecryptService`。
- 提供对称加密实现：`AesCipherService`、`DesCipherService`、`Sm4CipherService`。
- 提供非对称加密与签名实现：`RsaCipherService`、`Sm2CipherService`。
- 提供哈希能力：`Hash`、`HashService`、`HashRequest`、`HashAlgorithmMode`。
- 提供字节载体与编解码工具：`ByteSource`、`SimpleByteSource`、`Base64`、`Hex`、`CodecUtils`。
- 提供访问加解密元数据模型：`AccessCrypto`、`AccessCryptoPredicate`。
- 提供访问 token 模型：`AccessToken`、`ExpirationToken`、`SimpleToken`、`SimpleExpirationToken`、`SignToken`。
- 注册 Bouncy Castle Provider，用于支持 SM2、SM3、SM4 等国密算法。

## 依赖引入

```xml
<dependency>
    <groupId>io.github.loncra.framework</groupId>
    <artifactId>spring-boot-starter-access-crypto</artifactId>
    <version>${framework.version}</version>
</dependency>
```

运行依赖说明：

- 核心依赖：`commons`、`bcprov-jdk18on`。
- `commons` 提供 `NameValueEnum`、`TimeProperties`、`YesOrNo`、`DisabledOrEnabled` 等基础模型。
- `bcprov-jdk18on` 用于国密算法支持，尤其是 SM2、SM3、SM4。
- 测试中使用 `junit` 与 `spring-test`，生产代码当前不依赖 Spring Boot 自动配置能力。

## 包结构

- `io.github.loncra.framework.crypto`：算法枚举、算法服务入口与密钥配置模型，包括 `CipherAlgorithm`、`CipherAlgorithmService`、`AlgorithmProperties`、`RsaProperties`、`Sm2Properties`、`Sm4Properties`。
- `io.github.loncra.framework.crypto.algorithm`：字节源、Base64、Hex、编码转换、随机数生成器等基础工具。
- `io.github.loncra.framework.crypto.algorithm.cipher`：加解密接口、对称/非对称抽象实现、AES/DES/RSA/SM2/SM4 具体实现、分组模式和填充方案。
- `io.github.loncra.framework.crypto.algorithm.hash`：哈希算法、哈希请求和哈希服务。
- `io.github.loncra.framework.crypto.algorithm.exception`：加密、编解码、签名、验签、未知算法等异常类型。
- `io.github.loncra.framework.crypto.access`：访问加解密配置、访问 token 与过期 token 抽象。
- `io.github.loncra.framework.crypto.access.token`：简单 token、过期 token、签名 token 实现。

## 支持能力

### 加解密算法

| 算法 | 类型 | 默认配置 | 说明 |
| --- | --- | --- | --- |
| AES | 对称加密 | CBC + PKCS5，256 位密钥，128 位 IV | 适合通用敏感数据加密 |
| DES | 对称加密 | CBC + PKCS5，56 位密钥，64 位 IV | 兼容旧系统，不建议新场景优先使用 |
| RSA | 非对称加密 | ECB + PKCS1，默认 2048 位密钥 | 支持加解密、签名、验签和分段处理 |
| SM2 | 国密非对称加密 | SM2 曲线 `sm2p256v1` | 支持加解密、签名、验签 |
| SM4 | 国密对称加密 | CBC + PKCS5，128 位密钥，128 位 IV | 适合国密场景的数据加密 |

### 哈希算法

`HashAlgorithmMode` 内置 `MD5`、`SHA-1`、`SHA-256`、`SHA-384`、`SHA-512`、`SM3`。`HashService` 支持私有盐、公共盐、自动生成公共盐和多次迭代。

## 快速开始

### AES 对称加解密

```java
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;

import java.security.Key;
import java.nio.charset.StandardCharsets;

AesCipherService cipherService = new AesCipherService();
Key key = cipherService.generateKey();

ByteSource encrypted = cipherService.encrypt(
        "hello loncra".getBytes(StandardCharsets.UTF_8),
        key.getEncoded()
);

ByteSource decrypted = cipherService.decrypt(
        encrypted.obtainBytes(),
        key.getEncoded()
);

String plainText = decrypted.obtainString();
```

默认 AES 会生成初始化向量，并将 IV 拼接在密文前部。解密时 `decrypt(...)` 会自动从密文头部提取 IV。

### SM4 国密对称加解密

```java
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.Sm4CipherService;

import java.security.Key;
import java.nio.charset.StandardCharsets;

Sm4CipherService cipherService = new Sm4CipherService();
Key key = cipherService.generateKey();

ByteSource encrypted = cipherService.encrypt(
        "国密 SM4 加密内容".getBytes(StandardCharsets.UTF_8),
        key.getEncoded()
);

ByteSource decrypted = cipherService.decrypt(
        encrypted.obtainBytes(),
        key.getEncoded()
);
```

### RSA 非对称加解密与签名

```java
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.RsaCipherService;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

RsaCipherService cipherService = new RsaCipherService();
KeyPair keyPair = cipherService.generateKeyPair();

byte[] plainText = "需要 RSA 加密的内容".getBytes(StandardCharsets.UTF_8);

ByteSource encrypted = cipherService.encrypt(
        plainText,
        keyPair.getPublic().getEncoded()
);

ByteSource sign = cipherService.sign(
        encrypted.obtainBytes(),
        keyPair.getPrivate().getEncoded()
);

boolean verified = cipherService.verify(
        encrypted.obtainBytes(),
        keyPair.getPublic().getEncoded(),
        sign.obtainBytes()
);

ByteSource decrypted = cipherService.decrypt(
        encrypted.obtainBytes(),
        keyPair.getPrivate().getEncoded()
);
```

RSA 默认签名算法为 `SHA1withRSA`，可通过 `setSignatureAlgorithmName(...)` 修改。

### SM2 国密加解密与签名

`Sm2CipherService` 提供两套能力：继承自非对称抽象类的签名/验签，以及基于 Bouncy Castle `SM2Engine` 的加解密。SM2 加解密推荐使用 `encryptWithEngine(...)` / `decryptWithEngine(...)`。

```java
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.Sm2CipherService;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

Sm2CipherService cipherService = new Sm2CipherService();
KeyPair keyPair = cipherService.generateKeyPair();

PublicKey publicKey = keyPair.getPublic();
PrivateKey privateKey = keyPair.getPrivate();

byte[] publicKeyBytes = cipherService.getPublicKeyBytes(publicKey);
byte[] privateKeyBytes = cipherService.getPrivateKeyBytes(privateKey);

byte[] encrypted = cipherService.encryptWithEngine(
        "SM2 加密内容".getBytes(StandardCharsets.UTF_8),
        publicKeyBytes
);

byte[] decrypted = cipherService.decryptWithEngine(encrypted, privateKeyBytes);

ByteSource sign = cipherService.sign(decrypted, privateKeyBytes);
boolean verified = cipherService.verify(decrypted, publicKeyBytes, sign.obtainBytes());
```

SM2 默认签名算法为 `SM3withSM2`。

### 流式加解密

`EncryptService` 与 `DecryptService` 同时支持字节数组和流式处理。流式方法适合文件、较大请求体或响应体处理。

```java
import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Key;

AesCipherService cipherService = new AesCipherService();
Key key = cipherService.generateKey();

ByteArrayInputStream input = new ByteArrayInputStream("file content".getBytes());
ByteArrayOutputStream encryptedOutput = new ByteArrayOutputStream();

cipherService.encrypt(input, encryptedOutput, key.getEncoded());

ByteArrayInputStream encryptedInput = new ByteArrayInputStream(encryptedOutput.toByteArray());
ByteArrayOutputStream decryptedOutput = new ByteArrayOutputStream();

cipherService.decrypt(encryptedInput, decryptedOutput, key.getEncoded());
```

## 哈希用法

### 直接计算 Hash

```java
import io.github.loncra.framework.crypto.algorithm.hash.Hash;
import io.github.loncra.framework.crypto.algorithm.hash.HashAlgorithmMode;

Hash hash = new Hash(HashAlgorithmMode.SHA256.getName(), "hello loncra");

String hex = hash.getHex();
String base64 = hash.getBase64();
```

### 使用 HashService

```java
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.crypto.algorithm.hash.Hash;
import io.github.loncra.framework.crypto.algorithm.hash.HashAlgorithmMode;
import io.github.loncra.framework.crypto.algorithm.hash.HashRequest;
import io.github.loncra.framework.crypto.algorithm.hash.HashService;

HashService hashService = new HashService();
hashService.setAlgorithmMode(HashAlgorithmMode.SM3);
hashService.setPrivateSalt(new SimpleByteSource("private-salt"));
hashService.setIterations(3);

HashRequest request = new HashRequest(
        new SimpleByteSource("hello loncra"),
        new SimpleByteSource("public-salt"),
        3,
        HashAlgorithmMode.SM3.getName()
);

Hash hash = hashService.computeHash(request);
```

`HashService` 返回的 `Hash` 只暴露公共盐，不暴露私有盐和组合盐。

## 字节源与编码

`ByteSource` 是模块内统一的字节载体。`SimpleByteSource` 支持从 `byte[]`、`char[]`、`String`、`File`、`InputStream`、`ByteSource` 创建，并提供 Hex、Base64 输出。

```java
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;

SimpleByteSource source = new SimpleByteSource("hello loncra");

byte[] bytes = source.obtainBytes();
String text = source.obtainString();
String hex = source.getHex();
String base64 = source.getBase64();
```

## 算法服务入口

`CipherAlgorithmService` 维护算法名称到 `CipherService` 实现类的映射，默认注册 AES、DES、RSA、SM2、SM4。

```java
import io.github.loncra.framework.crypto.CipherAlgorithmService;
import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;

CipherAlgorithmService algorithmService = new CipherAlgorithmService();
AesCipherService aes = algorithmService.getCipherService(CipherAlgorithmService.AES_ALGORITHM);
```

也可以通过 `setAlgorithmServiceMap(...)` 增加自定义算法实现。

## 访问加解密模型

`AccessCrypto` 是访问加解密的配置模型，用于描述某个访问场景是否启用、是否请求解密、是否响应加密，以及命中条件。

```java
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;
import io.github.loncra.framework.crypto.access.AccessCrypto;
import io.github.loncra.framework.crypto.access.AccessCryptoPredicate;

import java.util.List;

AccessCrypto crypto = new AccessCrypto();
crypto.setName("open-api");
crypto.setType("SM4");
crypto.setValue("default-key");
crypto.setRequestDecrypt(YesOrNo.Yes);
crypto.setResponseEncrypt(YesOrNo.Yes);

AccessCryptoPredicate predicate = new AccessCryptoPredicate();
predicate.setName("path");
predicate.setValue("#request.requestURI.startsWith('/open-api')");

crypto.setPredicates(List.of(predicate));
```

该模块只定义模型，不执行 Spring EL 判断，也不内置 Web 请求解密/响应加密过滤器；这些逻辑应由上层 starter 或业务模块实现。

## Token 模型

### 简单 Token

```java
import io.github.loncra.framework.crypto.access.AccessToken;
import io.github.loncra.framework.crypto.access.token.SimpleToken;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;

SimpleToken token = SimpleToken.requestDecrypt(
        AccessToken.ACCESS_TOKEN_KEY_NAME,
        new SimpleByteSource("secret-key")
);
```

`SimpleToken` 会自动生成 UUID 作为 token 值，并提供 `generate(...)`、`requestDecrypt(...)`、`responseEncrypt(...)` 三种工厂方法。

### 过期 Token

```java
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.crypto.access.token.SimpleExpirationToken;
import io.github.loncra.framework.crypto.access.token.SimpleToken;

SimpleToken token = SimpleToken.generate("access-key", new SimpleByteSource("secret-key"));
SimpleExpirationToken expirationToken = new SimpleExpirationToken(
        token,
        TimeProperties.ofMinutes(30)
);

boolean expired = expirationToken.isExpired();
```

### 签名 Token

```java
import io.github.loncra.framework.crypto.access.token.SignToken;
import io.github.loncra.framework.crypto.access.token.SimpleToken;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;

ByteSource keySource = new SimpleByteSource("public-key");
ByteSource sign = new SimpleByteSource("signature");

SimpleToken token = SimpleToken.generate("public-key", keySource);
SignToken signToken = new SignToken(token, sign);

ByteSource tokenSign = signToken.getSign();
```

## 关键类说明

### `CipherService`

- 统一加解密接口，组合 `EncryptService` 与 `DecryptService`。
- 支持 `byte[]` 加解密，也支持 `InputStream` 到 `OutputStream` 的流式加解密。

### `AbstractJcaCipherService`

- 基于 JCA/JCE 的加解密抽象实现。
- 支持初始化向量生成，并将 IV 拼接到密文前部。
- 对非对称加密支持分段处理，避免明文超过 RSA 分段限制。

### `AbstractBlockCipherService`

- 构建 JCA transformation 字符串，例如 `AES/CBC/PKCS5Padding`。
- 管理分组模式、填充方案、块大小、流式分组模式和流式填充方案。
- `ECB`、`NONE` 模式不支持初始化向量。

### `AbstractAsymmetricCipherService`

- 提供非对称加密的密钥对生成、签名和验签能力。
- 默认密钥大小为 2048 位。
- 默认签名算法为 `SHA1withRSA`，SM2 实现会改为 `SM3withSM2`。

### `HashService`

- 默认算法为 `MD5`，默认迭代次数为 1。
- 支持私有盐、请求公共盐、自动生成公共盐和多次迭代。
- 如果存在私有盐，会自动生成公共盐以保护私有盐的完整性。

## 推荐实践

- 新业务优先使用 AES、RSA、SM2、SM3、SM4；DES 仅用于兼容历史系统。
- 对称加密密钥应使用 `generateKey()` 生成，并以安全方式保存，不要硬编码在代码中。
- RSA 公钥用于加密和验签，私钥用于解密和签名。
- SM2 加解密优先使用 `encryptWithEngine(...)` / `decryptWithEngine(...)`，密钥字节使用 `getPublicKeyBytes(...)`、`getPrivateKeyBytes(...)` 获取。
- 存储密码类数据时不要直接使用单次 MD5，建议使用强哈希算法、盐和足够迭代次数。
- `AccessCrypto` 和 token 模型适合作为上层访问加解密流程的数据契约，不建议在业务逻辑中重复定义类似结构。

## 注意事项

- 当前模块没有 Spring Boot 自动配置入口，引用依赖后需要手动创建服务对象或由上层模块装配。
- `SimpleByteSource.toString()` 返回 Base64，不是原文字符串；需要原文时使用 `obtainString()`。
- AES、DES、SM4 默认启用 IV，密文格式为 `IV + encrypted`；不要在传输或存储时截断密文头部。
- `AlgorithmProperties` 只是配置载体，使用 `CipherAlgorithmService.getCipherService(AlgorithmProperties)` 时需要完整设置算法名、模式、填充、密钥长度和 IV 长度等字段。
- SM2、SM3、SM4 依赖 Bouncy Castle Provider，模块会注册 Provider，但运行环境仍需要引入 `bcprov-jdk18on`。
- 非对称加密不适合直接加密大文件；大数据建议使用“对称密钥加密数据 + 非对称密钥加密对称密钥”的组合方式。
- `SimpleExpirationToken` 的过期时间会在设置 `lastAccessedTime` 时重新计算；如果修改 `maxInactiveInterval`，需要注意已有 `expirationTime` 是否符合预期。

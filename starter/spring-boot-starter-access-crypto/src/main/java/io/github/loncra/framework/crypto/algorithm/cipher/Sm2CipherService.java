
package io.github.loncra.framework.crypto.algorithm.cipher;

import io.github.loncra.framework.crypto.CipherAlgorithmService;
import io.github.loncra.framework.crypto.algorithm.exception.CryptoException;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

/**
 * SM2 非对称加密实现，中国国家密码标准（GB/T 32905-2016）
 * <p>
 * SM2 是中国自主设计的椭圆曲线公钥密码算法，基于椭圆曲线离散对数问题（ECDLP）。
 * 特点：
 * <ul>
 *     <li>基于椭圆曲线密码体制（ECC）</li>
 *     <li>密钥长度：256 比特</li>
 *     <li>安全性高，性能优于 RSA</li>
 *     <li>支持加密、解密、签名、验签功能</li>
 * </ul>
 * </p>
 * <p>
 * 注意：SM2 使用 Bouncy Castle 库提供的实现，需要注册 BouncyCastleProvider
 * </p>
 *
 * @author maurice
 */
public class Sm2CipherService extends AbstractAsymmetricCipherService {

    /**
     * SM2 曲线参数
     */
    private static final X9ECParameters X9_EC_PARAMETERS = GMNamedCurves.getByName("sm2p256v1");

    /**
     * SM2 曲线规范
     */
    private static final ECDomainParameters EC_DOMAIN_PARAMETERS = new ECDomainParameters(
            X9_EC_PARAMETERS.getCurve(),
            X9_EC_PARAMETERS.getG(),
            X9_EC_PARAMETERS.getN(),
            X9_EC_PARAMETERS.getH()
    );

    /**
     * SM2 引擎（加密/解密）
     */
    private SM2Engine sm2Engine;

    /**
     * 密钥工厂
     */
    private KeyFactory keyFactory;

    /**
     * SM2 非对称加密实现
     */
    public Sm2CipherService() {
        super(CipherAlgorithmService.SM2_ALGORITHM);

        // SM2 密钥长度固定为 256 位
        setKeySize(256);

        // SM2 不使用传统的密码分组模式和填充方案
        setMode(OperationMode.ECB);
        setPaddingScheme(PaddingScheme.NONE);

        setStreamingMode(OperationMode.ECB);
        setStreamingPaddingScheme(PaddingScheme.NONE);

        // SM2 默认签名算法
        setSignatureAlgorithmName("SM3withSM2");

        // 注册 Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public PrivateKey getPrivateKey(byte[] key) {
        try {
            // SM2 私钥是一个大整数，直接从字节数组创建
            BigInteger privateKeyS = new BigInteger(1, key);
            
            // 创建私钥参数
            ECPrivateKeyParameters privateKeyParams = new ECPrivateKeyParameters(
                    privateKeyS,
                    EC_DOMAIN_PARAMETERS
            );

            // 使用 BC Provider 创建私钥对象
            KeyFactory keyFactory = getKeyFactory();
            return keyFactory.generatePrivate(
                    new org.bouncycastle.jce.spec.ECPrivateKeySpec(privateKeyS, getECParameterSpec())
            );
        }
        catch (Exception e) {
            String msg = "解析 SM2 私有密钥错误：" + Arrays.toString(key);
            throw new CryptoException(msg, e);
        }
    }

    @Override
    public PublicKey getPublicKey(byte[] key) {
        try {
            // SM2 公钥是椭圆曲线上的点
            byte[] publicKeyBytes = key;
            
            // 如果没有前缀 0x04，添加它（未压缩格式）
            if (publicKeyBytes.length == 64) {
                byte[] temp = new byte[65];
                temp[0] = 0x04;
                System.arraycopy(publicKeyBytes, 0, temp, 1, 64);
                publicKeyBytes = temp;
            }

            // 解析椭圆曲线点
            ECPoint ecPoint = X9_EC_PARAMETERS.getCurve().decodePoint(publicKeyBytes);
            
            // 使用 BC Provider 创建公钥对象
            KeyFactory keyFactory = getKeyFactory();
            return keyFactory.generatePublic(
                    new org.bouncycastle.jce.spec.ECPublicKeySpec(ecPoint, getECParameterSpec())
            );
        }
        catch (Exception e) {
            String msg = "解析 SM2 公共密钥错误：" + Arrays.toString(key);
            throw new CryptoException(msg, e);
        }
    }

    /**
     * 获取 SM2 曲线参数规范
     *
     * @return EC 参数规范
     */
    private ECParameterSpec getECParameterSpec() {
        return new ECParameterSpec(
                X9_EC_PARAMETERS.getCurve(),
                X9_EC_PARAMETERS.getG(),
                X9_EC_PARAMETERS.getN(),
                X9_EC_PARAMETERS.getH()
        );
    }

    /**
     * 获取密钥工厂
     *
     * @return 密钥工厂
     */
    public KeyFactory getKeyFactory() {
        try {
            if (keyFactory == null) {
                keyFactory = KeyFactory.getInstance("EC", "BC");
            }
            return keyFactory;
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new CryptoException("获取 SM2 密钥工厂失败", e);
        }
    }

    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("sm2p256v1");
            keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new CryptoException("生成 SM2 密钥对失败", e);
        }
    }

    /**
     * 使用 SM2 引擎加密（推荐方式）
     *
     * @param plaintext 明文
     * @param key       公钥字节（未压缩格式的椭圆曲线点）
     * @return 加密结果
     */
    public byte[] encryptWithEngine(byte[] plaintext, byte[] key) {
        try {
            // 解析公钥
            byte[] publicKeyBytes = key;
            if (publicKeyBytes.length == 64) {
                byte[] temp = new byte[65];
                temp[0] = 0x04;
                System.arraycopy(publicKeyBytes, 0, temp, 1, 64);
                publicKeyBytes = temp;
            }
            
            ECPoint ecPoint = X9_EC_PARAMETERS.getCurve().decodePoint(publicKeyBytes);
            ECPublicKeyParameters ecPublicKeyParams = new ECPublicKeyParameters(
                    ecPoint,
                    EC_DOMAIN_PARAMETERS
            );

            SM2Engine engine = getSm2Engine();
            engine.init(true, new ParametersWithRandom(ecPublicKeyParams, new SecureRandom()));

            return engine.processBlock(plaintext, 0, plaintext.length);
        }
        catch (Exception e) {
            throw new CryptoException("SM2 加密失败", e);
        }
    }

    /**
     * 使用 SM2 引擎解密（推荐方式）
     *
     * @param cipherText 密文
     * @param key        私钥字节（大整数）
     * @return 解密结果
     */
    public byte[] decryptWithEngine(byte[] cipherText, byte[] key) {
        try {
            // 解析私钥
            BigInteger privateKeyS = new BigInteger(1, key);
            ECPrivateKeyParameters ecPrivateKeyParams = new ECPrivateKeyParameters(
                    privateKeyS,
                    EC_DOMAIN_PARAMETERS
            );

            SM2Engine engine = getSm2Engine();
            engine.init(false, ecPrivateKeyParams);

            return engine.processBlock(cipherText, 0, cipherText.length);
        }
        catch (Exception e) {
            throw new CryptoException("SM2 解密失败", e);
        }
    }

    /**
     * 获取公钥字节（未压缩格式）
     *
     * @param publicKey 公钥
     * @return 公钥字节
     */
    public byte[] getPublicKeyBytes(PublicKey publicKey) {
        BCECPublicKey bcPublicKey = (BCECPublicKey) publicKey;
        return bcPublicKey.getQ().getEncoded(false); // false = 未压缩格式
    }

    /**
     * 获取私钥字节（大整数）
     *
     * @param privateKey 私钥
     * @return 私钥字节
     */
    public byte[] getPrivateKeyBytes(PrivateKey privateKey) {
        BCECPrivateKey bcPrivateKey = (BCECPrivateKey) privateKey;
        return bcPrivateKey.getD().toByteArray();
    }

    /**
     * 获取 SM2 引擎
     *
     * @return SM2 引擎
     */
    private SM2Engine getSm2Engine() {
        if (sm2Engine == null) {
            sm2Engine = new SM2Engine();
        }
        return sm2Engine;
    }

    /**
     * 获取椭圆曲线域参数
     *
     * @return 椭圆曲线域参数
     */
    public static ECDomainParameters getECDomainParameters() {
        return EC_DOMAIN_PARAMETERS;
    }

    /**
     * 获取 X9 曲线参数
     *
     * @return X9 曲线参数
     */
    public static X9ECParameters getX9ECParameters() {
        return X9_EC_PARAMETERS;
    }
}

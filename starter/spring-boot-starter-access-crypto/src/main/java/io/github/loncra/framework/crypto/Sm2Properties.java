
package io.github.loncra.framework.crypto;

import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;

/**
 * SM2 配置信息（国密非对称加密）
 * <p>
 * SM2 是中国国家密码标准（GB/T 32905-2016）规定的椭圆曲线公钥密码算法。
 * 本配置类用于存储和管理 SM2 加密/解密、签名/验签所需的密钥对。
 * </p>
 *
 * @author maurice
 */
public class Sm2Properties {

    /**
     * 公共密钥（Base64 编码）
     */
    private String publicKey;

    /**
     * 私有密钥（Base64 编码）
     */
    private String privateKey;

    /**
     * 创建一个 SM2 配置信息
     */
    public Sm2Properties() {
    }

    /**
     * 创建一个 SM2 配置信息
     *
     * @param publicKey  公共密钥
     * @param privateKey 私有密钥
     */
    public Sm2Properties(
            String publicKey,
            String privateKey
    ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * 获取公共密钥
     *
     * @return 公共密钥
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * 设置公共密钥
     *
     * @param publicKey 公共密钥
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * 获取私有密钥
     *
     * @return 私有密钥
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 设置私有密钥
     *
     * @param privateKey 私有密钥
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 创建一个 SM2 配置信息
     *
     * @param publicKey  公共密钥
     * @param privateKey 私有密钥
     *
     * @return SM2 配置信息
     */
    public static Sm2Properties of(
            String publicKey,
            String privateKey
    ) {
        return new Sm2Properties(publicKey, privateKey);
    }

    /**
     * 获取公共密钥字节原
     *
     * @return 公共密钥字节原
     */
    public ByteSource getPublicKeyByteSource() {
        return new SimpleByteSource(publicKey);
    }

    /**
     * 获取私有密钥字节原
     *
     * @return 私有密钥字节原
     */
    public ByteSource getPrivateKeyByteSource() {
        return new SimpleByteSource(privateKey);
    }

}

package io.github.loncra.framework.mybatis.plus;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加解密配置
 *
 * @author maurice.chen
 *
 */
@ConfigurationProperties("loncra.framework.mybatis.plus.crypto")
public class CryptoProperties {

    /**
     * AES 加解密服务 Bean 名称
     */
    public static final String MYBATIS_PLUS_DATA_AES_CRYPTO_SERVICE_NAME = "mybatisPlusDataAesCryptoService";

    /**
     * RSA 加解密服务 Bean 名称
     */
    public static final String MYBATIS_PLUS_DATA_RSA_CRYPTO_SERVICE_NAME = "mybatisPlusDataRsaCryptoService";

    /**
     * AES 加密密钥
     */
    private String dataAesCryptoKey;

    /**
     * RSA 加密公钥
     */
    private String dataRasCryptoPublicKey;

    /**
     * RSA 解密私钥
     */
    private String dataRasCryptoPrivateKey;

    /**
     * 构造函数
     */
    public CryptoProperties() {

    }

    /**
     * 获取 AES 加密密钥
     *
     * @return AES 加密密钥
     */
    public String getDataAesCryptoKey() {
        return dataAesCryptoKey;
    }

    /**
     * 设置 AES 加密密钥
     *
     * @param dataAesCryptoKey AES 加密密钥
     */
    public void setDataAesCryptoKey(String dataAesCryptoKey) {
        this.dataAesCryptoKey = dataAesCryptoKey;
    }

    /**
     * 获取 RSA 加密公钥
     *
     * @return RSA 加密公钥
     */
    public String getDataRasCryptoPublicKey() {
        return dataRasCryptoPublicKey;
    }

    /**
     * 设置 RSA 加密公钥
     *
     * @param dataRasCryptoPublicKey RSA 加密公钥
     */
    public void setDataRasCryptoPublicKey(String dataRasCryptoPublicKey) {
        this.dataRasCryptoPublicKey = dataRasCryptoPublicKey;
    }

    /**
     * 获取 RSA 解密私钥
     *
     * @return RSA 解密私钥
     */
    public String getDataRasCryptoPrivateKey() {
        return dataRasCryptoPrivateKey;
    }

    /**
     * 设置 RSA 解密私钥
     *
     * @param dataRasCryptoPrivateKey RSA 解密私钥
     */
    public void setDataRasCryptoPrivateKey(String dataRasCryptoPrivateKey) {
        this.dataRasCryptoPrivateKey = dataRasCryptoPrivateKey;
    }
}

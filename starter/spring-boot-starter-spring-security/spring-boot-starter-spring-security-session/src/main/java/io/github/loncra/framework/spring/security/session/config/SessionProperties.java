package io.github.loncra.framework.spring.security.session.config;

import io.github.loncra.framework.crypto.CipherAlgorithmService;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 访问令牌配置属性
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.session")
public class SessionProperties {

    /**
     * 加解密算法名称
     */
    private String cipherAlgorithmName = CipherAlgorithmService.AES_ALGORITHM;

    /**
     * 加解密密钥
     */
    private String cryptoKey;

    /**
     * 最大 session 数
     */
    private int maximumSessions = Integer.MAX_VALUE;

    /**
     * 构造函数
     */
    public SessionProperties() {
    }

    /**
     * 获取加解密算法名称
     *
     * @return 加解密算法名称
     */
    public String getCipherAlgorithmName() {
        return cipherAlgorithmName;
    }

    /**
     * 设置加解密算法名称
     *
     * @param cipherAlgorithmName 加解密算法名称
     */
    public void setCipherAlgorithmName(String cipherAlgorithmName) {
        this.cipherAlgorithmName = cipherAlgorithmName;
    }

    /**
     * 获取加解密密钥
     *
     * @return 加解密密钥
     */
    public String getCryptoKey() {
        return cryptoKey;
    }

    /**
     * 设置加解密密钥
     *
     * @param cryptoKey 加解密密钥
     */
    public void setCryptoKey(String cryptoKey) {
        this.cryptoKey = cryptoKey;
    }

    /**
     * 获取最大 session 数
     *
     * @return 最大 session 数
     */
    public int getMaximumSessions() {
        return maximumSessions;
    }

    /**
     * 设置最大 session 数
     *
     * @param maximumSessions 最大 session 数
     */
    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }
}

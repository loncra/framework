package io.github.loncra.framework.spring.security.oauth2.authentication.config;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * OAuth2 配置属性
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.spring.security.oauth2")
public class OAuth2Properties {

    /**
     * 授权在缓存中的命名等；未覆盖 name 时默认使用新 Redis key 前缀，与历史默认前缀可能不同，集群升级请按需配置为旧 name。
     */
    private CacheProperties authorizationCache = CacheProperties.of(
            "loncra:framework:authentication:spring:security:oauth2:authorization:",
            TimeProperties.of(5, TimeUnit.MINUTES)
    );

    /**
     * 颁发者
     */
    private String issuer;

    /**
     * jwt 私钥信息
     */
    private String privateKey;

    /**
     * jwt 公钥信息
     */
    private String publicKey;

    /**
     * 秘钥 id
     */
    private String keyId;

    public OAuth2Properties() {
    }

    public CacheProperties getAuthorizationCache() {
        return authorizationCache;
    }

    public void setAuthorizationCache(CacheProperties authorizationCache) {
        this.authorizationCache = authorizationCache;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}

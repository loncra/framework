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
@ConfigurationProperties("loncra.framework.authentication.oauth2")
public class OAuth2Properties {

    /**
     * 授权缓存配置
     */
    private CacheProperties authorizationCache = CacheProperties.of(
            "loncra:framework:authentication:oauth2:authorization:",
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

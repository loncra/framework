package io.github.loncra.framework.captcha.token.support;

import io.github.loncra.framework.captcha.ConstructionCaptchaMetadata;
import io.github.loncra.framework.captcha.token.InterceptToken;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * 简单的验证码拦截 token 实现
 *
 * @author maurice
 */
public class SimpleInterceptToken extends ConstructionCaptchaMetadata implements InterceptToken {

    @Serial
    private static final long serialVersionUID = -7503502769793672985L;

    /**
     * 唯一识别
     */
    private String id;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 绑定 token 值
     */
    private CacheProperties token;

    /**
     * 对应的 token 参数名称
     */
    private String tokenParamName;

    public SimpleInterceptToken() {
    }

    public SimpleInterceptToken(
            String type,
            Map<String, Object> args
    ) {
        super(type, args);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public CacheProperties getToken() {
        return token;
    }

    public void setToken(CacheProperties token) {
        this.token = token;
    }

    public String getTokenParamName() {
        return tokenParamName;
    }

    public void setTokenParamName(String tokenParamName) {
        this.tokenParamName = tokenParamName;
    }

    @Override
    public boolean isExpired() {
        TimeProperties time = token.getExpiresTime();
        return Duration.between(getCreationTime(), Instant.now()).toMillis() > time.toMillis();
    }
}

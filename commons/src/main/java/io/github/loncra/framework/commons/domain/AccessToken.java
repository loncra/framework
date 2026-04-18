package io.github.loncra.framework.commons.domain;

import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;
import java.time.Instant;

/**
 * 认证访问 token
 *
 * @author maurice.chen
 */
public class AccessToken implements ExpiredToken {

    @Serial
    private static final long serialVersionUID = -8600594693881884136L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * token 值
     */
    private String value;

    /**
     * 超时时间
     */
    private TimeProperties expiresTime;

    /**
     * 构造函数
     */
    public AccessToken() {
    }

    /**
     * 构造函数
     *
     * @param value       token 值
     * @param expiresTime 超时时间配置
     */
    public AccessToken(
            String value,
            TimeProperties expiresTime
    ) {
        this.value = value;
        this.expiresTime = expiresTime;
    }

    @Override
    public Instant getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取 token 值
     *
     * @return token 值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 设置 token 值
     *
     * @param value token 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取超时时间配置
     *
     * @return 超时时间配置
     */
    @Override
    public TimeProperties getExpiresTime() {
        return expiresTime;
    }

    /**
     * 设置超时时间配置
     *
     * @param expiresTime 超时时间配置
     */
    public void setExpiresTime(TimeProperties expiresTime) {
        this.expiresTime = expiresTime;
    }
}

package io.github.loncra.framework.captcha;

import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

/**
 * 抽象的可过期验证码实现
 *
 * @author maurice
 */
public class ExpiredCaptcha implements Expired, Serializable {

    @Serial
    private static final long serialVersionUID = 2371567553401150929L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 过期时间（单位：秒）
     */
    private TimeProperties expireTime;

    /**
     * 构造函数
     */
    public ExpiredCaptcha() {
    }

    @Override
    public boolean isExpired() {
        return Duration.between(getCreationTime(), Instant.now()).toMillis() > expireTime.toMillis();
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
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
     * 获取过期时间
     *
     * @return 过期时间
     */
    public TimeProperties getExpireTime() {
        return expireTime;
    }

    /**
     * 设置过期时间
     *
     * @param expireTime 过期时间
     */
    public void setExpireTime(TimeProperties expireTime) {
        this.expireTime = expireTime;
    }
}

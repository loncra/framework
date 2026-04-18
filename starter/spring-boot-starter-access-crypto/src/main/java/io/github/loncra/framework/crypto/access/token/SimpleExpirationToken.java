package io.github.loncra.framework.crypto.access.token;

import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.crypto.access.AccessToken;
import io.github.loncra.framework.crypto.access.ExpirationToken;

import java.io.Serial;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 简单的可过期的 token 实现
 *
 * @author maurice
 */
public class SimpleExpirationToken extends SimpleToken implements ExpirationToken {

    @Serial
    private static final long serialVersionUID = -2524113941584019855L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 最后访问时间
     */
    private Instant lastAccessedTime = Instant.now();

    /**
     * 超时时间
     */
    private TimeProperties maxInactiveInterval = new TimeProperties(DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS, TimeUnit.SECONDS);

    /**
     * 时间超时时间
     */
    private Instant expirationTime = lastAccessedTime.plus(maxInactiveInterval.getValue(), maxInactiveInterval.getUnit().toChronoUnit());

    /**
     * 超时的 token
     *
     * @param token               访问 token
     * @param maxInactiveInterval 最大超时时间
     */
    public SimpleExpirationToken(
            AccessToken token,
            TimeProperties maxInactiveInterval
    ) {
        super(token.getType(), token.getToken(), token.getName(), token.getKey());
        this.maxInactiveInterval = maxInactiveInterval;
        this.expirationTime = creationTime.plus(maxInactiveInterval.getValue(), maxInactiveInterval.getUnit().toChronoUnit());
    }

    /**
     * 超时的 token
     */
    public SimpleExpirationToken() {
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

    @Override
    public Instant getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public void setLastAccessedTime(Instant lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
        this.expirationTime = lastAccessedTime.plus(
                maxInactiveInterval.getValue(),
                maxInactiveInterval.getUnit().toChronoUnit()
        );
    }

    @Override
    public TimeProperties getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public boolean isExpired() {
        return Instant.now().isAfter(this.expirationTime);
    }

    @Override
    public void setMaxInactiveInterval(TimeProperties maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override
    public Instant getExpirationTime() {
        return expirationTime;
    }

    /**
     * 设置过期时间
     *
     * @param expirationTime 过期时间
     */
    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }
}

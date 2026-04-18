package io.github.loncra.framework.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

/**
 * 可过期的 token
 *
 * @author maurice.chen
 */
public interface ExpiredToken extends Serializable {

    /**
     * 获取 token 值
     *
     * @return token 值
     */
    String getValue();

    /**
     * 获取超时时间
     *
     * @return 超时时间
     */
    TimeProperties getExpiresTime();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Instant getCreationTime();

    /**
     * 是否超时
     *
     * @return true 是，否则 false
     */
    @JsonIgnore
    default boolean isExpired() {
        return Duration.between(getCreationTime(), Instant.now()).toMillis() > getExpiresTime().toMillis();
    }

    /**
     * 获取过期日期时间
     *
     * @return 过期日期时间
     */
    default Instant getExpiresInDateTime() {
        return getCreationTime().plusMillis(getExpiresTime().toMillis());
    }
}

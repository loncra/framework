package io.github.loncra.framework.commons.retry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.TimeProperties;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 可重试的对象实现
 *
 * @author maurice.chen
 */
public interface Retryable {

    /**
     * 默认间隔时间次方
     */
    TimeProperties DEFAULT_POW_INTERVAL_TIME = new TimeProperties(5, TimeUnit.SECONDS);

    /**
     * 获取当前重试次数
     *
     * @return 重试次数
     */
    Integer getRetryCount();

    /**
     * 设置当前重试次数
     *
     * @param retryCount 当前重试次数
     */
    void setRetryCount(Integer retryCount);

    /**
     * 设置当前重试次数
     *
     * @return 重试次数
     */
    Integer getMaxRetryCount();

    /**
     * 设置最大重试次数
     *
     * @param maxRetryCount 最大重试次数
     */
    void setMaxRetryCount(Integer maxRetryCount);

    /**
     * 获取重试时间配置
     *
     * @return 重试时间配置
     */
    @JsonIgnore
    default TimeProperties getNextRetryTimeProperties() {
        return DEFAULT_POW_INTERVAL_TIME;
    }

    /**
     * 获取重试时间
     *
     * @return 重试时间
     */
    Instant getRetryTime();

    /**
     * 设置重试时间
     *
     * @param retryTime 重试时间
     */
    void setRetryTime(Instant retryTime);

    /**
     * 获取下一次重试时间
     *
     * @return 重试时间
     */
    @JsonIgnore
    default Instant getNextRetryTime() {
        return getRetryTime().plus(getNextRetryTimeProperties().toDuration());
    }

    /**
     * 是否可重试
     *
     * @return true 是，否则 false
     */
    default boolean isRetry() {
        return getRetryCount() < getMaxRetryCount();
    }
}

package io.github.loncra.framework.commons.domain.metadata;

import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.retry.Retryable;

import java.time.Instant;

/**
 * 重试元数据信息
 *
 * @author maurice.chen
 */
public class RetryMetadata implements ExecuteStatus.Body, Retryable {

    private ExecuteStatus executeStatus;

    private Instant successTime;

    private Instant retryTime;

    private Integer retryCount;

    private Integer maxRetryCount;

    private String exception;

    public RetryMetadata() {
    }

    @Override
    public ExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    @Override
    public void setExecuteStatus(ExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    @Override
    public Instant getSuccessTime() {
        return successTime;
    }

    @Override
    public void setSuccessTime(Instant successTime) {
        this.successTime = successTime;
    }

    @Override
    public Instant getRetryTime() {
        return retryTime;
    }

    @Override
    public void setRetryTime(Instant retryTime) {
        this.retryTime = retryTime;
    }

    @Override
    public Integer getRetryCount() {
        return retryCount;
    }

    @Override
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    @Override
    public void setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public String getException() {
        return exception;
    }

    @Override
    public void setException(String exception) {
        this.exception = exception;
    }
}

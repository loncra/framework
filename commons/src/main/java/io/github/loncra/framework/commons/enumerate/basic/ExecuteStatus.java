package io.github.loncra.framework.commons.enumerate.basic;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.retry.Retryable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * 执行状态枚举
 *
 * @author maurice
 */
public enum ExecuteStatus implements NameValueEnum<Integer> {

    /**
     * 待处理
     */
    Pending("待处理", -1),

    /**
     * 执行中
     */
    Processing("执行中", 0),

    /**
     * 执行成功
     */
    Success("执行成功", 1),

    /**
     * 重试中
     */
    Retrying("重试中", 2),

    /**
     * 不处理
     */
    Ignore("不处理", 3),

    /**
     * 执行失败
     */
    Failure("执行失败", 99),

    /**
     * 未知
     */
    Unknown("未知", Integer.parseInt(RestResult.UNKNOWN_EXECUTE_CODE));

    /**
     * 执行中的状态
     */
    public static final List<ExecuteStatus> EXECUTING_STATUS = Arrays.asList(Retrying, Processing);

    /**
     * 执行失败的状态
     */
    public static final List<ExecuteStatus> FAILURE_STATUS = Arrays.asList(Failure, Unknown);

    /**
     * 执行状态枚举
     *
     * @param name  名称
     * @param value 值
     */
    ExecuteStatus(
            String name,
            Integer value
    ) {
        this.name = name;
        this.value = value;
    }

    /**
     * 名称
     */
    private final String name;

    /**
     * 值
     */
    private final Integer value;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 成功设置值
     *
     * @param body 数据体
     */
    public static void success(Body body) {
        body.setExecuteStatus(ExecuteStatus.Success);
        body.setSuccessTime(Instant.now());
    }

    /**
     * 重试设置值（不设置为失败状态）
     *
     * @param body      数据体
     * @param exception 异常信息
     */
    public static void retry(
            Body body,
            String exception
    ) {
        retry(body, exception, false);
    }

    /**
     * 重试设置值
     *
     * @param body             数据体
     * @param exception        异常信息
     * @param setFailureStatus 是否设置为失败状态 true 是，否则 false
     */
    public static void retry(
            Body body,
            String exception,
            boolean setFailureStatus
    ) {
        body.setException(exception);

        if (body instanceof Retryable) {
            Retryable retryable = CastUtils.cast(body);
            retryable.setRetryCount(retryable.getRetryCount() + 1);
            retryable.setRetryTime(Instant.now());
            if (retryable.getRetryCount() > retryable.getMaxRetryCount()) {
                body.setExecuteStatus(setFailureStatus ? ExecuteStatus.Failure : ExecuteStatus.Unknown);
            }
            else {
                body.setExecuteStatus(ExecuteStatus.Retrying);
            }

        }
        else {
            body.setExecuteStatus(ExecuteStatus.Retrying);
        }
    }

    /**
     * 失败设置值
     *
     * @param body      数据体
     * @param exception 异常信息
     */
    public static void failure(
            Body body,
            String exception
    ) {
        body.setExecuteStatus(ExecuteStatus.Failure);
        body.setException(exception);
        body.setSuccessTime(Instant.now());
    }

    /**
     * 执行状态数据体
     *
     * @author maurice.chen
     */
    public interface Body {
        /**
         * 设置异常
         *
         * @param exception 异常信息
         */
        void setException(String exception);

        /**
         * 获取异常
         *
         * @return 异常信息
         */
        String getException();

        /**
         * 设置成功时间
         *
         * @param successTime 成功时间
         */
        void setSuccessTime(Instant successTime);

        /**
         * 获取成功时间
         *
         * @return 成功时间
         */
        Instant getSuccessTime();

        /**
         * 设置状态
         *
         * @param status 状态
         */
        void setExecuteStatus(ExecuteStatus status);

        /**
         * 获取状态
         *
         * @return 状态
         */
        ExecuteStatus getExecuteStatus();
    }
}

package io.github.loncra.framework.captcha;

import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * 抽象的可重试的验证码实现
 *
 * @author maurice
 */
public class ReusableCaptcha extends ExpiredCaptcha implements Serializable, Reusable {

    @Serial
    private static final long serialVersionUID = 2295130548867148592L;

    /**
     * 重试时间（单位：秒）
     */
    private TimeProperties retryTime;

    /**
     * 构造函数
     */
    public ReusableCaptcha() {
    }

    /**
     * 是否可重试
     *
     * @return true 是，否则 false
     */
    @Override
    public boolean isRetry() {

        if (Objects.isNull(retryTime)) {
            return true;
        }
        return Duration.between(getCreationTime(), Instant.now()).toMillis() > retryTime.toMillis();
    }

    /**
     * 获取重试时间
     *
     * @return 重试时间
     */
    public TimeProperties getRetryTime() {
        return retryTime;
    }

    /**
     * 设置重试时间
     *
     * @param retryTime 重试时间
     */
    public void setRetryTime(TimeProperties retryTime) {
        this.retryTime = retryTime;
    }
}

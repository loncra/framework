package io.github.loncra.framework.commons.minio;

import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;


/**
 * 可过期的桶
 *
 * @author maurice.chen
 */
public class ExpirableBucket extends Bucket {

    @Serial
    private static final long serialVersionUID = -2648131524041207239L;

    /**
     * 过期时间
     */
    private TimeProperties expirationTime;

    /**
     * 获取过期时间
     *
     * @return 过期时间配置
     */
    public TimeProperties getExpirationTime() {
        return expirationTime;
    }

    /**
     * 设置过期时间
     *
     * @param expirationTime 过期时间配置
     */
    public void setExpirationTime(TimeProperties expirationTime) {
        this.expirationTime = expirationTime;
    }
}

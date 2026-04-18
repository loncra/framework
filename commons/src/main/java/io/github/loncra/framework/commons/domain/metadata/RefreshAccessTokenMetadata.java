package io.github.loncra.framework.commons.domain.metadata;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;
import java.util.concurrent.TimeUnit;

/**
 * @author maurice.chen
 */
public class RefreshAccessTokenMetadata extends CloudSecretMetadata {

    @Serial
    private static final long serialVersionUID = 6636708642147562687L;

    /**
     * 刷新 token 提前时间配置
     */
    private TimeProperties refreshAccessTokenLeadTime = TimeProperties.of(10, TimeUnit.MINUTES);

    /**
     * 访问 token 缓存配置
     */
    private CacheProperties cache;

    public RefreshAccessTokenMetadata() {
    }

    public RefreshAccessTokenMetadata(CacheProperties cache) {
        this.cache = cache;
    }

    public RefreshAccessTokenMetadata(
            TimeProperties refreshAccessTokenLeadTime,
            CacheProperties cache
    ) {
        this.refreshAccessTokenLeadTime = refreshAccessTokenLeadTime;
        this.cache = cache;
    }

    public TimeProperties getRefreshAccessTokenLeadTime() {
        return refreshAccessTokenLeadTime;
    }

    public void setRefreshAccessTokenLeadTime(TimeProperties refreshAccessTokenLeadTime) {
        this.refreshAccessTokenLeadTime = refreshAccessTokenLeadTime;
    }

    public CacheProperties getCache() {
        return cache;
    }

    public void setCache(CacheProperties cache) {
        this.cache = cache;
    }
}

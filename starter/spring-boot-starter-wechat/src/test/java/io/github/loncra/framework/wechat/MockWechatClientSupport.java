package io.github.loncra.framework.wechat;

import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 为接口单测提供带缓存 access_token 的 {@link ConcurrentInterceptor} 替身，避免请求微信 stable_token。
 */
public final class MockWechatClientSupport {

    private MockWechatClientSupport() {
    }

    /**
     * 任意 getBucket 名均返回已填好的 access_token，供 getAccessTokenIfCacheNull 等直接命中缓存。
     */
    public static ConcurrentInterceptor concurrentWithCachedAccessToken(String tokenValue) {
        ConcurrentInterceptor ci = mock(ConcurrentInterceptor.class);
        RedissonClient redisson = mock(RedissonClient.class);
        @SuppressWarnings("unchecked")
        RBucket<AccessToken> bucket = mock(RBucket.class);
        when(ci.getRedissonClient()).thenReturn(redisson);
        doReturn(bucket).when(redisson).getBucket(anyString());
        AccessToken t = new AccessToken();
        t.setValue(tokenValue);
        t.setExpiresTime(TimeProperties.of(7_200, TimeUnit.SECONDS));
        when(bucket.get()).thenReturn(t);
        return ci;
    }
}

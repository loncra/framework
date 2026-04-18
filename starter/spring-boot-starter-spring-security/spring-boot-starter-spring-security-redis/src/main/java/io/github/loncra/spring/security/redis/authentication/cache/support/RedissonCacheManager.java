package io.github.loncra.spring.security.redis.authentication.cache.support;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.commons.domain.RefreshToken;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.DigestUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * redisson 形式的缓存管理实现
 *
 * @author maurice.chen
 */
public class RedissonCacheManager implements CacheManager {

    private final RedissonClient redissonClient;

    public RedissonCacheManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public SecurityPrincipal getSecurityPrincipal(CacheProperties authenticationCache) {
        RBucket<SecurityPrincipal> bucket = redissonClient.getBucket(authenticationCache.getName());
        return bucket.get();
    }

    @Override
    public void saveSecurityPrincipal(
            SecurityPrincipal principal,
            CacheProperties authenticationCache
    ) {
        RBucket<SecurityPrincipal> bucket = redissonClient.getBucket(authenticationCache.getName());
        TimeProperties time = authenticationCache.getExpiresTime();
        if (Objects.nonNull(time)) {
            bucket.setAsync(principal, time.getValue(), time.getUnit());
        }
        else {
            bucket.setAsync(principal);
        }
    }

    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(CacheProperties authorizationCache) {
        RBucket<Collection<GrantedAuthority>> bucket = redissonClient.getBucket(
                authorizationCache.getName(),
                new SerializationCodec()
        );
        return bucket.get();
    }

    @Override
    public void saveGrantedAuthorities(
            Collection<GrantedAuthority> grantedAuthorities,
            CacheProperties authorizationCache
    ) {
        RBucket<Collection<GrantedAuthority>> bucket = redissonClient.getBucket(
                authorizationCache.getName(),
                new SerializationCodec()
        );
        TimeProperties time = authorizationCache.getExpiresTime();
        if (Objects.nonNull(time)) {
            bucket.setAsync(grantedAuthorities, time.getValue(), time.getUnit());
        }
        else {
            bucket.setAsync(grantedAuthorities);
        }
    }

    @Override
    public SecurityContext getSecurityContext(
            String type,
            Object id,
            CacheProperties accessTokenCache
    ) {
        RBucket<SecurityContext> bucket = getSecurityContextBucket(type, id, accessTokenCache);
        return bucket.get();
    }

    @Override
    public void delaySecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    ) {
        TimeProperties timeProperties = accessTokenCache.getExpiresTime();
        if (Objects.isNull(timeProperties)) {
            return;
        }

        AuditAuthenticationToken authenticationToken = CastUtils.cast(context.getAuthentication());

        RBucket<SecurityContext> bucket = getSecurityContextBucket(
                authenticationToken.getType(),
                authenticationToken.getSecurityPrincipal().getId(),
                accessTokenCache
        );
        bucket.expireAsync(timeProperties.toDuration());
    }

    @Override
    public void saveSecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    ) {

        AuditAuthenticationToken authenticationToken = CastUtils.cast(context.getAuthentication());
        RBucket<SecurityContext> bucket = getSecurityContextBucket(
                authenticationToken.getType(),
                authenticationToken.getSecurityPrincipal().getId(),
                accessTokenCache
        );

        TimeProperties timeProperties = accessTokenCache.getExpiresTime();
        if (Objects.isNull(timeProperties)) {
            bucket.set(context);
        }
        else {
            bucket.set(context, timeProperties.toDuration());
        }

    }

    @Override
    public void saveSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties refreshTokenCache
    ) {
        RBucket<ExpiredToken> refreshBucket = getTokenBucket(
                refreshToken.getValue(),
                refreshTokenCache
        );
        TimeProperties timeProperties = refreshTokenCache.getExpiresTime();
        if (Objects.isNull(timeProperties)) {
            refreshBucket.setAsync(refreshToken);
        }
        else {
            refreshBucket.setAsync(refreshToken, timeProperties.getValue(), timeProperties.getUnit());
        }
    }

    @Override
    public RestResult<ExpiredToken> getRefreshToken(
            String refreshToken,
            CacheProperties refreshTokenCache
    ) {
        RBucket<ExpiredToken> refreshTokenBucket = getTokenBucket(refreshToken, refreshTokenCache);
        if (!refreshTokenBucket.isExists()) {
            return RestResult.ofException(
                    new ErrorCodeException("[" + refreshToken + "] 刷新令牌不存在", ErrorCodeException.CONTENT_NOT_EXIST)
            );
        }

        ExpiredToken refreshTokenValue = refreshTokenBucket.get();
        if (refreshTokenValue.isExpired()) {
            return RestResult.ofException(
                    new ErrorCodeException("[" + refreshToken + "] 刷新令牌已过期", ErrorCodeException.TIMEOUT_CODE)
            );
        }

        return RestResult.ofSuccess(refreshTokenValue);
    }

    @Override
    public void deleteSecurityContext(
            SecurityContext securityContext,
            CacheProperties accessTokenCache
    ) {
        String key = accessTokenCache.getName(securityContext.getAuthentication().getName());
        redissonClient.getKeys().delete(key);
    }

    @Override
    public void deleteSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties cache
    ) {
        String key = cache.getName(refreshToken.getValue());
        redissonClient.getKeys().delete(key);
    }

    public RBucket<ExpiredToken> getTokenBucket(
            String token,
            CacheProperties tokenCache
    ) {
        String md5 = DigestUtils.md5DigestAsHex(token.getBytes());
        return redissonClient.getBucket(tokenCache.getName(md5));
    }

    public RBucket<SecurityContext> getSecurityContextBucket(
            String type,
            Object deviceIdentified,
            CacheProperties accessTokenCache
    ) {
        String key = accessTokenCache.getName(type + CacheProperties.DEFAULT_SEPARATOR + deviceIdentified);
        return redissonClient.getBucket(key, new SerializationCodec());
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
}


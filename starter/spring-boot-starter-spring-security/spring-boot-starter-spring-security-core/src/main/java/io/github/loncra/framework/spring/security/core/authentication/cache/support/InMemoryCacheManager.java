package io.github.loncra.framework.spring.security.core.authentication.cache.support;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.commons.domain.RefreshToken;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.DigestUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存形式的缓存管理实现
 *
 * @author maurice.chen
 */
public class InMemoryCacheManager implements CacheManager {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    @Override
    public SecurityPrincipal getSecurityPrincipal(CacheProperties authenticationCache) {
        return CastUtils.cast(CACHE.get(authenticationCache.getName()));
    }

    @Override
    public void saveSecurityPrincipal(
            SecurityPrincipal principal,
            CacheProperties authenticationCache
    ) {
        CACHE.put(authenticationCache.getName(), principal);
    }

    @Override
    public Collection<GrantedAuthority> getGrantedAuthorities(CacheProperties authorizationCache) {
        return CastUtils.cast(CACHE.get(authorizationCache.getName()));
    }

    @Override
    public void saveGrantedAuthorities(
            Collection<GrantedAuthority> grantedAuthorities,
            CacheProperties authorizationCache
    ) {
        CACHE.put(authorizationCache.getName(), grantedAuthorities);
    }

    @Override
    public SecurityContext getSecurityContext(
            String type,
            Object id,
            CacheProperties accessTokenCache
    ) {
        return CastUtils.cast(CACHE.get(accessTokenCache.getName(type + CacheProperties.DEFAULT_SEPARATOR + id)));
    }

    @Override
    public void delaySecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    ) {

    }

    @Override
    public void saveSecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    ) {
        CACHE.put(accessTokenCache.getName(context.getAuthentication().getName()), context);
    }

    @Override
    public void saveSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties refreshTokenCache
    ) {
        String md5 = DigestUtils.md5DigestAsHex(refreshToken.getValue().getBytes());
        CACHE.put(refreshTokenCache.getName(md5), refreshToken);
    }

    @Override
    public RestResult<ExpiredToken> getRefreshToken(
            String refreshToken,
            CacheProperties refreshTokenCache
    ) {
        String md5 = DigestUtils.md5DigestAsHex(refreshToken.getBytes());
        ExpiredToken token = CastUtils.cast(CACHE.get(refreshTokenCache.getName(md5)));
        if (Objects.isNull(token)) {
            return RestResult.ofException(
                    new ErrorCodeException("token 已失效", ErrorCodeException.CONTENT_NOT_EXIST)
            );
        }
        if (token.isExpired()) {
            return RestResult.ofException(
                    new ErrorCodeException("token 已过期", ErrorCodeException.TIMEOUT_CODE)
            );
        }
        return RestResult.ofSuccess(token);
    }

    @Override
    public void deleteSecurityContext(
            SecurityContext securityContext,
            CacheProperties cache
    ) {
        CACHE.remove(cache.getName(securityContext.getAuthentication().getName()));
    }

    @Override
    public void deleteSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties cache
    ) {
        String md5 = DigestUtils.md5DigestAsHex(refreshToken.getValue().getBytes());
        CACHE.remove(cache.getName(md5), refreshToken);
    }
}

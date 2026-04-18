package io.github.loncra.framework.spring.security.core.authentication.cache;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.commons.domain.RefreshToken;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import java.util.Collection;

/**
 * 缓存管理器,用于多 spring security 所扩展的缓存内容进行管理。
 *
 * @author maurice.chen
 */
public interface CacheManager {

    /**
     * 默认存储安全上下文键名称
     */
    String DEFAULT_SPRING_SECURITY_CONTEXT_KEY = "spring:security:context:access-token:";

    /**
     * 默认刷新令牌键名称
     */
    String DEFAULT_REFRESH_TOKEN_KEY = "spring:security:context:refresh-token:";

    /**
     * 获取缓存的用户信息
     *
     * @param authenticationCache 认证缓存配置
     *
     * @return 用户信息
     */
    SecurityPrincipal getSecurityPrincipal(CacheProperties authenticationCache);

    /**
     * 缓存当前用户
     *
     * @param principal           当前用户
     * @param authenticationCache 认证缓存配置
     */
    void saveSecurityPrincipal(
            SecurityPrincipal principal,
            CacheProperties authenticationCache
    );

    /**
     * 获取授权信息
     *
     * @param authorizationCache 授权缓存配置
     *
     * @return 授权信息集合
     */
    Collection<GrantedAuthority> getGrantedAuthorities(CacheProperties authorizationCache);

    /**
     * 缓存授权信息
     *
     * @param grantedAuthorities 授权信息集合
     * @param authorizationCache 授权缓存配置
     */
    void saveGrantedAuthorities(
            Collection<GrantedAuthority> grantedAuthorities,
            CacheProperties authorizationCache
    );

    /**
     * 获取 spring security 上下文内容
     *
     * @param type             用户类型
     * @param id               主键 id
     * @param accessTokenCache 缓存配置信息
     *
     * @return spring security 上下文内容
     */
    SecurityContext getSecurityContext(
            String type,
            Object id,
            CacheProperties accessTokenCache
    );

    /**
     * 延期 spring security 上下文内容
     *
     * @param context          spring security 上下文内容
     * @param accessTokenCache 缓存配置信息
     */
    void delaySecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    );

    /**
     * 保存 spring security 上下文内容
     *
     * @param context          spring security 上下文内容
     * @param accessTokenCache 令牌访问缓存配置
     */
    void saveSecurityContext(
            SecurityContext context,
            CacheProperties accessTokenCache
    );

    /**
     * 保存 spring security 上下文 的刷新 token
     *
     * @param refreshToken      spring security 上下文 的刷新 token
     * @param refreshTokenCache spring security 上下文 的刷新 token 缓存配置
     */
    void saveSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties refreshTokenCache
    );

    /**
     * 验证 token 是否有效
     *
     * @param refreshToken      刷新 token
     * @param refreshTokenCache 刷新 token 缓存配置
     *
     * @return rest 结果集
     */
    RestResult<ExpiredToken> getRefreshToken(
            String refreshToken,
            CacheProperties refreshTokenCache
    );

    /**
     * 删除 spring security 上下文
     * @param securityContext 上下文内容
     * @param cache 缓存配置
     */
    void deleteSecurityContext(
            SecurityContext securityContext,
            CacheProperties cache
    );

    /**
     * 删除 spring security 上下文的刷新 token
     *
     * @param refreshToken 刷新 token
     * @param cache 缓存配置
     */
    void deleteSecurityContextRefreshToken(
            RefreshToken refreshToken,
            CacheProperties cache
    );
}

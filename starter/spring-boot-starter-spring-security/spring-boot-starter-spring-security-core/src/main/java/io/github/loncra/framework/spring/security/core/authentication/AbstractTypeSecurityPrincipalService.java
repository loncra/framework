package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;

/**
 * 抽象的用户明细服务实现，实现创建认证 token，等部分公用功能
 *
 * @author maurice.chen
 *
 */
public abstract class AbstractTypeSecurityPrincipalService implements TypeSecurityPrincipalService {

    private AuthenticationProperties authenticationProperties;

    public AbstractTypeSecurityPrincipalService() {
    }

    public void setAuthenticationProperties(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    /**
     * 获取配置信息
     *
     * @return 配置信息
     */
    public AuthenticationProperties getAuthenticationProperties() {
        return authenticationProperties;
    }

    @Override
    public CacheProperties getAuthorizationCache(
            TypeAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        return CacheProperties.of(
                authenticationProperties.getAuthorizationCache().getName(token.getType() + CacheProperties.DEFAULT_SEPARATOR + principal.getName()),
                authenticationProperties.getAuthorizationCache().getExpiresTime()
        );
    }

    @Override
    public CacheProperties getAuthenticationCache(TypeAuthenticationToken token) {
        return CacheProperties.of(
                authenticationProperties.getAuthenticationCache().getName(token.getName()),
                authenticationProperties.getAuthenticationCache().getExpiresTime()
        );
    }
}

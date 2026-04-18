package io.github.loncra.framework.spring.security.session.service;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.session.config.RememberMeProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * 带用户类型的 TokenBasedRememberMeServices 扩展
 *
 * @author maurice.chen
 */
public class TypeTokenBasedRememberMeServices extends TokenBasedRememberMeServices {


    public TypeTokenBasedRememberMeServices(
            RememberMeProperties rememberMeProperties,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager,
            UserDetailsManager userDetailsManager
    ) {
        super(rememberMeProperties.getKey(), new TypeTokenBasedRememberMeUserDetailsService(typeSecurityPrincipalManager, userDetailsManager));
        setAlwaysRemember(rememberMeProperties.isAlways());
        setParameter(rememberMeProperties.getParamName());
        setCookieName(rememberMeProperties.getCookieName());
        if (StringUtils.isNotEmpty(rememberMeProperties.getDomain())) {
            setCookieDomain(rememberMeProperties.getDomain());
        }
        setUseSecureCookie(rememberMeProperties.isUseSecureCookie());
        setTokenValiditySeconds(rememberMeProperties.getTokenValiditySeconds());
    }

    /**
     * 重写原始方法，如果是自身实现的 {@link AuditAuthenticationToken} 应该返回是 [类型:登录账户] 格式
     */
    @Override
    protected String retrieveUserName(Authentication authentication) {
        if (authentication instanceof AuditAuthenticationToken) {
            AuditAuthenticationToken token = CastUtils.cast(authentication);
            return token.getName();
        }
        else {
            return super.retrieveUserName(authentication);
        }
    }
}

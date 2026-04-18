package io.github.loncra.framework.spring.security.session;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

import java.util.Objects;

/**
 * AuthenticationSuccessToken 的信任解析器实现
 *
 * @author maurice.chen
 */
public class AuthenticationSuccessTokenTrustResolver extends AuthenticationTrustResolverImpl {

    @Override
    public boolean isAnonymous(Authentication authentication) {
        return super.isAnonymous(authentication);
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return false;
        }

        if (AuditAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            AuditAuthenticationToken token = CastUtils.cast(authentication);
            return token.isRememberMe();
        }
        return super.isRememberMe(authentication);
    }
}

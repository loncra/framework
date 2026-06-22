package io.github.loncra.framework.spring.security.core.captcha;

import io.github.loncra.framework.commons.RestResult;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CaptchaVerifyAuthenticationToken extends AbstractAuthenticationToken {

    public static final String AUTHORITY = "CAPTCHA_VERIFY";

    private final Object principal;

    private final Object credentials;

    public CaptchaVerifyAuthenticationToken(Object principal, String credentials) {
        super(List.of(new SimpleGrantedAuthority(AUTHORITY)));
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}

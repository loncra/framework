package io.github.loncra.framework.spring.security.core.captcha;

import io.github.loncra.framework.captcha.filter.CaptchaVerificationFilter;
import io.github.loncra.framework.captcha.filter.CaptchaVerificationService;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Supplier;

public class CaptchaMethodSecurityExpressionRoot extends SecurityExpressionRoot {

    public CaptchaMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public CaptchaMethodSecurityExpressionRoot(Supplier<Authentication> authentication) {
        super(authentication);
    }
    // FIXME 感觉这些实现不好，先不弄了先了，想清楚先
    public boolean isCaptchaVerify() {
        Object attribute = SpringMvcUtils.getRequestAttribute(CaptchaVerificationFilter.class.getName());
        if (attribute instanceof RestResult<?> result) {

            CaptchaVerifyAuthenticationToken token = new CaptchaVerifyAuthenticationToken(result.getData(), null);
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        return false;
    }
}

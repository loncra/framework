package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.security.audit.AuditPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * 当前用户方法拦截器，用于针对参数如果实现了 {@link AuditPrincipal} 时候可以自动将当前用户信息写入到该对象中。
 *
 * @author maurice.chen
 */
public class AuditPrincipalMethodInterceptor implements MethodInterceptor {

    @Nullable
    @Override
    public Object invoke(
            @Nonnull
            MethodInvocation invocation
    ) throws Throwable {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.isNull(securityContext)) {
            return invocation.proceed();
        }

        Authentication authentication = securityContext.getAuthentication();
        if (Objects.isNull(authentication) || !AuditAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return invocation.proceed();
        }

        AuditAuthenticationToken auditAuthenticationToken = CastUtils.cast(authentication);
        Object[] args = invocation.getArguments();
        for (Object arg : args) {
            if (arg instanceof AuditPrincipal auditPrincipal && StringUtils.isEmpty(auditPrincipal.getPrincipal())) {
                auditPrincipal.setPrincipal(auditAuthenticationToken.getName());
            }
        }

        return invocation.proceed();
    }
}

package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.spring.security.core.audit.config.AuditDetailsSource;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.actuate.audit.AuditEvent;

/**
 * spring security 审计仓库拦截，用于根据 {@link AuthenticationProperties#getIgnoreAuditTypes()} 和 {@link AuthenticationProperties#getIgnoreAuditTypes()}
 * 过滤具体的审计类型和审计用户使用，如:登录失败，认证失败等审计类型不需要写入审计里。
 *
 * @author maurice.chen
 */
public class SecurityAuditEventRepositoryWriteInterceptor implements AuditEventRepositoryWriteInterceptor {

    private final AuthenticationProperties authenticationProperties;

    public SecurityAuditEventRepositoryWriteInterceptor(
            AuthenticationProperties authenticationProperties
    ) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public boolean preAddHandle(AuditEvent auditEvent) {
        if (CollectionUtils.isNotEmpty(authenticationProperties.getIgnoreAuditTypes()) && authenticationProperties.getIgnoreAuditTypes().contains(auditEvent.getType())) {
            return false;
        }

        if (CollectionUtils.isNotEmpty(authenticationProperties.getIgnoreAuditPrincipals()) && authenticationProperties.getIgnoreAuditPrincipals().contains(auditEvent.getPrincipal())) {
            return false;
        }

        Object details = auditEvent.getData().get(AuditAuthenticationToken.DETAILS_KEY);
        if (!AuditDetailsSource.class.isAssignableFrom(details.getClass())) {
            return false;
        }

        return AuditEventRepositoryWriteInterceptor.super.preAddHandle(auditEvent);
    }

}

package io.github.loncra.framework.spring.security.session.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.audit.SecurityAuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import io.github.loncra.framework.spring.security.session.config.RememberMeProperties;
import io.github.loncra.framework.spring.security.session.entity.RememberMeAuthenticationDetails;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.util.AntPathMatcher;

/**
 * 记住我审计事件扩展
 *
 * @author maurice.chen
 */
public class RememberMeAuditEventRepositoryWriteInterceptor extends SecurityAuditEventRepositoryWriteInterceptor {

    private final RememberMeProperties rememberMeProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public RememberMeAuditEventRepositoryWriteInterceptor(
            AuthenticationProperties authenticationProperties,
            RememberMeProperties rememberMeProperties
    ) {
        super(authenticationProperties);
        this.rememberMeProperties = rememberMeProperties;
    }

    @Override
    public boolean preAddHandle(AuditEvent auditEvent) {

        Object details = auditEvent.getData().get(AuditAuthenticationToken.DETAILS_KEY);
        if (AuditAuthenticationSuccessDetails.class.isAssignableFrom(details.getClass())) {
            AuditAuthenticationSuccessDetails auditDetails = CastUtils.cast(details);
            return postAuditAuthenticationSuccessDetails(auditDetails, auditEvent);
        }

        return super.preAddHandle(auditEvent);
    }

    private boolean postAuditAuthenticationSuccessDetails(
            AuditAuthenticationSuccessDetails auditDetails,
            AuditEvent auditEvent
    ) {
        if (!auditDetails.isRemember()) {
            return RememberMeAuditEventRepositoryWriteInterceptor.super.preAddHandle(auditEvent);
        }

        if (!RememberMeAuthenticationDetails.class.isAssignableFrom(auditDetails.getRequestDetails().getClass())) {
            return RememberMeAuditEventRepositoryWriteInterceptor.super.preAddHandle(auditEvent);
        }

        RememberMeAuthenticationDetails requestAuthenticationDetails = CastUtils.cast(auditDetails.getRequestDetails());
        return antPathMatcher.match(rememberMeProperties.getLoginProcessingUrl(), requestAuthenticationDetails.getRequestUri());
    }
}

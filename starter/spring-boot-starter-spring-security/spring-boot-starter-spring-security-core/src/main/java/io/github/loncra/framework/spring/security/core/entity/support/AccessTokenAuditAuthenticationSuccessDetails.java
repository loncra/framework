package io.github.loncra.framework.spring.security.core.entity.support;

import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.spring.security.core.entity.AccessTokenDetails;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;

import java.io.Serial;
import java.util.Map;

/**
 * 访问令牌认证名称实现
 *
 * @author maurice.chen
 */
public class AccessTokenAuditAuthenticationSuccessDetails extends AuditAuthenticationSuccessDetails implements AccessTokenDetails {

    @Serial
    private static final long serialVersionUID = -5197874001081052789L;

    private ExpiredToken token;

    public AccessTokenAuditAuthenticationSuccessDetails(
            Object requestDetails,
            Map<String, Object> metadata,
            ExpiredToken token
    ) {
        super(requestDetails, metadata);
        this.token = token;
    }

    public AccessTokenAuditAuthenticationSuccessDetails(
            AuditAuthenticationSuccessDetails details,
            ExpiredToken token
    ) {
        super(details.getRequestDetails(), details.getMetadata());
        this.token = token;
    }

    @Override
    public ExpiredToken getToken() {
        return token;
    }

    @Override
    public void setToken(ExpiredToken token) {
        this.token = token;
    }
}

package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.security.entity.SecurityPrincipal;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * spring 安全的租户安全上下文实现
 *
 * @author maurice.chen
 */
public class SpringSecurityTenantContext extends SimpleTenantContext {

    private String type;

    private SecurityPrincipal  principal;

    private Instant lastAuthenticationTime;

    @Serial
    private static final long serialVersionUID = -6618308232292802985L;

    public SpringSecurityTenantContext(
            Serializable id,
            Map<String, Object> details
    ) {
        super(id, details);
    }

    public SpringSecurityTenantContext(Serializable id) {
        super(id);
    }

    public SpringSecurityTenantContext() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SecurityPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(SecurityPrincipal principal) {
        this.principal = principal;
    }

    public Instant getLastAuthenticationTime() {
        return lastAuthenticationTime;
    }

    public void setLastAuthenticationTime(Instant lastAuthenticationTime) {
        this.lastAuthenticationTime = lastAuthenticationTime;
    }
}

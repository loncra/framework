package io.github.loncra.framework.spring.security.core.entity;

import io.github.loncra.framework.spring.security.core.audit.config.AuditDetailsSource;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 审计的认证明细
 *
 * @author maurice.chen
 */
public class AuditAuthenticationSuccessDetails implements Serializable, AuditDetailsSource {

    @Serial
    private static final long serialVersionUID = -5201412324396969736L;

    private final Object requestDetails;

    private final Map<String, Object> metadata;

    private boolean isRemember;

    public AuditAuthenticationSuccessDetails(
            Object requestDetails,
            Map<String, Object> metadata
    ) {

        this.requestDetails = requestDetails;
        this.metadata = metadata;
    }

    public Object getRequestDetails() {
        return requestDetails;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

}

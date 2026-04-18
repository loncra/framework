package io.github.loncra.framework.commons.tenant;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 租户上下文
 *
 * @author maurice.chen
 */
public class SimpleTenantContext implements TenantContext {

    @Serial
    private static final long serialVersionUID = 6472085698811032959L;

    private Serializable id;

    private Map<String, Object> details;

    public SimpleTenantContext(
            Serializable id,
            Map<String, Object> details
    ) {
        this.id = id;
        this.details = details;
    }

    public SimpleTenantContext(Serializable id) {
        this.id = id;
    }

    public SimpleTenantContext() {
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public Map<String, Object> getDetails() {
        return details;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }
}

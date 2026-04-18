package io.github.loncra.framework.commons.tenant;

import java.util.Map;

/**
 * 简单的租户实体实现
 *
 * @author maurice.chen
 */
public class SimpleTenantEntity implements TenantEntity<String> {

    private String tenantId;

    private Map<String, Object> details;

    public SimpleTenantEntity(
            String tenantId,
            Map<String, Object> details
    ) {
        this.tenantId = tenantId;
        this.details = details;
    }

    public SimpleTenantEntity(String tenantId) {
        this.tenantId = tenantId;
    }

    public SimpleTenantEntity() {
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }
}

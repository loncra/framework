package io.github.loncra.framework.commons.tenant;

import java.io.Serializable;

/**
 * 租户实体
 *
 * @author maurice.chen
 *
 * @param <T> 字段类型
 */
public interface TenantEntity<T extends Serializable> extends Serializable {

    /**
     * 租户 id 字段
     */
    String TENANT_ID_FIELD = "tenantId";

    /**
     * 获取租户 id
     *
     * @return 租户 id
     */
    T getTenantId();
}

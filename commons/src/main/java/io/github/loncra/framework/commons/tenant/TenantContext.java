package io.github.loncra.framework.commons.tenant;

import java.io.Serializable;
import java.util.Map;

/**
 * 租户上下文接口
 *
 * @author maurice.chen
 */
public interface TenantContext extends Serializable {

    /**
     * 租户 id
     *
     * @return 租户 id
     */
    Serializable getId();

    /**
     * 明细信息
     *
     * @return 明细信息
     */
    Map<String, Object> getDetails();

    /**
     * 是否忽略租户
     * @return true 是，否则 false
     */
    boolean isIgnore();

    /**
     * 设置是否忽略租户
     * @param ignore true 是，否则 false
     */
    void setIgnore(boolean ignore);
}

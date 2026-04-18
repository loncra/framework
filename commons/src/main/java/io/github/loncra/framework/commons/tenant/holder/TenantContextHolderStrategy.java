package io.github.loncra.framework.commons.tenant.holder;

import io.github.loncra.framework.commons.tenant.SimpleTenantContext;

/**
 * 租户上下文 结果集持有者策略
 *
 * @author maurice.chen
 */
public interface TenantContextHolderStrategy {

    /**
     * 清除 租户上下文 结果集
     */
    void clear();

    /**
     * 获取 租户上下文 结果集
     *
     * @return 租户上下文 结果集
     */
    SimpleTenantContext get();

    /**
     * 设置 租户上下文 结果集
     *
     * @param result 租户上下文 结果集
     */
    void set(SimpleTenantContext result);

    /**
     * 创建 租户上下文 结果集
     *
     * @return 租户上下文 结果集
     */
    SimpleTenantContext create();
}

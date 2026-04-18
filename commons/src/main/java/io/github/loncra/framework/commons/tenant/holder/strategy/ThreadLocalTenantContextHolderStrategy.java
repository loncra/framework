package io.github.loncra.framework.commons.tenant.holder.strategy;


import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolderStrategy;

import java.util.Objects;

/**
 * Thread Local 的 socket 结果集持有者实现
 *
 * @author maurice.chen
 */
public class ThreadLocalTenantContextHolderStrategy implements TenantContextHolderStrategy {

    private static final ThreadLocal<SimpleTenantContext> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void clear() {
        THREAD_LOCAL.remove();
    }

    @Override
    public SimpleTenantContext get() {
        SimpleTenantContext result = THREAD_LOCAL.get();

        if (result == null) {
            result = create();
            THREAD_LOCAL.set(result);
        }

        return result;
    }

    @Override
    public void set(SimpleTenantContext result) {
        Objects.requireNonNull(result, "socket result 不能为空");
        THREAD_LOCAL.set(result);
    }

    @Override
    public SimpleTenantContext create() {
        return new SimpleTenantContext();
    }
}

package io.github.loncra.framework.mybatis.plus.tenant;

import io.github.loncra.framework.commons.tenant.TenantContext;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;

/**
 * 租户行条件策略：由接入方实现 Bean，决定当前 SQL 是否应追加租户条件（是否从 {@link TenantContextHolder} 解析租户 id）。
 * <p>用于运营后台跨租户查询等场景：返回 {@code false} 时，租户行插件不追加租户条件。</p>
 *
 * @author maurice.chen
 */
@FunctionalInterface
public interface TenantLinePolicy {

    /**
     * 始终追加租户行条件（与未自定义策略时的默认行为一致）。
     */
    TenantLinePolicy ALWAYS = (tenantContext) -> true;

    /**
     * 是否支持租户 id 扩展
     *
     * @param tenantContext 租户上下文
     *
     * @return {@code true} 表示应从 {@link TenantContextHolder} 取值并拼租户条件；{@code false} 表示不拼租户条件（不返回租户 id）。
     */
    boolean tenantIdSupport(TenantContext tenantContext);
}

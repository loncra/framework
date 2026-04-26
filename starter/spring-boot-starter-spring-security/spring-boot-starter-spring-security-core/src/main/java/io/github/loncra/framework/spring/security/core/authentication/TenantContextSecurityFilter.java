package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * 将当前认证主体在 {@link AuditAuthenticationSuccessDetails#getMetadata()} 中的租户 id 同步到 {@link TenantContextHolder}。
 * <p>
 * 须在 {@link org.springframework.security.web.context.SecurityContextHolderFilter} 之后执行，以便 {@link SecurityContextHolder} 已就绪。
 *
 * @author maurice.chen
 */
public class TenantContextSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            TenantContextHolder.clear();
            SecurityContext securityContext = SecurityContextHolder.getContext();
            SimpleTenantContext tenantContext = resolveTenantContext(securityContext);
            TenantContextHolder.set(tenantContext);

            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }

    /**
     * 从 {@link SecurityContextHolder} 解析租户 id（字符串形式）。
     *
     * @return 非空时可为业务使用；无法解析时为 {@code null}
     */
    public static SimpleTenantContext resolveTenantContext(SecurityContext securityContext) {
        if (Objects.isNull(securityContext)) {
            return new SpringSecurityTenantContext();
        }

        Authentication authentication = securityContext.getAuthentication();
        if (Objects.isNull(authentication)) {
            return new SpringSecurityTenantContext();
        }

        if (!AuditAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            SimpleTenantContext simpleTenantContext = new SimpleTenantContext();
            simpleTenantContext.setId(authentication.getName());
            simpleTenantContext.setDetails(CastUtils.convertValue(authentication.getDetails(), CastUtils.MAP_TYPE_REFERENCE));
            return simpleTenantContext;
        }

        AuditAuthenticationToken token = CastUtils.cast(authentication);

        Object details = token.getDetails();
        if (Objects.isNull(details) || !AuditAuthenticationSuccessDetails.class.isAssignableFrom(details.getClass())) {
            return new SpringSecurityTenantContext();
        }

        AuditAuthenticationSuccessDetails successDetails = CastUtils.cast(details);
        Object tenantId = successDetails.getMetadata().get(TenantEntity.TENANT_ID_FIELD);
        if (Objects.isNull(tenantId)) {
            tenantId = authentication.getName();
        }

        SpringSecurityTenantContext tenantContext = new SpringSecurityTenantContext(tenantId.toString(), successDetails.getMetadata());
        tenantContext.setType(token.getType());
        tenantContext.setPrincipal(token.getSecurityPrincipal());
        tenantContext.setLastAuthenticationTime(token.getLastAuthenticationTime());

        return tenantContext;
    }
}

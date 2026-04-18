package io.github.loncra.framework.spring.security.core.test.service;

import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.AbstractTypeSecurityPrincipalService;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.authentication.token.RequestAuthenticationToken;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MapTypeSecurityPrincipalService extends AbstractTypeSecurityPrincipalService implements InitializingBean {

    private static final Map<String, SimpleSecurityPrincipal> USER_DETAILS = Collections.synchronizedMap(new HashMap<>());

    private final PasswordEncoder passwordEncoder;

    public MapTypeSecurityPrincipalService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationProperties(AuthenticationProperties authenticationProperties) {
        super.setAuthenticationProperties(authenticationProperties);
    }

    @Override
    public void afterPropertiesSet() {
        USER_DETAILS.put("test", new SimpleSecurityPrincipal(1, getPasswordEncoder().encode("123456"), "test"));
        USER_DETAILS.put("admin", new SimpleSecurityPrincipal(2, getPasswordEncoder().encode("123456"), "admin"));
    }

    @Override
    public SecurityPrincipal getSecurityPrincipal(TypeAuthenticationToken token) throws AuthenticationException {
        return USER_DETAILS.get(token.getPrincipal().toString());
    }

    @Override
    public Collection<GrantedAuthority> getPrincipalGrantedAuthorities(
            TypeAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        Collection<GrantedAuthority> result = new LinkedHashSet<>();
        result.add(new SimpleGrantedAuthority("perms[operate]"));
        return result;
    }

    @Override
    public List<String> getType() {
        return Collections.singletonList("test");
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * 为租户上下文集成测试注入 {@link TenantEntity#TENANT_ID_FIELD} 元数据（与生产侧由业务写入 metadata 的行为一致）。
     */
    @Override
    public AuditAuthenticationSuccessDetails getPrincipalDetails(
            SecurityPrincipal principal,
            TypeAuthenticationToken token,
            AuditAuthenticationToken successToken,
            Collection<? extends GrantedAuthority> grantedAuthorities
    ) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        if (token instanceof RequestAuthenticationToken requestAuthenticationToken) {
            metadata.putAll(requestAuthenticationToken.getMetadata());
        }
        metadata.put(TenantEntity.TENANT_ID_FIELD, "tenant-user-" + principal.getUsername());
        return new AuditAuthenticationSuccessDetails(token.getDetails(), metadata);
    }

}

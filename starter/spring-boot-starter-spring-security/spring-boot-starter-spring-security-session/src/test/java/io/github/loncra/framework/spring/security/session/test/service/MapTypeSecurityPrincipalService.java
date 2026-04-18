package io.github.loncra.framework.spring.security.session.test.service;

import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.AbstractTypeSecurityPrincipalService;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
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
        SecurityPrincipal result = USER_DETAILS.get(token.getPrincipal().toString());
        if (Objects.isNull(result)) {
            result = USER_DETAILS.values()
                    .stream()
                    .filter(s -> s.getId().toString().equals(token.getPrincipal().toString()))
                    .findFirst()
                    .orElse(null);
        }

        return result;
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

}

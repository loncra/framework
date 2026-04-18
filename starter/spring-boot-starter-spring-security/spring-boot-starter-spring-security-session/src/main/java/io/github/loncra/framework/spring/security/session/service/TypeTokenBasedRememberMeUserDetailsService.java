package io.github.loncra.framework.spring.security.session.service;

import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * 基于登录用户密码的记住我用户明细服务实现,用于完成原生记住我且根据用户类型构造 {@link UserDetails } 使用
 *
 * @author maurice.chen
 */
public class TypeTokenBasedRememberMeUserDetailsService implements UserDetailsService, MessageSourceAware {

    private final TypeSecurityPrincipalManager typeSecurityPrincipalManager;

    private final UserDetailsManager userDetailsManager;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public TypeTokenBasedRememberMeUserDetailsService(
            TypeSecurityPrincipalManager typeSecurityPrincipalManager,
            UserDetailsManager userDetailsManager
    ) {
        this.typeSecurityPrincipalManager = typeSecurityPrincipalManager;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = getInMemoryUserDetails(username);
        if (Objects.nonNull(userDetails)) {
            return userDetails;
        }

        TypeAuthenticationToken token = typeSecurityPrincipalManager.createTypeAuthenticationToken(
                username,
                null,
                null
        );

        SecurityPrincipal principal = typeSecurityPrincipalManager.getSecurityPrincipal(token);
        if (Objects.isNull(principal)) {
            throw new UsernameNotFoundException(
                    messages.getMessage(
                            "TypeTokenBasedRememberMeUserDetailsService.badCredentials",
                            "自动登录获取用户信息失败"
                    )
            );
        }

        return new User(username, principal.getCredentials().toString(), new LinkedHashSet<>());
    }

    private UserDetails getInMemoryUserDetails(String username) {
        try {
            return userDetailsManager.loadUserByUsername(username);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

package io.github.loncra.framework.spring.security.session.test;

import io.github.loncra.framework.spring.security.core.SpringSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.core.authentication.AuditAuthenticationDetailsSource;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 自定义 spring security 的配置
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureAfter(SpringSecurityAutoConfiguration.class)
public class SpringSecurityConfig implements WebSecurityConfigurerAfterAdapter/*, OAuth2AuthorizationConfigurerAdapter*/ {

    private final AuthenticationProperties authenticationProperties;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SpringSecurityConfig(
            AuthenticationProperties authenticationProperties,
            AuthenticationFailureHandler authenticationFailureHandler,
            AuthenticationSuccessHandler authenticationSuccessHandler
    ) {
        this.authenticationProperties = authenticationProperties;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {

        try {

            httpSecurity
                    .formLogin(f -> f.passwordParameter(authenticationProperties.getPasswordParamName())
                            .usernameParameter(authenticationProperties.getUsernameParamName())
                            .loginProcessingUrl(authenticationProperties.getLoginProcessingUrl())
                            .authenticationDetailsSource(new AuditAuthenticationDetailsSource(authenticationProperties))
                            .failureHandler(authenticationFailureHandler)
                            .successHandler(authenticationSuccessHandler)
                    )
                    .logout(l -> {
                        try {
                            l.configure(httpSecurity);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

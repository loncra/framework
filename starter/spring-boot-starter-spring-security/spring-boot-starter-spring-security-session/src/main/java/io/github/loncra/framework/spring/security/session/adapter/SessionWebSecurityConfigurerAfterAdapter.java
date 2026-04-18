package io.github.loncra.framework.spring.security.session.adapter;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.provider.TypeRememberMeAuthenticationProvider;
import io.github.loncra.framework.spring.security.session.authentication.RememberMeAuthenticationDetailsSource;
import io.github.loncra.framework.spring.security.session.config.RememberMeProperties;
import io.github.loncra.framework.spring.security.session.config.SessionProperties;
import io.github.loncra.framework.spring.security.session.service.PersistentTokenRememberMeUserDetailsService;
import io.github.loncra.framework.spring.security.session.service.TypeTokenBasedRememberMeServices;
import io.github.loncra.framework.spring.security.session.token.SessionAccessTokenContextRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.util.Objects;

/**
 * session 安全是配置，用于扩展spring security 针对 session 的配置
 *
 * @author maurice.chen
 */
public class SessionWebSecurityConfigurerAfterAdapter implements WebSecurityConfigurerAfterAdapter {

    /**
     * 访问令牌上下文仓库
     */
    private final SessionAccessTokenContextRepository sessionAccessTokenContextRepository;

    /**
     * JSON 会话信息过期策略
     */
    private final SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * 会话注册表
     */
    private final SessionRegistry sessionRegistry;

    private final SessionProperties sessionProperties;

    private final RememberMeProperties rememberMeProperties;

    /**
     * 类型记住我认证提供者
     */
    private final TypeRememberMeAuthenticationProvider typeRememberMeAuthenticationProvider;

    /**
     * 基于令牌的记住我服务
     */
    private final TypeTokenBasedRememberMeServices typeTokenBasedRememberMeServices;

    public SessionWebSecurityConfigurerAfterAdapter(
            SessionAccessTokenContextRepository sessionAccessTokenContextRepository,
            SessionInformationExpiredStrategy sessionInformationExpiredStrategy,
            SessionRegistry sessionRegistry,
            SessionProperties sessionProperties,
            RememberMeProperties rememberMeProperties,
            TypeTokenBasedRememberMeServices typeTokenBasedRememberMeServices,
            TypeRememberMeAuthenticationProvider typeRememberMeAuthenticationProvider
    ) {
        this.sessionAccessTokenContextRepository = sessionAccessTokenContextRepository;
        this.sessionInformationExpiredStrategy = sessionInformationExpiredStrategy;
        this.sessionRegistry = sessionRegistry;
        this.sessionProperties = sessionProperties;
        this.rememberMeProperties = rememberMeProperties;
        this.typeTokenBasedRememberMeServices = typeTokenBasedRememberMeServices;
        this.typeRememberMeAuthenticationProvider = typeRememberMeAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(session -> {
            SessionManagementConfigurer<HttpSecurity>.ConcurrencyControlConfigurer sessionConfigurer = session.maximumSessions(sessionProperties.getMaximumSessions())
                    .sessionRegistry(sessionRegistry);
            if (Objects.nonNull(sessionInformationExpiredStrategy)) {
                sessionConfigurer.expiredSessionStrategy(sessionInformationExpiredStrategy);
            }
        }).securityContext(s -> s.securityContextRepository(sessionAccessTokenContextRepository));

        if (rememberMeProperties.isEnabled()) {
            createRememberMeSetting(httpSecurity);
        }

    }

    @Override
    public void build(SecurityFilterChain securityFilterChain, HttpSecurity httpSecurity) {
        RememberMeServices rememberMeServices = httpSecurity.getSharedObject(RememberMeServices.class);
        if (Objects.nonNull(rememberMeServices) && AbstractRememberMeServices.class.isAssignableFrom(rememberMeServices.getClass())) {
            AbstractRememberMeServices abstractRememberMeServices = CastUtils.cast(rememberMeServices);
            abstractRememberMeServices.setAuthenticationDetailsSource(new RememberMeAuthenticationDetailsSource());
        }
    }

    /**
     * 创建记住我设置
     *
     * @param httpSecurity HTTP 安全配置
     * @throws Exception 配置异常
     */
    private void createRememberMeSetting(HttpSecurity httpSecurity) throws Exception {
        try {
            PersistentTokenRepository tokenRepository = httpSecurity
                    .getSharedObject(ApplicationContext.class)
                    .getBean(PersistentTokenRepository.class);

            httpSecurity
                    .rememberMe(r -> r
                            .userDetailsService(new PersistentTokenRememberMeUserDetailsService())
                            .alwaysRemember(rememberMeProperties.isAlways())
                            .rememberMeCookieName(rememberMeProperties.getCookieName())
                            .tokenValiditySeconds(rememberMeProperties.getTokenValiditySeconds())
                            .tokenRepository(tokenRepository)
                            .rememberMeCookieDomain(rememberMeProperties.getDomain())
                            .rememberMeParameter(rememberMeProperties.getParamName())
                            .useSecureCookie(rememberMeProperties.isUseSecureCookie())
                            .key(rememberMeProperties.getKey())
                    );
        } catch (Exception e){
            httpSecurity.rememberMe(r -> r.rememberMeServices(typeTokenBasedRememberMeServices));
        }
        httpSecurity.authenticationProvider(typeRememberMeAuthenticationProvider);
    }

}

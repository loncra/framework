package io.github.loncra.framework.spring.security.session;

import io.github.loncra.framework.spring.security.core.SpringSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.core.audit.SecurityAuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.spring.security.core.authentication.JsonSessionInformationExpiredStrategy;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.provider.TypeRememberMeAuthenticationProvider;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.session.adapter.SessionWebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.session.audit.RememberMeAuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.spring.security.session.config.RememberMeProperties;
import io.github.loncra.framework.spring.security.session.config.SessionProperties;
import io.github.loncra.framework.spring.security.session.controller.TokenController;
import io.github.loncra.framework.spring.security.session.filter.AccessTokenSessionFilter;
import io.github.loncra.framework.spring.security.session.service.TypeTokenBasedRememberMeServices;
import io.github.loncra.framework.spring.security.session.token.SessionAccessTokenContextRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * spring session 自动配置
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass({SessionRepositoryFilter.class})
@EnableConfigurationProperties({SessionProperties.class, RememberMeProperties.class})
@AutoConfigureBefore({SpringSecurityAutoConfiguration.class})
public class SpringSessionAutoConfiguration {

    /**
     * 创建访问令牌上下文仓库 Bean
     *
     * @param authenticationProperties 认证配置属性
     * @param sessionProperties        session 配置
     * @param cacheManager             缓存管理器
     *
     * @return 访问令牌上下文仓库实例
     */
    @Bean
    @ConditionalOnMissingBean(SessionAccessTokenContextRepository.class)
    public SessionAccessTokenContextRepository accessTokenContextRepository(
            AuthenticationProperties authenticationProperties,
            SessionProperties sessionProperties,
            CacheManager cacheManager
    ) {

        return new SessionAccessTokenContextRepository(
                cacheManager,
                sessionProperties,
                authenticationProperties
        );
    }

    /**
     * 创建访问令牌控制器 Bean
     *
     * @param cacheManager             缓存管理器
     * @param authenticationProperties 访问令牌配置属性
     *
     * @return 访问令牌控制器实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "loncra.framework.authentication.access-token", value = "enable-refresh-access-token", havingValue = "true")
    public TokenController accessTokenController(
            CacheManager cacheManager,
            AuthenticationProperties authenticationProperties
    ) {
        return new TokenController(cacheManager, authenticationProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy jsonSessionInformationExpiredStrategy() {
        return new JsonSessionInformationExpiredStrategy();
    }

    @Bean
    public SessionWebSecurityConfigurerAfterAdapter sessionWebSecurityConfigurerAfterAdapter(
            SessionAccessTokenContextRepository sessionAccessTokenContextRepository,
            SessionInformationExpiredStrategy sessionInformationExpiredStrategy,
            SessionRegistry sessionRegistry,
            SessionProperties sessionProperties,
            RememberMeProperties rememberMeProperties,
            TypeTokenBasedRememberMeServices typeTokenBasedRememberMeServices,
            TypeRememberMeAuthenticationProvider typeRememberMeAuthenticationProvider
    ) {
        return new SessionWebSecurityConfigurerAfterAdapter(
                sessionAccessTokenContextRepository,
                sessionInformationExpiredStrategy,
                sessionRegistry,
                sessionProperties,
                rememberMeProperties,
                typeTokenBasedRememberMeServices,
                typeRememberMeAuthenticationProvider
        );
    }

    /**
     * 配置一个 accessToken session filter 用于存在 accessToken 时，spring session 不自动创建
     *
     * @param sessionAccessTokenContextRepository accessToken 上下文仓库
     *
     * @return filter注册实例
     */
    @Bean
    public FilterRegistrationBean<AccessTokenSessionFilter> accessTokenSessionFilter(SessionAccessTokenContextRepository sessionAccessTokenContextRepository) {
        AccessTokenSessionFilter accessTokenSessionFilter = new AccessTokenSessionFilter(sessionAccessTokenContextRepository);

        FilterRegistrationBean<AccessTokenSessionFilter> filterRegistrationBean = new FilterRegistrationBean<>(accessTokenSessionFilter);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new JsonSessionInformationExpiredStrategy();
    }

    /**
     * 创建类型记住我认证提供者 Bean
     *
     * @param rememberMe                   记住我配置属性
     * @param typeSecurityPrincipalManager 类型安全主体管理器
     *
     * @return 类型记住我认证提供者实例
     */
    @Bean
    @ConditionalOnMissingBean(TypeRememberMeAuthenticationProvider.class)
    public TypeRememberMeAuthenticationProvider typeRememberMeAuthenticationProvider(
            RememberMeProperties rememberMe,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager
    ) {
        return new TypeRememberMeAuthenticationProvider(rememberMe.getKey(), typeSecurityPrincipalManager);
    }

    /**
     * 创建基于令牌的记住我服务 Bean
     *
     * @param typeSecurityPrincipalManager 类型安全主体管理器
     * @param rememberMeProperties         记住我配置属性
     * @param userDetailsManager           用户详情管理器
     *
     * @return 基于令牌的记住我服务实例
     */
    @Bean
    @ConditionalOnMissingBean(TypeTokenBasedRememberMeServices.class)
    public TypeTokenBasedRememberMeServices typeTokenBasedRememberMeServices(
            TypeSecurityPrincipalManager typeSecurityPrincipalManager,
            RememberMeProperties rememberMeProperties,
            UserDetailsManager userDetailsManager
    ) {
        return new TypeTokenBasedRememberMeServices(rememberMeProperties, typeSecurityPrincipalManager, userDetailsManager);
    }

    /**
     * 创建默认方法安全表达式处理器 Bean
     *
     * @param defaultsProvider 授予权限默认值提供者
     * @param context          应用上下文
     *
     * @return 默认方法安全表达式处理器实例
     */
    @Bean
    public DefaultMethodSecurityExpressionHandler authenticationSuccessTokenTrustResolverExpressionHandler(
            ObjectProvider<GrantedAuthorityDefaults> defaultsProvider,
            ApplicationContext context
    ) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setTrustResolver(new AuthenticationSuccessTokenTrustResolver());
        defaultsProvider.ifAvailable((d) -> handler.setDefaultRolePrefix(d.getRolePrefix()));
        handler.setApplicationContext(context);
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityAuditEventRepositoryWriteInterceptor.class)
    public RememberMeAuditEventRepositoryWriteInterceptor rememberMeAuditEventRepositoryWriteInterceptor(
            AuthenticationProperties authenticationProperties,
            RememberMeProperties rememberMeProperties
    ) {
        return new RememberMeAuditEventRepositoryWriteInterceptor(authenticationProperties, rememberMeProperties);
    }
}


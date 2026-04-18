package io.github.loncra.framework.spring.security.core;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.spring.security.core.authentication.AuditAuthenticationDetailsSource;
import io.github.loncra.framework.spring.security.core.authentication.IpAuthenticationFilter;
import io.github.loncra.framework.spring.security.core.authentication.RestResultAuthenticationEntryPoint;
import io.github.loncra.framework.spring.security.core.authentication.TenantContextSecurityFilter;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.provider.SecurityPrincipalAuthenticationProvider;
import io.github.loncra.framework.spring.security.core.plugin.PluginSourceAuthorizationManager;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * spring security 配置实现
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(AuthenticationProperties.class/*{AuthenticationProperties.class, RememberMeProperties.class}*/)
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security", value = "enabled", matchIfMissing = true)
public class DefaultWebSecurityAutoConfiguration {

    /**
     * 认证配置属性
     */
    private final AuthenticationProperties authenticationProperties;

    /**
     * Web 安全配置后适配器列表
     */
    private final List<WebSecurityConfigurerAfterAdapter> webSecurityConfigurerAfterAdapters;

    /**
     * 错误结果解析器列表
     */
    private final List<ErrorResultResolver> resultResolvers;

    private final SecurityPrincipalAuthenticationProvider securityPrincipalAuthenticationProvider;

    /**
     * 构造函数
     *
     * @param authenticationProperties 认证配置属性
     * @param errorResultResolvers 错误结果解析器提供者
     * @param webSecurityConfigurerAfterAdapter Web 安全配置后适配器提供者
     */
    public DefaultWebSecurityAutoConfiguration(AuthenticationProperties authenticationProperties,
                                               ObjectProvider<ErrorResultResolver> errorResultResolvers,
                                               ObjectProvider<WebSecurityConfigurerAfterAdapter> webSecurityConfigurerAfterAdapter,
                                               SecurityPrincipalAuthenticationProvider securityPrincipalAuthenticationProvider) {

        this.authenticationProperties = authenticationProperties;
        this.webSecurityConfigurerAfterAdapters = webSecurityConfigurerAfterAdapter.stream().collect(Collectors.toList());
        this.resultResolvers = errorResultResolvers.stream().collect(Collectors.toList());
        this.securityPrincipalAuthenticationProvider = securityPrincipalAuthenticationProvider;
    }

    /**
     * 创建安全过滤器链 Bean
     *
     * @param httpSecurity HTTP 安全配置
     * @return 安全过滤器链实例
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authenticationProvider(securityPrincipalAuthenticationProvider)
                .authorizeHttpRequests(a -> a
                        .requestMatchers(authenticationProperties.getPermitUriAntMatchers().toArray(new String[0]))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(b -> b
                        .securityContextRepository(new RequestAttributeSecurityContextRepository())
                        .authenticationDetailsSource(new AuditAuthenticationDetailsSource(authenticationProperties))
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .exceptionHandling(c -> c
                        .accessDeniedHandler(this.forbiddenAccessDeniedHandler())
                        .authenticationEntryPoint(new RestResultAuthenticationEntryPoint(resultResolvers))
                )
                .requestCache(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.ignoringRequestMatchers(SpringMvcUtils.ANT_PATH_MATCH_ALL));



        if (CollectionUtils.isNotEmpty(webSecurityConfigurerAfterAdapters)) {
            for (WebSecurityConfigurerAfterAdapter a : webSecurityConfigurerAfterAdapters) {
                a.configure(httpSecurity);
            }
        }

        httpSecurity.addFilterBefore(new IpAuthenticationFilter(this.authenticationProperties), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(new TenantContextSecurityFilter(), AuthorizationFilter.class);

        SecurityFilterChain securityFilterChain = httpSecurity.build();

        if (CollectionUtils.isNotEmpty(webSecurityConfigurerAfterAdapters)) {
            for (WebSecurityConfigurerAfterAdapter a : webSecurityConfigurerAfterAdapters) {
                a.build(securityFilterChain, httpSecurity);
            }
        }

        return securityFilterChain;
    }

    /**
     * 创建禁止访问拒绝处理器
     *
     * @return 禁止访问拒绝处理器
     */
    private AccessDeniedHandler forbiddenAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            RestResult<?> result = RestResult.of(accessDeniedException.getMessage(), HttpStatus.FORBIDDEN.value(), String.valueOf(HttpStatus.FORBIDDEN.value()));
            response.getWriter().write(CastUtils.getObjectMapper().writeValueAsString(result));
        };
    }

    /**
     * 创建 Web 安全定制器 Bean
     *
     * @return Web 安全定制器实例
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            if (CollectionUtils.isNotEmpty(webSecurityConfigurerAfterAdapters)) {
                for (WebSecurityConfigurerAfterAdapter a : webSecurityConfigurerAfterAdapters) {
                    a.configure(web);
                }
            }
        };
    }

    /**
     * 创建 Dao 认证提供者 Bean
     *
     * @param userDetailsManager 用户详情管理器
     * @param passwordEncoder 密码编码器
     * @return Dao 认证提供者实例
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsManager);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * 创建插件源授权管理器 Bean
     *
     * @param authenticationProperties 认证配置属性
     * @return 插件源授权管理器实例
     */
    @Bean
    @ConditionalOnMissingBean(PluginSourceAuthorizationManager.class)
    public PluginSourceAuthorizationManager pluginSourceAuthorizationManager(AuthenticationProperties authenticationProperties) {
        return new PluginSourceAuthorizationManager(authenticationProperties);
    }

    /**
     * 创建插件授权方法拦截器 Bean
     *
     * @param strategyProvider 安全上下文持有策略提供者
     * @param pluginSourceAuthorizationManager 插件源授权管理器
     * @param eventPublisherProvider 授权事件发布器提供者
     * @return 插件授权方法拦截器实例
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor pluginAuthorizationMethodInterceptor(ObjectProvider<SecurityContextHolderStrategy> strategyProvider,
                                                        PluginSourceAuthorizationManager pluginSourceAuthorizationManager,
                                                        ObjectProvider<AuthorizationEventPublisher> eventPublisherProvider) {


        AuthorizationManagerBeforeMethodInterceptor interceptor = new AuthorizationManagerBeforeMethodInterceptor(
                new AnnotationMatchingPointcut(null, Plugin.class, true),
                pluginSourceAuthorizationManager
        );

        interceptor.setOrder(AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder() - BigDecimal.ONE.intValue());
        strategyProvider.ifAvailable(interceptor::setSecurityContextHolderStrategy);
        eventPublisherProvider.ifAvailable(interceptor::setAuthorizationEventPublisher);

        return interceptor;
    }
}

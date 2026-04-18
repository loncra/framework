package io.github.loncra.framework.spring.security.core;

import io.github.loncra.framework.security.entity.RoleAuthority;
import io.github.loncra.framework.spring.security.core.audit.*;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.authentication.TypeSecurityPrincipalService;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.cache.support.InMemoryCacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.config.CaptchaVerificationProperties;
import io.github.loncra.framework.spring.security.core.authentication.config.PluginProperties;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationFailureHandler;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationFailureResponse;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationSuccessHandler;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationSuccessResponse;
import io.github.loncra.framework.spring.security.core.authentication.provider.SecurityPrincipalAuthenticationProvider;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.core.plugin.PluginEndpoint;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * spring security 重写支持自动配置类
 *
 * @author maurice
 */
@Configuration
@EnableConfigurationProperties({
        AuthenticationProperties.class,
        PluginProperties.class,
        CaptchaVerificationProperties.class,
        ControllerAuditProperties.class
})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security", value = "enabled", matchIfMissing = true)
public class SpringSecurityAutoConfiguration {

    /**
     * 创建插件端点 Bean
     *
     * @param infoContributor 信息贡献者提供者
     *
     * @return 插件端点实例
     */
    @Bean
    public PluginEndpoint pluginEndpoint(
            ObjectProvider<InfoContributor> infoContributor,
            PluginProperties properties
    ) {
        return new PluginEndpoint(infoContributor.stream().collect(Collectors.toList()), properties);
    }

    /**
     * 创建控制器审计处理器拦截器 Bean
     *
     * @param controllerAuditProperties 控制器审计配置属性
     *
     * @return 控制器审计处理器拦截器实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "loncra.framework.security.audit", name = "enabled", havingValue = "true")
    public ControllerAuditHandlerInterceptor controllerAuditHandlerInterceptor(ControllerAuditProperties controllerAuditProperties) {
        return new ControllerAuditHandlerInterceptor(controllerAuditProperties);
    }

    /**
     * 创建密码编码器 Bean
     *
     * @return BCrypt 密码编码器实例
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 创建内存缓存管理器 Bean
     *
     * @return 内存缓存管理器实例
     */
    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager() {
        return new InMemoryCacheManager();
    }

    /**
     * 创建 JSON 认证失败处理器 Bean
     *
     * @param failureResponse          认证失败响应提供者
     * @param authenticationProperties 认证配置属性
     *
     * @return JSON 认证失败处理器实例
     */
    @Bean
    @ConditionalOnMissingBean(JsonAuthenticationFailureHandler.class)
    public JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler(
            ObjectProvider<JsonAuthenticationFailureResponse> failureResponse,
            AuthenticationProperties authenticationProperties
    ) {
        return new JsonAuthenticationFailureHandler(
                failureResponse.orderedStream().collect(Collectors.toList()),
                authenticationProperties
        );
    }

    /**
     * 创建 JSON 认证成功处理器 Bean
     *
     * @param successResponse 认证成功响应提供者
     * @param properties      认证配置属性
     *
     * @return JSON 认证成功处理器实例
     */
    @Bean
    @ConditionalOnMissingBean(JsonAuthenticationSuccessHandler.class)
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler(
            ObjectProvider<JsonAuthenticationSuccessResponse> successResponse,
            AuthenticationProperties properties
    ) {

        List<PathPatternRequestMatcher> antPathRequestMatchers = new LinkedList<>();

        return new JsonAuthenticationSuccessHandler(
                successResponse.orderedStream().collect(Collectors.toList()),
                properties,
                antPathRequestMatchers
        );
    }

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(prefix = "loncra.framework.security.audit", name = "enabled", havingValue = "true")
    public static class DefaultWebMvcConfigurer implements WebMvcConfigurer {

        /**
         * 控制器审计处理器拦截器
         */
        private final ControllerAuditHandlerInterceptor controllerAuditHandlerInterceptor;

        /**
         * 构造函数
         *
         * @param controllerAuditHandlerInterceptor 控制器审计处理器拦截器
         */
        public DefaultWebMvcConfigurer(ControllerAuditHandlerInterceptor controllerAuditHandlerInterceptor) {
            this.controllerAuditHandlerInterceptor = controllerAuditHandlerInterceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(controllerAuditHandlerInterceptor);
        }
    }

    /**
     * 创建审计主体切面顾问 Bean
     *
     * @return 审计主体切面顾问实例
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @ConditionalOnProperty(prefix = "loncra.framework.security.audit", name = "enabled", havingValue = "true")
    public AuditPrincipalPointcutAdvisor auditPrincipalPointcutAdvisor() {
        return new AuditPrincipalPointcutAdvisor(new AuditPrincipalMethodInterceptor());
    }

    /**
     * 创建请求体属性增强适配器 Bean
     *
     * @return 请求体属性增强适配器实例
     */
    @Bean
    @ConditionalOnMissingBean(RequestBodyAttributeAdviceAdapter.class)
    public RequestBodyAttributeAdviceAdapter requestBodyAttributeAdviceAdapter() {
        return new RequestBodyAttributeAdviceAdapter();
    }

    /**
     * 创建安全审计事件仓库拦截器 Bean
     *
     * @param authenticationProperties 认证配置属性
     *
     * @return 安全审计事件仓库拦截器实例
     */
    @Bean
    @ConditionalOnMissingBean(SecurityAuditEventRepositoryWriteInterceptor.class)
    public SecurityAuditEventRepositoryWriteInterceptor securityAuditEventRepositoryInterceptor(
            AuthenticationProperties authenticationProperties
    ) {
        return new SecurityAuditEventRepositoryWriteInterceptor(authenticationProperties);
    }

    /**
     * 创建类型安全主体管理器 Bean
     *
     * @param cacheManager                  缓存管理器
     * @param typeSecurityPrincipalServices 类型安全主体服务提供者
     *
     * @return 类型安全主体管理器实例
     */
    @Bean
    @ConditionalOnMissingBean(TypeSecurityPrincipalManager.class)
    public TypeSecurityPrincipalManager typeSecurityPrincipalManager(
            CacheManager cacheManager,
            ObjectProvider<TypeSecurityPrincipalService> typeSecurityPrincipalServices
    ) {

        return new TypeSecurityPrincipalManager(
                typeSecurityPrincipalServices.stream().collect(Collectors.toList()),
                cacheManager
        );

    }

    /**
     * 创建内存用户详情管理器 Bean
     *
     * @param authenticationProperties 认证配置属性
     * @param passwordEncoder          密码编码器
     *
     * @return 内存用户详情管理器实例
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            AuthenticationProperties authenticationProperties,
            PasswordEncoder passwordEncoder
    ) {

        List<UserDetails> userDetails = new LinkedList<>();
        for (SecurityProperties.User user : authenticationProperties.getUsers()) {
            List<SimpleGrantedAuthority> roleAuthorities = user
                    .getRoles()
                    .stream()
                    .map(s -> Strings.CS.prependIfMissing(s, RoleAuthority.DEFAULT_ROLE_PREFIX))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            userDetails.add(new User(user.getName(), passwordEncoder.encode(user.getPassword()), roleAuthorities));
        }
        return new InMemoryUserDetailsManager(userDetails);
    }


    /**
     * 创建会话注册表 Bean
     *
     * @return 会话注册表实例
     */
    @Bean
    @ConditionalOnMissingBean(SessionRegistry.class)
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SecurityPrincipalAuthenticationProvider.class)
    public SecurityPrincipalAuthenticationProvider securityPrincipalAuthenticationProvider(
            AuthenticationProperties authenticationProperties,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager
    ) {
        return new SecurityPrincipalAuthenticationProvider(
                authenticationProperties,
                typeSecurityPrincipalManager
        );
    }
}

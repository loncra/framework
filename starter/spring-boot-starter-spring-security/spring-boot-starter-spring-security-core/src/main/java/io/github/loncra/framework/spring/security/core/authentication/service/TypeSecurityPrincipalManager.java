package io.github.loncra.framework.spring.security.core.authentication.service;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.TypeSecurityPrincipalService;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.token.RequestAuthenticationToken;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
import io.github.loncra.framework.spring.security.core.exception.CodeAuthenticationServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 带类型的安全用户管理
 *
 * @author maurice.chen
 */
public class TypeSecurityPrincipalManager implements InitializingBean, MessageSourceAware {

    /**
     * 认证缓存块名称
     */
    public final static String DEFAULT_AUTHENTICATION_KEY_NAME = "loncra:framework:spring:security:authentication:";

    /**
     * 授权缓存块名称
     */
    public final static String DEFAULT_AUTHORIZATION_KEY_NAME = "loncra:framework:spring:security:authorization:";

    /**
     * 带类型的安全用户服务集合
     */
    private final List<TypeSecurityPrincipalService> typeSecurityPrincipalServices;

    /**
     * 缓存管理
     */
    private final CacheManager cacheManager;

    /**
     * spring security 国际化访问者
     */
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public TypeSecurityPrincipalManager(
            List<TypeSecurityPrincipalService> typeSecurityPrincipalServices,
            CacheManager cacheManager
    ) {
        this.typeSecurityPrincipalServices = typeSecurityPrincipalServices;
        this.cacheManager = cacheManager;
    }

    /**
     * 获取带类型的安全用户服务
     *
     * @param type 类型
     *
     * @return 带类型的安全用户服务
     */
    public TypeSecurityPrincipalService getTypeSecurityPrincipalService(String type) {
        Optional<TypeSecurityPrincipalService> optional = typeSecurityPrincipalServices
                .stream()
                .filter(uds -> uds.getType().contains(type))
                .findFirst();

        String message = messages.getMessage(
                "TypeSecurityPrincipalManager.userDetailsServiceNotFound",
                "找不到适用于 " + type + " 的 TypeSecurityPrincipalService 实现"
        );

        return optional.orElseThrow(() -> new CodeAuthenticationServiceException(message, String.valueOf(HttpStatus.UNAUTHORIZED.value())));
    }

    /**
     * 根据带类型的认证 token 获取安全用户信息
     *
     * @param token 带类型的认证 token
     *
     * @return 安全用户信息
     */
    public SecurityPrincipal getSecurityPrincipal(TypeAuthenticationToken token) {
        TypeSecurityPrincipalService typeSecurityPrincipalService = getTypeSecurityPrincipalService(token.getType());
        CacheProperties authenticationCache = typeSecurityPrincipalService.getAuthenticationCache(token);

        SecurityPrincipal principal = null;
        // 如果启用认证缓存，从认证缓存里获取用户
        if (Objects.nonNull(authenticationCache)) {
            principal = cacheManager.getSecurityPrincipal(authenticationCache);
        }

        if (Objects.isNull(principal)) {
            principal = typeSecurityPrincipalService.getSecurityPrincipal(token);
        }
        if (Objects.isNull(principal)) {
            String message = messages.getMessage(
                    "TypeSecurityPrincipalManager.usernameNotFound",
                    "在类型为 " + token.getType() + " 的 TypeSecurityPrincipalService 实现里，找不到名为 [" + token.getPrincipal().toString() + "] 登录账户"
            );
            throw new CodeAuthenticationServiceException(message, String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        return principal;
    }

    /**
     * 获取安全用户授权信息
     *
     * @param token     带类型的认证 token
     * @param principal 安全用户信息
     *
     * @return 授权信息
     */
    public Collection<GrantedAuthority> getSecurityPrincipalGrantedAuthorities(
            TypeAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        TypeSecurityPrincipalService typeSecurityPrincipalService = getTypeSecurityPrincipalService(token.getType());
        CacheProperties authorizationCache = typeSecurityPrincipalService.getAuthorizationCache(token, principal);

        // 如果启用授权缓存，从授权缓存里获取授权信息
        if (Objects.nonNull(authorizationCache)) {
            Collection<GrantedAuthority> grantedAuthorities = cacheManager.getGrantedAuthorities(authorizationCache);
            if (CollectionUtils.isEmpty(grantedAuthorities)) {
                grantedAuthorities = typeSecurityPrincipalService.getPrincipalGrantedAuthorities(token, principal);
            }

            if (CollectionUtils.isNotEmpty(grantedAuthorities) || typeSecurityPrincipalService.preSaveGrantedAuthoritiesCache(token, principal, grantedAuthorities)) {
                cacheManager.saveGrantedAuthorities(grantedAuthorities, authorizationCache);
            }

            return grantedAuthorities;
        }
        else {
            return typeSecurityPrincipalService.getPrincipalGrantedAuthorities(token, principal);
        }
    }

    public void saveSecurityPrincipalCache(
            TypeAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        TypeSecurityPrincipalService typeSecurityPrincipalService = getTypeSecurityPrincipalService(token.getType());
        CacheProperties authenticationCache = typeSecurityPrincipalService.getAuthenticationCache(token);
        // 如果启用认证缓存，存储用户信息到缓存里
        if (Objects.isNull(authenticationCache) || !typeSecurityPrincipalService.preSaveSecurityPrincipalCache(token, principal)) {
            return;
        }
        cacheManager.saveSecurityPrincipal(principal, authenticationCache);
    }

    /**
     * 校验安全用户密码
     *
     * @param presentedPassword 提交的密码
     * @param token             请求认证 token
     * @param principal         当前安全用户信息
     *
     * @return ture 验证通过，否则 false
     */
    public boolean matchesSecurityPrincipalPassword(
            String presentedPassword,
            RequestAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        return getTypeSecurityPrincipalService(token.getType()).matchesPassword(presentedPassword, token, principal);
    }

    /**
     * 创建带类型的认证 token
     *
     * @param splitString 分割字符串，内容为 <类型>:[id]:<登录账户>
     * @param details     明细信息
     *
     * @return 带类型的认证 token
     */
    public TypeAuthenticationToken createTypeAuthenticationToken(
            String splitString,
            Object details,
            Object credentials
    ) {
        try {
            return TypeAuthenticationToken.ofString(splitString, credentials, details);
        }
        catch (Exception e) {
            throw new InvalidCookieException(e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(typeSecurityPrincipalServices, "至少要一个" + TypeSecurityPrincipalService.class.getName() + "接口的实现");
    }

    /**
     * 获取带类型的安全用户服务的集合
     *
     * @return 带类型的安全用户服务集合
     */
    public List<TypeSecurityPrincipalService> getTypeSecurityPrincipalServices() {
        return typeSecurityPrincipalServices;
    }

    /**
     * 获取缓存配置
     *
     * @return 缓存配置
     */
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

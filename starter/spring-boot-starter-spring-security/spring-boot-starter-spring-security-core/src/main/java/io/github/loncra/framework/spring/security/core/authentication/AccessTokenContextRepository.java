package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.commons.domain.RefreshToken;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AccessTokenDetails;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.util.Objects;

/**
 * 借助 spring security 的 上下文仓库，扩展访问令牌，通过传 token 后完成认证工作
 *
 * @author maurice.chen
 */
public interface AccessTokenContextRepository extends SecurityContextRepository {
    /**
     * 默认访问令牌请求头名称
     */
    String DEFAULT_ACCESS_TOKEN_HEADER_NAME = "X-ACCESS-TOKEN";

    /**
     * 默认刷新令牌请求头名称
     */
    String DEFAULT_REFRESH_TOKEN_HEADER_NAME = "X-REFRESH-TOKEN";

    /**
     * 默认访问令牌参数名称
     */
    String DEFAULT_ACCESS_TOKEN_PARAM_NAME = "accessToken";

    /**
     * 默认刷新令牌参数名称
     */
    String DEFAULT_REFRESH_TOKEN_PARAM_NAME = "refreshToken";

    /**
     * 获取 spring security 上下文
     *
     * @param type 用户类型
     * @param id 用户 id
     *
     * @return spring security 上下文
     */
    default SecurityContext getSecurityContext(String type, Object id) {
        if (Objects.isNull(getAuthenticationProperties().getAccessToken())) {
            return null;
        }
        if (Objects.isNull(getAuthenticationProperties().getAccessToken().getCache())) {
            return null;
        }
        return getCacheManager().getSecurityContext(type, id, getAuthenticationProperties().getAccessToken().getCache());
    }

    /**
     * 获取 spring security 上下文
     *
     * @param token 认证 token
     *
     * @return spring security 上下文
     */
    default SecurityContext getSecurityContext(AuditAuthenticationToken token) {
        return getSecurityContext(token.getType(), token.getSecurityPrincipal().getId());
    }

    @Override
    default SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String token = getAccessToken(requestResponseHolder.getRequest());

        SecurityContext securityContext = getSecurityContext(token);
        if (Objects.isNull(securityContext)) {
            return null;
        }

        Authentication authentication = securityContext.getAuthentication();
        if (Objects.isNull(authentication)) {
            return securityContext;
        }

        if (!AccessTokenDetails.class.isAssignableFrom(authentication.getDetails().getClass())) {
            return securityContext;
        }

        AccessTokenDetails accessTokenDetails = CastUtils.cast(securityContext.getAuthentication().getDetails());
        if (RefreshToken.class.isAssignableFrom(accessTokenDetails.getToken().getClass())) {
            return securityContext;
        }

        getCacheManager().delaySecurityContext(securityContext, getAuthenticationProperties().getAccessToken().getCache());
        return securityContext;
    }

    /**
     * 根据访问令牌获取安全上下文
     *
     * @param token 访问令牌
     * @return 安全上下文
     */
    SecurityContext getSecurityContext(String token);

    /**
     * 从 HTTP 请求中获取访问令牌
     *
     * @param request HTTP 请求
     * @return 访问令牌
     */
    default String getAccessToken(HttpServletRequest request) {

        if (getLoginRequestMatcher().matches(request)) {
            return null;
        }

        if (Objects.isNull(getAuthenticationProperties().getAccessToken())) {
            return StringUtils.EMPTY;
        }
        String token = request.getHeader(getAuthenticationProperties().getAccessToken().getHeaderName());
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(getAuthenticationProperties().getAccessToken().getParamName());
        }

        return token;
    }

    @Override
    default void saveContext(
            SecurityContext context,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = context.getAuthentication();
        if (Objects.isNull(authentication) || !AuditAuthenticationToken.class.isAssignableFrom(context.getAuthentication().getClass())) {
            return ;
        }

        AuditAuthenticationToken token = CastUtils.cast(authentication, AuditAuthenticationToken.class);
        if (!AuditAuthenticationSuccessDetails.class.isAssignableFrom(token.getDetails().getClass())) {
            return ;
        }

        Object details = token.getDetails();
        if (!AccessTokenDetails.class.isAssignableFrom(details.getClass())) {
            return ;
        }

        AccessTokenDetails accessTokenDetails = CastUtils.cast(details);
        ExpiredToken accessTokenValue = accessTokenDetails.getToken();
        if (Objects.isNull(accessTokenValue)) {
            return ;
        }

        if (RefreshToken.class.isAssignableFrom(accessTokenValue.getClass())) {
            RefreshToken refreshToken = CastUtils.cast(accessTokenValue);
            getCacheManager().saveSecurityContextRefreshToken(refreshToken, getAuthenticationProperties().getRefreshToken().getCache());
        }

        if (Objects.nonNull(getAuthenticationProperties().getAccessToken().getCache())) {
            getCacheManager().saveSecurityContext(context, getAuthenticationProperties().getAccessToken().getCache());
        }
    }

    /**
     * 获取缓存管理器
     *
     * @return 缓存管理器
     */
    CacheManager getCacheManager();

    /**
     * 获取认证配置信息
     *
     * @return 认证配置信息
     */
    AuthenticationProperties getAuthenticationProperties();

    /**
     * 登录请求匹配器
     *
     * @return 登录请求正则路径匹配
     */
    default PathPatternRequestMatcher getLoginRequestMatcher() {
        return PathPatternRequestMatcher
                .withDefaults()
                .matcher(HttpMethod.POST, getAuthenticationProperties().getLoginProcessingUrl());
    }

    /**
     * 删除 spring security 上下文缓存
     *
     * @param type 用户类型
     * @param id 用户 id
     */
    default void deleteSecurityContext(String type, Object id) {
        SecurityContext securityContext = getSecurityContext(type, id);
        if (Objects.isNull(securityContext)) {
            return ;

        }
        getCacheManager().deleteSecurityContext(securityContext, getAuthenticationProperties().getAccessToken().getCache());

        if (!AuditAuthenticationToken.class.isAssignableFrom(securityContext.getAuthentication().getDetails().getClass())) {
            return ;
        }

        AuditAuthenticationToken token =  CastUtils.cast(securityContext.getAuthentication(), AuditAuthenticationToken.class);
        Object details = token.getDetails();
        if (RefreshToken.class.isAssignableFrom(details.getClass())) {
            RefreshToken refreshToken = CastUtils.cast(details);
            getCacheManager().deleteSecurityContextRefreshToken(refreshToken, getAuthenticationProperties().getRefreshToken().getCache());
        }
        getCacheManager().deleteSecurityContext(securityContext, getAuthenticationProperties().getAccessToken().getCache());
    }

    /**
     * 删除 spring security 上下文缓存
     *
     * @param securityContext spring security 上下文
     */
    default void deleteSecurityContext(SecurityContext securityContext) {
        if (!AuditAuthenticationToken.class.isAssignableFrom(securityContext.getAuthentication().getDetails().getClass())) {
            return ;
        }

        AuditAuthenticationToken auditAuthenticationToken = CastUtils.cast(securityContext.getAuthentication());

        deleteSecurityContext(auditAuthenticationToken.getType(), auditAuthenticationToken.getSecurityPrincipal().getId());
    }

}

package io.github.loncra.framework.spring.security.session.controller;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.commons.domain.ExpiredToken;
import io.github.loncra.framework.commons.domain.RefreshToken;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AccessTokenDetails;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 令牌控制器
 *
 * @author maurice.chen
 */
@RestController
public class TokenController {

    /**
     * 缓存管理器
     */
    private final CacheManager cacheManager;

    /**
     * 访问令牌配置属性
     */
    private final AuthenticationProperties authenticationProperties;

    /**
     * 构造函数
     *
     * @param cacheManager          缓存管理器
     * @param authenticationProperties 访问令牌配置属性
     */
    public TokenController(
            CacheManager cacheManager,
            AuthenticationProperties authenticationProperties
    ) {
        this.cacheManager = cacheManager;
        this.authenticationProperties = authenticationProperties;
    }

    /**
     * 刷新访问令牌
     *
     * @param refreshToken    刷新令牌
     * @param securityContext 当前安全上下文
     *
     * @return 包含新访问令牌和刷新令牌的结果
     */
    @PostMapping("refreshAccessToken")
    public RestResult<Map<String, Object>> refreshAccessToken(
            @RequestParam
            String refreshToken,
            @CurrentSecurityContext
            SecurityContext securityContext
    ) {


        SystemException.isTrue(
                Objects.nonNull(securityContext.getAuthentication()),
                "当前用户不存在"
        );

        SystemException.isTrue(
                AuditAuthenticationToken.class.isAssignableFrom(securityContext.getAuthentication().getClass()),
                "当前用户非安全用户明细"
        );

        RestResult<ExpiredToken> validResult = cacheManager.getRefreshToken(
                refreshToken,
                authenticationProperties.getRefreshToken().getCache()
        );
        if (!validResult.isSuccess()) {
            return RestResult.of(validResult.getMessage(), validResult.getStatus(), validResult.getExecuteCode());
        }

        AuditAuthenticationToken authenticationToken = CastUtils.cast(securityContext.getAuthentication());

        Object details = authenticationToken.getDetails();
        if (Objects.isNull(details) || !AccessTokenDetails.class.isAssignableFrom(details.getClass())) {
            return RestResult.ofException(
                    new ErrorCodeException("当前认证明细非访问令牌明细", ErrorCodeException.BED_REQUEST_CODE)
            );
        }

        AccessTokenDetails accessTokenDetails = CastUtils.cast(details);
        if (RefreshToken.class.isAssignableFrom(accessTokenDetails.getToken().getClass())) {
            return RestResult.ofException(
                    new ErrorCodeException("当前认证明细非刷新令牌明细", ErrorCodeException.BED_REQUEST_CODE)
            );
        }

        RefreshToken authenticationRefreshToken = CastUtils.cast(accessTokenDetails.getToken());
        RefreshToken refreshTokenValue = CastUtils.cast(validResult.getData());
        Assert.isTrue(
                Strings.CS.equals(refreshTokenValue.getValue(), authenticationRefreshToken.getValue()),
                "刷新令牌匹配不正确"
        );

        SecurityContext context = cacheManager.getSecurityContext(
                authenticationToken.getType(),
                authenticationToken.getSecurityPrincipal().getId(),
                authenticationProperties.getAccessToken().getCache()
        );
        if (Objects.isNull(context)) {
            return RestResult.ofException(
                    new ErrorCodeException("找不到令牌对应的用户明细", ErrorCodeException.CONTENT_NOT_EXIST)
            );
        }

        cacheManager.delaySecurityContext(context, authenticationProperties.getAccessToken().getCache());

        refreshTokenValue.setCreationTime(Instant.now());
        refreshTokenValue.getAccessToken().setCreationTime(Instant.now());
        cacheManager.saveSecurityContextRefreshToken(refreshTokenValue, authenticationProperties.getRefreshToken().getCache());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put(authenticationProperties.getRefreshToken().getParamName(), CastUtils.of(refreshTokenValue, AccessToken.class));
        result.put(authenticationProperties.getAccessToken().getParamName(), refreshTokenValue.getAccessToken());

        if (AuditAuthenticationSuccessDetails.class.isAssignableFrom(accessTokenDetails.getClass())) {
            AuditAuthenticationSuccessDetails successDetails = CastUtils.cast(accessTokenDetails);
            successDetails.getMetadata().putAll(result);
        }

        return RestResult.ofSuccess("延期 [" + refreshTokenValue.getAccessToken().getValue() + "] 令牌成功", result);
    }

}

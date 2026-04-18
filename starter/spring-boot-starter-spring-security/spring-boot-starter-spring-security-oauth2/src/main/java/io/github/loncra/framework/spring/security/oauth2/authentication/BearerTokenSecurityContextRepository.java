package io.github.loncra.framework.spring.security.oauth2.authentication;

import io.github.loncra.framework.commons.id.metadata.IdNameMetadata;
import io.github.loncra.framework.commons.id.metadata.TypeIdNameMetadata;
import io.github.loncra.framework.spring.security.core.authentication.AccessTokenContextRepository;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Objects;

/**
 * Bearer token spring security 安全上下文仓库实现，用于在使用 带有 Bearer 认证头信息解析获取当前认证用户使用。
 *
 * @author maurice.chen
 */
public class BearerTokenSecurityContextRepository implements AccessTokenContextRepository {

    private final CacheManager cacheManager;

    private final JwtDecoder jwtDecoder;

    private final AuthenticationProperties authenticationProperties;

    public BearerTokenSecurityContextRepository(
            CacheManager cacheManager,
            JwtDecoder jwtDecoder,
            AuthenticationProperties authenticationProperties
    ) {
        this.cacheManager = cacheManager;
        this.jwtDecoder = jwtDecoder;
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public SecurityContext getSecurityContext(String token) {
        if (StringUtils.isEmpty(token)) {
            return SecurityContextHolder.createEmptyContext();
        }

        SecurityContext result;
        try {
            Jwt context = jwtDecoder.decode(token);
            String sub = context.getClaimAsString(JwtClaimNames.SUB);
            TypeIdNameMetadata typeIdNameMetadata = IdNameMetadata.ofPrincipalString(sub);
            result = cacheManager.getSecurityContext(
                    typeIdNameMetadata.getType(),
                    typeIdNameMetadata.getId(),
                    authenticationProperties.getAccessToken().getCache()
            );
            if (Objects.isNull(result)) {
                result = SecurityContextHolder.createEmptyContext();
            }
        }
        catch (Exception e) {
            result = SecurityContextHolder.createEmptyContext();
        }
        return result;
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return StringUtils.isNotEmpty(getAccessToken(request));
    }

    @Override
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public AuthenticationProperties getAuthenticationProperties() {
        return authenticationProperties;
    }

}

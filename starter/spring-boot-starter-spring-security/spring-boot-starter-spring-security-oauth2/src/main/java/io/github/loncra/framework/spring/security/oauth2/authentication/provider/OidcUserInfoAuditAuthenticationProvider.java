package io.github.loncra.framework.spring.security.oauth2.authentication.provider;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.security.UserStatus;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * 扩展 oidc 用户信息审计认证授权供应者实现
 *
 * @author maurice.chen
 */
public class OidcUserInfoAuditAuthenticationProvider implements AuthenticationProvider {

    private final OidcUserInfoAuthenticationProvider authenticationProvider;

    private final TypeSecurityPrincipalManager typeSecurityPrincipalManager;

    public OidcUserInfoAuditAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager
    ) {
        this.authenticationProvider = new OidcUserInfoAuthenticationProvider(authorizationService);
        this.typeSecurityPrincipalManager = typeSecurityPrincipalManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticate = authenticationProvider.authenticate(authentication);
        if (!OidcUserInfoAuthenticationToken.class.isAssignableFrom(authenticate.getClass())) {
            return authenticate;
        }

        String type = StringUtils.substringBefore(authenticate.getName(), CacheProperties.DEFAULT_SEPARATOR);

        JwtAuthenticationToken jwtAuthenticationToken = CastUtils.cast(authenticate.getPrincipal());

        String idName = StringUtils.substringAfter(authenticate.getName(), CacheProperties.DEFAULT_SEPARATOR);

        SimpleSecurityPrincipal principal = new SimpleSecurityPrincipal();
        principal.setId(StringUtils.substringBefore(idName, CacheProperties.DEFAULT_SEPARATOR));
        //principal.setUsername(StringUtils.substringAfter(idName, CacheProperties.DEFAULT_SEPARATOR));

        Map<String, Object> details = CastUtils.convertValue(jwtAuthenticationToken.getDetails(), CastUtils.MAP_TYPE_REFERENCE);
        if (Jwt.class.isAssignableFrom(jwtAuthenticationToken.getPrincipal().getClass())) {
            Jwt jwt = CastUtils.cast(jwtAuthenticationToken.getPrincipal());
            if (Objects.nonNull(jwt.getExpiresAt())) {
                principal.setStatus(Instant.now().isBefore(jwt.getExpiresAt()) ? UserStatus.Enabled : UserStatus.Disabled);
            }
            details.put(Elements.JWT, jwt);
        }
        else {
            principal.setStatus(UserStatus.Lock);
        }

        details.put(AuditAuthenticationToken.AUTHORITIES_KEY, jwtAuthenticationToken.getAuthorities());

        TypeAuthenticationToken token = new TypeAuthenticationToken(principal, null, type);
        token.setDetails(details);

        return typeSecurityPrincipalManager
                .getTypeSecurityPrincipalService(type)
                .createOidcUserInfoSuccessAuthentication(principal, token, jwtAuthenticationToken);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authenticationProvider.supports(authentication);
    }
}


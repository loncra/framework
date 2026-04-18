package io.github.loncra.framework.spring.security.oauth2.authentication.oidc;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.security.Principal;
import java.util.*;
import java.util.function.Function;

/**
 * oauth 用户信息映射
 *
 * @author maurice.chen
 */
public class OidcUserInfoAuthenticationMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {

    private final List<OidcUserInfoAuthenticationResolver> oidcUserInfoAuthenticationResolvers;

    public OidcUserInfoAuthenticationMapper(List<OidcUserInfoAuthenticationResolver> systemUserResolvers) {
        this.oidcUserInfoAuthenticationResolvers = systemUserResolvers;
    }

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext oidcUserInfoAuthenticationContext) {
        OAuth2Authorization oAuth2Authorization = oidcUserInfoAuthenticationContext.getAuthorization();

        Map<String, Object> claims = new LinkedHashMap<>();

        Object principal = oAuth2Authorization.getAttributes().get(Principal.class.getName());

        if (Objects.nonNull(principal) && principal instanceof AuditAuthenticationToken) {
            AuditAuthenticationToken authenticationToken = CastUtils.cast(principal);

            Optional<OidcUserInfoAuthenticationResolver> optional = oidcUserInfoAuthenticationResolvers
                    .stream()
                    .filter(s -> s.isSupport(authenticationToken.getType()))
                    .findFirst();

            if (optional.isPresent()) {
                return optional.get().mappingOidcUserInfoClaims(oAuth2Authorization, claims, authenticationToken);
            }

        }

        return new OidcUserInfo(claims);
    }
}


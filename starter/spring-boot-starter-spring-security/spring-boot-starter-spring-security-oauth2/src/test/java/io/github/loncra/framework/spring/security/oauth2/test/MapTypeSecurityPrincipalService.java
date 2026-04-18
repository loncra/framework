package io.github.loncra.framework.spring.security.oauth2.test;

import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.AbstractTypeSecurityPrincipalService;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.authentication.token.TypeAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import io.github.loncra.framework.spring.security.core.entity.support.AccessTokenAuditAuthenticationSuccessDetails;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MapTypeSecurityPrincipalService extends AbstractTypeSecurityPrincipalService implements InitializingBean {

    private static final Map<String, SimpleSecurityPrincipal> USER_DETAILS = Collections.synchronizedMap(new HashMap<>());

    private final PasswordEncoder passwordEncoder;

    private final JwtGenerator jwtGenerator;

    private final RegisteredClientRepository registeredClientRepository;

    public MapTypeSecurityPrincipalService(
            PasswordEncoder passwordEncoder,
            JwtGenerator jwtGenerator,
            RegisteredClientRepository registeredClientRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.registeredClientRepository = registeredClientRepository;
    }

    @Autowired
    public void setAuthenticationProperties(AuthenticationProperties authenticationProperties) {
        super.setAuthenticationProperties(authenticationProperties);
    }

    @Override
    public void afterPropertiesSet() {
        USER_DETAILS.put("test", new SimpleSecurityPrincipal(1, getPasswordEncoder().encode("123456"), "test"));
        USER_DETAILS.put("admin", new SimpleSecurityPrincipal(2, getPasswordEncoder().encode("123456"), "admin"));
    }

    @Override
    public AuditAuthenticationSuccessDetails getPrincipalDetails(
            SecurityPrincipal principal,
            TypeAuthenticationToken token,
            AuditAuthenticationToken successToken,
            Collection<? extends GrantedAuthority> grantedAuthorities
    ) {

        AuditAuthenticationSuccessDetails successDetails = super.getPrincipalDetails(principal, token, successToken, grantedAuthorities);

        RegisteredClient registeredClient = Objects.requireNonNull(registeredClientRepository.findByClientId("test"));
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(successToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                //.authorization(authorization)
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN);

        OAuth2Token generatedAccessToken = jwtGenerator.generate(tokenContextBuilder.build());
        if (Objects.isNull(generatedAccessToken)) {
            return successDetails;
        }

        AccessToken accessTokenDetails = getAccessToken(generatedAccessToken);
        return new AccessTokenAuditAuthenticationSuccessDetails(successDetails, accessTokenDetails);
    }

    private static @NonNull AccessToken getAccessToken(OAuth2Token generatedAccessToken) {
        AccessToken accessTokenDetails = new AccessToken();
        accessTokenDetails.setValue(generatedAccessToken.getTokenValue());
        if (Objects.nonNull(generatedAccessToken.getExpiresAt()) && Objects.nonNull(generatedAccessToken.getIssuedAt())) {
            accessTokenDetails.setCreationTime(generatedAccessToken.getIssuedAt());
            long expiresAt = generatedAccessToken.getExpiresAt()
                    .minusMillis(generatedAccessToken.getIssuedAt().toEpochMilli())
                    .toEpochMilli();
            accessTokenDetails.setExpiresTime(TimeProperties.ofMilliseconds(expiresAt));
        }
        return accessTokenDetails;
    }

    @Override
    public SecurityPrincipal getSecurityPrincipal(TypeAuthenticationToken token) throws AuthenticationException {
        return USER_DETAILS.get(token.getPrincipal().toString());
    }

    @Override
    public Collection<GrantedAuthority> getPrincipalGrantedAuthorities(
            TypeAuthenticationToken token,
            SecurityPrincipal principal
    ) {
        Collection<GrantedAuthority> result = new LinkedHashSet<>();
        result.add(new SimpleGrantedAuthority("perms[operate]"));
        return result;
    }

    @Override
    public List<String> getType() {
        return Collections.singletonList("test");
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


}

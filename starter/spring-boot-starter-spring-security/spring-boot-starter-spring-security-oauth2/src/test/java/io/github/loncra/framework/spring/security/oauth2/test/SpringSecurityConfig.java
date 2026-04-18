package io.github.loncra.framework.spring.security.oauth2.test;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.authentication.AuditAuthenticationDetailsSource;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.provider.SecurityPrincipalAuthenticationProvider;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.oauth2.OAuth2WebSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.oauth2.authentication.adapter.OAuth2AuthorizationConfigurerAdapter;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.authentication.*;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * 自定义 spring security 的配置
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureAfter(OAuth2WebSecurityAutoConfiguration.class)
public class SpringSecurityConfig implements WebSecurityConfigurerAfterAdapter, OAuth2AuthorizationConfigurerAdapter {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

    private final AuthenticationProperties authenticationProperties;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final TypeSecurityPrincipalManager typeSecurityPrincipalManager;

    public SpringSecurityConfig(
            AuthenticationProperties authenticationProperties,
            AuthenticationFailureHandler authenticationFailureHandler,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            @Lazy TypeSecurityPrincipalManager typeSecurityPrincipalManager
    ) {
        this.authenticationProperties = authenticationProperties;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.typeSecurityPrincipalManager = typeSecurityPrincipalManager;
    }

    @Override
    public void configAuthorizationEndpoint(OAuth2AuthorizationEndpointConfigurer authorizationEndpoint) {
        authorizationEndpoint.authenticationProviders(this::settingAuthenticationProviders);
    }

    @Bean
    public InMemoryRegisteredClientRepository inMemoryRegisteredClientRepository(PasswordEncoder passwordEncoder) {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient
                        .withId("test")
                        .clientId("test")
                        .clientSecret(passwordEncoder.encode("123456"))
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .clientAuthenticationMethods(c -> c.addAll(List.of(ClientAuthenticationMethod.CLIENT_SECRET_POST, ClientAuthenticationMethod.CLIENT_SECRET_JWT)))
                        .redirectUri("www.domain.com")
                        .clientSettings(ClientSettings.builder().requireProofKey(false).build())
                        .scopes(s -> s.addAll(List.of(OidcScopes.OPENID, OidcScopes.PROFILE)))
                        .build()
        );
    }

    private void settingAuthenticationProviders(List<AuthenticationProvider> authenticationProviders) {
        Optional<OAuth2AuthorizationCodeRequestAuthenticationProvider> optional = authenticationProviders
                .stream()
                .filter(o -> OAuth2AuthorizationCodeRequestAuthenticationProvider.class.isAssignableFrom(o.getClass()))
                .map(o -> CastUtils.cast(o, OAuth2AuthorizationCodeRequestAuthenticationProvider.class))
                .findFirst();

        if (optional.isEmpty()) {
            return;
        }
        OAuth2AuthorizationCodeRequestAuthenticationProvider authenticationProvider = optional.get();
        authenticationProvider.setAuthenticationValidator(OAuth2AuthorizationCodeRequestAuthenticationValidator.DEFAULT_SCOPE_VALIDATOR.andThen(this::validateRedirectUri));
    }

    private void validateRedirectUri(OAuth2AuthorizationCodeRequestAuthenticationContext authenticationContext) {
        OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication =
                authenticationContext.getAuthentication();
        RegisteredClient registeredClient = authenticationContext.getRegisteredClient();

        String requestedRedirectUri = authorizationCodeRequestAuthentication.getRedirectUri();

        if (StringUtils.hasText(requestedRedirectUri)) {
            // ***** redirect_uri is available in authorization request

            UriComponents requestedRedirect = null;
            try {
                requestedRedirect = UriComponentsBuilder.fromUriString(requestedRedirectUri).build();
            }
            catch (Exception ignored) {
            }

            if (requestedRedirect == null || !registeredClient.getRedirectUris().contains(requestedRedirect.getHost())) {
                throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI,
                           authorizationCodeRequestAuthentication, registeredClient);
            }

            if (SpringMvcUtils.isLoopbackAddress(requestedRedirect.getHost())) {
                // As per https://datatracker.ietf.org/doc/html/draft-ietf-oauth-security-topics-22#section-4.1.3
                // When comparing client redirect URIs against pre-registered URIs,
                // authorization servers MUST utilize exact string matching.
                throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI,
                           authorizationCodeRequestAuthentication, registeredClient);
            }
            else {
                // As per https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-08#section-8.4.2
                // The authorization server MUST allow any port to be specified at the
                // time of the request for loopback IP redirect URIs, to accommodate
                // clients that obtain an available ephemeral port from the operating
                // system at the time of the request.
                boolean validRedirectUri = false;
                for (String registeredRedirectUri : registeredClient.getRedirectUris()) {
                    UriComponentsBuilder registeredRedirect = UriComponentsBuilder.fromUriString(registeredRedirectUri);
                    registeredRedirect.port(requestedRedirect.getPort());
                    if (registeredRedirect.build().toString().equals(requestedRedirect.getHost())) {
                        validRedirectUri = true;
                        break;
                    }
                }
                if (!validRedirectUri) {
                    throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI,
                               authorizationCodeRequestAuthentication, registeredClient);
                }
            }

        }
        else {
            // ***** redirect_uri is NOT available in authorization request

            if (authorizationCodeRequestAuthentication.getScopes().contains(OidcScopes.OPENID) ||
                    registeredClient.getRedirectUris().size() != 1) {
                // redirect_uri is REQUIRED for OpenID Connect
                throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI,
                           authorizationCodeRequestAuthentication, registeredClient);
            }
        }
    }

    private static void throwError(
            String errorCode,
            String parameterName,
            OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication,
            RegisteredClient registeredClient
    ) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, ERROR_URI);
        throwError(error, parameterName, authorizationCodeRequestAuthentication, registeredClient);
    }

    private static void throwError(
            OAuth2Error error,
            String parameterName,
            OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication,
            RegisteredClient registeredClient
    ) {

        String redirectUri = StringUtils.hasText(authorizationCodeRequestAuthentication.getRedirectUri()) ?
                authorizationCodeRequestAuthentication.getRedirectUri() :
                registeredClient.getRedirectUris().iterator().next();
        if (error.getErrorCode().equals(OAuth2ErrorCodes.INVALID_REQUEST) &&
                parameterName.equals(OAuth2ParameterNames.REDIRECT_URI)) {
            redirectUri = null;        // Prevent redirects
        }

        OAuth2AuthorizationCodeRequestAuthenticationToken token = getAuthorizationCodeRequestAuthenticationToken(authorizationCodeRequestAuthentication, redirectUri);

        throw new OAuth2AuthorizationCodeRequestAuthenticationException(error, token);
    }

    private static OAuth2AuthorizationCodeRequestAuthenticationToken getAuthorizationCodeRequestAuthenticationToken(
            OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication,
            String redirectUri
    ) {
        OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthenticationResult =
                new OAuth2AuthorizationCodeRequestAuthenticationToken(
                        authorizationCodeRequestAuthentication.getAuthorizationUri(), authorizationCodeRequestAuthentication.getClientId(),
                        (Authentication) authorizationCodeRequestAuthentication.getPrincipal(), redirectUri,
                        authorizationCodeRequestAuthentication.getState(), authorizationCodeRequestAuthentication.getScopes(),
                        authorizationCodeRequestAuthentication.getAdditionalParameters());
        authorizationCodeRequestAuthenticationResult.setAuthenticated(true);
        return authorizationCodeRequestAuthenticationResult;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {

        try {

            httpSecurity
                    .formLogin(f -> f.passwordParameter(authenticationProperties.getPasswordParamName())
                            .usernameParameter(authenticationProperties.getUsernameParamName())
                            .loginProcessingUrl(authenticationProperties.getLoginProcessingUrl())
                            .authenticationDetailsSource(new AuditAuthenticationDetailsSource(authenticationProperties))
                            .failureHandler(authenticationFailureHandler)
                            .successHandler(authenticationSuccessHandler)
                    )
                    //.sessionManagement(s -> s.maximumSessions(Integer.MAX_VALUE))
                    .logout(l -> {
                        try {
                            l.configure(httpSecurity);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

            httpSecurity.authenticationProvider(
                    new SecurityPrincipalAuthenticationProvider(
                            authenticationProperties,
                            typeSecurityPrincipalManager
                    )
            );

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

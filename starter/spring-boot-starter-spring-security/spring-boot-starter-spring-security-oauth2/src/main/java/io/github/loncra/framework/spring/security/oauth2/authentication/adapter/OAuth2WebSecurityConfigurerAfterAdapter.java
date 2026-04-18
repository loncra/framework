package io.github.loncra.framework.spring.security.oauth2.authentication.adapter;


import io.github.loncra.framework.spring.security.core.authentication.RestResultAuthenticationEntryPoint;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationFailureHandler;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationSuccessHandler;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.oauth2.authentication.BearerTokenSecurityContextRepository;
import io.github.loncra.framework.spring.security.oauth2.authentication.config.OAuth2Properties;
import io.github.loncra.framework.spring.security.oauth2.authentication.oidc.OidcUserInfoAuthenticationMapper;
import io.github.loncra.framework.spring.security.oauth2.authentication.provider.OidcUserInfoAuditAuthenticationProvider;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.List;

/**
 * oauth 2 配置内容
 *
 * @author maurice.chen
 */
public class OAuth2WebSecurityConfigurerAfterAdapter implements WebSecurityConfigurerAfterAdapter {

    private final JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;

    private final JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;

    private final List<ErrorResultResolver> resultResolvers;

    private final OidcUserInfoAuthenticationMapper oidcUserInfoAuthenticationMapper;

    private final List<OAuth2AuthorizationConfigurerAdapter> oAuth2AuthorizationConfigurerAdapters;

    private final OAuth2AuthorizationService authenticationProvider;

    private final TypeSecurityPrincipalManager typeSecurityPrincipalManager;

    private final BearerTokenSecurityContextRepository bearerTokenSecurityContextRepository;

    private final OAuth2Properties oAuth2Properties;

    public OAuth2WebSecurityConfigurerAfterAdapter(
            JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler,
            JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler,
            OidcUserInfoAuthenticationMapper oidcUserInfoAuthenticationMapper,
            List<OAuth2AuthorizationConfigurerAdapter> oAuth2AuthorizationConfigurerAdapters,
            List<ErrorResultResolver> resultResolvers,
            OAuth2AuthorizationService authenticationProvider,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager,
            BearerTokenSecurityContextRepository bearerTokenSecurityContextRepository,
            OAuth2Properties oAuth2Properties
    ) {
        this.jsonAuthenticationFailureHandler = jsonAuthenticationFailureHandler;
        this.jsonAuthenticationSuccessHandler = jsonAuthenticationSuccessHandler;
        this.oidcUserInfoAuthenticationMapper = oidcUserInfoAuthenticationMapper;
        this.oAuth2AuthorizationConfigurerAdapters = oAuth2AuthorizationConfigurerAdapters;
        this.resultResolvers = resultResolvers;
        this.authenticationProvider = authenticationProvider;
        this.typeSecurityPrincipalManager = typeSecurityPrincipalManager;
        this.bearerTokenSecurityContextRepository = bearerTokenSecurityContextRepository;
        this.oAuth2Properties = oAuth2Properties;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthorizationServerSettings.Builder builder = AuthorizationServerSettings.builder();
        if (StringUtils.isNotEmpty(oAuth2Properties.getIssuer())) {
            builder.issuer(oAuth2Properties.getIssuer());
        }
        httpSecurity
                .with(
                        new OAuth2AuthorizationServerConfigurer(),
                        o -> o
                                .oidc(this::configOidc)
                                .clientAuthentication(this::configClientAuthentication)
                                .authorizationEndpoint(this::configAuthorizationEndpoint)
                                .tokenEndpoint(this::configTokenEndpoint)
                                .authorizationServerSettings(builder.build()))
                .oauth2ResourceServer(this::configResourceServer)
                .securityContext(s -> s.securityContextRepository(bearerTokenSecurityContextRepository));

        httpSecurity.authenticationProvider(new OidcUserInfoAuditAuthenticationProvider(authenticationProvider, typeSecurityPrincipalManager));
    }

    private void configResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> resourceServer) {
        resourceServer
                .jwt(Customizer.withDefaults())
                .authenticationEntryPoint(new RestResultAuthenticationEntryPoint(resultResolvers));

        if (CollectionUtils.isNotEmpty(oAuth2AuthorizationConfigurerAdapters)) {
            oAuth2AuthorizationConfigurerAdapters.forEach(o -> o.configResourceServer(resourceServer));
        }
    }

    private void configClientAuthentication(OAuth2ClientAuthenticationConfigurer clientAuthentication) {
        clientAuthentication.errorResponseHandler(jsonAuthenticationFailureHandler);

        if (CollectionUtils.isNotEmpty(oAuth2AuthorizationConfigurerAdapters)) {
            oAuth2AuthorizationConfigurerAdapters.forEach(o -> o.configClientAuthentication(clientAuthentication));
        }
    }

    private void configTokenEndpoint(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        tokenEndpoint
                .accessTokenResponseHandler(jsonAuthenticationSuccessHandler)
                .errorResponseHandler(jsonAuthenticationFailureHandler);

        if (CollectionUtils.isNotEmpty(oAuth2AuthorizationConfigurerAdapters)) {
            oAuth2AuthorizationConfigurerAdapters.forEach(o -> o.configTokenEndpoint(tokenEndpoint));
        }
    }

    private void configOidc(OidcConfigurer oidc) {
        oidc.userInfoEndpoint(endpoint -> endpoint
                .userInfoRequestConverter(this::createUserInfoRequestConverter)
                .userInfoMapper(oidcUserInfoAuthenticationMapper)
                .userInfoResponseHandler(jsonAuthenticationSuccessHandler)
                .errorResponseHandler(jsonAuthenticationFailureHandler));

        if (CollectionUtils.isNotEmpty(oAuth2AuthorizationConfigurerAdapters)) {
            oAuth2AuthorizationConfigurerAdapters.forEach(o -> o.configOidc(oidc));
        }
    }

    private Authentication createUserInfoRequestConverter(HttpServletRequest request) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        OidcUserInfoAuthenticationToken token = new OidcUserInfoAuthenticationToken(principal);
        token.setDetails(new WebAuthenticationDetails(request));
        return token;
    }

    private void configAuthorizationEndpoint(OAuth2AuthorizationEndpointConfigurer authorizationEndpoint) {
        authorizationEndpoint
                .authorizationResponseHandler(jsonAuthenticationSuccessHandler)
                .errorResponseHandler(jsonAuthenticationFailureHandler);

        if (CollectionUtils.isNotEmpty(oAuth2AuthorizationConfigurerAdapters)) {
            oAuth2AuthorizationConfigurerAdapters.forEach(o -> o.configAuthorizationEndpoint(authorizationEndpoint));
        }
    }

}


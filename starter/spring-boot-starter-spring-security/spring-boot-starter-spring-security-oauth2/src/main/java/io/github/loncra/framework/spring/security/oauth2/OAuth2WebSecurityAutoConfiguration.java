package io.github.loncra.framework.spring.security.oauth2;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.cipher.RsaCipherService;
import io.github.loncra.framework.spring.security.core.SpringSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.core.authentication.adapter.WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationFailureHandler;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationSuccessHandler;
import io.github.loncra.framework.spring.security.core.authentication.handler.JsonAuthenticationSuccessResponse;
import io.github.loncra.framework.spring.security.core.authentication.service.TypeSecurityPrincipalManager;
import io.github.loncra.framework.spring.security.oauth2.authentication.BearerTokenSecurityContextRepository;
import io.github.loncra.framework.spring.security.oauth2.authentication.adapter.OAuth2AuthorizationConfigurerAdapter;
import io.github.loncra.framework.spring.security.oauth2.authentication.adapter.OAuth2WebSecurityConfigurerAfterAdapter;
import io.github.loncra.framework.spring.security.oauth2.authentication.config.OAuth2Properties;
import io.github.loncra.framework.spring.security.oauth2.authentication.oidc.OidcUserInfoAuthenticationMapper;
import io.github.loncra.framework.spring.security.oauth2.authentication.oidc.OidcUserInfoAuthenticationResolver;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OAuth2 Web 安全自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore(SpringSecurityAutoConfiguration.class)
@ConditionalOnClass(OAuth2AuthorizationServerConfigurer.class)
@EnableConfigurationProperties({AuthenticationProperties.class, OAuth2Properties.class})
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security.oauth2", value = "enabled", matchIfMissing = true)
public class OAuth2WebSecurityAutoConfiguration implements WebSecurityConfigurerAfterAdapter {

    /**
     * RSA 加密服务
     */
    private static final RsaCipherService cipherService = new RsaCipherService();

    @Bean
    @ConditionalOnMissingBean(JsonAuthenticationSuccessHandler.class)
    public JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler(
            ObjectProvider<JsonAuthenticationSuccessResponse> successResponse,
            AuthenticationProperties properties,
            AuthorizationServerSettings authorizationServerSettings
    ) {

        List<PathPatternRequestMatcher> antPathRequestMatchers = new LinkedList<>();

        antPathRequestMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(authorizationServerSettings.getAuthorizationEndpoint()));
        antPathRequestMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(authorizationServerSettings.getTokenEndpoint()));
        antPathRequestMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(authorizationServerSettings.getOidcUserInfoEndpoint()));

        return new JsonAuthenticationSuccessHandler(
                successResponse.orderedStream().collect(Collectors.toList()),
                properties,
                antPathRequestMatchers
        );
    }

    /**
     * JWK 源配置
     *
     * @param oAuth2Properties OAuth2 配置属性
     *
     * @return JWK 源实例
     */
    @Bean
    @ConditionalOnMissingBean(JWKSource.class)
    public JWKSource<SecurityContext> jwkSource(OAuth2Properties oAuth2Properties) {
        PublicKey publicKey = cipherService.getPublicKey(Base64.decode(oAuth2Properties.getPublicKey()));
        PrivateKey privateKey = cipherService.getPrivateKey(Base64.decode(oAuth2Properties.getPrivateKey()));

        RSAPublicKey rsaPublicKey = CastUtils.cast(publicKey);
        RSAPrivateKey rsaPrivateKey = CastUtils.cast(privateKey);

        RSAKey rsaKey = new RSAKey.Builder(rsaPublicKey)
                .privateKey(rsaPrivateKey)
                .keyID(oAuth2Properties.getKeyId())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 配置 JWT 解析器
     *
     * @param jwkSource JWK 源
     *
     * @return JWT 解析器实例
     */
    @Bean
    @ConditionalOnMissingBean(JwtDecoder.class)
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 配置 JWT token 生成器
     *
     * @param jwkSource JWK 源
     *
     * @return JWT 解析器实例
     */
    @Bean
    @ConditionalOnMissingBean(JwtGenerator.class)
    public JwtGenerator jwtGenerator(JWKSource<SecurityContext> jwkSource) {
        return new JwtGenerator(new NimbusJwtEncoder(jwkSource));
    }

    /**
     * 创建 OIDC 用户信息认证映射器 Bean
     *
     * @param oidcUserInfoAuthenticationResolvers OIDC 用户信息认证解析器提供者
     *
     * @return OIDC 用户信息认证映射器实例
     */
    @Bean
    @ConditionalOnMissingBean(OidcUserInfoAuthenticationMapper.class)
    public OidcUserInfoAuthenticationMapper oidcUserInfoAuthenticationMapper(
            ObjectProvider<OidcUserInfoAuthenticationResolver> oidcUserInfoAuthenticationResolvers
    ) {
        return new OidcUserInfoAuthenticationMapper(oidcUserInfoAuthenticationResolvers.orderedStream().collect(Collectors.toList()));
    }

    /**
     * 创建内存 OAuth2 授权服务 Bean
     *
     * @return 内存 OAuth2 授权服务实例
     */
    @Bean
    @ConditionalOnMissingBean(OAuth2AuthorizationService.class)
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public BearerTokenSecurityContextRepository bearerTokenSecurityContextRepository(
            CacheManager cacheManager,
            JwtDecoder jwtDecoder,
            AuthenticationProperties authenticationProperties
    ) {
        return new BearerTokenSecurityContextRepository(
                cacheManager,
                jwtDecoder,
                authenticationProperties
        );
    }

    /**
     * 创建 OAuth2 Web 安全配置后适配器 Bean
     *
     * @param jsonAuthenticationFailureHandler      JSON 认证失败处理器
     * @param jsonAuthenticationSuccessHandler      JSON 认证成功处理器
     * @param oidcUserInfoAuthenticationMapper      OIDC 用户信息认证映射器
     * @param oAuth2AuthorizationConfigurerAdapters OAuth2 授权配置适配器提供者
     * @param resultResolvers                       错误结果解析器提供者
     * @param authenticationProvider                OAuth2 授权服务
     * @param typeSecurityPrincipalManager          类型安全主体管理器
     *
     * @return OAuth2 Web 安全配置后适配器实例
     */
    @Bean
    @ConditionalOnMissingBean(OAuth2WebSecurityConfigurerAfterAdapter.class)
    public OAuth2WebSecurityConfigurerAfterAdapter oAuth2WebSecurityConfigurerAfterAdapter(
            JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler,
            JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler,
            OidcUserInfoAuthenticationMapper oidcUserInfoAuthenticationMapper,
            ObjectProvider<OAuth2AuthorizationConfigurerAdapter> oAuth2AuthorizationConfigurerAdapters,
            ObjectProvider<ErrorResultResolver> resultResolvers,
            OAuth2AuthorizationService authenticationProvider,
            OAuth2Properties oAuth2Properties,
            TypeSecurityPrincipalManager typeSecurityPrincipalManager,
            BearerTokenSecurityContextRepository bearerTokenSecurityContextRepository
    ) {
        return new OAuth2WebSecurityConfigurerAfterAdapter(
                jsonAuthenticationFailureHandler,
                jsonAuthenticationSuccessHandler,
                oidcUserInfoAuthenticationMapper,
                oAuth2AuthorizationConfigurerAdapters.orderedStream().collect(Collectors.toList()),
                resultResolvers.orderedStream().collect(Collectors.toList()),
                authenticationProvider,
                typeSecurityPrincipalManager,
                bearerTokenSecurityContextRepository,
                oAuth2Properties
        );
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }
}


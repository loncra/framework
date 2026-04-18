package io.github.loncra.framework.spring.security.oauth2.authentication.adapter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2ClientAuthenticationConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OidcConfigurer;

/**
 * oauth2 授权配置适配器
 *
 * @author maurice.chen
 */
public interface OAuth2AuthorizationConfigurerAdapter {

    /**
     * 配置授权终端
     *
     * @param authorizationEndpoint 授权终端配置
     */
    default void configAuthorizationEndpoint(OAuth2AuthorizationEndpointConfigurer authorizationEndpoint) {

    }

    /**
     * 配置 oidc 内容
     *
     * @param oidc oidc 配置
     */
    default void configOidc(OidcConfigurer oidc) {

    }

    /**
     * 配置令牌终端
     *
     * @param tokenEndpoint 令牌终端配置
     */
    default void configTokenEndpoint(OAuth2TokenEndpointConfigurer tokenEndpoint) {

    }

    /**
     * 配置客户端认证
     *
     * @param clientAuthentication 客户端认证配置
     */
    default void configClientAuthentication(OAuth2ClientAuthenticationConfigurer clientAuthentication) {

    }

    /**
     * 配置资源服务
     *
     * @param resourceServer 资源服务配置
     */
    default void configResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> resourceServer) {

    }
}


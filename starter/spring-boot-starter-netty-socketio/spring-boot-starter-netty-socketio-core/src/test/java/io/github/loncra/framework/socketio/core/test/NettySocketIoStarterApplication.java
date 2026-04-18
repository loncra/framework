package io.github.loncra.framework.socketio.core.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;

@SpringBootApplication
public class NettySocketIoStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettySocketIoStarterApplication.class, args);
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

}

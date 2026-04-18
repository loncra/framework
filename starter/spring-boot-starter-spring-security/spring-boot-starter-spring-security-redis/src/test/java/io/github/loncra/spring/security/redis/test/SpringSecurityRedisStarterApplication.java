package io.github.loncra.spring.security.redis.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication(
        scanBasePackages = "io.github.loncra.framework.spring.security.redis.test"
)
public class SpringSecurityRedisStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRedisStarterApplication.class, args);
    }

    @Bean
    public RegisteredClientRepository  registeredClientRepository() {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient
                        .withId("test")
                        .clientId("test")
                        .clientSecret("123456")
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

package io.github.loncra.spring.security.redis;

import io.github.loncra.framework.spring.security.oauth2.OAuth2WebSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.oauth2.authentication.config.OAuth2Properties;
import io.github.loncra.spring.security.redis.authentication.cache.support.OAuth2RedissonAuthorizationService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

/**
 * oauth redisson 缓存配置类
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass({OAuth2WebSecurityAutoConfiguration.class})
@AutoConfigureBefore({OAuth2WebSecurityAutoConfiguration.class})
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security", value = "enabled", matchIfMissing = true)
public class OAuth2RedissonOperationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OAuth2AuthorizationService.class)
    public OAuth2AuthorizationService authorizationService(
            RedissonClient redissonClient,
            OAuth2Properties oAuth2Properties
    ) {
        return new OAuth2RedissonAuthorizationService(redissonClient, oAuth2Properties);
    }
}

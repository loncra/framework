package io.github.loncra.spring.security.redis;

import io.github.loncra.framework.spring.security.core.SpringSecurityAutoConfiguration;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.spring.security.redis.authentication.cache.support.RedissonCacheManager;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 缓存管理配置
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore({SpringSecurityAutoConfiguration.class})
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security", value = "enabled", matchIfMissing = true)
public class RedissonOperationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonCacheManager(redissonClient);
    }
}


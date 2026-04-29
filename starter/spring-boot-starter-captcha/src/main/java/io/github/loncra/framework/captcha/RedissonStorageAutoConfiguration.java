package io.github.loncra.framework.captcha;

import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.storage.support.RedissonCaptchaStorageManager;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfigurationV2;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(CaptchaAutoConfiguration.class)
@ConditionalOnClass(RedissonAutoConfigurationV2.class)
@ConditionalOnProperty(prefix = "loncra.framework.captcha", value = "enabled", matchIfMissing = true)
public class RedissonStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CaptchaStorageManager.class)
    public RedissonCaptchaStorageManager captchaStorageManager(
            RedissonClient redissonClient,
            CaptchaProperties captchaProperties
    ) {
        return new RedissonCaptchaStorageManager(redissonClient, captchaProperties);
    }
}

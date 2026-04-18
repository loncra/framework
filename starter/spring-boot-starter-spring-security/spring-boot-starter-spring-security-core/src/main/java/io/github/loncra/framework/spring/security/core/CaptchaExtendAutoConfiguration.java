package io.github.loncra.framework.spring.security.core;

import io.github.loncra.framework.captcha.CaptchaAutoConfiguration;
import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.captcha.DelegateCaptchaService;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.spring.security.core.controller.CaptchaExtendController;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 验证码扩展配置
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass(CaptchaAutoConfiguration.class)
@AutoConfigureAfter(CaptchaAutoConfiguration.class)
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.captcha", value = "enabled", matchIfMissing = true)
public class CaptchaExtendAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "loncra.framework.captcha.controller", value = "enabled", matchIfMissing = true)
    public CaptchaExtendController captchaExtendController(
            @Lazy
            Interceptor interceptor,
            CaptchaProperties captchaProperties,
            DelegateCaptchaService delegateCaptchaService
    ) {
        return new CaptchaExtendController(interceptor, captchaProperties, delegateCaptchaService);
    }
}

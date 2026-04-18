package io.github.loncra.framework.spring.security.core;


import feign.FeignException;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.service.feign.FeignExceptionResultResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 认证配置
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass(FeignException.class)
@EnableConfigurationProperties(AuthenticationProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.authentication.spring.security", value = "enabled", matchIfMissing = true)
public class FeignSpringSecurityAutoConfiguration {

    @Bean
    public FeignExceptionResultResolver feignExceptionResultResolver() {
        return new FeignExceptionResultResolver();
    }

}

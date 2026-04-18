package io.github.loncra.framework.security;

import io.github.loncra.framework.security.filter.result.IgnoreOrDesensitizeResultFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 安全配置自动配置
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass({OncePerRequestFilter.class})
@EnableConfigurationProperties(WebProperties.class)
public class SecurityConfiguration {

    /**
     * 创建忽略或脱敏结果过滤器 Bean
     *
     * @param webProperties Web 配置属性
     *
     * @return FilterRegistrationBean 实例
     */
    @Bean
    public FilterRegistrationBean<IgnoreOrDesensitizeResultFilter> ignoreOrDesensitizeResultFilter(WebProperties webProperties) {
        FilterRegistrationBean<IgnoreOrDesensitizeResultFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new IgnoreOrDesensitizeResultFilter(webProperties));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }
}

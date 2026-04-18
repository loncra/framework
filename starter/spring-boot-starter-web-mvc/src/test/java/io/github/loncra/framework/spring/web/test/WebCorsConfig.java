package io.github.loncra.framework.spring.web.test;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/captcha/**")                    // 匹配所有路径
                .allowedOriginPatterns("*")          // 允许所有来源，生产环境建议替换为具体域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的HTTP方法
                .allowedHeaders("*")                // 允许的请求头
                .allowCredentials(false)            // 是否允许携带凭证(cookie)，为true时allowedOrigins不能为*
                .maxAge(3600);                     // 预检请求的有效期(秒)
    }
}

package io.github.loncra.framework.spring.security.core.authentication.service.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;

import java.nio.charset.Charset;

/**
 * 基础认证 feign 配置
 *
 * @author maurice
 */
@Configuration
@EnableConfigurationProperties(AuthenticationProperties.class)
public class FeignAuthenticationConfiguration {

    public static final String DEFAULT_TYPE = "feign";

    /**
     * feign 调用认证拦截器
     *
     * @param properties 认证配置信息
     *
     * @return feign 请求拦截器
     */
    @Bean
    public RequestInterceptor feignAuthRequestInterceptor(AuthenticationProperties properties) {
        return requestTemplate -> initRequestTemplate(requestTemplate, properties);
    }

    /**
     * 初始化 request template
     *
     * @param requestTemplate request template
     * @param properties      认证配置信息
     */
    public static void initRequestTemplate(
            RequestTemplate requestTemplate,
            AuthenticationProperties properties
    ) {

        SecurityProperties.User user = properties
                .getUsers()
                .stream()
                .filter(u -> u.getName().equals(DEFAULT_TYPE))
                .findFirst()
                .orElseThrow(() -> new SystemException("找不到类型为:" + DEFAULT_TYPE + "的默认用户"));

        String base64 = encodeUserProperties(user);

        requestTemplate.header(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + base64);

    }

    /**
     * 对认证用户进行编码
     *
     * @param user 当前用户
     *
     * @return 编码后的字符串
     */
    public static String encodeUserProperties(SecurityProperties.User user) {

        String token = user.getName() + CacheProperties.DEFAULT_SEPARATOR + user.getPassword();

        return Base64.encodeToString(token.getBytes(Charset.defaultCharset()));
    }

    /**
     * 构造 feign 认证的 http headers
     *
     * @param properties 认证配置信息
     *
     * @return feign 认证的 http headers
     */
    public static HttpHeaders of(AuthenticationProperties properties) {
        HttpHeaders httpHeaders = new HttpHeaders();

        SecurityProperties.User user = properties
                .getUsers()
                .stream()
                .filter(u -> u.getName().equals(DEFAULT_TYPE))
                .findFirst()
                .orElseThrow(() -> new SystemException("找不到类型为:" + DEFAULT_TYPE + "的默认用户"));

        String base64 = encodeUserProperties(user);

        httpHeaders.add(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + base64);
        return httpHeaders;
    }

}

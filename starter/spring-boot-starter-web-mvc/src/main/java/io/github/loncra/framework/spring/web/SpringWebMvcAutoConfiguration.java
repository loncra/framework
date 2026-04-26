package io.github.loncra.framework.spring.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.jackson.deserializer.NameEnumDeserializer;
import io.github.loncra.framework.commons.jackson.deserializer.NameValueEnumDeserializer;
import io.github.loncra.framework.commons.jackson.deserializer.ValueEnumDeserializer;
import io.github.loncra.framework.commons.jackson.serializer.NameEnumSerializer;
import io.github.loncra.framework.commons.jackson.serializer.NameValueEnumSerializer;
import io.github.loncra.framework.commons.jackson.serializer.ValueEnumSerializer;
import io.github.loncra.framework.idempotent.exception.IdempotentException;
import io.github.loncra.framework.spring.web.config.SpringWebMvcProperties;
import io.github.loncra.framework.spring.web.device.DeviceResolverRequestFilter;
import io.github.loncra.framework.spring.web.endpoint.EnumerateEndpoint;
import io.github.loncra.framework.spring.web.interceptor.CustomClientHttpRequestInterceptor;
import io.github.loncra.framework.spring.web.json.MimeTypeDeserializer;
import io.github.loncra.framework.spring.web.result.RestResponseBodyAdvice;
import io.github.loncra.framework.spring.web.result.RestResultErrorAttributes;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import io.github.loncra.framework.spring.web.result.error.support.BindingResultErrorResultResolver;
import io.github.loncra.framework.spring.web.result.error.support.ErrorCodeResultResolver;
import io.github.loncra.framework.spring.web.result.error.support.IdempotentErrorResultResolver;
import io.github.loncra.framework.spring.web.result.error.support.MissingServletRequestParameterResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * spring mvc 自动配置实现
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore({ErrorMvcAutoConfiguration.class, JacksonAutoConfiguration.class})
@EnableConfigurationProperties({SpringWebMvcProperties.class, JacksonProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "loncra.framework.mvc", value = "enabled", matchIfMissing = true)
public class SpringWebMvcAutoConfiguration {

    public static final String JACKSON_JSON_OBJECT_MODULE_NAME = "loncra-framework-extend-module";
    /**
     * 创建 RestResultErrorAttributes Bean
     *
     * @param resultResolvers 错误结果解析器列表
     * @param properties      SpringWebMvcProperties 配置属性
     *
     * @return RestResultErrorAttributes 实例
     */
    @Bean
    @ConditionalOnMissingBean(RestResultErrorAttributes.class)
    public RestResultErrorAttributes servletRestResultErrorAttributes(
            List<ErrorResultResolver> resultResolvers,
            SpringWebMvcProperties properties
    ) {
        return new RestResultErrorAttributes(
                resultResolvers,
                properties.getSupportException(),
                properties.getSupportHttpStatus()
        );
    }

    /**
     * 创建 RestResponseBodyAdvice Bean
     *
     * @param properties SpringWebMvcProperties 配置属性
     *
     * @return RestResponseBodyAdvice 实例
     */
    @Bean
    @ConditionalOnMissingBean(RestResponseBodyAdvice.class)
    public RestResponseBodyAdvice restResponseBodyAdvice(SpringWebMvcProperties properties) {
        return new RestResponseBodyAdvice(properties);
    }

    /**
     * 注册设备解析请求过滤器
     *
     * @param mvcProperties Spring Web MVC 配置（见 {@code enabled-device-filter}、{@code device-filter-order-value}）
     *
     * @return FilterRegistrationBean 实例
     */
    @Bean
    @ConditionalOnProperty(
            prefix = "loncra.framework.mvc",
            name = "enabled-device-filter",
            havingValue = "true",
            matchIfMissing = true
    )
    public FilterRegistrationBean<DeviceResolverRequestFilter> deviceResolverRequestFilter(
            SpringWebMvcProperties mvcProperties
    ) {
        int order = mvcProperties.getDeviceFilterOrderValue();
        FilterRegistrationBean<DeviceResolverRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new DeviceResolverRequestFilter(order));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(order);
        return filterRegistrationBean;
    }

    /**
     * 创建 BindingResultErrorResultResolver Bean
     *
     * @return BindingResultErrorResultResolver 实例
     */
    @Bean
    public BindingResultErrorResultResolver bindingResultErrorResultResolver() {
        return new BindingResultErrorResultResolver();
    }

    /**
     * 创建 MissingServletRequestParameterResolver Bean
     *
     * @return MissingServletRequestParameterResolver 实例
     */
    @Bean
    public MissingServletRequestParameterResolver missingServletRequestParameterResolver() {
        return new MissingServletRequestParameterResolver(MissingServletRequestParameterResolver.DEFAULT_OBJECT_NAME);
    }

    /**
     * 创建 IdempotentErrorResultResolver Bean
     *
     * @return IdempotentErrorResultResolver 实例
     */
    @Bean
    @ConditionalOnClass(IdempotentException.class)
    public IdempotentErrorResultResolver idempotentErrorResultResolver() {
        return new IdempotentErrorResultResolver();
    }

    /**
     * 创建 ErrorCodeResultResolver Bean
     *
     * @return ErrorCodeResultResolver 实例
     */
    @Bean
    public ErrorCodeResultResolver errorCodeResultResolver() {
        return new ErrorCodeResultResolver();
    }

    /**
     * 创建 RestTemplate Bean
     *
     * @param clientHttpRequestInterceptors 客户端 HTTP 请求拦截器提供者
     *
     * @return RestTemplate 实例
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public RestTemplate restTemplate(ObjectProvider<CustomClientHttpRequestInterceptor> clientHttpRequestInterceptors) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setInterceptors(clientHttpRequestInterceptors.stream().collect(Collectors.toList()));

        return restTemplate;
    }

    @Bean
    public ObjectMapper loncraFrameworkJacksonJsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        SimpleModule module = new SimpleModule(JACKSON_JSON_OBJECT_MODULE_NAME);

        module.addSerializer(NameValueEnum.class, new NameValueEnumSerializer());
        module.addSerializer(ValueEnum.class, new ValueEnumSerializer());
        module.addSerializer(NameEnum.class, new NameEnumSerializer());

        module.addDeserializer(NameValueEnum.class, new NameValueEnumDeserializer<>());
        module.addDeserializer(ValueEnum.class, new ValueEnumDeserializer<>());
        module.addDeserializer(NameEnum.class, new NameEnumDeserializer<>());
        module.addDeserializer(MimeType.class, new MimeTypeDeserializer());

        objectMapper.registerModule(module);

        CastUtils.setObjectMapper(objectMapper);

        return objectMapper;
    }


    /**
     * 创建 EnumerateEndpoint Bean
     *
     * @param infoContributor 信息贡献者提供者
     * @param properties      spring mvc 配置信息
     *
     * @return EnumerateEndpoint 实例
     */
    @Bean
    public EnumerateEndpoint enumerateEndpoint(
            ObjectProvider<InfoContributor> infoContributor,
            SpringWebMvcProperties properties
    ) {
        return new EnumerateEndpoint(infoContributor.stream().collect(Collectors.toList()), properties);
    }
}

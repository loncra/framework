package io.github.loncra.framework.captcha;


import io.github.loncra.framework.captcha.filter.CaptchaVerificationFilter;
import io.github.loncra.framework.captcha.filter.CaptchaVerificationInterceptor;
import io.github.loncra.framework.captcha.filter.CaptchaVerificationService;
import io.github.loncra.framework.captcha.filter.support.TianaiCaptchaVerificationService;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.captcha.intercept.support.DelegateCaptchaInterceptor;
import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.storage.support.InMemoryCaptchaStorageManager;
import io.github.loncra.framework.captcha.tianai.TianaiCaptchaService;
import io.github.loncra.framework.captcha.tianai.config.TianaiCaptchaProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

/**
 * 验证码自动配置
 *
 * @author maurice.chen
 */
@Configuration
@EnableConfigurationProperties({CaptchaProperties.class, TianaiCaptchaProperties.class})
@ConditionalOnProperty(prefix = "loncra.framework.captcha", value = "enabled", matchIfMissing = true)
public class CaptchaAutoConfiguration {

    /**
     * 创建验证码拦截器 Bean
     *
     * @param delegateCaptchaService 委托验证码服务
     *
     * @return Interceptor 实例
     */
    @Bean
    @ConditionalOnMissingBean(Interceptor.class)
    public Interceptor interceptor(DelegateCaptchaService delegateCaptchaService) {
        return new DelegateCaptchaInterceptor(delegateCaptchaService);
    }

    /**
     * 创建委托验证码服务 Bean
     *
     * @param captchaServices 验证码服务提供者
     *
     * @return DelegateCaptchaService 实例
     */
    @Bean
    @ConditionalOnMissingBean(DelegateCaptchaService.class)
    public DelegateCaptchaService delegateCaptchaService(ObjectProvider<CaptchaService> captchaServices) {
        return new DelegateCaptchaService(captchaServices.stream().collect(Collectors.toList()));
    }

    /**
     * 创建天爱验证码服务 Bean
     *
     * @param captchaProperties       验证码配置属性
     * @param validator               验证器
     * @param interceptor             验证码拦截器
     * @param captchaStorageManager   验证码存储管理器
     * @param tianaiCaptchaProperties 天爱验证码配置属性
     *
     * @return TianaiCaptchaService 实例
     */
    @Bean
    @ConditionalOnMissingBean(TianaiCaptchaService.class)
    public TianaiCaptchaService tianaiCaptchaService(
            CaptchaProperties captchaProperties,
            @Qualifier("mvcValidator")
            Validator validator,
            @Lazy
            Interceptor interceptor,
            CaptchaStorageManager captchaStorageManager,
            TianaiCaptchaProperties tianaiCaptchaProperties
    ) {
        return new TianaiCaptchaService(
                captchaProperties,
                validator,
                interceptor,
                captchaStorageManager,
                tianaiCaptchaProperties
        );
    }

    /**
     * 创建验证码存储管理器 Bean
     *
     * @return CaptchaStorageManager 实例
     */
    @Bean
    @ConditionalOnMissingBean(CaptchaStorageManager.class)
    public CaptchaStorageManager captchaStorageManager() {
        return new InMemoryCaptchaStorageManager();
    }

    /**
     * 创建天爱验证码验证服务 Bean
     *
     * @param tianaiCaptchaService 天爱验证码服务
     *
     * @return TianaiCaptchaVerificationService 实例
     */
    @Bean
    @ConditionalOnMissingBean(TianaiCaptchaVerificationService.class)
    public TianaiCaptchaVerificationService tianaiCaptchaVerificationService(TianaiCaptchaService tianaiCaptchaService) {
        return new TianaiCaptchaVerificationService(tianaiCaptchaService);
    }

    /**
     * 创建验证码验证过滤器 Bean
     *
     * @param captchaProperties               验证码配置属性
     * @param captchaVerificationServices     验证码验证服务提供者
     * @param captchaVerificationInterceptors 验证码验证拦截器提供者
     *
     * @return FilterRegistrationBean 实例
     */
    @Bean
    public FilterRegistrationBean<CaptchaVerificationFilter> captchaVerificationFilter(
            CaptchaProperties captchaProperties,
            ObjectProvider<CaptchaVerificationService> captchaVerificationServices,
            ObjectProvider<CaptchaVerificationInterceptor> captchaVerificationInterceptors
    ) {
        CaptchaVerificationFilter captchaVerificationFilter = new CaptchaVerificationFilter(
                captchaProperties,
                captchaVerificationServices.stream().collect(Collectors.toList()),
                captchaVerificationInterceptors.stream().collect(Collectors.toList())
        );

        FilterRegistrationBean<CaptchaVerificationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(captchaVerificationFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

}

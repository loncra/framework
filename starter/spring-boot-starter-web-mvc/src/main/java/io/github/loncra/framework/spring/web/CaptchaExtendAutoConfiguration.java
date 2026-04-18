package io.github.loncra.framework.spring.web;

import io.github.loncra.framework.captcha.CaptchaAutoConfiguration;
import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.captcha.DelegateCaptchaService;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.tianai.TianaiCaptchaService;
import io.github.loncra.framework.captcha.tianai.config.TianaiCaptchaProperties;
import io.github.loncra.framework.spring.web.captcha.CaptchaController;
import io.github.loncra.framework.spring.web.captcha.ControllerTianaiCaptchaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.validation.Validator;

/**
 * 验证码扩展自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass(CaptchaAutoConfiguration.class)
@AutoConfigureBefore(CaptchaAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CaptchaExtendAutoConfiguration {

    /**
     * 创建验证码控制器 Bean
     *
     * @param interceptor            验证码拦截器
     * @param captchaProperties      验证码配置属性
     * @param captchaService         天爱验证码服务
     * @param resourceLoader         资源加载器
     * @param delegateCaptchaService 委托验证码服务
     *
     * @return CaptchaController 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "loncra.framework.captcha.controller", value = "enabled", matchIfMissing = true)
    public CaptchaController captchaController(
            @Lazy
            Interceptor interceptor,
            CaptchaProperties captchaProperties,
            TianaiCaptchaService captchaService,
            ResourceLoader resourceLoader,
            DelegateCaptchaService delegateCaptchaService
    ) {
        return new CaptchaController(delegateCaptchaService, interceptor, resourceLoader, captchaProperties, captchaService);
    }

    /**
     * 创建控制器天爱验证码服务 Bean
     *
     * @param captchaProperties       验证码配置属性
     * @param validator               验证器
     * @param interceptor             验证码拦截器
     * @param captchaStorageManager   验证码存储管理器
     * @param tianaiCaptchaProperties 天爱验证码配置属性
     *
     * @return ControllerTianaiCaptchaService 实例
     */
    @Bean
    @ConditionalOnMissingBean(TianaiCaptchaService.class)
    @ConditionalOnProperty(prefix = "loncra.framework.captcha", value = "enabled", matchIfMissing = true)
    public ControllerTianaiCaptchaService controllerTianaiCaptchaService(
            CaptchaProperties captchaProperties,
            @Qualifier("mvcValidator")
            Validator validator,
            @Lazy
            Interceptor interceptor,
            CaptchaStorageManager captchaStorageManager,
            TianaiCaptchaProperties tianaiCaptchaProperties
    ) {
        return new ControllerTianaiCaptchaService(
                captchaProperties,
                validator,
                interceptor,
                captchaStorageManager,
                tianaiCaptchaProperties
        );
    }
}

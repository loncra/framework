package io.github.loncra.framework.spring.web.captcha;

import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.tianai.TianaiCaptchaService;
import io.github.loncra.framework.captcha.tianai.config.TianaiCaptchaProperties;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 响应 js http 路径的 天爱验证码服务实现
 *
 * @author maurice.chen
 */
public class ControllerTianaiCaptchaService extends TianaiCaptchaService {

    /**
     * 创建一个控制器天爱验证码服务
     *
     * @param captchaProperties       验证码配置属性
     * @param validator               验证器
     * @param interceptor             验证码拦截器
     * @param captchaStorageManager   验证码存储管理器
     * @param tianaiCaptchaProperties 天爱验证码配置属性
     */
    public ControllerTianaiCaptchaService(
            CaptchaProperties captchaProperties,
            Validator validator,
            Interceptor interceptor,
            CaptchaStorageManager captchaStorageManager,
            TianaiCaptchaProperties tianaiCaptchaProperties
    ) {
        super(captchaProperties, validator, interceptor, captchaStorageManager, tianaiCaptchaProperties);
    }

    @Override
    protected Map<String, Object> createGenerateArgs() {
        Map<String, Object> result = new LinkedHashMap<>();
        String url = getTianaiCaptchaProperties().getApiBaseUrl()
                + AntPathMatcher.DEFAULT_PATH_SEPARATOR
                + CaptchaController.CONTROLLER_NAME
                + AntPathMatcher.DEFAULT_PATH_SEPARATOR
                + TianaiCaptchaProperties.JS_CONTROLLER;

        result.put(TianaiCaptchaProperties.JS_URL_KEY, url);

        return result;
    }
}

package io.github.loncra.framework.captcha.intercept.support;

import io.github.loncra.framework.captcha.CaptchaService;
import io.github.loncra.framework.captcha.DelegateCaptchaService;
import io.github.loncra.framework.captcha.intercept.Interceptor;
import io.github.loncra.framework.captcha.token.BuildToken;
import io.github.loncra.framework.captcha.token.InterceptToken;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.SystemException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 委托验证码拦截器
 *
 * @author maurice.chen
 */
public class DelegateCaptchaInterceptor implements Interceptor {

    private final DelegateCaptchaService delegateCaptchaService;

    public DelegateCaptchaInterceptor(DelegateCaptchaService delegateCaptchaService) {
        this.delegateCaptchaService = delegateCaptchaService;
    }

    @Override
    public InterceptToken generateCaptchaIntercept(
            String token,
            String type,
            String interceptType
    ) {

        CaptchaService captchaService = delegateCaptchaService.getCaptchaServiceByType(type);

        BuildToken buildToken = captchaService.getBuildToken(type, token);

        return generateCaptchaIntercept(buildToken, interceptType);
    }

    @Override
    public InterceptToken generateCaptchaIntercept(
            BuildToken buildToken,
            String interceptType
    ) {
        SystemException.isTrue(Objects.nonNull(buildToken), "buildToken 不能为空");
        CaptchaService interceptorService = delegateCaptchaService.getCaptchaServiceByType(interceptType);

        InterceptToken interceptToken = interceptorService.generateInterceptorToken(buildToken);

        buildToken.setInterceptToken(interceptToken);

        return interceptToken;
    }

    @Override
    public RestResult<Map<String, Object>> verifyCaptcha(HttpServletRequest request) {
        // 通过本次请求看看是否需要做一次拦截验证
        CaptchaService captchaService = delegateCaptchaService.getCaptchaServiceByRequest(request);

        BuildToken buildToken = captchaService.getBuildToken(request);

        if (Objects.isNull(buildToken)) {
            return RestResult.of("找不到 token 内容，无需拦截验证验证");
        }

        InterceptToken interceptToken = buildToken.getInterceptToken();

        if (Objects.isNull(interceptToken)) {
            return RestResult.of("ID 为 [" + buildToken.getId() + "], 类型为 [" + buildToken.getType() + "] token 内容没有拦截 token，无需拦截验证验证");
        }

        CaptchaService interceptService = delegateCaptchaService.getCaptchaServiceByType(interceptToken.getType());

        return interceptService.verifyInterceptToken(request);
    }
}

package io.github.loncra.framework.captcha.filter.support;

import io.github.loncra.framework.captcha.filter.CaptchaVerificationService;
import io.github.loncra.framework.captcha.tianai.TianaiCaptchaService;
import io.github.loncra.framework.commons.RestResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * tianai 验证码校验服务实现
 *
 * @author maurice.chen
 */
public class TianaiCaptchaVerificationService implements CaptchaVerificationService {

    public static final String DEFAULT_TYPE = "tianai";

    private final TianaiCaptchaService tianaiCaptchaService;

    public TianaiCaptchaVerificationService(TianaiCaptchaService tianaiCaptchaService) {
        this.tianaiCaptchaService = tianaiCaptchaService;
    }

    @Override
    public List<String> getType() {
        return Collections.singletonList(DEFAULT_TYPE);
    }

    @Override
    public RestResult<Object> verify(HttpServletRequest request) {
        RestResult<Object> result = tianaiCaptchaService.verify(request);
        Assert.isTrue(result.isSuccess(), result.getMessage());
        return result;
    }

    @Override
    public void delete(HttpServletRequest request) {
        tianaiCaptchaService.deleteCaptcha(request);
    }
}

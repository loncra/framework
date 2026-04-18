package io.github.loncra.framework.spring.security.core.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * 验证码校验配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.captcha-verification")
public class CaptchaVerificationProperties {

    public static final String DEFAULT_CAPTCHA_TYPE_HEADER_NAME = "X-CAPTCHA-TYPE";

    public static final String DEFAULT_CAPTCHA_TYPE_PARAM_NAME = "captchaType";

    private String captchaTypeHeaderName = DEFAULT_CAPTCHA_TYPE_HEADER_NAME;

    private String captchaTypeParamName = DEFAULT_CAPTCHA_TYPE_PARAM_NAME;
    /**
     * 一定要验证码才能使用的链接
     */
    private List<String> verifyUrls = new LinkedList<>();

    public CaptchaVerificationProperties() {
    }

    public List<String> getVerifyUrls() {
        return verifyUrls;
    }

    public void setVerifyUrls(List<String> verifyUrls) {
        this.verifyUrls = verifyUrls;
    }

    public String getCaptchaTypeHeaderName() {
        return captchaTypeHeaderName;
    }

    public void setCaptchaTypeHeaderName(String captchaTypeHeaderName) {
        this.captchaTypeHeaderName = captchaTypeHeaderName;
    }

    public String getCaptchaTypeParamName() {
        return captchaTypeParamName;
    }

    public void setCaptchaTypeParamName(String captchaTypeParamName) {
        this.captchaTypeParamName = captchaTypeParamName;
    }
}

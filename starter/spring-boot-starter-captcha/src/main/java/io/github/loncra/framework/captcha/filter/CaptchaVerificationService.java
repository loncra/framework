package io.github.loncra.framework.captcha.filter;

import io.github.loncra.framework.commons.RestResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 验证码校验服务
 *
 * @author maurice.chen
 */
public interface CaptchaVerificationService {

    /**
     * 获取验证码类型
     *
     * @return 验证码类型
     */
    List<String> getType();

    /**
     * 验证数据
     *
     * @param request http servlet request
     */
    RestResult<Object> verify(HttpServletRequest request);

    /**
     * 删除验证码
     *
     * @param request http servlet request
     */
    void delete(HttpServletRequest request);
}

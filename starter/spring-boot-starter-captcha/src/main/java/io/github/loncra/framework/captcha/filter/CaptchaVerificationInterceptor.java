package io.github.loncra.framework.captcha.filter;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码校验拦截器，用于在需要验证码校验的链接时，是否根据某些条件不需要校验验证码或一些阶段性处理使用
 *
 * @author maurice.chen
 */
public interface CaptchaVerificationInterceptor {

    /**
     * 校验验证码之前触发此方法
     *
     * @param request  http servlet request
     * @param response http servlet response
     *
     * @return true 不需要校验验证码，否则 false，默认为 false
     */
    default boolean preVerify(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return false;
    }

    /**
     * 校验验证码之后触发此方法
     *
     * @param request  http servlet request
     * @param response http servlet response
     */
    default void postVerify(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

    }

    /**
     * 校验异常时触发此方法
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @param e        异常信息
     */
    default void exceptionVerify(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e
    ) {
        RestResult<Map<String, Object>> result = RestResult.ofException(e);
        result.setData(new LinkedHashMap<>());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.getWriter().write(CastUtils.getObjectMapper().writeValueAsString(result));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * 请求体 body 自动设置到 Request attribute 实现
 *
 * @author maurice.chen
 */
@ControllerAdvice
public class RequestBodyAttributeAdviceAdapter extends RequestBodyAdviceAdapter {

    public static final String REQUEST_BODY_ATTRIBUTE_NAME = RequestBodyAttributeAdviceAdapter.class.getName();

    @Override
    public boolean supports(
            MethodParameter methodParameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object afterBodyRead(
            Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        SpringMvcUtils.setRequestAttribute(RequestBodyAttributeAdviceAdapter.REQUEST_BODY_ATTRIBUTE_NAME, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}

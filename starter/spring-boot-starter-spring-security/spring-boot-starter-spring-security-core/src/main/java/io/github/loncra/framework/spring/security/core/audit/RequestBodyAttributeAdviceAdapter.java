package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * 在 {@code @RequestBody} 反序列化完成后，将 body 写入 request attribute，供控制层审计 metadata 延后填充。
 * <p>拦截器 {@code preHandle} 早于 body 绑定，不能在此阶段读 {@link jakarta.servlet.ServletInputStream}；
 * 本类通过 {@link RequestBodyAdviceAdapter} 在 {@link #afterBodyRead} 写入 attribute（键 {@link #REQUEST_BODY_ATTRIBUTE_NAME}）。</p>
 * <p>消费方：{@link io.github.loncra.framework.spring.security.core.audit.creator.AuditableInterceptor#afterCompletion}；
 * {@link io.github.loncra.framework.spring.security.core.audit.SecurityPrincipalOperationDataTraceRepository#createAuditEvent}（写库留痕路径）。</p>
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

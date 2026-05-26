package io.github.loncra.framework.spring.security.core.audit.creator;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.security.audit.AuditProperties;
import io.github.loncra.framework.security.audit.Auditable;
import io.github.loncra.framework.spring.security.core.audit.RequestBodyAttributeAdviceAdapter;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;
import java.util.Objects;

/**
 * 绑定 {@link io.github.loncra.framework.security.audit.Auditable} 的控制器审计拦截器；
 * {@link #getAuditType()} 对应 {@link io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties#getControllerAuditName()}（默认 {@code controllerAudit}）。
 * <p>请求体不在 {@link #preHandle} 写入 metadata：{@link #afterCompletion} 从 {@link RequestBodyAttributeAdviceAdapter} 的 attribute 读取 body 并
 * {@link ControllerAuditEventMetadata#setBody}（受 {@link Auditable#ignoreProperties()} {@code ignoreRequestBody} 控制），
 * 在 {@link io.github.loncra.framework.spring.security.core.audit.ControllerAuditHandlerInterceptor} 发布事件之前完成。</p>
 *
 * @author maurice.chen
 * @see io.github.loncra.framework.security.audit.Auditable
 */
public class AuditableInterceptor extends AbstractAuditEventInterceptor {

    public AuditableInterceptor(
            ControllerAuditProperties controllerAuditProperties
    ) {
        super(controllerAuditProperties);
    }

    protected Metadata[] postControllerAuditEventMetadata(
            ControllerAuditEventMetadata controllerAuditEventMetadata,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {

        Auditable auditable = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Auditable.class);

        controllerAuditEventMetadata.setName(auditable.value());
        controllerAuditEventMetadata.setRemark(auditable.remark());

        return auditable.metadata();
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler,
            Exception ex,
            AuditEvent auditEvent
    ) {
        super.afterCompletion(request, response, handler, ex, auditEvent);

        Auditable auditable = AnnotationUtils.findAnnotation(handler.getMethod(), Auditable.class);

        Object body = SpringMvcUtils.getRequestAttribute(RequestBodyAttributeAdviceAdapter.REQUEST_BODY_ATTRIBUTE_NAME);
        if (Objects.nonNull(body) && !auditable.ignoreProperties().ignoreRequestBody()) {
            ControllerAuditEventMetadata metadata = CastUtils.cast(auditEvent.getData().get(RestResult.DEFAULT_METADATA_NAME));
            Map<String, Object> bodyMap = CastUtils.convertValue(body, CastUtils.MAP_TYPE_REFERENCE);
            bodyMap.put(BeanDefinitionParserDelegate.CLASS_ATTRIBUTE, body.getClass());
            metadata.setBody(bodyMap);
        }
    }

    @Override
    protected AuditProperties getControllerHandlerMethodAuditProperties(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {
        Auditable auditable = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Auditable.class);
        return auditable.ignoreProperties();
    }

    @Override
    public String getAuditType() {
        return getControllerAuditProperties().getControllerAuditName();
    }

    @Override
    public boolean isSupport(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {
        Auditable auditable = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Auditable.class);
        return Objects.nonNull(auditable);
    }
}

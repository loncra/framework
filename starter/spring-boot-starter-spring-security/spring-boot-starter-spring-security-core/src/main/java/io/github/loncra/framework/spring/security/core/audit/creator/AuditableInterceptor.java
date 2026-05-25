package io.github.loncra.framework.spring.security.core.audit.creator;

import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.security.audit.AuditProperties;
import io.github.loncra.framework.security.audit.Auditable;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;

/**
 * 绑定 {@link io.github.loncra.framework.security.audit.Auditable} 的控制器审计拦截器；
 * {@link #getAuditType()} 对应 {@link io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties#getControllerAuditName()}（默认 {@code controllerAudit}）。
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

        controllerAuditEventMetadata.setName(auditable.name());
        controllerAuditEventMetadata.setRemark(auditable.remark());

        return auditable.metadata();
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

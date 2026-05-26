package io.github.loncra.framework.spring.security.core.audit.creator;

import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.security.audit.AuditProperties;
import io.github.loncra.framework.spring.security.core.audit.OperationDataTrace;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;

/**
 * 绑定 {@link io.github.loncra.framework.spring.security.core.audit.OperationDataTrace} 的控制器审计拦截器；
 * 在 request 上预置 {@code AuditEvent}，供 {@link io.github.loncra.framework.spring.security.core.audit.SecurityPrincipalOperationDataTraceRepository} 触发写库留痕并合并 {@code operationTrace}。
 * <p>{@link #getAuditType()} 对应 {@link io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties#getOperationDataTraceAuditName()}（默认 {@code operationDataTraceAudit}）。</p>
 * <p>本类不在 {@link #afterCompletion} 写 body：留痕时在 {@code SecurityPrincipalOperationDataTraceRepository#createAuditEvent} 从
 * {@link io.github.loncra.framework.spring.security.core.audit.RequestBodyAttributeAdviceAdapter} attribute 读取并写入 metadata；
 * {@link #postControllerAuditEventMetadata} 将 {@code ignoreRequestBody} 写入 request attribute {@link #IGNORE_REQUEST_BODY_ATTR_NAME} 供留痕仓库判断。</p>
 *
 * @author maurice.chen
 */
public class OperationDataTraceAuditEventInterceptor extends AbstractAuditEventInterceptor{

    public static final String IGNORE_REQUEST_BODY_ATTR_NAME = "ignoreRequestBody";

    public OperationDataTraceAuditEventInterceptor(ControllerAuditProperties controllerAuditProperties) {
        super(controllerAuditProperties);
    }

    @Override
    protected Metadata[] postControllerAuditEventMetadata(
            ControllerAuditEventMetadata controllerAuditEventMetadata,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {

        OperationDataTrace dataTrace = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), OperationDataTrace.class);

        controllerAuditEventMetadata.setName(dataTrace.value());
        controllerAuditEventMetadata.setRemark(dataTrace.remark());

        request.setAttribute(IGNORE_REQUEST_BODY_ATTR_NAME, dataTrace.ignoreProperties().ignoreRequestBody());

        return dataTrace.metadata();
    }

    @Override
    protected AuditProperties getControllerHandlerMethodAuditProperties(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {
        OperationDataTrace auditable = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), OperationDataTrace.class);
        if (Objects.isNull(auditable)) {
            return null;
        }
        return auditable.ignoreProperties();
    }

    @Override
    public String getAuditType() {
        return getControllerAuditProperties().getOperationDataTraceAuditName();
    }

    @Override
    public boolean isSupport(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler
    ) {
        OperationDataTrace auditable = AnnotationUtils.findAnnotation(handler.getMethod(), OperationDataTrace.class);
        return Objects.nonNull(auditable);
    }
}

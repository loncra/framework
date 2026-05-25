package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.CastUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.List;
import java.util.Objects;

/**
 * 控制器审计方法拦截器
 *
 * @author maurice
 */
public class ControllerAuditHandlerInterceptor implements ApplicationEventPublisherAware, AsyncHandlerInterceptor {

    /**
     * 审计事件拦截器
     */
    private final List<AuditEventInterceptor> auditEventInterceptors;

    /**
     * spring 应用的事件推送器
     */
    private ApplicationEventPublisher applicationEventPublisher;

    public ControllerAuditHandlerInterceptor(
            List<AuditEventInterceptor> auditEventInterceptors
    ) {
        this.auditEventInterceptors = auditEventInterceptors;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
        }

        HandlerMethod handlerMethod = CastUtils.cast(handler);
        List<AuditEventInterceptor> filterList = auditEventInterceptors.stream()
                .filter(s -> s.isSupport(request, response, handlerMethod))
                .toList();
        for (AuditEventInterceptor interceptor : filterList) {
            AuditEvent event = interceptor.preHandle(request, response, handlerMethod);
            if (Objects.isNull(event)) {
                continue;
            }
            request.setAttribute(interceptor.getAuditType(), event);
        }

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {

        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            return;
        }

        HandlerMethod handlerMethod = CastUtils.cast(handler);
        for (AuditEventInterceptor interceptor : auditEventInterceptors) {
            Object event = request.getAttribute(interceptor.getAuditType());
            if (Objects.isNull(event)) {
                continue;
            }
            AuditEvent auditEvent = CastUtils.cast(event);
            interceptor.afterCompletion(request, response, handlerMethod, ex, auditEvent);
            // 推送审计事件
            applicationEventPublisher.publishEvent(new AuditApplicationEvent(auditEvent));
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}

package io.github.loncra.framework.spring.security.core.audit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.method.HandlerMethod;

/**
 * 控制器审计事件拦截策略：在 {@link ControllerAuditHandlerInterceptor} 的 {@code preHandle} / {@code afterCompletion} 生命周期内
 * 创建、补全并发布 {@link AuditEvent}。
 * <p>{@link #getAuditType()} 返回值作为 {@link jakarta.servlet.http.HttpServletRequest#setAttribute(String, Object)} 的键，
 * 同一请求上可并存多种审计（如 {@code controllerAudit} 与 {@code operationDataTraceAudit}）。</p>
 *
 * @author maurice.chen
 * @see ControllerAuditHandlerInterceptor
 */
public interface AuditEventInterceptor {

    /**
     * 在控制器方法执行前创建审计事件并写入 request attribute（由 {@link ControllerAuditHandlerInterceptor} 调用）。
     *
     * @param request       HTTP 请求
     * @param response      HTTP 响应
     * @param handler       控制器方法句柄
     * @return 审计事件；{@code null} 表示本拦截器不参与本次请求
     */
    AuditEvent preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler
    );

    /**
     * 在请求完成后补全 {@link io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata}（结束时间、执行状态等），
     * 随后由 {@link ControllerAuditHandlerInterceptor} 发布 {@link AuditEvent}。
     *
     * @param request    HTTP 请求
     * @param response   HTTP 响应
     * @param handler    控制器方法句柄
     * @param ex         处理过程中抛出的异常；无异常时为 {@code null}
     * @param auditEvent {@code preHandle} 阶段写入 request 的同一事件实例
     */
    void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler,
            Exception ex,
            AuditEvent auditEvent
    );

    /**
     * 与 {@link jakarta.servlet.http.HttpServletRequest#setAttribute} 配合使用的审计类型键（见 {@link io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties}）。
     *
     * @return request attribute 名称
     */
    String getAuditType();

    /**
     * 是否支持本次请求
     *
     * @param request http servlet request
     * @param response Http Servlet response
     * @param handler controller 方法句柄
     *
     * @return true 是，否则 false
     */
    boolean isSupport(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler
    );
}

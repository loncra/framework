package io.github.loncra.framework.security.audit;

import org.springframework.boot.actuate.audit.AuditEvent;

/**
 * 审计事件存储库拦截器, 用于对审计事件一些执行前后得拦截处理使用
 *
 * @author maurice.chen
 */
public interface AuditEventRepositoryWriteInterceptor {

    /**
     * 执行添加审计事件时触发
     *
     * @param auditEvent 审计事件
     *
     * @return 返回 false 时，将不对审计事件进行写入
     */
    default boolean preAddHandle(AuditEvent auditEvent) {
        return true;
    }

    /**
     *
     * 执行写入审计事件后触发
     *
     * @param auditEvent 审计事件
     */
    default void postAddHandle(AuditEvent auditEvent) {

    }
}

package io.github.loncra.framework.security.audit.memory;

import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;

import java.util.List;

/**
 * 自定义内存审计仓库实现
 *
 * @author maurice.chen
 */
public class CustomInMemoryAuditEventRepository extends InMemoryAuditEventRepository {

    /**
     * 审计事件仓库拦截器列表
     */
    private final List<AuditEventRepositoryWriteInterceptor> interceptors;

    /**
     * 构造函数
     *
     * @param capacity     容量
     * @param interceptors 审计事件仓库拦截器列表
     */
    public CustomInMemoryAuditEventRepository(
            int capacity,
            List<AuditEventRepositoryWriteInterceptor> interceptors
    ) {
        super(capacity);
        this.interceptors = interceptors;
    }

    @Override
    public void add(AuditEvent event) {
        for (AuditEventRepositoryWriteInterceptor interceptor : interceptors) {
            if (!interceptor.preAddHandle(event)) {
                return;
            }
        }

        super.add(event);

        interceptors.forEach(i -> i.postAddHandle(event));
    }
}

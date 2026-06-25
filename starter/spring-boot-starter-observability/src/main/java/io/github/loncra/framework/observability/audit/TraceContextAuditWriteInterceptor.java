package io.github.loncra.framework.observability.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.observability.support.DefaultTraceContextContributor;
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 审计写入拦截器：向 AuditEvent.data 注入 traceId / spanId / applicationName
 *
 * @author maurice.chen
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceContextAuditWriteInterceptor implements AuditEventRepositoryWriteInterceptor {

    private final DefaultTraceContextContributor traceContextContributor;

    public TraceContextAuditWriteInterceptor(DefaultTraceContextContributor traceContextContributor) {
        this.traceContextContributor = traceContextContributor;
    }

    @Override
    public boolean preAddHandle(AuditEvent auditEvent) {
        if (Objects.isNull(auditEvent) || Objects.isNull(auditEvent.getData())) {
            return true;
        }

        Map<String, Object> data = CastUtils.convertValue(auditEvent.getData(), CastUtils.MAP_TYPE_REFERENCE);
        if (Objects.isNull(data)) {
            data = new LinkedHashMap<>();
        }
        else if (!(data instanceof LinkedHashMap)) {
            data = new LinkedHashMap<>(data);
        }

        traceContextContributor.contributeAuditData(data);

        auditEvent.getData().clear();
        auditEvent.getData().putAll(data);
        return true;
    }
}

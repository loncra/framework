package io.github.loncra.framework.observability.support;

import io.github.loncra.framework.commons.observability.TraceContextContributor;
import io.github.loncra.framework.observability.config.ObservabilityProperties;
import io.micrometer.tracing.Tracer;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * 默认链路追踪上下文贡献者
 *
 * @author maurice.chen
 */
public class DefaultTraceContextContributor implements TraceContextContributor {

    private final Tracer tracer;

    private final Environment environment;

    private final ObservabilityProperties properties;

    public DefaultTraceContextContributor(
            Tracer tracer,
            Environment environment,
            ObservabilityProperties properties
    ) {
        this.tracer = tracer;
        this.environment = environment;
        this.properties = properties;
    }

    @Override
    public void contribute(Map<String, Object> target) {
        if (!TraceContextSupport.isResponseMetadataEnabled(properties)) {
            return;
        }
        TraceContextSupport.contribute(tracer, environment, target);
    }

    /**
     * 向审计 data 写入 trace 上下文（不受 response-metadata 开关影响）
     *
     * @param auditData 审计 data
     */
    public void contributeAuditData(Map<String, Object> auditData) {
        if (!TraceContextSupport.isAuditCorrelationEnabled(properties)) {
            return;
        }
        TraceContextSupport.contributeAuditData(tracer, environment, auditData);
    }
}

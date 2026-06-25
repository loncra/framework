package io.github.loncra.framework.observability.support;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.observability.config.ObservabilityProperties;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Objects;

/**
 * 链路追踪上下文读写工具
 *
 * @author maurice.chen
 */
public final class TraceContextSupport {

    public static final String AUDIT_OBSERVABILITY_CORRELATION = "observability.correlation";

    private TraceContextSupport() {
    }

    /**
     * 向目标 Map 写入 trace 上下文
     *
     * @param tracer     追踪器，可为 null
     * @param environment 环境，可为 null
     * @param target     目标 Map
     */
    public static void contribute(
            Tracer tracer,
            Environment environment,
            Map<String, Object> target
    ) {
        if (Objects.isNull(target)) {
            return;
        }

        Span currentSpan = tracer != null ? tracer.currentSpan() : null;
        if (currentSpan != null) {
            target.put(RestResult.DEFAULT_TRACE_ID_NAME, currentSpan.context().traceId());
            target.put(RestResult.DEFAULT_SPAN_ID_NAME, currentSpan.context().spanId());
        }

        if (environment != null) {
            String applicationName = environment.getProperty("spring.application.name");
            if (StringUtils.isNotBlank(applicationName)) {
                target.put(RestResult.DEFAULT_APPLICATION_NAME, applicationName);
            }
        }
    }

    /**
     * 向审计事件 data 写入 trace 上下文
     *
     * @param tracer      追踪器
     * @param environment 环境
     * @param auditData   审计 data
     */
    public static void contributeAuditData(
            Tracer tracer,
            Environment environment,
            Map<String, Object> auditData
    ) {
        contribute(tracer, environment, auditData);
        auditData.put(AUDIT_OBSERVABILITY_CORRELATION, Boolean.TRUE);
        auditData.put(RestResult.DEFAULT_OBSERVABILITY_CORRELATION_NAME, Boolean.TRUE);
    }

    /**
     * 当前是否存在 active span
     *
     * @param tracer 追踪器
     *
     * @return true 存在
     */
    public static boolean hasCurrentSpan(Tracer tracer) {
        return tracer != null && tracer.currentSpan() != null;
    }

    /**
     * 响应 metadata 是否应写入 trace 上下文
     *
     * @param properties 配置
     *
     * @return true 应写入
     */
    public static boolean isResponseMetadataEnabled(ObservabilityProperties properties) {
        return properties.isEnabled() && properties.getResponseMetadata().isEnabled();
    }

    /**
     * 审计 correlation 是否启用
     *
     * @param properties 配置
     *
     * @return true 启用
     */
    public static boolean isAuditCorrelationEnabled(ObservabilityProperties properties) {
        return properties.isEnabled() && properties.getAuditCorrelation().isEnabled();
    }
}

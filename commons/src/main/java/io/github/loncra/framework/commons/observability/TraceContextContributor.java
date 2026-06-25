package io.github.loncra.framework.commons.observability;

import java.util.Map;

/**
 * 链路追踪上下文贡献者，用于向 {@link io.github.loncra.framework.commons.RestResult#getMetadata()}
 * 或审计事件 data 中写入 traceId 等关联字段。
 *
 * @author maurice.chen
 */
public interface TraceContextContributor {

    /**
     * 向目标 Map 写入当前请求的 trace 上下文（traceId、spanId、applicationName 等）。
     *
     * @param target 目标 Map，通常为 RestResult.metadata 或 AuditEvent.data
     */
    void contribute(Map<String, Object> target);
}

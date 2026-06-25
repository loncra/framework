package io.github.loncra.framework.observability.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * loncra 框架级指标注册器
 *
 * @author maurice.chen
 */
@ConditionalOnBean(MeterRegistry.class)
@ConditionalOnProperty(
        prefix = "loncra.framework.observability.metrics",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class LoncraObservabilityMetrics {

    public static final String ERROR_CODE_COUNTER = "loncra.error.code";

    public static final String IDEMPOTENT_CONFLICT_COUNTER = "loncra.idempotent.conflict";

    public static final String CONCURRENT_CONFLICT_COUNTER = "loncra.concurrent.conflict";

    public static final String REST_FORMAT_COUNTER = "loncra.rest.format.applied";

    private final MeterRegistry meterRegistry;

    public LoncraObservabilityMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * 记录业务错误码
     *
     * @param executeCode 执行代码
     */
    public void recordErrorCode(String executeCode) {
        meterRegistry.counter(ERROR_CODE_COUNTER, "code", executeCode).increment();
    }

    /**
     * 记录幂等冲突
     */
    public void recordIdempotentConflict() {
        meterRegistry.counter(IDEMPOTENT_CONFLICT_COUNTER).increment();
    }

    /**
     * 记录并发冲突
     */
    public void recordConcurrentConflict() {
        meterRegistry.counter(CONCURRENT_CONFLICT_COUNTER).increment();
    }

    /**
     * 记录 RestResult 格式化
     *
     * @param formatted 是否格式化
     */
    public void recordRestFormat(boolean formatted) {
        meterRegistry.counter(REST_FORMAT_COUNTER, "formatted", String.valueOf(formatted)).increment();
    }
}

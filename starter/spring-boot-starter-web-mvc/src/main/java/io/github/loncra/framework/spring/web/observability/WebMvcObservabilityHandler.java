package io.github.loncra.framework.spring.web.observability;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.observability.TraceContextContributor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.ObjectProvider;

/**
 * Web MVC 可观测性增强处理器
 *
 * @author maurice.chen
 */
public class WebMvcObservabilityHandler {

    private static final String REST_FORMAT_COUNTER = "loncra.rest.format.applied";

    private static final String ERROR_CODE_COUNTER = "loncra.error.code";

    private final ObjectProvider<TraceContextContributor> traceContextContributor;

    private final ObjectProvider<ObservationRegistry> observationRegistry;

    private final ObjectProvider<MeterRegistry> meterRegistry;

    public WebMvcObservabilityHandler(
            ObjectProvider<TraceContextContributor> traceContextContributor,
            ObjectProvider<ObservationRegistry> observationRegistry,
            ObjectProvider<MeterRegistry> meterRegistry
    ) {
        this.traceContextContributor = traceContextContributor;
        this.observationRegistry = observationRegistry;
        this.meterRegistry = meterRegistry;
    }

    /**
     * 增强 RestResult：写入 metadata trace 上下文并记录指标
     *
     * @param result    响应结果
     * @param formatted 是否经过 RestResult 统一格式化
     */
    public void enrichRestResult(RestResult<?> result, boolean formatted) {
        if (result == null) {
            return;
        }

        traceContextContributor.ifAvailable(contributor -> contributor.contribute(result.getMetadata()));

        ObservationRegistry registry = observationRegistry.getIfAvailable();
        if (registry != null) {
            Observation current = registry.getCurrentObservation();
            if (current != null) {
                current.lowCardinalityKeyValue("loncra.rest.formatted", String.valueOf(formatted));
            }
        }

        meterRegistry.ifAvailable(meter -> meter.counter(
                REST_FORMAT_COUNTER,
                "formatted",
                String.valueOf(formatted)
        ).increment());
    }

    /**
     * 记录异常到当前 Observation
     *
     * @param error 异常
     */
    public void recordError(Throwable error) {
        if (error == null) {
            return;
        }
        ObservationRegistry registry = observationRegistry.getIfAvailable();
        if (registry == null) {
            return;
        }
        Observation current = registry.getCurrentObservation();
        if (current != null) {
            current.error(error);
        }
    }

    /**
     * 记录错误码指标
     *
     * @param executeCode 执行代码
     */
    public void recordErrorCode(String executeCode) {
        meterRegistry.ifAvailable(meter -> meter.counter(ERROR_CODE_COUNTER, "code", executeCode).increment());
    }
}

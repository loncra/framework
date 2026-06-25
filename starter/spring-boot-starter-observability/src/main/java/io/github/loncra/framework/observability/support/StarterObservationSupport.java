package io.github.loncra.framework.observability.support;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import java.util.function.Supplier;

/**
 * 供各 starter 模块复用的 Observation 工具（避免各模块重复依赖 observability starter）
 *
 * @author maurice.chen
 */
public final class StarterObservationSupport {

    public static final String CONCURRENT_OBSERVATION_NAME = "loncra.concurrent.check";

    public static final String IDEMPOTENT_OBSERVATION_NAME = "loncra.idempotent.check";

    public static final String OPERATION_TRACE_OBSERVATION_NAME = "loncra.operation.trace";

    private StarterObservationSupport() {
    }

    public static <T> T observe(
            ObservationRegistry registry,
            String name,
            Supplier<T> supplier
    ) {
        if (registry == null) {
            return supplier.get();
        }
        return Observation.createNotStarted(name, registry).observe(supplier);
    }

    public static void observe(
            ObservationRegistry registry,
            String name,
            Runnable runnable
    ) {
        if (registry == null) {
            runnable.run();
            return;
        }
        Observation.createNotStarted(name, registry).observe(runnable);
    }

    public static void tagCurrent(
            ObservationRegistry registry,
            String key,
            String value
    ) {
        if (registry == null) {
            return;
        }
        Observation current = registry.getCurrentObservation();
        if (current != null) {
            current.lowCardinalityKeyValue(key, value);
        }
    }

    public static void increment(
            MeterRegistry meterRegistry,
            String name,
            String... tags
    ) {
        if (meterRegistry == null) {
            return;
        }
        meterRegistry.counter(name, tags).increment();
    }
}

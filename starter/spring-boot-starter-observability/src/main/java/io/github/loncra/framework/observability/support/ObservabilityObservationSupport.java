package io.github.loncra.framework.observability.support;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import java.util.function.Supplier;

/**
 * Observation 执行工具，在无 ObservationRegistry 时降级为直接执行
 *
 * @author maurice.chen
 */
public final class ObservabilityObservationSupport {

    private ObservabilityObservationSupport() {
    }

    /**
     * 在指定 Observation 下执行逻辑
     *
     * @param registry Observation 注册表，可为 null
     * @param name     Observation 名称
     * @param supplier 执行逻辑
     * @param <T>      返回类型
     *
     * @return 执行结果
     */
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

    /**
     * 在指定 Observation 下执行无返回值逻辑
     *
     * @param registry Observation 注册表，可为 null
     * @param name     Observation 名称
     * @param runnable 执行逻辑
     */
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

    /**
     * 向当前 Observation 写入低基数键值
     *
     * @param registry Observation 注册表
     * @param key      键
     * @param value    值
     */
    public static void lowCardinalityKeyValue(
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

    /**
     * 记录当前 Observation 错误
     *
     * @param registry Observation 注册表
     * @param error    异常
     */
    public static void recordError(
            ObservationRegistry registry,
            Throwable error
    ) {
        if (registry == null || error == null) {
            return;
        }
        Observation current = registry.getCurrentObservation();
        if (current != null) {
            current.error(error);
        }
    }

    /**
     * 递增 counter 指标
     *
     * @param meterRegistry 指标注册表
     * @param name          指标名
     * @param tags          tag 键值对（偶数个）
     */
    public static void incrementCounter(
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

package io.github.loncra.framework.observability.config;

import io.github.loncra.framework.commons.observability.TraceContextContributor;
import io.github.loncra.framework.observability.audit.TraceContextAuditWriteInterceptor;
import io.github.loncra.framework.observability.metrics.LoncraObservabilityMetrics;
import io.github.loncra.framework.observability.support.DefaultTraceContextContributor;
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * loncra 可观测性自动配置
 *
 * @author maurice.chen
 */
@Configuration
@EnableConfigurationProperties(ObservabilityProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.observability", name = "enabled", matchIfMissing = true)
@AutoConfigureAfter(name = {
        "org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration",
        "org.springframework.boot.actuate.autoconfigure.tracing.OpenTelemetryAutoConfiguration"
})
public class ObservabilityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(DefaultTraceContextContributor.class)
    public DefaultTraceContextContributor defaultTraceContextContributor(
            ObjectProvider<Tracer> tracer,
            Environment environment,
            ObservabilityProperties properties
    ) {
        return new DefaultTraceContextContributor(tracer.getIfAvailable(), environment, properties);
    }

    @Bean
    @ConditionalOnMissingBean(TraceContextContributor.class)
    public TraceContextContributor traceContextContributor(DefaultTraceContextContributor contributor) {
        return contributor;
    }

    @Bean
    @ConditionalOnClass(AuditEventRepositoryWriteInterceptor.class)
    @ConditionalOnProperty(
            prefix = "loncra.framework.observability.audit-correlation",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    @ConditionalOnMissingBean(TraceContextAuditWriteInterceptor.class)
    public TraceContextAuditWriteInterceptor traceContextAuditWriteInterceptor(
            DefaultTraceContextContributor traceContextContributor
    ) {
        return new TraceContextAuditWriteInterceptor(traceContextContributor);
    }

    @Bean
    @ConditionalOnMissingBean(LoncraObservabilityMetrics.class)
    @ConditionalOnProperty(
            prefix = "loncra.framework.observability.metrics",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    public LoncraObservabilityMetrics loncraObservabilityMetrics(ObjectProvider<MeterRegistry> meterRegistry) {
        return new LoncraObservabilityMetrics(meterRegistry.getObject());
    }
}

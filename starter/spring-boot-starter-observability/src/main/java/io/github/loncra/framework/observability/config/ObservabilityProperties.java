package io.github.loncra.framework.observability.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * loncra 可观测性配置属性
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.observability")
public class ObservabilityProperties {

    /**
     * 是否启用可观测性支持
     */
    private boolean enabled = true;

    @NestedConfigurationProperty
    private final Tracing tracing = new Tracing();

    @NestedConfigurationProperty
    private final Logging logging = new Logging();

    @NestedConfigurationProperty
    private final AuditCorrelation auditCorrelation = new AuditCorrelation();

    @NestedConfigurationProperty
    private final ResponseMetadata responseMetadata = new ResponseMetadata();

    @NestedConfigurationProperty
    private final Metrics metrics = new Metrics();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Tracing getTracing() {
        return tracing;
    }

    public Logging getLogging() {
        return logging;
    }

    public AuditCorrelation getAuditCorrelation() {
        return auditCorrelation;
    }

    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public static class Tracing {

        /**
         * 是否启用链路追踪
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Logging {

        /**
         * 是否启用日志 MDC 关联（traceId / spanId）
         */
        private boolean correlationEnabled = true;

        /**
         * 日志关联前缀格式，为空时使用 Spring Boot 默认格式
         */
        private String correlationPattern = "[${spring.application.name:},%X{traceId:-},%X{spanId:-}] ";

        public boolean isCorrelationEnabled() {
            return correlationEnabled;
        }

        public void setCorrelationEnabled(boolean correlationEnabled) {
            this.correlationEnabled = correlationEnabled;
        }

        public String getCorrelationPattern() {
            return correlationPattern;
        }

        public void setCorrelationPattern(String correlationPattern) {
            this.correlationPattern = correlationPattern;
        }
    }

    public static class AuditCorrelation {

        /**
         * 是否向 AuditEvent.data 注入 traceId 等字段
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class ResponseMetadata {

        /**
         * 是否向 RestResult.metadata 回传 traceId 等字段
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Metrics {

        /**
         * 是否注册 loncra 框架级自定义指标
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}

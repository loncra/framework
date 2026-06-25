package io.github.loncra.framework.observability.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 可观测性默认配置注入（日志 MDC 关联等）
 *
 * @author maurice.chen
 */
public class ObservabilityEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    static final String PROPERTY_SOURCE_NAME = "loncraObservabilityDefaults";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!Boolean.parseBoolean(environment.getProperty("loncra.framework.observability.enabled", "true"))) {
            return;
        }

        Map<String, Object> defaults = new HashMap<>(8);

        if (Boolean.parseBoolean(
                environment.getProperty("loncra.framework.observability.logging.correlation-enabled", "true")
        )) {
            if (!environment.containsProperty("logging.pattern.correlation")) {
                String pattern = environment.getProperty(
                        "loncra.framework.observability.logging.correlation-pattern",
                        "[${spring.application.name:},%X{traceId:-},%X{spanId:-}] "
                );
                defaults.put("logging.pattern.correlation", pattern);
            }
        }

        if (Boolean.parseBoolean(
                environment.getProperty("loncra.framework.observability.tracing.enabled", "true")
        )) {
            if (!environment.containsProperty("management.tracing.enabled")) {
                defaults.put("management.tracing.enabled", true);
            }
        }

        if (!defaults.isEmpty()) {
            environment.getPropertySources().addLast(new MapPropertySource(PROPERTY_SOURCE_NAME, defaults));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

package io.github.loncra.framework.observability.test;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.observability.TraceContextContributor;
import io.github.loncra.framework.observability.support.TraceContextSupport;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ObservabilityStarterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.application.name=observability-test",
        "loncra.framework.observability.enabled=true",
        "loncra.framework.observability.response-metadata.enabled=true",
        "management.tracing.enabled=false"
})
class ObservabilityAutoConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TraceContextContributor traceContextContributor;

    @Test
    void contextLoads() {
        assertThat(traceContextContributor).isNotNull();
    }

    @Test
    void demoEndpointIsReachable() throws Exception {
        mockMvc.perform(get("/api/orders/123"))
                .andExpect(status().isOk());
    }

    @Test
    void traceContextContributorWritesApplicationName() {
        Map<String, Object> metadata = new LinkedHashMap<>();
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("spring.application.name", "order-service");

        TraceContextSupport.contribute(null, environment, metadata);

        assertThat(metadata).containsEntry(RestResult.DEFAULT_APPLICATION_NAME, "order-service");
    }

    @Test
    void traceContextContributorWritesTraceIdWhenSpanPresent() {
        Map<String, Object> metadata = new LinkedHashMap<>();
        Tracer tracer = org.mockito.Mockito.mock(Tracer.class);
        Span span = org.mockito.Mockito.mock(Span.class);
        io.micrometer.tracing.TraceContext traceContext = org.mockito.Mockito.mock(io.micrometer.tracing.TraceContext.class);

        org.mockito.Mockito.when(tracer.currentSpan()).thenReturn(span);
        org.mockito.Mockito.when(span.context()).thenReturn(traceContext);
        org.mockito.Mockito.when(traceContext.traceId()).thenReturn("trace-123");
        org.mockito.Mockito.when(traceContext.spanId()).thenReturn("span-456");

        TraceContextSupport.contribute(tracer, null, metadata);

        assertThat(metadata)
                .containsEntry(RestResult.DEFAULT_TRACE_ID_NAME, "trace-123")
                .containsEntry(RestResult.DEFAULT_SPAN_ID_NAME, "span-456");
    }
}

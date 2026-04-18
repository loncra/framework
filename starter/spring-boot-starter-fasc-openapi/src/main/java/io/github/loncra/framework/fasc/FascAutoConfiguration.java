package io.github.loncra.framework.fasc;

import io.github.loncra.framework.fasc.client.*;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "loncra.framework.fasc", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(FascProperties.class)
public class FascAutoConfiguration {

    @Bean
    public OpenApiClient openApiClient(
            FascProperties fascProperties,
            ConcurrentInterceptor concurrentInterceptor
    ) {
        return new OpenApiClient(fascProperties, concurrentInterceptor);
    }

    @Bean
    public DocClient docClient(OpenApiClient openApiClient) {
        return new DocClient(openApiClient);
    }

    @Bean
    public SignTaskClient signTaskClient(OpenApiClient openApiClient) {
        return new SignTaskClient(openApiClient);
    }

    @Bean
    public UserClient userClient(OpenApiClient openApiClient) {
        return new UserClient(openApiClient);
    }

    @Bean
    public TemplateClient templateClient(OpenApiClient openApiClient) {
        return new TemplateClient(openApiClient);
    }

    @Bean
    public AppClient appClient(OpenApiClient openApiClient) {
        return new AppClient(openApiClient);
    }

    @Bean
    public ApproveClient approveClient(OpenApiClient openApiClient) {
        return new ApproveClient(openApiClient);
    }

    @Bean
    public ArchivesPerformanceClient archivesPerformanceClient(OpenApiClient openApiClient) {
        return new ArchivesPerformanceClient(openApiClient);
    }

    @Bean
    public CallBackClient callBackClient(OpenApiClient openApiClient) {
        return new CallBackClient(openApiClient);
    }

    @Bean
    public CorpClient corpClient(OpenApiClient openApiClient) {
        return new CorpClient(openApiClient);
    }

    @Bean
    public DraftClient draftClient(OpenApiClient openApiClient) {
        return new DraftClient(openApiClient);
    }

    @Bean
    public EUIClient eucClient(OpenApiClient openApiClient) {
        return new EUIClient(openApiClient);
    }

    @Bean
    public OCRClient ocrClient(OpenApiClient openApiClient) {
        return new OCRClient(openApiClient);
    }

    @Bean
    public OrgClient orgClient(OpenApiClient openApiClient) {
        return new OrgClient(openApiClient);
    }

    @Bean
    public SealClient sealClient(OpenApiClient openApiClient) {
        return new SealClient(openApiClient);
    }

    @Bean
    public ToolServiceClient toolServiceClient(OpenApiClient openApiClient) {
        return new ToolServiceClient(openApiClient);
    }

    @Bean
    public VoucherClient voucherClient(OpenApiClient openApiClient) {
        return new VoucherClient(openApiClient);
    }

}

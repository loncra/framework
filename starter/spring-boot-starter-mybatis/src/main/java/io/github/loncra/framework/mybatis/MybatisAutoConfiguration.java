package io.github.loncra.framework.mybatis;


import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceInterceptor;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceResolver;
import io.github.loncra.framework.mybatis.interceptor.json.support.JacksonJsonCollectionPostInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 自动配置实现
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnProperty(prefix = "loncra.framework.mybatis", value = "enabled", matchIfMissing = true)
public class MybatisAutoConfiguration {

    /**
     * 创建 Jackson JSON 集合后置拦截器 Bean
     *
     * @return Jackson JSON 集合后置拦截器实例
     */
    @Bean
    public JacksonJsonCollectionPostInterceptor jacksonCollectionPostInterceptor() {
        return new JacksonJsonCollectionPostInterceptor();
    }

    /**
     * 创建操作数据追踪拦截器 Bean
     *
     * @param operationDataTraceResolver 操作数据追踪解析器
     *
     * @return 操作数据追踪拦截器实例
     */
    @Bean
    @ConditionalOnBean(OperationDataTraceResolver.class)
    @ConditionalOnProperty(prefix = "loncra.framework.mybatis.operation-data-trace", value = "enabled", matchIfMissing = true)
    public OperationDataTraceInterceptor operationDataTraceInterceptor(OperationDataTraceResolver operationDataTraceResolver) {
        return new OperationDataTraceInterceptor(operationDataTraceResolver);
    }
}

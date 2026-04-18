package io.github.loncra.framework.canal;

import io.github.loncra.framework.canal.config.CanalAdminProperties;
import io.github.loncra.framework.canal.config.CanalNoticeProperties;
import io.github.loncra.framework.canal.config.CanalProperties;
import io.github.loncra.framework.canal.endpoint.NotifiableTableEndpoint;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeNoticeResolver;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeResolver;
import io.github.loncra.framework.canal.resolver.support.HttpCanalRowDataChangeNoticeResolver;
import io.github.loncra.framework.canal.resolver.support.SimpleCanalRowDataChangeResolver;
import io.github.loncra.framework.canal.service.CanalRowDataChangeNoticeService;
import io.github.loncra.framework.canal.service.support.InMemoryCanalRowDataChangeNoticeService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.stream.Collectors;

/**
 * canal 自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@EnableConfigurationProperties({CanalProperties.class, CanalAdminProperties.class, CanalNoticeProperties.class})
@ConditionalOnProperty(prefix = "loncra.framework.canal", value = "enabled", matchIfMissing = true)
public class CanalAutoConfiguration {

    /**
     * 创建 Canal Admin 服务 Bean
     *
     * @param canalAdminProperties Canal Admin 配置属性
     * @param restTemplate         HTTP 客户端
     * @param canalInstanceManager Canal 实例管理器
     * @param redissonClient       Redisson 客户端
     *
     * @return Canal Admin 服务实例
     */
    @Bean
    @ConditionalOnMissingBean(CanalInstanceManager.class)
    @ConditionalOnProperty(prefix = "loncra.framework.canal.admin", value = "enabled", matchIfMissing = true)
    public CanalAdminService canalAdminService(
            CanalAdminProperties canalAdminProperties,
            RestTemplate restTemplate,
            CanalInstanceManager canalInstanceManager,
            RedissonClient redissonClient
    ) {
        return new CanalAdminService(
                canalAdminProperties,
                restTemplate,
                redissonClient,
                canalInstanceManager
        );
    }

    /**
     * 创建 Canal 实例管理器 Bean
     *
     * @param canalRowDataChangeResolvers Canal 行数据变更解析器提供者
     * @param canalProperties             Canal 配置属性
     *
     * @return Canal 实例管理器实例
     */
    @Bean
    @ConditionalOnMissingBean(CanalInstanceManager.class)
    public CanalInstanceManager canalInstanceManager(
            ObjectProvider<CanalRowDataChangeResolver> canalRowDataChangeResolvers,
            CanalProperties canalProperties
    ) {
        return new CanalInstanceManager(
                canalRowDataChangeResolvers.stream().collect(Collectors.toList()),
                canalProperties
        );
    }

    /**
     * 创建 HTTP Canal 行数据变更通知解析器 Bean
     *
     * @param restTemplate RestTemplate 提供者
     *
     * @return HTTP Canal 行数据变更通知解析器实例
     */
    @Bean
    @ConditionalOnMissingBean(HttpCanalRowDataChangeNoticeResolver.class)
    public HttpCanalRowDataChangeNoticeResolver httpCanalRowDataChangeNoticeResolver(ObjectProvider<RestTemplate> restTemplate) {
        return new HttpCanalRowDataChangeNoticeResolver(restTemplate.getIfAvailable(RestTemplate::new));
    }

    /**
     * 创建内存 Canal 行数据变更通知服务 Bean
     *
     * @param canalRowDataChangeNoticeResolvers Canal 行数据变更通知解析器提供者
     *
     * @return Canal 行数据变更通知服务实例
     */
    @Bean
    @ConditionalOnMissingBean(CanalRowDataChangeNoticeService.class)
    public CanalRowDataChangeNoticeService inMemoryCanalRowDataChangeNoticeService(ObjectProvider<CanalRowDataChangeNoticeResolver> canalRowDataChangeNoticeResolvers) {
        return new InMemoryCanalRowDataChangeNoticeService(canalRowDataChangeNoticeResolvers.stream().collect(Collectors.toList()));
    }

    /**
     * 创建简单 Canal 行数据变更解析器 Bean
     *
     * @param canalRowDataChangeNoticeService Canal 行数据变更通知服务
     *
     * @return Canal 行数据变更解析器实例
     */
    @Bean
    public CanalRowDataChangeResolver simpleCanalRowDataChangeResolver(CanalRowDataChangeNoticeService canalRowDataChangeNoticeService) {
        return new SimpleCanalRowDataChangeResolver(canalRowDataChangeNoticeService);
    }

    /**
     * 创建可通知表端点 Bean
     *
     * @param infoContributors 信息贡献者提供者
     * @param dataSource       数据源
     * @param noticeProperties Canal 通知配置属性
     *
     * @return 可通知表端点实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "loncra.framework.canal.notice", value = "enabled", matchIfMissing = true)
    public NotifiableTableEndpoint notifiableTableEndpoint(
            ObjectProvider<InfoContributor> infoContributors,
            DataSource dataSource,
            CanalNoticeProperties noticeProperties
    ) {
        return new NotifiableTableEndpoint(infoContributors.stream().collect(Collectors.toList()), noticeProperties, dataSource);
    }
}

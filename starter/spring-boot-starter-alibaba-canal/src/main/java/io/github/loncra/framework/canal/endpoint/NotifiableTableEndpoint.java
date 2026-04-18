package io.github.loncra.framework.canal.endpoint;

import io.github.loncra.framework.canal.MysqlUtils;
import io.github.loncra.framework.canal.annotation.NotifiableTable;
import io.github.loncra.framework.canal.config.CanalNoticeProperties;
import io.github.loncra.framework.canal.domain.meta.TableMetadata;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Endpoint(id = NotifiableTableEndpoint.DEFAULT_ENDPOINT_NAME)
public class NotifiableTableEndpoint {

    /**
     * 默认的插件节点名称
     */
    public final static String DEFAULT_ENDPOINT_NAME = "notifiableTables";

    /**
     * 缓存值
     */
    private static final Map<String, Object> CACHE = new LinkedHashMap<>();

    /**
     * 信息奉献者集合
     */
    private final List<InfoContributor> infoContributors = new ArrayList<>();

    /**
     * spring 资源解析器
     */
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * spring 元数据读取工厂
     */
    private final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    /**
     * 通知配置
     */
    private CanalNoticeProperties noticeProperties;

    /**
     * 数据原
     */
    private DataSource dataSource;

    /**
     * 并发锁
     */
    private final Lock lock = new ReentrantLock();

    public NotifiableTableEndpoint(
            List<InfoContributor> infoContributors,
            CanalNoticeProperties noticeProperties,
            DataSource dataSource
    ) {
        this.infoContributors.addAll(infoContributors);
        this.noticeProperties = noticeProperties;
        this.dataSource = dataSource;
    }

    public NotifiableTableEndpoint() {
    }

    @ReadOperation
    public Map<String, Object> notifiableTable() throws Exception {
        // 如果缓存没有，就去扫描遍历
        lock.lock();

        try {

            if (CACHE.isEmpty()) {

                Info.Builder builder = new Info.Builder();

                for (InfoContributor contributor : this.infoContributors) {
                    contributor.contribute(builder);
                }

                Info build = builder.build();

                Map<String, Object> info = new LinkedHashMap<>();

                Map<String, Object> details = build.getDetails();

                if (MapUtils.isNotEmpty(details)) {
                    info.putAll(details);
                }

                List<TableMetadata> tableMetadata = resolveTableMeta();

                info.put(DEFAULT_ENDPOINT_NAME, tableMetadata);

                CACHE.putAll(info);
            }

            return CACHE;
        }
        finally {
            lock.unlock();
        }

    }

    public List<TableMetadata> resolveTableMeta() throws Exception {
        List<TableMetadata> tableMetadataList = new LinkedList<>();
        if (CollectionUtils.isEmpty(noticeProperties.getBasePackages())) {
            return tableMetadataList;
        }

        TypeFilter filter = new AnnotationTypeFilter(NotifiableTable.class);

        for (String basePackage : noticeProperties.getBasePackages()) {
            String classPath = ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
            Resource[] resources = this.resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + classPath);
            for (Resource resource : resources) {

                if (!resource.isReadable()) {
                    continue;
                }

                MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                Class<?> targetClass = Class.forName(metadataReader.getClassMetadata().getClassName());

                if (filter.match(metadataReader, metadataReaderFactory)) {
                    NotifiableTable notifiableTable = AnnotationUtils.findAnnotation(targetClass, NotifiableTable.class);
                    TableMetadata metadata = new TableMetadata();
                    metadata.setDatabase(noticeProperties.getDatabaseName());
                    metadata.setName(notifiableTable.value());
                    metadata.setComment(notifiableTable.comment());
                    metadata.setColumnInfoMetas(MysqlUtils.getTableColumns(metadata.getName(), noticeProperties.getDatabaseName(), dataSource.getConnection()));
                    tableMetadataList.add(metadata);
                }
            }
        }

        return tableMetadataList;

    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CanalNoticeProperties getNoticeProperties() {
        return noticeProperties;
    }

    public void setNoticeProperties(CanalNoticeProperties noticeProperties) {
        this.noticeProperties = noticeProperties;
    }
}

package io.github.loncra.framework.security.audit.elasticsearch;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.commons.page.PageRequest;
import io.github.loncra.framework.security.StoragePositionProperties;
import io.github.loncra.framework.security.audit.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BaseQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * es 审计事件仓库实现
 *
 * @author maurice.chen
 */
public class ElasticsearchAuditEventRepository extends AbstractExtendAuditEventRepository<BoolQuery.Builder> {

    public static final String MAPPING_FILE_PATH = "elasticsearch/plugin-audit-mapping.json";

    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticsearchAuditEventRepository.class);

    private final ElasticsearchOperations elasticsearchOperations;

    private final StoragePositioningGenerator storagePositioningGenerator;

    private final ElasticsearchQueryGenerator elasticsearchQueryGenerator;

    public ElasticsearchAuditEventRepository(
            List<AuditEventRepositoryWriteInterceptor> writeInterceptors,
            List<AuditEventRepositoryQueryInterceptor<BoolQuery.Builder>> queryInterceptors,
            ElasticsearchOperations elasticsearchOperations,
            ElasticsearchQueryGenerator elasticsearchQueryGenerator,
            StoragePositionProperties storagePositionProperties
    ) {
        super(writeInterceptors, queryInterceptors);
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticsearchQueryGenerator = elasticsearchQueryGenerator;
        this.storagePositioningGenerator = new SpringElStoragePositioningGenerator(storagePositionProperties);
    }

    @Override
    public void doAdd(AuditEvent event) {

        IdAuditEvent idAuditEvent = new IdAuditEvent(event);

        try {

            String index = storagePositioningGenerator.generatePositioning(idAuditEvent).toLowerCase();
            if (event instanceof StoragePositioningAuditEvent storagePositioningAuditEvent) {
                index = storagePositioningAuditEvent.getStoragePositioning();
            }

            IndexCoordinates indexCoordinates = IndexCoordinates.of(index);
            IndexOperations indexOperations = elasticsearchOperations.indexOps(indexCoordinates);
            createIndexIfNotExists(indexOperations, MAPPING_FILE_PATH);

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(idAuditEvent.getId())
                    .withObject(event)
                    .build();

            elasticsearchOperations.index(indexQuery, indexCoordinates);

        }
        catch (Exception e) {
            LOGGER.warn("新增 elasticsearch {} 审计事件出现异常:{}", event.getPrincipal(), e.getMessage());
        }

    }

    @Override
    protected long doCount(FindMetadata<BoolQuery.Builder> metadata) {
        NativeQueryBuilder builder = new NativeQueryBuilder().withQuery(new Query(metadata.getTargetQuery().build()));
        try {
            return countData(builder.build(), metadata.getStoragePositioning());
        }
        catch (Exception e) {
            LOGGER.warn("统计 elasticsearch 审计事件出现异常", e);
            return 0;
        }
    }

    private long countData(
            NativeQuery query,
            String index
    ) {
        return elasticsearchOperations.count(query, Map.class, IndexCoordinates.of(index));
    }

    public static void createIndexIfNotExists(
            IndexOperations indexOperations,
            String mappingFilePath
    ) throws IOException {
        if (indexOperations.exists()) {
            return;
        }

        indexOperations.create();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(mappingFilePath)) {
            Map<String, Object> mapping = CastUtils.getObjectMapper().readValue(input, CastUtils.MAP_TYPE_REFERENCE);
            indexOperations.putMapping(Document.from(mapping));
        }
    }


    @Override
    protected List<AuditEvent> doFind(FindMetadata<BoolQuery.Builder> metadata) {
        NativeQueryBuilder builder = new NativeQueryBuilder()
                .withQuery(new Query(metadata.getTargetQuery().build()))
                .withSort(SortOptions.of(s -> s.field(f -> f.field(RestResult.DEFAULT_TIMESTAMP_NAME).order(SortOrder.Desc))));

        Object number = metadata.getQuery().get(PageRequest.NUMBER_FIELD_NAME);
        Object size = metadata.getQuery().get(PageRequest.SIZE_FIELD_NAME);
        if (Objects.nonNull(number) && Objects.nonNull(size)) {
            builder.withPageable(
                    org.springframework.data.domain.PageRequest.of(
                            NumberUtils.toInt(number.toString()),
                            NumberUtils.toInt(size.toString())
                    )
            );
        }

        try {
            return findData(builder.build(), metadata.getStoragePositioning());
        }
        catch (Exception e) {
            LOGGER.warn("查询 elasticsearch 审计事件出现异常", e);
            return new LinkedList<>();
        }
    }

    public List<AuditEvent> findData(
            BaseQuery query,
            String index
    ) {
        return elasticsearchOperations
                .search(query, Map.class, IndexCoordinates.of(index))
                .stream()
                .map(SearchHit::getContent)
                .map(c -> createAuditEvent(CastUtils.cast(c)))
                .collect(Collectors.toList());
    }

    @Override
    public AuditEvent get(StringIdEntity idEntity) {

        String index = storagePositioningGenerator.generatePositioning(idEntity).toLowerCase();
        try {
            //noinspection unchecked
            Map<String, Object> map = elasticsearchOperations.get(idEntity.getId(), Map.class, IndexCoordinates.of(index));
            if (MapUtils.isEmpty(map)) {
                return null;
            }
            return createAuditEvent(map);
        }
        catch (Exception e) {
            LOGGER.warn("通过 ID 查询索引 [{}] 出现错误", index, e);
        }

        return null;
    }

    public String getIndexName(Instant instant) {
        StringIdEntity id = new StringIdEntity();
        id.setCreationTime(instant);
        return storagePositioningGenerator.generatePositioning(id).toLowerCase();
    }

    /**
     * 创建查询条件
     *
     * @param after 在什么时间之后的
     * @param query 查询条件
     *
     * @return 查询条件
     */
    @Override
    protected BoolQuery.Builder createQuery(
            Instant after,
            Map<String, Object> query
    ) {

        BoolQuery.Builder queryBuilder = elasticsearchQueryGenerator.createQueryWrapperFromMap(query);
        if (Objects.nonNull(after)) {
            queryBuilder = queryBuilder.must(m -> m.range(r -> r.term(d -> d.field(RestResult.DEFAULT_TIMESTAMP_NAME).gte(String.valueOf(after.getEpochSecond())))));
        }

        return queryBuilder;
    }

    @Override
    protected FindMetadata<BoolQuery.Builder> createFindEntity(
            BoolQuery.Builder targetQuery,
            Instant after,
            Map<String, Object> query
    ) {
        return new FindMetadata<>(targetQuery, getIndexName(after), query, after);
    }

    public ElasticsearchOperations getElasticsearchOperations() {
        return elasticsearchOperations;
    }

    public StoragePositioningGenerator getStoragePositioningGenerator() {
        return storagePositioningGenerator;
    }

    public ElasticsearchQueryGenerator getElasticsearchQueryGenerator() {
        return elasticsearchQueryGenerator;
    }
}

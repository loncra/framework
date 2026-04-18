package io.github.loncra.framework.security.test;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchAuditEventRepository;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchQueryGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.*;

/**
 * Elasticsearch 查询生成器测试类
 * <p>
 * 测试所有支持的 wildcard parser 的查询生成功能
 *
 * @author maurice.chen
 */
@SpringBootTest
@ActiveProfiles("elasticsearch")
@DisplayName("ElasticsearchQueryGenerator 测试")
public class ElasticsearchQueryGeneratorTest {

    private ElasticsearchQueryGenerator queryGenerator;

    @Autowired
    private ElasticsearchAuditEventRepository auditEventRepository;

    private ElasticsearchOperations elasticsearchOperations;

    private static final String TEST_INDEX_PREFIX = "test-query-generator-";

    private final Set<String> createdIndices = new HashSet<>();

    @BeforeEach
    public void setUp() {
        queryGenerator = new ElasticsearchQueryGenerator();
        if (auditEventRepository != null) {
            elasticsearchOperations = auditEventRepository.getElasticsearchOperations();
        }
    }

    @AfterEach
    public void tearDown() {
        // 清理所有创建的测试索引
        if (elasticsearchOperations != null) {
            for (String index : createdIndices) {
                try {
                    IndexCoordinates indexCoordinates = IndexCoordinates.of(index);
                    if (elasticsearchOperations.indexOps(indexCoordinates).exists()) {
                        elasticsearchOperations.indexOps(indexCoordinates).delete();
                    }
                }
                catch (Exception e) {
                    // 忽略删除索引时的异常
                }
            }
        }
        createdIndices.clear();
    }

    /**
     * 创建测试数据并写入 Elasticsearch
     *
     * @param indexName 索引名称
     * @param data      测试数据
     *
     * @return 创建的 IdAuditEvent
     */
    private IdAuditEvent createTestData(
            String indexName,
            Map<String, Object> data
    ) {
        if (elasticsearchOperations == null) {
            return null;
        }

        try {
            IdAuditEvent event = new IdAuditEvent("test-user", "test-type", data);

            IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
            if (!elasticsearchOperations.indexOps(indexCoordinates).exists()) {
                elasticsearchOperations.indexOps(indexCoordinates).create();
            }
            createdIndices.add(indexName);

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(event.getId())
                    .withObject(event)
                    .build();

            elasticsearchOperations.index(indexQuery, indexCoordinates);

            // 等待索引刷新
            Thread.sleep(5000);

            return event;
        }
        catch (Exception e) {
            throw new RuntimeException("创建测试数据失败", e);
        }
    }

    /**
     * 执行查询并返回结果数量
     *
     * @param queryBuilder 查询构建器
     * @param indexName    索引名称
     *
     * @return 查询结果数量
     */
    private long executeQuery(
            BoolQuery.Builder queryBuilder,
            String indexName
    ) {
        if (elasticsearchOperations == null) {
            return 0;
        }

        try {
            NativeQuery nativeQuery = new NativeQueryBuilder()
                    .withQuery(new Query(queryBuilder.build()))
                    .build();

            IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
            return elasticsearchOperations.count(nativeQuery, Map.class, indexCoordinates);
        }
        catch (Exception e) {
            throw new RuntimeException("执行查询失败", e);
        }
    }


    /**
     * 生成测试索引名称
     *
     * @return 测试索引名称
     */
    private String generateTestIndexName() {
        return TEST_INDEX_PREFIX + System.currentTimeMillis() + "-" + Thread.currentThread().threadId();
    }

    @Test
    @DisplayName("测试 EqWildcardParser - 等于查询")
    public void testEqWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "other");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_eq]", "test")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该只查询到一条 name=test 的记录");
    }

    @Test
    @DisplayName("测试 NeWildcardParser - 不等于查询")
    public void testNeWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("status", "active");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("status", "deleted");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("status", "pending");
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->status_ne]", "deleted")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(2, count, "应该查询到两条 status!=deleted 的记录");
    }

    @Test
    @DisplayName("测试 LikeWildcardParser - 模糊查询")
    public void testLikeWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("title", "test title");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("title", "other content");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->title_like]", "test")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条包含 test 的记录");
    }

    @Test
    @DisplayName("测试 LikeLeftWildcardParser - 左模糊查询（以...开始）")
    public void testLikeLeftWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "123test");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "123other");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_llike]", "test")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条以 test 开头的记录");
    }

    @Test
    @DisplayName("测试 LikeRightWildcardParser - 右模糊查询（以...结束）")
    public void testLikeRightWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test123");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "other123");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_rlike]", "test")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条以 test 结尾的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（单参数，使用 gte）")
    public void testBetweenWildcardParserWithSingleValue() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 20);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 15);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_between]", Collections.singletonList(18))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 age>=18 的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（双参数，使用 gte 和 lte）")
    public void testBetweenWildcardParserWithTwoValues() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 25);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 70);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("age", 10);
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_between]", Arrays.asList(18, 65))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 18<=age<=65 的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（多参数，使用第一个和最后一个）")
    public void testBetweenWildcardParserWithMultipleValues() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("score", 75);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("score", 50);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("score", 95);
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->score_between]", Arrays.asList(60, 70, 80, 90))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 60<=score<=90 的记录");
    }

    @Test
    @DisplayName("测试 GteWildcardParser - 大于等于查询")
    public void testGteWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 20);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 15);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_gte]", 18)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 age>=18 的记录");
    }

    @Test
    @DisplayName("测试 GtWildcardParser - 大于查询")
    public void testGtWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 20);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 18);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_gt]", 18)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 age>18 的记录");
    }

    @Test
    @DisplayName("测试 LteWildcardParser - 小于等于查询")
    public void testLteWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 60);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 70);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_lte]", 65)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 age<=65 的记录");
    }

    @Test
    @DisplayName("测试 LtWildcardParser - 小于查询")
    public void testLtWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("age", 60);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("age", 65);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->age_lt]", 65)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 age<65 的记录");
    }

    @Test
    @DisplayName("测试 InWildcardParser - 在列表中查询（列表参数）")
    public void testInWildcardParserWithList() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("status", "active");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("status", "pending");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("status", "deleted");
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->status_in]", Arrays.asList("active", "pending", "approved"))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(2, count, "应该查询到两条 status 在列表中的记录");
    }

    @Test
    @DisplayName("测试 InWildcardParser - 在列表中查询（单个值）")
    public void testInWildcardParserWithSingleValue() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("id", "123");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("id", "456");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->id_in]", "123")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 id=123 的记录");
    }

    @Test
    @DisplayName("测试 NotInWildcardParser - 不在列表中查询")
    public void testNotInWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("status", "active");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("status", "deleted");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("status", "archived");
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->status_nin]", Arrays.asList("deleted", "archived"))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 status 不在列表中的记录");
    }

    @Test
    @DisplayName("测试 EqnWildcardParser - 等于 null 查询")
    public void testEqnWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据 - 一条有 deletedTime，一条没有
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test1");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        // 不设置 deletedTime，表示 null
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "test2");
        data2.put("deletedTime", Instant.now().getEpochSecond());
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->deletedTime_eqn]", true)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 deletedTime 为 null 的记录");
    }

    @Test
    @DisplayName("测试 NenWildcardParser - 不等于 null 查询")
    public void testNenWildcardParser() {
        String indexName = generateTestIndexName();

        // 创建测试数据 - 一条有 name，一条没有
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test1");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        // 不设置 name，表示 null
        createTestData(indexName, data2);

        // 生成查询
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_nen]", true)
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 name 不为 null 的记录");
    }

    @Test
    @DisplayName("测试组合条件 - 多个字段的 And 条件")
    public void testCombinedAndConditions() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test");
        data1.put("age", 25);
        data1.put("status", "active");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "test");
        data2.put("age", 15);
        data2.put("status", "active");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("filter_[data->name_eq]", "test");
        filterMap.put("filter_[data->age_gte]", 18);
        filterMap.put("filter_[data->status_in]", Arrays.asList("active", "pending"));

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条满足所有条件的记录");
    }

    @Test
    @DisplayName("测试组合条件 - Or 条件")
    public void testCombinedOrConditions() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("username", "testuser");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("real_name", "other");
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        // 使用 _or_ 连接多个条件
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_eq]_or_[data->username_like]_or_[data->real_name_like]", "test")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条满足任一条件的记录");
    }

    @Test
    @DisplayName("测试多个字段的组合查询")
    public void testMultipleFieldsQuery() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test");
        data1.put("age", 25);
        data1.put("status", "active");
        data1.put("score", 75);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "test");
        data2.put("age", 25);
        data2.put("status", "deleted");
        data2.put("score", 50);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("filter_[data->name_eq]", "test");
        filterMap.put("filter_[data->age_between]", Arrays.asList(18, 65));
        filterMap.put("filter_[data->status_in]", Arrays.asList("active", "pending"));
        filterMap.put("filter_[data->score_gte]", 60);

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条满足所有条件的记录");
    }

    @Test
    @DisplayName("测试数字类型的范围查询")
    public void testNumericRangeQuery() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("price", 250.75);
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("price", 50.25);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("price", 600.00);
        data3.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data3);

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->price_between]", Arrays.asList(100.50, 500.99))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 price 在范围内的记录");
    }

    @Test
    @DisplayName("测试空条件映射")
    public void testEmptyConditionMap() {
        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Collections.emptyMap()
        );
        BoolQuery boolQuery = queryBuilder.build();

        Assertions.assertNotNull(boolQuery);
        // 空条件应该返回一个空的 bool 查询
        if (boolQuery.must() != null) {
            Assertions.assertTrue(boolQuery.must().isEmpty());
        }
    }

    @Test
    @DisplayName("测试字符串类型的比较查询")
    public void testStringComparisonQuery() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "def");
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "aaa");
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->name_gte]", "abc")
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 name>=abc 的记录");
    }

    @Test
    @DisplayName("测试日期类型的范围查询")
    public void testDateRangeQuery() {
        String indexName = generateTestIndexName();

        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 86400000); // +1 day
        Date beforeDate = new Date(System.currentTimeMillis() - 86400000); // -1 day

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("creationTime", new Date());
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("creationTime", beforeDate);
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(
                Map.of("filter_[data->creationTime_between]", Arrays.asList(CastUtils.convertValue(startDate, Long.class), CastUtils.convertValue(endDate, Long.class)))
        );

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条 creationTime 在范围内的记录");
    }

    @Test
    @DisplayName("测试不支持的 wildcard 条件异常")
    public void testUnsupportedWildcard() {
        Assertions.assertThrows(
                SystemException.class,
                () -> queryGenerator.createQueryWrapperFromMap(
                        Map.of("filter_[data->name_unsupported]", "test")
                )
        );
    }

    @Test
    @DisplayName("测试混合查询 - 包含多种查询类型")
    public void testMixedQueries() {
        String indexName = generateTestIndexName();

        // 创建测试数据
        Map<String, Object> data1 = new HashMap<>();
        data1.put("name", "test");
        data1.put("age", 25);
        data1.put("status", "active");
        data1.put("title", "keyword test");
        data1.put("score", 75);
        data1.put("created", Instant.now().getEpochSecond());
        // 不设置 deleted，表示 null
        data1.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "other");
        data2.put("age", 30);
        data2.put("status", "deleted");
        data2.put("title", "other content");
        data2.put("score", 50);
        data2.put("deleted", Instant.now().getEpochSecond());
        data2.put(RestResult.DEFAULT_TIMESTAMP_NAME, Instant.now().getEpochSecond());
        createTestData(indexName, data2);

        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("filter_[data->name_eq]", "test");
        filterMap.put("filter_[data->age_between]", Arrays.asList(18, 65));
        filterMap.put("filter_[data->status_in]", Arrays.asList("active", "pending"));
        filterMap.put("filter_[data->title_like]", "keyword");
        filterMap.put("filter_[data->score_gte]", 60);

        BoolQuery.Builder queryBuilder = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryBuilder, indexName);
        Assertions.assertEquals(1, count, "应该查询到一条满足所有混合条件的记录");
    }
}

package io.github.loncra.framework.mybatis.plus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.mybatis.plus.MybatisPlusQueryGenerator;
import io.github.loncra.framework.mybatis.plus.test.entity.AllTypeEntity;
import io.github.loncra.framework.mybatis.plus.test.service.AllTypeEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Mybatis-Plus 查询生成器测试类
 * <p>
 * 测试所有支持的 wildcard parser 的查询生成功能
 *
 * @author maurice.chen
 */
@SpringBootTest
@DisplayName("MybatisPlusQueryGenerator 测试")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MybatisPlusQueryGeneratorTest {

    private MybatisPlusQueryGenerator<AllTypeEntity> queryGenerator;

    @Autowired
    private AllTypeEntityService allTypeEntityService;

    @BeforeEach
    public void setUp() {
        queryGenerator = new MybatisPlusQueryGenerator<>();
    }

    /**
     * 执行查询并返回结果数量
     *
     * @param queryWrapper 查询包装器
     *
     * @return 查询结果数量
     */
    private long executeQuery(QueryWrapper<AllTypeEntity> queryWrapper) {
        return allTypeEntityService.count(queryWrapper);
    }

    @Test
    @DisplayName("测试 EqWildcardParser - 等于查询")
    public void testEqWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_eq]", "test");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(1, count, "应该只查询到一条 name=test 的记录");
    }

    @Test
    @DisplayName("测试 NeWildcardParser - 不等于查询")
    public void testNeWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[status_ne]", 0);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 5, "应该查询到多条 status!=0 的记录");
    }

    @Test
    @DisplayName("测试 LikeWildcardParser - 模糊查询")
    public void testLikeWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[title_like]", "test");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条包含 test 的记录");
    }

    @Test
    @DisplayName("测试 LikeLeftWildcardParser - 左模糊查询（以...开始）")
    public void testLikeLeftWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_rlike]", "test");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(3, count, "应该查询到三条以 test 结尾的记录");
    }

    @Test
    @DisplayName("测试 LikeRightWildcardParser - 右模糊查询（以...结束）")
    public void testLikeRightWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_llike]", "test");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(3, count, "应该查询到三条以 test 开头的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（单参数，使用 gte）")
    public void testBetweenWildcardParserWithSingleValue() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[age_between]", Collections.singletonList(18));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 age>=18 的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（双参数，使用 gte 和 lte）")
    public void testBetweenWildcardParserWithTwoValues() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[age_between]", Arrays.asList(18, 35));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 18<=age<=35 的记录");
    }

    @Test
    @DisplayName("测试 BetweenWildcardParser - 范围查询（多参数，使用第一个和最后一个）")
    public void testBetweenWildcardParserWithMultipleValues() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[score_between]", Arrays.asList(60.0, 70.0, 80.0, 90.0));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 60<=score<=90 的记录");
    }

    @Test
    @DisplayName("测试 GteWildcardParser - 大于等于查询")
    public void testGteWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[age_gte]", 18);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 age>=18 的记录");
    }

    @Test
    @DisplayName("测试 GtWildcardParser - 大于查询")
    public void testGtWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[age_gt]", 18);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 age>18 的记录");
    }

    @Test
    @DisplayName("测试 LteWildcardParser - 小于等于查询")
    public void testLteWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[age_lte]", 25);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 age<=25 的记录");
    }

    @Test
    @DisplayName("测试 LtWildcardParser - 小于查询")
    public void testLtWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[age_lt]", 25);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 age<25 的记录");
    }

    @Test
    @DisplayName("测试 InWildcardParser - 在列表中查询（列表参数）")
    public void testInWildcardParserWithList() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[name_in]", Arrays.asList("test", "other", "active"));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 2, "应该查询到至少两条 name 在列表中的记录");
    }

    @Test
    @DisplayName("测试 InWildcardParser - 在列表中查询（单个值）")
    public void testInWildcardParserWithSingleValue() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[id_in]", 1);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(1, count, "应该查询到一条 id=1 的记录");
    }

    @Test
    @DisplayName("测试 NotInWildcardParser - 不在列表中查询")
    public void testNotInWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[name_nin]", Arrays.asList("deleted", "archived"));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 name 不在列表中的记录");
    }

    @Test
    @DisplayName("测试 EqnWildcardParser - 等于 null 查询")
    public void testEqnWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[deleted_time_eqn]", true);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 deleted_time 为 null 的记录");
    }

    @Test
    @DisplayName("测试 NenWildcardParser - 不等于 null 查询")
    public void testNenWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_nen]", true);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 name 不为 null 的记录");
    }

    @Test
    @DisplayName("测试 JsonContainsWildcardParser - JSON 包含查询")
    public void testJsonContainsWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[device->os_jin]", "windows");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 验证生成的 SQL 是否包含 JSON_CONTAINS 函数
        String targetSql = queryWrapper.getTargetSql();
        Assertions.assertTrue(targetSql.contains("JSON_CONTAINS"), "SQL 应该包含 JSON_CONTAINS 函数");
        Assertions.assertTrue(targetSql.contains("JSON_ARRAY"), "SQL 应该包含 JSON_ARRAY 函数");
        Assertions.assertTrue(targetSql.contains("device"), "SQL 应该包含 device 字段");
    }

    @Test
    @DisplayName("测试 JsonEqWildcardParser - JSON 等于查询")
    public void testJsonEqWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[device->city.name_jeq]", "nanning");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 验证生成的 SQL 是否包含 JSON 路径查询
        String targetSql = queryWrapper.getTargetSql();
        Assertions.assertTrue(targetSql.contains("device"), "SQL 应该包含 device 字段");
        Assertions.assertTrue(targetSql.contains("city"), "SQL 应该包含 city 路径");
        Assertions.assertTrue(targetSql.contains("name"), "SQL 应该包含 name 路径");
        Assertions.assertTrue(targetSql.contains("->"), "SQL 应该包含 JSON 操作符 ->");
    }

    @Test
    @DisplayName("测试 JsonSearchOneWildcardParser - JSON 搜索单个值")
    public void testJsonSearchOneWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[device->os_jso]", "windows");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 验证生成的 SQL 是否包含 JSON_SEARCH 函数
        String targetSql = queryWrapper.getTargetSql();
        Assertions.assertTrue(targetSql.contains("JSON_SEARCH"), "SQL 应该包含 JSON_SEARCH 函数");
        Assertions.assertTrue(targetSql.contains("'one'"), "SQL 应该包含 'one' 参数");
        Assertions.assertTrue(targetSql.contains("IS NOT NULL"), "SQL 应该包含 IS NOT NULL 条件");
        Assertions.assertTrue(targetSql.contains("device"), "SQL 应该包含 device 字段");
    }

    @Test
    @DisplayName("测试 JsonSearchAllWildcardParser - JSON 搜索所有值")
    public void testJsonSearchAllWildcardParser() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[device->os_jsa]", "windows");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 验证生成的 SQL 是否包含 JSON_SEARCH 函数
        String targetSql = queryWrapper.getTargetSql();
        Assertions.assertTrue(targetSql.contains("JSON_SEARCH"), "SQL 应该包含 JSON_SEARCH 函数");
        Assertions.assertTrue(targetSql.contains("'all'"), "SQL 应该包含 'all' 参数");
        Assertions.assertTrue(targetSql.contains("IS NOT NULL"), "SQL 应该包含 IS NOT NULL 条件");
        Assertions.assertTrue(targetSql.contains("device"), "SQL 应该包含 device 字段");
    }

    @Test
    @DisplayName("测试组合条件 - 多个字段的 And 条件")
    public void testCombinedAndConditions() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_eq]", "test");
        filterMap.add("filter_[age_gte]", 18);
        filterMap.add("filter_[status_in]", List.of(1));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(1, count, "应该查询到一条满足所有条件的记录");
    }

    @Test
    @DisplayName("测试组合条件 - Or 条件")
    public void testCombinedOrConditions() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_eq]_or_[name_like]_or_[title_like]", "test");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条满足任一条件的记录");
    }

    @Test
    @DisplayName("测试多个字段的组合查询")
    public void testMultipleFieldsQuery() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_eq]", "test");
        filterMap.put("filter_[age_between]", Arrays.asList(18, 35));
        filterMap.put("filter_[status_in]", List.of(1));
        filterMap.add("filter_[score_gte]", new BigDecimal("60"));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertEquals(1, count, "应该查询到一条满足所有条件的记录");
    }

    @Test
    @DisplayName("测试数字类型的范围查询")
    public void testNumericRangeQuery() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[price_between]", Arrays.asList(new BigDecimal("100.50"), new BigDecimal("500.99")));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 price 在范围内的记录");
    }

    @Test
    @DisplayName("测试空条件映射")
    public void testEmptyConditionMap() {
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        Assertions.assertNotNull(queryWrapper);
        // 空条件应该返回所有记录
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 0, "空条件应该能正常执行查询");
    }

    @Test
    @DisplayName("测试字符串类型的比较查询")
    public void testStringComparisonQuery() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_gte]", "abc");

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 name>=abc 的记录");
    }

    @Test
    @DisplayName("测试日期类型的范围查询")
    public void testDateRangeQuery() {

        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.put("filter_[creation_time_between]", Arrays.asList(LocalDate.of(2024, 1, 4), LocalDate.of(2024, 1, 5)));

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 1, "应该查询到至少一条 creation_time 在范围内的记录");
    }

    @Test
    @DisplayName("测试不支持的 wildcard 条件异常")
    public void testUnsupportedWildcard() {
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_unsupported]", "test");

        Assertions.assertThrows(
                SystemException.class,
                () -> queryGenerator.createQueryWrapperFromMap(filterMap)
        );
    }

    @Test
    @DisplayName("测试混合查询 - 包含多种查询类型")
    public void testMixedQueries() {
        // 生成查询
        MultiValueMap<String, Object> filterMap = new LinkedMultiValueMap<>();
        filterMap.add("filter_[name_eq]", "test");
        filterMap.put("filter_[age_between]", Arrays.asList(18, 35));
        filterMap.put("filter_[status_in]", List.of(1));
        filterMap.add("filter_[title_like]", "keyword");
        filterMap.add("filter_[score_gte]", new BigDecimal("60"));
        filterMap.add("filter_[deleted_time_eqn]", true);

        QueryWrapper<AllTypeEntity> queryWrapper = queryGenerator.createQueryWrapperFromMap(filterMap);

        // 执行查询并验证结果
        long count = executeQuery(queryWrapper);
        Assertions.assertTrue(count >= 0, "应该能正常执行混合查询");
    }
}


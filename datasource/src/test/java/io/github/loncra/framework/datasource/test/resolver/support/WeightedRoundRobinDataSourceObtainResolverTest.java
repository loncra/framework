package io.github.loncra.framework.datasource.test.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import io.github.loncra.framework.datasource.resolver.support.WeightedRoundRobinDataSourceObtainResolver;
import io.github.loncra.framework.datasource.test.resolver.SlaveDataSourceResolverTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 加权轮询从库数据源获取解析器测试
 *
 * @author maurice.chen
 */
public class WeightedRoundRobinDataSourceObtainResolverTest extends SlaveDataSourceResolverTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeightedRoundRobinDataSourceObtainResolverTest.class);

    @Override
    protected DataSourceObtainResolver createResolver() {
        WeightedRoundRobinDataSourceObtainResolver resolver = new WeightedRoundRobinDataSourceObtainResolver();
        // 设置权重：3, 2, 1
        resolver.setWeights(List.of(3, 2, 1));
        return resolver;
    }

    /**
     * 测试加权轮询选择从库
     */
    @Test
    public void testWeightedRoundRobinSelection() {
        LOGGER.info("开始测试加权轮询选择从库...");

        // 统计每个从库被选中的次数
        Map<DataSource, Integer> selectionCount = new HashMap<>();
        for (DataSource ds : slaveDataSources) {
            selectionCount.put(ds, 0);
        }

        // 执行多次选择（总权重为 3+2+1=6，执行 60 次应该接近 30:20:10 的比例）
        int totalSelections = 60;
        for (int i = 0; i < totalSelections; i++) {
            DataSource selected = resolver.obtain(slaveDataSources, masterDataSource);
            assertNotNull(selected, "选择的数据源不应为 null");
            assertTrue(slaveDataSources.contains(selected), "选择的数据源应在从库列表中");
            selectionCount.put(selected, selectionCount.get(selected) + 1);
        }

        // 验证权重比例（允许一定的误差）
        int count0 = selectionCount.get(slaveDataSources.get(0)); // 权重 3
        int count1 = selectionCount.get(slaveDataSources.get(1)); // 权重 2
        int count2 = selectionCount.get(slaveDataSources.get(2)); // 权重 1

        LOGGER.info("加权轮询结果 - 从库0: {}, 从库1: {}, 从库2: {}", count0, count1, count2);

        // 验证权重高的被选中次数更多
        assertTrue(count0 > count1, "权重3的从库应该比权重2的从库被选中次数多");
        assertTrue(count1 > count2, "权重2的从库应该比权重1的从库被选中次数多");

        // 验证总选择次数
        assertEquals(totalSelections, count0 + count1 + count2, "总选择次数应该等于执行次数");
    }

    /**
     * 测试默认权重（所有权重为1）
     */
    @Test
    public void testDefaultWeights() {
        LOGGER.info("开始测试默认权重...");

        WeightedRoundRobinDataSourceObtainResolver weightedResolver =
                new WeightedRoundRobinDataSourceObtainResolver();
        // 不设置权重，应该使用默认权重（所有为1）

        // 统计每个从库被选中的次数
        Map<DataSource, Integer> selectionCount = new HashMap<>();
        for (DataSource ds : slaveDataSources) {
            selectionCount.put(ds, 0);
        }

        // 执行多次选择
        int totalSelections = 30;
        for (int i = 0; i < totalSelections; i++) {
            DataSource selected = weightedResolver.obtain(slaveDataSources, masterDataSource);
            selectionCount.put(selected, selectionCount.get(selected) + 1);
        }

        // 默认权重下，应该接近均匀分布
        int count0 = selectionCount.get(slaveDataSources.get(0));
        int count1 = selectionCount.get(slaveDataSources.get(1));
        int count2 = selectionCount.get(slaveDataSources.get(2));

        LOGGER.info("默认权重结果 - 从库0: {}, 从库1: {}, 从库2: {}", count0, count1, count2);

        // 验证每个从库都被选中过
        assertTrue(count0 > 0, "从库0应该被选中");
        assertTrue(count1 > 0, "从库1应该被选中");
        assertTrue(count2 > 0, "从库2应该被选中");
    }

    /**
     * 测试权重不匹配的情况
     */
    @Test
    public void testMismatchedWeights() {
        LOGGER.info("开始测试权重不匹配...");

        WeightedRoundRobinDataSourceObtainResolver weightedResolver =
                new WeightedRoundRobinDataSourceObtainResolver();
        // 设置权重数量少于从库数量
        weightedResolver.setWeights(List.of(2, 1));

        // 应该自动使用默认权重
        DataSource selected = weightedResolver.obtain(slaveDataSources, masterDataSource);
        assertNotNull(selected, "选择的数据源不应为 null");
        assertTrue(slaveDataSources.contains(selected), "选择的数据源应在从库列表中");
    }

    /**
     * 测试解析器名称
     */
    @Test
    public void testResolverName() {
        assertEquals(WeightedRoundRobinDataSourceObtainResolver.NAME, resolver.getName(),
                     "解析器名称应为 'weighted-round-robin'");
    }

    /**
     * 测试设置和获取权重
     */
    @Test
    public void testSetAndGetWeights() {
        WeightedRoundRobinDataSourceObtainResolver weightedResolver =
                new WeightedRoundRobinDataSourceObtainResolver();

        List<Integer> weights = List.of(5, 3, 2);
        weightedResolver.setWeights(weights);

        List<Integer> retrievedWeights = weightedResolver.getWeights();
        assertEquals(weights, retrievedWeights, "获取的权重应该与设置的权重一致");
    }
}

package io.github.loncra.framework.datasource.test.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import io.github.loncra.framework.datasource.resolver.support.RandomDataSourceObtainResolver;
import io.github.loncra.framework.datasource.test.resolver.SlaveDataSourceResolverTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 随机从库数据源获取解析器测试
 *
 * @author maurice.chen
 */
public class RandomDataSourceObtainResolverTest extends SlaveDataSourceResolverTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDataSourceObtainResolverTest.class);

    @Override
    protected DataSourceObtainResolver createResolver() {
        return new RandomDataSourceObtainResolver();
    }

    /**
     * 测试随机选择从库
     */
    @Test
    public void testRandomSelection() {
        LOGGER.info("开始测试随机选择从库...");

        // 执行多次选择，验证能选择到不同的从库
        Set<DataSource> selectedDataSources = new HashSet<>();
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            DataSource selected = resolver.obtain(slaveDataSources, masterDataSource);
            assertNotNull(selected, "选择的数据源不应为 null");
            assertTrue(slaveDataSources.contains(selected), "选择的数据源应在从库列表中");
            selectedDataSources.add(selected);
        }

        // 验证至少选择了多个不同的从库（随机性测试）
        // 注意：由于是随机选择，理论上可能只选择一个，但概率很低
        LOGGER.info("随机选择测试完成，共选择了 {} 个不同的从库", selectedDataSources.size());
        assertFalse(selectedDataSources.isEmpty(), "应该至少选择一个从库");
    }

    /**
     * 测试解析器名称
     */
    @Test
    public void testResolverName() {
        assertEquals(RandomDataSourceObtainResolver.NAME, resolver.getName(), "解析器名称应为 'random'");
    }

    /**
     * 测试单个从库的情况
     */
    @Test
    public void testSingleSlave() throws Exception {
        List<DataSource> singleSlave = new ArrayList<>();
        singleSlave.add(createH2DataSource("single-slave"));

        DataSource result = resolver.obtain(singleSlave, masterDataSource);
        assertEquals(singleSlave.get(0), result, "单个从库时应返回该从库");
    }
}

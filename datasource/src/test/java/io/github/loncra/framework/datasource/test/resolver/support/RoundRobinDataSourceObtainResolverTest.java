package io.github.loncra.framework.datasource.test.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import io.github.loncra.framework.datasource.resolver.support.RoundRobinDataSourceObtainResolver;
import io.github.loncra.framework.datasource.test.resolver.SlaveDataSourceResolverTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 轮询从库数据源获取解析器测试
 *
 * @author maurice.chen
 */
public class RoundRobinDataSourceObtainResolverTest extends SlaveDataSourceResolverTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoundRobinDataSourceObtainResolverTest.class);

    @Override
    protected DataSourceObtainResolver createResolver() {
        return new RoundRobinDataSourceObtainResolver();
    }

    /**
     * 测试轮询选择从库
     */
    @Test
    public void testRoundRobinSelection() {
        LOGGER.info("开始测试轮询选择从库...");

        // 验证轮询顺序
        int size = slaveDataSources.size();
        List<DataSource> selectedOrder = new ArrayList<>();

        // 执行多轮轮询
        for (int i = 0; i < size * 3; i++) {
            DataSource selected = resolver.obtain(slaveDataSources, masterDataSource);
            assertNotNull(selected, "选择的数据源不应为 null");
            assertTrue(slaveDataSources.contains(selected), "选择的数据源应在从库列表中");
            selectedOrder.add(selected);
        }

        // 验证轮询顺序：每 size 个选择应该包含所有从库
        for (int round = 0; round < 3; round++) {
            int start = round * size;
            int end = start + size;
            List<DataSource> roundSelection = selectedOrder.subList(start, end);

            // 验证这一轮包含了所有从库
            assertEquals(size, new java.util.HashSet<>(roundSelection).size(),
                         "每轮应该选择所有不同的从库");

            LOGGER.debug("第 {} 轮选择: {}", round + 1, roundSelection);
        }

        LOGGER.info("轮询选择测试完成");
    }

    /**
     * 测试解析器名称
     */
    @Test
    public void testResolverName() {
        assertEquals(RoundRobinDataSourceObtainResolver.NAME, resolver.getName(),
                     "解析器名称应为 'round-robin'");
    }

    /**
     * 测试单个从库的情况
     */
    @Test
    public void testSingleSlave() throws Exception {
        List<DataSource> singleSlave = new ArrayList<>();
        singleSlave.add(createH2DataSource("single-slave"));

        // 多次选择应该总是返回同一个从库
        DataSource first = resolver.obtain(singleSlave, masterDataSource);
        DataSource second = resolver.obtain(singleSlave, masterDataSource);
        DataSource third = resolver.obtain(singleSlave, masterDataSource);

        assertEquals(first, second, "单个从库时应该总是返回同一个");
        assertEquals(second, third, "单个从库时应该总是返回同一个");
    }

    /**
     * 测试并发轮询
     */
    @Test
    public void testConcurrentRoundRobin() throws InterruptedException {
        LOGGER.info("开始测试并发轮询...");

        int threadCount = 10;
        int selectionsPerThread = 20;
        List<Thread> threads = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = getThread(i, selectionsPerThread, exceptions);
            threads.add(thread);
        }

        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        // 验证没有异常
        assertTrue(exceptions.isEmpty(),
                   "并发测试应该没有异常，但发现 " + exceptions.size() + " 个异常: " + exceptions);

        LOGGER.info("并发轮询测试完成，{} 个线程全部成功", threadCount);
    }

    private Thread getThread(
            int i,
            int selectionsPerThread,
            List<Exception> exceptions
    ) {
        final int threadId = i;
        return new Thread(() -> {
            try {
                for (int j = 0; j < selectionsPerThread; j++) {
                    DataSource selected = resolver.obtain(slaveDataSources, masterDataSource);
                    assertNotNull(selected, "线程 " + threadId + " 选择的数据源不应为 null");
                    assertTrue(slaveDataSources.contains(selected),
                               "线程 " + threadId + " 选择的数据源应在从库列表中");
                }
            }
            catch (Exception e) {
                synchronized (exceptions) {
                    exceptions.add(e);
                }
            }
        });
    }
}

package io.github.loncra.framework.datasource.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询从库数据源获取解析器
 * <p>
 * 使用轮询算法按顺序从从库列表中获取数据源，实现负载均衡
 *
 * @author maurice.chen
 */
public class RoundRobinDataSourceObtainResolver implements DataSourceObtainResolver {

    /**
     * 解析器名称
     */
    public static final String NAME = "round-robin";

    /**
     * 当前索引（线程安全）
     */
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public DataSource obtain(
            List<DataSource> slaveDataSources,
            DataSource masterDataSource
    ) {
        if (CollectionUtils.isEmpty(slaveDataSources)) {
            return masterDataSource;
        }

        int size = slaveDataSources.size();
        // 使用 CAS 操作获取下一个索引，避免并发问题
        int index = currentIndex.getAndUpdate(i -> (i + 1) % size);
        return slaveDataSources.get(index);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

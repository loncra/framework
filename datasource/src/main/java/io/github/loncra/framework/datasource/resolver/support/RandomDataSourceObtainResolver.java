package io.github.loncra.framework.datasource.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机从库数据源获取解析器
 * <p>
 * 使用随机算法从从库列表中获取一个数据源
 *
 * @author maurice.chen
 */
public class RandomDataSourceObtainResolver implements DataSourceObtainResolver {

    /**
     * 解析器名称
     */
    public static final String NAME = "random";

    @Override
    public DataSource obtain(
            List<DataSource> slaveDataSources,
            DataSource masterDataSource
    ) {
        if (CollectionUtils.isEmpty(slaveDataSources)) {
            return masterDataSource;
        }
        int index = ThreadLocalRandom.current().nextInt(slaveDataSources.size());
        return slaveDataSources.get(index);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

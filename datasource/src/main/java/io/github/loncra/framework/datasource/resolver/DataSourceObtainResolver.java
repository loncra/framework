package io.github.loncra.framework.datasource.resolver;

import javax.sql.DataSource;
import java.util.List;

/**
 * 从库数据源获取解析器接口
 * <p>
 * 用于从多个从库数据源中选择一个数据源，支持不同的负载均衡策略
 *
 * @author maurice.chen
 */
public interface DataSourceObtainResolver {

    /**
     * 从从库数据源列表中获取一个数据源
     *
     * @param slaveDataSources 从库数据源列表
     * @param masterDataSource 主库数据源（当从库列表为空时返回）
     *
     * @return 获取的数据源
     */
    DataSource obtain(
            List<DataSource> slaveDataSources,
            DataSource masterDataSource
    );

    /**
     * 获取解析器名称
     *
     * @return 解析器名称
     */
    String getName();
}

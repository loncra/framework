package io.github.loncra.framework.datasource.resolver.support;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 加权轮询从库数据源获取解析器
 * <p>
 * 根据数据源的权重进行轮询获取，权重越高的数据源被选中的概率越大
 * <p>
 * 使用平滑加权轮询算法（Smooth Weighted Round-Robin）：
 * 1. 每个节点有两个权重：固定权重（weight）和当前权重（current）
 * 2. 每次获取时：
 * - 将所有节点的当前权重加上固定权重
 * - 选择当前权重最大的节点
 * - 将选中节点的当前权重减去总权重
 * <p>
 * 使用示例：
 * <pre>
 * WeightedRoundRobinSlaveDataSourceObtainResolver resolver = new WeightedRoundRobinSlaveDataSourceObtainResolver();
 * resolver.setWeights(List.of(3, 2, 1)); // 设置三个从库的权重分别为 3、2、1
 * </pre>
 *
 * @author maurice.chen
 */
public class WeightedRoundRobinDataSourceObtainResolver implements DataSourceObtainResolver {

    /**
     * 解析器名称
     */
    public static final String NAME = "weighted-round-robin";

    /**
     * 权重列表（与从库数据源列表一一对应）
     */
    private List<Integer> weights = new ArrayList<>();

    /**
     * 当前权重数组（每个数据源对应一个当前权重）
     */
    private volatile AtomicIntegerArray currentWeights;

    /**
     * 默认构造函数
     */
    public WeightedRoundRobinDataSourceObtainResolver() {
    }

    /**
     * 构造函数
     *
     * @param weights 权重列表
     */
    public WeightedRoundRobinDataSourceObtainResolver(List<Integer> weights) {
        this.weights = weights != null ? new ArrayList<>(weights) : new ArrayList<>();
    }

    @Override
    public DataSource obtain(
            List<DataSource> slaveDataSources,
            DataSource masterDataSource
    ) {
        if (CollectionUtils.isEmpty(slaveDataSources)) {
            return masterDataSource;
        }

        int size = slaveDataSources.size();

        // 如果权重列表为空或长度不匹配，使用默认权重（所有数据源权重为1）
        if (weights.isEmpty() || weights.size() != size) {
            weights = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                weights.add(1);
            }
        }

        // 初始化当前权重数组（如果未初始化或大小不匹配）
        if (currentWeights == null || currentWeights.length() != size) {
            currentWeights = new AtomicIntegerArray(size);
        }

        // 平滑加权轮询算法
        // 1. 计算总权重
        int totalWeight = weights.stream().mapToInt(Integer::intValue).sum();

        // 2. 将所有节点的当前权重加上固定权重，并找到最大权重
        int maxCurrentWeight = Integer.MIN_VALUE;
        int selectedIndex = 0;

        for (int i = 0; i < size; i++) {
            // 当前权重加上固定权重
            int newCurrentWeight = currentWeights.addAndGet(i, weights.get(i));

            // 找到当前权重最大的节点
            if (newCurrentWeight > maxCurrentWeight) {
                maxCurrentWeight = newCurrentWeight;
                selectedIndex = i;
            }
        }

        // 3. 将选中节点的当前权重减去总权重
        currentWeights.addAndGet(selectedIndex, -totalWeight);

        return slaveDataSources.get(selectedIndex);
    }

    /**
     * 设置权重列表
     *
     * @param weights 权重列表
     */
    public void setWeights(List<Integer> weights) {
        this.weights = weights != null ? new ArrayList<>(weights) : new ArrayList<>();
        // 重置当前权重数组
        currentWeights = null;
    }

    /**
     * 获取权重列表
     *
     * @return 权重列表
     */
    public List<Integer> getWeights() {
        return new ArrayList<>(weights);
    }

    @Override
    public String getName() {
        return NAME;
    }
}

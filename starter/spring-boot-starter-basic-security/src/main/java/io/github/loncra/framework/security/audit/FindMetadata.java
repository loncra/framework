package io.github.loncra.framework.security.audit;

import java.time.Instant;
import java.util.Map;

/**
 * 查询元数据
 *
 * @param <T> 目标查询类型
 *
 * @author maurice.chen
 */
public class FindMetadata<T> {

    /**
     * 目标查询对象
     */
    private T targetQuery;

    /**
     * 存储定位
     */
    private String storagePositioning;

    /**
     * 查询条件
     */
    private Map<String, Object> query;

    /**
     * 时间范围（在此时间之后）
     */
    private Instant after;

    /**
     * 构造函数
     */
    public FindMetadata() {
    }

    /**
     * 构造函数
     *
     * @param targetQuery        目标查询对象
     * @param storagePositioning 存储定位
     * @param query              查询条件
     * @param after              时间范围（在此时间之后）
     */
    public FindMetadata(
            T targetQuery,
            String storagePositioning,
            Map<String, Object> query,
            Instant after
    ) {
        this.targetQuery = targetQuery;
        this.storagePositioning = storagePositioning;
        this.query = query;
        this.after = after;
    }

    /**
     * 获取目标查询对象
     *
     * @return 目标查询对象
     */
    public T getTargetQuery() {
        return targetQuery;
    }

    /**
     * 设置目标查询对象
     *
     * @param targetQuery 目标查询对象
     */
    public void setTargetQuery(T targetQuery) {
        this.targetQuery = targetQuery;
    }

    /**
     * 获取存储定位
     *
     * @return 存储定位
     */
    public String getStoragePositioning() {
        return storagePositioning;
    }

    /**
     * 设置存储定位
     *
     * @param storagePositioning 存储定位
     */
    public void setStoragePositioning(String storagePositioning) {
        this.storagePositioning = storagePositioning;
    }

    /**
     * 获取查询条件
     *
     * @return 查询条件
     */
    public Map<String, Object> getQuery() {
        return query;
    }

    /**
     * 设置查询条件
     *
     * @param query 查询条件
     */
    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    /**
     * 获取时间范围（在此时间之后）
     *
     * @return 时间范围
     */
    public Instant getAfter() {
        return after;
    }

    /**
     * 设置时间范围（在此时间之后）
     *
     * @param after 时间范围
     */
    public void setAfter(Instant after) {
        this.after = after;
    }
}

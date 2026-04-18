package io.github.loncra.framework.security.audit;

import java.time.Instant;
import java.util.Map;

/**
 * 审计事件查询拦截器
 *
 * @param <T> 查询对象类型
 *
 * @author maurice.chen
 */
public interface AuditEventRepositoryQueryInterceptor<T> {

    /**
     * 执行查询审计事件前触发
     *
     * @param after 时间范围（在此时间之后）
     * @param query 查询条件
     *
     * @return 返回 false 时，将不对审计事件进行查询
     */
    default boolean preFind(
            Instant after,
            Map<String, Object> query
    ) {
        return true;
    }

    /**
     * 创建查询对象后触发
     *
     * @param findMetadata 查询元数据
     */
    default void postCreateQuery(FindMetadata<T> findMetadata) {

    }

    /**
     * 执行统计审计事件前触发
     *
     * @param after 时间范围（在此时间之后）
     * @param query 查询条件
     *
     * @return 返回 false 时，将不对审计事件进行统计
     */
    default boolean preCount(
            Instant after,
            Map<String, Object> query
    ) {
        return true;
    }
}

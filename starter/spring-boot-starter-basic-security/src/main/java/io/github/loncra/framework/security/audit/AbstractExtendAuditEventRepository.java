package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.IdEntity;
import io.github.loncra.framework.commons.page.Page;
import io.github.loncra.framework.commons.page.PageRequest;
import io.github.loncra.framework.commons.page.TotalPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

/**
 * 抽象扩展审计事件仓库实现
 *
 * @param <T> 查询对象类型
 *
 * @author maurice.chen
 */
public abstract class AbstractExtendAuditEventRepository<T> implements ExtendAuditEventRepository {

    /**
     * 审计事件仓库拦截器列表
     */
    private final List<AuditEventRepositoryWriteInterceptor> writeInterceptors;

    private final List<AuditEventRepositoryQueryInterceptor<T>> queryInterceptors;

    /**
     * 构造函数
     *
     * @param writeInterceptors 审计事件仓库拦截器列表
     */
    public AbstractExtendAuditEventRepository(
            List<AuditEventRepositoryWriteInterceptor> writeInterceptors,
            List<AuditEventRepositoryQueryInterceptor<T>> queryInterceptors
    ) {
        this.writeInterceptors = writeInterceptors;
        this.queryInterceptors = queryInterceptors;
    }

    @Override
    public void add(AuditEvent event) {

        for (AuditEventRepositoryWriteInterceptor interceptor : writeInterceptors) {
            if (!interceptor.preAddHandle(event)) {
                return;
            }
        }

        // 统一将 data 转换为 map，让一下没有构造函数的对象可以反序列化
        Map<String, Object> data = CastUtils.convertValue(event.getData(), CastUtils.MAP_TYPE_REFERENCE);
        Field field = ReflectionUtils.findField(event.getClass(), RestResult.DEFAULT_DATA_NAME);
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            ReflectionUtils.setField(field, event, data);
        }
        doAdd(event);

        writeInterceptors.forEach(i -> i.postAddHandle(event));
    }

    /**
     * 执行添加审计事件操作
     *
     * @param event 审计事件
     */
    protected abstract void doAdd(AuditEvent event);

    @Override
    public List<AuditEvent> find(
            String principal,
            Instant after,
            String type
    ) {
        Map<String, Object> query = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(principal)) {
            query.put(IdAuditEvent.PRINCIPAL_FIELD_NAME, principal);
        }
        if (StringUtils.isNotBlank(type)) {
            query.put(IdAuditEvent.TYPE_FIELD_NAME, type);
        }

        return find(after, query);
    }

    @Override
    public Page<AuditEvent> findPage(
            PageRequest pageRequest,
            Instant after,
            Map<String, Object> query
    ) {
        query.put(PageRequest.NUMBER_FIELD_NAME, pageRequest.getNumber() - 1);
        query.put(PageRequest.SIZE_FIELD_NAME, pageRequest.getSize());

        List<AuditEvent> content = find(after, query);
        long count = count(after, query);

        return new TotalPage<>(pageRequest, content, count);
    }

    @Override
    public long count(
            Instant after,
            Map<String, Object> query
    ) {
        for (AuditEventRepositoryQueryInterceptor<T> interceptor : queryInterceptors) {
            if (!interceptor.preCount(after, query)) {
                return 0;
            }
        }

        T targetQuery = createQuery(after, query);
        FindMetadata<T> findMetadata = createFindEntity(targetQuery, after, query);

        queryInterceptors.forEach(interceptor -> interceptor.postCreateQuery(findMetadata));

        return doCount(findMetadata);
    }

    /**
     * 执行统计操作
     *
     * @param findMetadata 查询元数据
     *
     * @return 统计数量
     */
    protected abstract long doCount(FindMetadata<T> findMetadata);

    /**
     * 创建查询对象
     *
     * @param after 时间范围（在此时间之后）
     * @param query 查询条件
     *
     * @return 查询对象
     */
    protected abstract T createQuery(
            Instant after,
            Map<String, Object> query
    );

    @Override
    public List<AuditEvent> find(
            Instant after,
            Map<String, Object> query
    ) {
        for (AuditEventRepositoryQueryInterceptor<T> interceptor : queryInterceptors) {
            if (!interceptor.preFind(after, query)) {
                return new LinkedList<>();
            }
        }

        T targetQuery = createQuery(after, query);
        FindMetadata<T> findMetadata = createFindEntity(targetQuery, after, query);

        queryInterceptors.forEach(interceptor -> interceptor.postCreateQuery(findMetadata));

        return doFind(findMetadata);
    }

    /**
     * 创建查询元数据实体
     *
     * @param targetQuery 目标查询对象
     * @param after       时间范围（在此时间之后）
     * @param query       查询条件
     *
     * @return 查询元数据
     */
    protected abstract FindMetadata<T> createFindEntity(
            T targetQuery,
            Instant after,
            Map<String, Object> query
    );

    /**
     * 执行查询操作
     *
     * @param entity 查询元数据
     *
     * @return 审计事件列表
     */
    protected abstract List<AuditEvent> doFind(FindMetadata<T> entity);

    /**
     * 创建审计事件
     *
     * @param map map 数据源
     *
     * @return 审计事件
     */
    protected AuditEvent createAuditEvent(Map<String, Object> map) {
        Object timestamp = map.get(RestResult.DEFAULT_TIMESTAMP_NAME);

        Instant instant;
        switch (timestamp) {
            case Date date -> instant = date.toInstant();
            case Instant i -> instant = i;
            case Long l -> instant = Instant.ofEpochMilli(l);
            case String s -> instant = Instant.parse(s);
            case null, default ->
                    throw new SystemException("找不到 " + RestResult.DEFAULT_TIMESTAMP_NAME + " 的数据转换支持");
        }

        String principal = map.get(IdAuditEvent.PRINCIPAL_FIELD_NAME).toString();
        String type = map.get(IdAuditEvent.TYPE_FIELD_NAME).toString();

        Map<String, Object> data = CastUtils.cast(map.getOrDefault(RestResult.DEFAULT_DATA_NAME, new LinkedHashMap<>()));
        String id = map.getOrDefault(IdEntity.ID_FIELD_NAME, StringUtils.EMPTY).toString();

        return new IdAuditEvent(id, instant, principal, type, data);
    }
}

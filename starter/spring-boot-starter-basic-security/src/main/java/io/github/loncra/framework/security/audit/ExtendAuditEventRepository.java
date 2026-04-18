package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.commons.page.Page;
import io.github.loncra.framework.commons.page.PageRequest;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * 插件审计事件的仓库实现
 *
 * @author maurice.chen
 */
public interface ExtendAuditEventRepository extends AuditEventRepository {

    /**
     * 获取分页信息
     *
     * @param pageRequest 分页请求
     * @param after       时间范围（在改至之后的所有数据）
     * @param query       查询内容
     *
     * @return 分页信息
     */
    Page<AuditEvent> findPage(
            PageRequest pageRequest,
            Instant after,
            Map<String, Object> query
    );

    /**
     * 统计数量
     *
     * @param after 时间范围（在改至之后的所有数据）
     * @param query 查询内容
     *
     * @return 统计数
     */
    long count(
            Instant after,
            Map<String, Object> query
    );

    /**
     * 获取查询信息
     *
     * @param after 时间范围（在改至之后的所有数据）
     * @param query 查询内容
     *
     * @return 审计时间结合
     */
    List<AuditEvent> find(
            Instant after,
            Map<String, Object> query
    );

    /**
     * 通过唯一识别获取数据
     *
     * @param idEntity 唯一识别;
     *
     * @return 审计事件
     */
    AuditEvent get(StringIdEntity idEntity);
}

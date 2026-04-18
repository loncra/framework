package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.id.BasicIdentification;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.io.Serial;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

/**
 * 审计事件实体类
 *
 * @author maurice
 */
public class IdAuditEvent extends AuditEvent implements BasicIdentification<String> {

    @Serial
    private static final long serialVersionUID = 8633684304971875621L;

    /**
     * 当事人字段名称
     */
    public static final String PRINCIPAL_FIELD_NAME = "principal";

    /**
     * 类型字段名称
     */
    public static final String TYPE_FIELD_NAME = "type";

    /**
     * 主键 id
     */
    private String id;

    /**
     * 构造函数
     *
     * @param auditEvent 审计事件
     */
    public IdAuditEvent(AuditEvent auditEvent) {
        super(auditEvent.getTimestamp(), auditEvent.getPrincipal(), auditEvent.getType(), auditEvent.getData());
        String id = UUID.randomUUID().toString();
        if (auditEvent instanceof BasicIdentification<?> idAuditEvent) {
            id = idAuditEvent.getId().toString();
        }
        this.id = id;
    }

    /**
     * 构造函数
     *
     * @param id         主键 id
     * @param auditEvent 审计事件
     */
    public IdAuditEvent(
            String id,
            AuditEvent auditEvent
    ) {
        this(id, auditEvent.getTimestamp(), auditEvent.getPrincipal(), auditEvent.getType(), auditEvent.getData());
    }

    /**
     * 构造函数（自动生成 id）
     *
     * @param principal 当事人
     * @param type      审计类型
     * @param data      数据
     */
    public IdAuditEvent(
            String principal,
            String type,
            Map<String, Object> data
    ) {
        this(UUID.randomUUID().toString(), Instant.now(), principal, type, data);
    }

    /**
     * 构造函数（使用当前时间）
     *
     * @param id        主键 id
     * @param principal 当事人
     * @param type      审计类型
     * @param data      数据
     */
    public IdAuditEvent(
            String id,
            String principal,
            String type,
            Map<String, Object> data
    ) {
        this(id, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant(), principal, type, data);
    }

    /**
     * 构造函数
     *
     * @param id        主键 id
     * @param timestamp 时间戳
     * @param principal 当事人
     * @param type      审计类型
     * @param data      数据
     */
    public IdAuditEvent(
            String id,
            Instant timestamp,
            String principal,
            String type,
            Map<String, Object> data
    ) {
        super(timestamp, principal, type, data);
        this.id = id;
    }

    /**
     * 获取主键 id
     *
     * @return 主键 id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 设置主键 id
     *
     * @param id 主键 id
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

}

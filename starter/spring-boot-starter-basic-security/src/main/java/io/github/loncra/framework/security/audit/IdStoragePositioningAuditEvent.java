package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.id.BasicIdentification;

import java.time.Instant;
import java.util.Map;

/**
 * 带 id 的 存储指定位置的审计事件
 *
 * @author maurice.chen
 */
public class IdStoragePositioningAuditEvent extends StoragePositioningAuditEvent implements BasicIdentification<String> {

    /**
     * 主键 id
     */
    private String id;

    /**
     * 构造函数
     *
     * @param id         主键 id
     * @param auditEvent 存储定位审计事件
     */
    public IdStoragePositioningAuditEvent(
            String id,
            StoragePositioningAuditEvent auditEvent
    ) {
        this(
                id,
                auditEvent.getStoragePositioning(),
                auditEvent.getTimestamp(),
                auditEvent.getPrincipal(),
                auditEvent.getType(),
                auditEvent.getData()
        );
    }

    /**
     * 构造函数（使用当前时间）
     *
     * @param id                 主键 id
     * @param storagePositioning 存储定位
     * @param principal          当事人
     * @param type               审计类型
     * @param data               数据
     */
    public IdStoragePositioningAuditEvent(
            String id,
            String storagePositioning,
            String principal,
            String type,
            Map<String, Object> data
    ) {
        this(id, storagePositioning, Instant.now(), principal, type, data);
    }

    /**
     * 构造函数
     *
     * @param id                 主键 id
     * @param storagePositioning 存储定位
     * @param timestamp          时间戳
     * @param principal          当事人
     * @param type               审计类型
     * @param data               数据
     */
    public IdStoragePositioningAuditEvent(
            String id,
            String storagePositioning,
            Instant timestamp,
            String principal,
            String type,
            Map<String, Object> data
    ) {
        super(storagePositioning, timestamp, principal, type, data);
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

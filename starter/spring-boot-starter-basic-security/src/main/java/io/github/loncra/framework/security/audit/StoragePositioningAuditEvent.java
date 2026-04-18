package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchAuditEventRepository;
import io.github.loncra.framework.security.audit.mongo.MongoAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.io.Serial;
import java.time.Instant;
import java.util.Map;

/**
 * 存储指定位置的审计事件，用于根据 storagePositioning 修改默认存储位置的审计事件
 *
 * @author maurice.chen
 * @see ElasticsearchAuditEventRepository#doAdd(AuditEvent)
 * @see MongoAuditEventRepository#doAdd(AuditEvent)
 */
public class StoragePositioningAuditEvent extends AuditEvent {

    @Serial
    private static final long serialVersionUID = -2637890771981260326L;

    /**
     * 存储定位字段名称
     */
    public static final String STORAGE_POSITIONING_FIELD = "storagePositioning";

    /**
     * 存储定位
     */
    private final String storagePositioning;

    /**
     * 构造函数
     *
     * @param storagePositioning 存储定位
     * @param auditEvent         审计事件
     */
    public StoragePositioningAuditEvent(
            String storagePositioning,
            AuditEvent auditEvent
    ) {
        this(
                storagePositioning,
                auditEvent.getTimestamp(),
                auditEvent.getPrincipal(),
                auditEvent.getType(),
                auditEvent.getData()
        );
    }


    /**
     * 构造函数
     *
     * @param storagePositioning 存储定位
     * @param timestamp          时间戳
     * @param principal          当事人
     * @param type               审计类型
     * @param data               数据
     */
    public StoragePositioningAuditEvent(
            String storagePositioning,
            Instant timestamp,
            String principal,
            String type,
            Map<String, Object> data
    ) {
        super(timestamp, principal, type, data);
        this.storagePositioning = storagePositioning;
    }

    /**
     * 获取存储定位
     *
     * @return 存储定位
     */
    public String getStoragePositioning() {
        return storagePositioning;
    }
}

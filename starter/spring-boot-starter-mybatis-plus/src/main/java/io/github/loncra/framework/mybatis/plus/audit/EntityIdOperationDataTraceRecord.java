package io.github.loncra.framework.mybatis.plus.audit;

import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;

import java.io.Serial;
import java.time.Instant;

/**
 * 带实体 id 的操作数据留痕记录
 *
 * @author maurice.chen
 */
public class EntityIdOperationDataTraceRecord extends OperationDataTraceRecord {

    @Serial
    private static final long serialVersionUID = 7972904701408013089L;

    /**
     * 实体 id
     */
    private Object entityId;

    /**
     * 构造函数
     */
    public EntityIdOperationDataTraceRecord() {
        super();
    }

    /**
     * 构造函数
     *
     * @param creationTime 创建时间
     */
    public EntityIdOperationDataTraceRecord(Instant creationTime) {
        super(creationTime);
    }

    /**
     * 获取实体 id
     *
     * @return 实体 id
     */
    public Object getEntityId() {
        return entityId;
    }

    /**
     * 设置实体 id
     *
     * @param entityId 实体 id
     */
    public void setEntityId(Object entityId) {
        this.entityId = entityId;
    }
}

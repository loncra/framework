package io.github.loncra.framework.spring.security.core.entity;

import io.github.loncra.framework.mybatis.plus.audit.EntityIdOperationDataTraceRecord;

import java.io.Serial;
import java.time.Instant;

/**
 * 用户明细操作数据留痕记录
 *
 * @author maurice.chen
 */
public class SecurityPrincipalOperationDataTraceRecord extends EntityIdOperationDataTraceRecord {

    @Serial
    private static final long serialVersionUID = 3211452737634539720L;

    /**
     * 目标类型
     */
    private String controllerAuditType;

    public SecurityPrincipalOperationDataTraceRecord() {
        super();
    }

    public SecurityPrincipalOperationDataTraceRecord(
            String id,
            Instant creationTime
    ) {
        super(creationTime);
    }

    /**
     * 获取审计类型
     *
     * @return 审计类型
     */
    public String getControllerAuditType() {
        return controllerAuditType;
    }

    /**
     * 设置审计类型
     *
     * @param controllerAuditType 审计类型
     */
    public void setControllerAuditType(String controllerAuditType) {
        this.controllerAuditType = controllerAuditType;
    }

}

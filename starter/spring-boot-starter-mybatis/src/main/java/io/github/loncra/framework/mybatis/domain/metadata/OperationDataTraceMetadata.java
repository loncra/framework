package io.github.loncra.framework.mybatis.domain.metadata;

import io.github.loncra.framework.mybatis.enumerate.OperationDataType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 操作数据留痕的业务元数据，嵌套在 {@link io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord#getData()} 中。
 * <p>与安全侧控制器审计合并时，整段 metadata 以 {@link #OPERATION_DATA_TRACE_DATA_FIELD} 为键写入 {@code AuditEvent.data}。</p>
 * <p>{@link #remark} 可选；框架不在 {@link io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceRepository} 内自动填充，
 * 由 {@code @Auditable} / {@code @OperationDataTrace} 或 {@link io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecordHook} 按需设置。</p>
 *
 * @author maurice.chen
 * @see io.github.loncra.framework.mybatis.plus.audit.EntityIdOperationDataTraceMetadata
 */
public class OperationDataTraceMetadata implements Serializable {

    /**
     * 写入 {@code AuditEvent.data} 时 DB 留痕载荷的键名（替代旧 {@code submitData} / {@code SUBMIT_DATA_FIELD}）。
     */
    public static final String OPERATION_DATA_TRACE_DATA_FIELD = "operationTrace";

    /**
     * 操作目标
     */
    private String target;

    /**
     * 数据信息
     */
    private Map<String, Object> data = new LinkedHashMap<>();

    /**
     * 参数数据类型
     */
    private OperationDataType type;

    /**
     * 标注
     */
    private String remark;

    public OperationDataTraceMetadata() {
    }

    /**
     * 获取操作目标
     *
     * @return 操作目标
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置操作目标
     *
     * @param target 操作目标
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 获取提交的数据
     *
     * @return 提交的数据
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 设置提交的数据
     *
     * @param data 提交的数据
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * 获取操作数据类型
     *
     * @return 操作数据类型
     */
    public OperationDataType getType() {
        return type;
    }

    /**
     * 设置操作数据类型
     *
     * @param type 操作数据类型
     */
    public void setType(OperationDataType type) {
        this.type = type;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}

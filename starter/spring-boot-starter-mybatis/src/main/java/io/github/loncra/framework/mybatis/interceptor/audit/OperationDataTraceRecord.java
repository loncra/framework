package io.github.loncra.framework.mybatis.interceptor.audit;

import io.github.loncra.framework.mybatis.enumerate.OperationDataType;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 操作数据留痕记录
 *
 * @author maurice.chen
 */
public class OperationDataTraceRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1987280604707609834L;

    /**
     * 提交数据字段名称
     */
    public static final String SUBMIT_DATA_FIELD = "submitData";

    /**
     * 备注字段名称
     */
    public static final String REMARK_FIELD = "remark";

    /**
     * 创建时间
     */
    private Instant creationTime;

    /**
     * 操作人信息
     */
    private Object principal;

    /**
     * 操作目标
     */
    private String target;

    /**
     * 提交的数据
     */
    private Map<String, Object> submitData = new LinkedHashMap<>();

    /**
     * 参数数据类型
     */
    private OperationDataType type;

    /**
     * 存储定位
     */
    private String storagePositioning;

    /**
     * 标注
     */
    private String remark;

    /**
     * 构造函数
     */
    public OperationDataTraceRecord() {
        creationTime = Instant.now();
    }

    /**
     * 构造函数
     *
     * @param creationTime 创建时间
     */
    public OperationDataTraceRecord(Instant creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Instant getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取操作人信息
     *
     * @return 操作人信息
     */
    public Object getPrincipal() {
        return principal;
    }

    /**
     * 设置操作人信息
     *
     * @param principal 操作人信息
     */
    public void setPrincipal(Object principal) {
        this.principal = principal;
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
    public Map<String, Object> getSubmitData() {
        return submitData;
    }

    /**
     * 设置提交的数据
     *
     * @param submitData 提交的数据
     */
    public void setSubmitData(Map<String, Object> submitData) {
        this.submitData = submitData;
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
}

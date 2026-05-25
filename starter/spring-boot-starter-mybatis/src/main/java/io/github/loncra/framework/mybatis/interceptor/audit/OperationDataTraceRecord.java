package io.github.loncra.framework.mybatis.interceptor.audit;

import io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * 操作数据留痕记录
 *
 * @author maurice.chen
 */
public class OperationDataTraceRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1987280604707609834L;
    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 操作人信息
     */
    private Object principal;

    /**
     * 存储定位
     */
    private String storagePositioning;

    /**
     * 审计数据信息
     */
    private OperationDataTraceMetadata data;

    /**
     * 构造函数
     */
    public OperationDataTraceRecord() {

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

    /**
     * 获取审计数据信息
     *
     * @return 审计数据信息
     */
    public OperationDataTraceMetadata getData() {
        return data;
    }

    /**
     * 设置审计数据信息
     *
     * @param data 审计数据信息
     */
    public void setData(OperationDataTraceMetadata data) {
        this.data = data;
    }
}

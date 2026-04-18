package io.github.loncra.framework.commons.id.metadata;

import java.io.Serial;

/**
 * 带值和记录的元数据信息
 *
 * @author maurice.chen
 */
public class IdValueRecordMetadata<T, V> extends IdValueMetadata<T, V> {

    @Serial
    private static final long serialVersionUID = -7703726594978304705L;

    /**
     * 记录信息
     */
    private Object record;

    /**
     * 获取记录信息
     *
     * @return 记录信息
     */
    public Object getRecord() {
        return record;
    }

    /**
     * 设置记录信息
     *
     * @param record 记录信息
     */
    public void setRecord(Object record) {
        this.record = record;
    }
}

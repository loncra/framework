package io.github.loncra.framework.commons.id.number;

import io.github.loncra.framework.commons.id.BasicIdentification;

import java.time.Instant;

/**
 * 数字值的主键实体接口
 *
 * @author maurice.chen
 */
public interface NumberIdEntity<T extends Number> extends BasicIdentification<T> {

    /**
     * 创建时间字段名称
     */
    String CREATION_TIME_FIELD_NAME = "creationTime";

    /**
     * 获取主键 id
     *
     * @return 主键 id
     */
    @Override
    T getId();

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    Instant getCreationTime();

}

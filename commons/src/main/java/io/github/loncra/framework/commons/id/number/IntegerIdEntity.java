package io.github.loncra.framework.commons.id.number;

import io.github.loncra.framework.commons.id.IdEntity;

import java.io.Serial;
import java.time.Instant;

/**
 * 整型主键实体
 *
 * @author maurice.chen
 */
public class IntegerIdEntity extends IdEntity<Integer> implements NumberIdEntity<Integer> {

    @Serial
    private static final long serialVersionUID = 6284036190187423322L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 创建一个整型主键实体
     */
    public IntegerIdEntity() {
    }

    /**
     * 创建一个整型主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     */
    public IntegerIdEntity(
            Integer id,
            Instant creationTime
    ) {
        super(id);
        this.creationTime = creationTime;
    }

    @Override
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
     * 创建一个整型主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     */
    public static IntegerIdEntity of(
            Integer id,
            Instant creationTime
    ) {
        return new IntegerIdEntity(id, creationTime);
    }
}

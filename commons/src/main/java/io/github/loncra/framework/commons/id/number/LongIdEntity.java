package io.github.loncra.framework.commons.id.number;

import io.github.loncra.framework.commons.id.IdEntity;

import java.time.Instant;

/**
 * 长整型主键实体
 *
 * @author maurice.chen
 */
public class LongIdEntity extends IdEntity<Long> implements NumberIdEntity<Long> {
    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 创建一个长整型主键实体
     */
    public LongIdEntity() {
    }

    /**
     * 创建一个长整型主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     */
    public LongIdEntity(
            Long id,
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
     * 创建一个长整型主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     *
     * @return 长整型主键实体
     */
    public static LongIdEntity of(
            Long id,
            Instant creationTime
    ) {
        return new LongIdEntity(id, creationTime);
    }
}

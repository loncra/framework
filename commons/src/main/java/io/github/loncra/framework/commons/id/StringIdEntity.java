package io.github.loncra.framework.commons.id;


import java.io.Serial;
import java.time.Instant;

/**
 * 字符串的 id 主键实体
 *
 * @author maurice.chen
 */
public class StringIdEntity extends IdEntity<String> {

    @Serial
    private static final long serialVersionUID = 6774769809276207267L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 创建一个字符串的 id 主键实体
     */
    public StringIdEntity() {
    }

    /**
     * 创建字符串的 id 主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     */
    public StringIdEntity(
            String id,
            Instant creationTime
    ) {
        super(id);
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
     * 创建字符串的 id 主键实体
     *
     * @param id           主键 id
     * @param creationTime 创建时间
     */
    public static StringIdEntity of(
            String id,
            Instant creationTime
    ) {
        return new StringIdEntity(id, creationTime);
    }
}

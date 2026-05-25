package io.github.loncra.framework.mybatis.plus.audit;

import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata;

import java.io.Serial;

/**
 * 带实体主键的操作数据留痕元数据，用于 MyBatis-Plus 从 {@link io.github.loncra.framework.commons.id.BasicIdentification} 提取 {@code id} 后写入 SpEL 分桶等场景。
 * <p>由 {@link MybatisPlusOperationDataTraceRepository} 通过 {@link io.github.loncra.framework.commons.CastUtils#of} 从
 * {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata} 拷贝并 {@link #setId(Object)}，须保持 metadata 类型一致。</p>
 *
 * @author maurice.chen
 */
public class EntityIdOperationDataTraceMetadata extends OperationDataTraceMetadata implements BasicIdentification<Object> {

    @Serial
    private static final long serialVersionUID = 7972904701408013089L;

    private Object id;

    /**
     * 构造函数
     */
    public EntityIdOperationDataTraceMetadata() {
        super();
    }


    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }
}

package io.github.loncra.framework.commons.id.metadata;

import java.io.Serial;

/**
 * id 名称 值元数据
 *
 * @param <T> id 类型
 * @param <V> 值类型
 *
 * @author maurice.chen
 */
public class IdNameValueMetadata<T, V> extends IdValueMetadata<T, V> {

    @Serial
    private static final long serialVersionUID = 8246309636011894220L;

    /**
     * 名称
     */
    private String name;

    /**
     * 构造函数
     */
    public IdNameValueMetadata() {
    }

    /**
     * 构造函数
     *
     * @param name 名称
     */
    public IdNameValueMetadata(String name) {
        this.name = name;
    }

    /**
     * 构造函数
     *
     * @param id    id 值
     * @param name  名称
     * @param value 值
     */
    public IdNameValueMetadata(
            T id,
            String name,
            V value
    ) {
        super(id, value);
        this.name = name;
    }

    /**
     * 构造函数
     *
     * @param metadata id 值元数据
     * @param name     名称
     */
    public IdNameValueMetadata(
            IdValueMetadata<T, V> metadata,
            String name
    ) {
        super(metadata.getId(), metadata.getValue());
        this.name = name;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }
}

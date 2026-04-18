package io.github.loncra.framework.commons.id.metadata;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.id.IdEntity;

import java.io.Serial;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 带值的 id 元数据
 *
 * @author maurice.chen
 */
public class IdValueMetadata<T, V> extends IdEntity<T> {

    @Serial
    private static final long serialVersionUID = -8885126404039341575L;

    /**
     * 值字段名称
     */
    public static final String VALUE_FIELD_NAME = "value";

    /**
     * 值
     */
    private V value;

    /**
     * 元数据信息
     */
    private Map<String, Object> metadata = new LinkedHashMap<>();

    /**
     * 构造函数
     */
    public IdValueMetadata() {
    }

    /**
     * 构造函数
     *
     * @param id    id 值
     * @param value 值
     */
    public IdValueMetadata(
            T id,
            V value
    ) {
        super(id);
        this.value = value;
    }

    /**
     * 创建带值的 id 元数据
     *
     * @param id    id 值
     * @param value 值
     * @param <T>   id 类型
     * @param <V>   值类型
     *
     * @return 带值的 id 元数据
     */
    public static <T, V> IdValueMetadata<T, V> of(
            T id,
            V value
    ) {
        IdValueMetadata<T, V> result = new IdValueMetadata<>();
        result.setId(id);
        result.setValue(value);

        return result;
    }

    /**
     * 将 Map 转换为 id 值元数据列表或保持原 Map 格式
     *
     * @param data          源 Map 数据
     * @param idValueFormat 是否转换为 id 值格式，true 转换为列表，false 保持原 Map
     * @param <T>           id 类型
     * @param <V>           值类型
     *
     * @return 转换后的数据
     */
    public static <T, V> Object ofMap(
            Map<T, V> data,
            boolean idValueFormat
    ) {
        if (idValueFormat) {
            List<IdValueMetadata<T, V>> listValue = new LinkedList<>();
            data.forEach((k, v) -> listValue.add(IdValueMetadata.of(k, v)));
            return listValue;
        }

        return data;
    }

    /**
     * 将 id 值元数据列表转换为 Map 或保持原列表格式
     *
     * @param data          源列表数据
     * @param idValueFormat 是否保持 id 值格式，true 保持列表，false 转换为 Map
     * @param <T>           id 类型
     * @param <V>           值类型
     *
     * @return 转换后的数据
     */
    public static <T, V> Object ofList(
            List<IdValueMetadata<T, V>> data,
            boolean idValueFormat
    ) {
        if (idValueFormat) {
            return data.stream().map(d -> CastUtils.of(d, IdValueMetadata.class)).collect(Collectors.toList());
        }

        Map<T, V> result = new LinkedHashMap<>();
        data.forEach(meta -> result.put(meta.getId(), meta.getValue()));

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        IdValueMetadata<?, ?> that = (IdValueMetadata<?, ?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public V getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * 获取元数据信息
     *
     * @return 元数据信息
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * 设置元数据信息
     *
     * @param metadata 元数据信息
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

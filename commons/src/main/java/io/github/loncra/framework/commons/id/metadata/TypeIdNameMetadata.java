package io.github.loncra.framework.commons.id.metadata;

import io.github.loncra.framework.commons.CacheProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 带类型的 id 名称元数据
 *
 * @author maurice.chen
 */
public class TypeIdNameMetadata extends IdNameMetadata {

    /**
     * 类型字段名称
     */
    public static final String TYPE_FIELD_NAME = "type";

    @Serial
    private static final long serialVersionUID = -5958623464505187650L;

    /**
     * 类型
     */
    private String type;

    /**
     * 构造函数
     */
    public TypeIdNameMetadata() {
    }

    /**
     * 获取类型
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 创建带类型的 id 名称元数据
     *
     * @param id   id 值
     * @param name 名称
     * @param type 类型
     *
     * @return 带类型的 id 名称元数据
     */
    public static TypeIdNameMetadata of(
            String id,
            String name,
            String type
    ) {
        TypeIdNameMetadata result = new TypeIdNameMetadata();

        result.setName(name);
        result.setId(id);
        result.setType(type);

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
        TypeIdNameMetadata that = (TypeIdNameMetadata) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }

    public String toPrincipalName() {
        return Stream.of(type, getId())
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(CacheProperties.DEFAULT_SEPARATOR));
    }

    public String toPrincipalFullName() {
        return Stream.of(type, getId(), getName())
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(CacheProperties.DEFAULT_SEPARATOR));
    }
}

package io.github.loncra.framework.commons.id.metadata;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.commons.id.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.io.Serial;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 带名称的 id 元数据
 *
 * @author maurice.chen
 */
public class IdNameMetadata extends IdEntity<String> {

    /**
     * 名称字段名称
     */
    public static final String NAME_FIELD_NAME = "name";

    @Serial
    private static final long serialVersionUID = -4127420686530448230L;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建带名称的 id 元数据
     *
     * @param id   id 值
     * @param name 名称
     *
     * @return 带名称的 id 元数据
     */
    public static IdNameMetadata of(
            String id,
            String name
    ) {
        IdNameMetadata result = new IdNameMetadata();
        result.setId(id);
        result.setName(name);
        return result;
    }

    /**
     * 从主体字符串创建类型 id 名称元数据
     * <p>主体字符串格式：type:id:name 或 type:id</p>
     *
     * @param principalString 主体字符串，格式为 type:id:name 或 type:id
     *
     * @return 类型 id 名称元数据
     */
    public static TypeIdNameMetadata ofPrincipalString(String principalString) {

        String[] split = StringUtils.splitByWholeSeparator(principalString, CacheProperties.DEFAULT_SEPARATOR);
        Assert.isTrue(split.length >= 2, "Invalid user string");

        TypeIdNameMetadata result = new TypeIdNameMetadata();

        result.setType(split[0]);
        result.setId(split[1]);

        if (split.length > 2) {
            result.setName(split[2]);
        }

        return result;
    }

    /**
     * 从 BasicIdentification 对象创建带名称的 id 元数据
     *
     * @param source 源对象
     *
     * @return 带名称的 id 元数据
     */
    public static IdNameMetadata of(BasicIdentification<String> source) {
        return of(source, NameEnum.FIELD_NAME);
    }

    /**
     * 从 BasicIdentification 对象创建带名称的 id 元数据
     *
     * @param source       源对象
     * @param nameProperty 名称属性字段名
     *
     * @return 带名称的 id 元数据
     */
    public static IdNameMetadata of(
            BasicIdentification<String> source,
            String nameProperty
    ) {
        IdNameMetadata result = new IdNameMetadata();
        result.setId(source.getId());

        Object nameValue = null;
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(nameProperty, source.getClass());
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                readMethod.setAccessible(true);
                nameValue = ReflectionUtils.invokeMethod(readMethod, source);
            }
        }
        catch (Exception e) {
            // 如果通过 getter 方法获取失败，尝试直接获取字段值
        }

        if (Objects.isNull(nameValue)) {
            Field field = ReflectionUtils.findField(source.getClass(), nameProperty);
            if (Objects.nonNull(field)) {
                field.setAccessible(true);
                nameValue = ReflectionUtils.getField(field, source);
            }
        }

        if (Objects.nonNull(nameValue)) {
            result.setName(nameValue.toString());
        }

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
        IdNameMetadata that = (IdNameMetadata) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
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

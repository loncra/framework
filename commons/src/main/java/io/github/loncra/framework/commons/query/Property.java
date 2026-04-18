package io.github.loncra.framework.commons.query;

import io.github.loncra.framework.commons.CastUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 属性信息，用户记录对应字段名称和值信息
 *
 * @author maurice.chen
 */
public class Property {

    /**
     * 别名
     */
    private String alias = StringUtils.EMPTY;

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 值
     */
    private Object value;

    /**
     * 创建一个属性对象
     *
     * @param propertyName 属性名称
     */
    public Property(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 创建一个属性对象
     *
     * @param propertyName 属性名称
     * @param value        属性值
     */
    public Property(
            String propertyName,
            Object value
    ) {
        this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * 创建一个属性对象
     *
     * @param alias        别名
     * @param propertyName 属性名称
     * @param value        属性值
     */
    public Property(
            String alias,
            String propertyName,
            Object value
    ) {
        this.alias = alias;
        this.propertyName = propertyName;
        this.value = value;
    }

    /**
     * 获取属性名称
     *
     * @return 属性名称
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 设置属性名称
     *
     * @param propertyName 属性名称
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 获取最终的属性名称（包含别名）
     *
     * @return 如果存在别名则返回 "别名.属性名"，否则返回属性名
     */
    public String getFinalPropertyName() {
        if (StringUtils.isNotEmpty(alias)) {
            return alias + CastUtils.DOT + propertyName;
        }
        return propertyName;
    }

    /**
     * 获取条件值
     *
     * @return 条件值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置条件值
     *
     * @param value 条件值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取别名
     *
     * @return 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 拼接属性名称（包含别名）
     *
     * @param field 字段名
     *
     * @return 如果存在别名则返回 "别名.字段名"，否则返回字段名
     */
    public String splicePropertyName(String field) {
        if (StringUtils.isNotEmpty(alias)) {
            return alias + CastUtils.DOT + field;
        }
        return field;
    }
}

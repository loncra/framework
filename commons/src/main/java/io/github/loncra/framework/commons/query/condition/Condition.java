package io.github.loncra.framework.commons.query.condition;

import io.github.loncra.framework.commons.query.Property;

import java.io.Serial;
import java.io.Serializable;

/**
 * 条件信息, 用于记录一个条件里包含的过滤查询内容
 *
 * @author maurice.chen
 */
public class Condition implements Serializable {

    @Serial
    private static final long serialVersionUID = -8626527871073433205L;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private ConditionType type;

    /**
     * 属性
     */
    private Property property;

    /**
     * 创建一个条件对象
     */
    public Condition() {
    }

    /**
     * 创建一个条件对象
     *
     * @param name     条件名称
     * @param type     条件类型
     * @param property 属性
     */
    public Condition(
            String name,
            ConditionType type,
            Property property
    ) {
        this.name = name;
        this.type = type;
        this.property = property;
    }

    /**
     * 获取条件名称
     *
     * @return 条件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置条件名称
     *
     * @param name 条件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取条件类型
     *
     * @return 条件类型
     */
    public ConditionType getType() {
        return type;
    }

    /**
     * 设置条件类型
     *
     * @param type 条件类型
     */
    public void setType(ConditionType type) {
        this.type = type;
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public Property getProperty() {
        return property;
    }

    /**
     * 设置属性
     *
     * @param property 属性
     */
    public void setProperty(Property property) {
        this.property = property;
    }
}

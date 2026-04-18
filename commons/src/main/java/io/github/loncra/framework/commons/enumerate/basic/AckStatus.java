package io.github.loncra.framework.commons.enumerate.basic;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 确认应答状态实枚举
 *
 * @author maurice.chen
 */
public enum AckStatus implements NameValueEnum<Integer> {

    /**
     * 确认
     */
    ACKNOWLEDGED("确认", 10),

    /**
     * 拒绝
     */
    REJECT("拒绝", 20),

    /**
     * 忽略
     */
    NEGLECT("忽略", 30),

    /**
     * 未知
     */
    UNKNOWN("未知", 40),

    ;

    /**
     * 确认应答状态枚举
     *
     * @param name  名称
     * @param value 值
     */
    AckStatus(
            String name,
            Integer value
    ) {
        this.name = name;
        this.value = value;
    }

    /**
     * 名称
     */
    private final String name;

    /**
     * 值
     */
    private final Integer value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}

package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author maurice.chen
 */
public enum BankCardBindStatusEnum implements NameValueEnum<String> {
    /**
     * 已绑定
     */
    BOUND("0", "已绑定"),

    /**
     * 已解绑
     */
    UNBOUND("2", "已解绑"),

    ;

    BankCardBindStatusEnum(
            String value,
            String name
    ) {
        this.name = name;
        this.value = value;
    }

    private final String value;

    private final String name;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

}

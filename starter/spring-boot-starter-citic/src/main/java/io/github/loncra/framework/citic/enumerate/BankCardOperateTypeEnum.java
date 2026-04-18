package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum BankCardOperateTypeEnum implements NameValueEnum<String> {

    BIND("1", "绑定"),

    UNBIND("2", "解绑"),

    ;

    BankCardOperateTypeEnum(
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

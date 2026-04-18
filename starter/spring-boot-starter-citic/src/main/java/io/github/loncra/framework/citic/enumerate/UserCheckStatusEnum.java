package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum UserCheckStatusEnum implements NameValueEnum<String> {

    REGISTER_PENDING("1", "注册待审核"),
    MODIFY_PENDING("2", "修改待审核"),
    REGISTER_SUCCESS("3", "注册审核成功"),
    MODIFY_SUCCESS("4", "修改审核成功"),
    REGISTER_FAILED("5", "注册审核失败"),
    MODIFY_FAILED("6", "修改审核失败");

    UserCheckStatusEnum(
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

package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 用户类型枚举
 *
 * @author maurice.chen
 */
public enum UserTypeEnum implements NameValueEnum<String> {

    PERSON("个人", "1"),

    ENTERPRISE("企业", "2"),

    INDIVIDUAL_BUSINESS("个体工商户", "3"),

    ;

    UserTypeEnum(
            String name,
            String value
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

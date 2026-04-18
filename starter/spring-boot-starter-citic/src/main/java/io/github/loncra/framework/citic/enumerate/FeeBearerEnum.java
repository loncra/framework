package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;


/**
 * @author maurice.chen
 */
public enum FeeBearerEnum implements NameValueEnum<String> {

    PLATFORM_BEARER("1", "平台承担"),  // 手续费由平台支付
    USER_BEARER("2", "用户承担");


    FeeBearerEnum(
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

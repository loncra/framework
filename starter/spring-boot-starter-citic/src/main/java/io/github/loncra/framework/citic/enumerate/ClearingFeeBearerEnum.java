package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 清分文件渠道手续费承担方式
 *
 * @author maurice.chen
 */
public enum ClearingFeeBearerEnum implements NameValueEnum<String> {

    /**
     * 平台商户承担
     */
    PLATFORM_MERCHANT("1", "平台商户承担"),
    /**
     * 用户承担
     */
    USER("2", "用户承担"),
    ;

    ClearingFeeBearerEnum(
            String value,
            String name
    ) {
        this.value = value;
        this.name = name;
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

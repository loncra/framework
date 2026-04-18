package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;


/**
 * @author maurice.chen
 */
public enum WithdrawalTypeEnum implements NameValueEnum<String> {

    USER_WITHDRAWAL("00", "用户提现"),
    PLATFORM_WITHDRAWAL("01", "平台提现");


    WithdrawalTypeEnum(
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

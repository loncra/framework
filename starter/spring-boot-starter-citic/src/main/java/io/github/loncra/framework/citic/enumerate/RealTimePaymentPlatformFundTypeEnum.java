package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author maurice.chen
 */
public enum RealTimePaymentPlatformFundTypeEnum implements NameValueEnum<String> {
    /**
     * 平台优惠
     */
    PLATFORM_DISCOUNT("D", "平台优惠"),
    /**
     * 平台分成
     */
    PLATFORM_SHARE("C", "平台分成"),
    /**
     * 无资金动账
     */
    NO_ACCOUNT_MOVEMENT("N", "无资金动账");;

    RealTimePaymentPlatformFundTypeEnum(
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

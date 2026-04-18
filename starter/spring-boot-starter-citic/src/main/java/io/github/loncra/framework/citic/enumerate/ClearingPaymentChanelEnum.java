package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 清分文件清算资金来源
 *
 * @author maurice.chen
 */
public enum ClearingPaymentChanelEnum implements NameValueEnum<String> {

    /**
     * 支付渠道
     */
    PAYMENT_CHANNEL("1", "支付渠道"),
    /**
     * 内部划转
     */
    INTERNAL_TRANSFER("2", "内部划转"),

    ;

    ClearingPaymentChanelEnum(
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

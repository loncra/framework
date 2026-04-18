package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 清分文件支付订单交易类型
 *
 * @author maurice.chen
 */
public enum ClearingPaymentTransactionTypeEnum implements NameValueEnum<String> {

    /**
     * 支付
     */
    PAYMENT("1", "支付"),
    /**
     * 退货
     */
    REJECTED("2", "退货"),
    /**
     * 其他
     */
    OTHER(StringUtils.SPACE, "其他");

    ClearingPaymentTransactionTypeEnum(
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

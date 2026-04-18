package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 清分文件业务订单交易类型
 *
 * @author maurice.chen
 */
public enum ClearingBusinessTransactionTypeEnum implements NameValueEnum<String> {

    /**
     * 实时交易支付
     */
    REAL_TIME_PAYMENT("1", "实时交易支付"),
    /**
     * 实时交易退货
     */
    REAL_TIME_REFUND("2", "实时交易退货"),
    /**
     * 预付交易支付
     */
    PREPAID_PAYMENT("3", "预付交易支付"),
    /**
     * 预付交易撤销
     */
    PREPAID_CANCEL("4", "预付交易撤销"),
    /**
     * 预付交易完成
     */
    PREPAID_COMPLETION("5", "预付交易完成");

    ClearingBusinessTransactionTypeEnum(
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

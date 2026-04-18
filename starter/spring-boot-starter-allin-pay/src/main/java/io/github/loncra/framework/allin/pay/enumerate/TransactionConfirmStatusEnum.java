package io.github.loncra.framework.allin.pay.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 回调确认状态
 *
 * @author mauirce.chen
 */
public enum TransactionConfirmStatusEnum implements NameValueEnum<String> {
    /**
     * 交易等待
     */
    PENDING("-1", "交易等待"),

    /**
     * 交易失败
     */
    FAILED("1", "交易失败"),

    /**
     * 交易成功
     */
    SUCCESS("2", "交易成功");

    /**
     * 构造函数
     *
     * @param value 支付类型值
     * @param name  支付类型名称
     */
    TransactionConfirmStatusEnum(
            String value,
            String name
    ) {
        this.name = name;
        this.value = value;
    }

    /**
     * 支付类型值
     */
    private final String value;

    /**
     * 支付类型名称
     */
    private final String name;

    /**
     * 获取支付类型值
     *
     * @return 支付类型值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取支付类型名称
     *
     * @return 支付类型名称
     */
    @Override
    public String getName() {
        return name;
    }
}

package io.github.loncra.framework.allin.pay.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 支付类型枚举
 *
 * @author maurice.chen
 */
public enum PayTypeEnum implements NameValueEnum<String> {

    /**
     * 微信扫码支付
     */
    WECHAT_QR("W01", "微信扫码支付"),

    /**
     * 微信JS支付
     */
    WECHAT_JS("W02", "微信JS支付"),

    /**
     * 支付宝扫码支付
     */
    ALIPAY_QR("A01", "支付宝扫码支付"),

    /**
     * 支付宝JS支付
     */
    ALIPAY_JS("A02", "支付宝JS支付"),

    /**
     * 微信小程序支付
     */
    WECHAT_MINI_PROGRAM("W06", "微信小程序支付"),

    /**
     * 云闪付扫码支付(CSB)
     */
    UNION_PAY_QR("U01", "云闪付扫码支付(CSB)"),

    /**
     * 云闪付JS支付
     */
    UNION_PAY_JS("U02", "云闪付JS支付"),

    /**
     * 收银宝小程序收银台
     */
    CASHIER_MINI_PROGRAM("W06S", "收银宝小程序收银台"),

    /**
     * 微信订单预消费
     */
    WECHAT_PRE_CONSUMPTION("W11", "微信订单预消费");

    /**
     * 构造函数
     *
     * @param value 支付类型值
     * @param name  支付类型名称
     */
    PayTypeEnum(
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

package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 平台付款交易类型
 *
 * @author maurice.chen
 */
public enum PlatformPaymentTransactionTypeEnum implements NameValueEnum<String> {

    /**
     * 优惠补贴
     */
    SUBSIDY("00", "优惠补贴"),
    /**
     * 垫付
     */
    ADVANCE_PAYMENT("01", "垫付"),
    /**
     * 奖励
     */
    REWARD("02", "奖励"),
    /**
     * 其他
     */
    OTHER("03", "其他"),
    /**
     * 佣金返还
     */
    COMMISSION_REBATE("04", "佣金返还"),
    ;

    PlatformPaymentTransactionTypeEnum(
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

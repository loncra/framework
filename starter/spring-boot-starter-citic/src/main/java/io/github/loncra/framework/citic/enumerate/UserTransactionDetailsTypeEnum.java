package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 用户交易明细类型
 *
 * @author maurice.chen
 */
public enum UserTransactionDetailsTypeEnum implements NameValueEnum<String> {

    /**
     * 入金分账
     */
    INCOME_SPLIT("01", "入金分账"),
    /**
     * 交易划转
     */
    TRANSFER("02", "交易划转"),
    /**
     * "提现"
     */
    WITHDRAWAL("03", "提现"),
    /**
     * 提现手续费
     */
    WITHDRAWAL_FEE("04", "提现手续费"),
    /**
     * 提现退汇
     */
    WITHDRAWAL_REFUND("05", "提现退汇"),
    /**
     * 渠道来账
     */
    CHANNEL_INCOME("06", "渠道来账"),
    /**
     * 支付交易
     */
    PAYMENT("07", "支付交易"),
    /**
     * 退款交易
     */
    REFUND("08", "退款交易"),
    /**
     * 平台商户预付交易
     */
    ADVANCE_PAYMENT("09", "平台商户预付交易"),
    /**
     * 平台扣罚
     */
    PLATFORM_PENALTY("11", "平台扣罚"),
    /**
     * 平台补贴
     */
    PLATFORM_SUBSIDY("12", "平台补贴"),
    /**
     * 实时预清分
     */
    REAL_TIME_CLEARING("13", "实时预清分"),
    /**
     * 所有（返回明细类型）
     */
    ALL_DETAIL("98", "所有（返回明细类型）"),
    /**
     * 所有（返回汇总类型）
     */
    ALL_SUMMARY("99", "所有（返回汇总类型）"),

    ;

    UserTransactionDetailsTypeEnum(
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

package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum TransactionSummaryTypeEnum implements NameValueEnum<String> {

    INCOME_ALLOCATION("01", "入金分账"),
    TRANSACTION_TRANSFER("02", "交易划转"),
    WITHDRAWAL("03", "提现"),
    WITHDRAWAL_FEE("04", "提现手续费"),
    WITHDRAWAL_REFUND("05", "提现退汇"),
    CHANNEL_RECEIPT("06", "渠道来账"),
    PAYMENT_TRANSACTION("07", "支付交易"),
    REFUND_TRANSACTION("08", "退款交易"),
    PLATFORM_MERCHANT_PREPAY("09", "平台商户预付交易"),
    PLATFORM_PENALTY("11", "平台扣罚"),
    PLATFORM_SUBSIDY("12", "平台补贴"),
    REAL_TIME_PRE_CLEARING("13", "实时预清分"),

    OTHER("10", "其他"),
    ;

    TransactionSummaryTypeEnum(
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

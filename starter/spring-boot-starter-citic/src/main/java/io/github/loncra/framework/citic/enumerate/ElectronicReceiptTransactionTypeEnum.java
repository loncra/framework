package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author 回执单交易类型
 *
 */
public enum ElectronicReceiptTransactionTypeEnum implements NameValueEnum<String> {

    /**
     * 资金划转
     */
    FUNDS_TRANSFER("00", "资金划转"),
    CLEARING_DEPOSIT("03", "清分入金"),
    SMART_WITHDRAWAL("04", "智能提现"),
    TRANSFER_DEPOSIT("05", "转账入金"),
    ONLINE_PAYMENT("06", "联机支付"),
    ONLINE_REFUND("07", "联机退款"),
    BATCH_PAYMENT("08", "批量支付"),
    BATCH_REFUND("09", "批量退款"),
    CLEARING_601("10", "601清分"),
    REAL_TIME_ADVANCE("11", "实时预付"),
    PLATFORM_PAYMENT("12", "平台付款"),
    PLATFORM_RECEIPT("13", "平台收款"),
    BATCH_WITHDRAWAL("14", "批量提现");

    ElectronicReceiptTransactionTypeEnum(
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

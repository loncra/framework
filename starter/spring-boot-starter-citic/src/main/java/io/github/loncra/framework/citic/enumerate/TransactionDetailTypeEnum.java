package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author maurice.chen
 */
public enum TransactionDetailTypeEnum implements NameValueEnum<String> {

    PLATFORM_INTERNAL_TRANSFER("JJ01", "平台自营资金划转"),
    USER_WITHDRAWAL("JJ02", "用户提现"),
    PLATFORM_WITHDRAWAL("JJ03", "平台提现"),
    USER_REFUND("JJ04", "用户退汇"),
    PLATFORM_REFUND("JJ05", "平台退汇"),
    CLEARING_DEPOSIT("JJ06", "清分入金"),
    INCOMING_REGISTER("JJ07", "来帐登记"),
    WITHDRAWAL_FEE_CHARGE("JJ08", "提现手续费"),
    UNKNOWN_ACCOUNT_ADJUST_IN("JJ09", "不明来账调增登记簿"),
    INTERMEDIARY_FEE_SETTLEMENT("JJ10", "居间手续费结算"),
    SETTLEMENT_FEE_REFUND("JJ11", "待结算手续费清算回退"),
    DIGIT_ADJUST_IN("JJ12", "28位打款账号调增登记簿"),
    DIGIT_ADJUST_OUT("JJ13", "28位打款账号调减登记簿"),
    UNKNOWN_ACCOUNT_ADJUST_OUT("JJ15", "不明来账调减登记簿"),
    END_OF_DAY_TRANSFER("JJ16", "日终清分往自由资金转账"),
    UNKNOWN_ACCOUNT_MATCH("JJ17", "不明来账重新匹配"),
    DIGIT_PART_REFUND_ADJUST_OUT("JJ18", "28位部分退款调减登记簿"),
    DIGIT_PART_REFUND_FAIL_ADJUST_IN("JJ19", "28位部分退款失败/退汇调增登记簿"),
    BIND_CARD_REFUND_ADJUST_OUT("JJ20", "普通绑卡入金部分退款调减登记簿"),
    BIND_CARD_REFUND_FAIL_ADJUST_IN("JJ21", "普通绑卡入金部分退款失败/退汇调增登记簿"),
    INTEREST_BATCH("JJJX", "结息批量"),
    REGISTER_TRANSFER("JJZZ", "登记簿资金划转"),
    TRANSFER_DEPOSIT("JJRJ", "转账入金"),
    PAYMENT("JJXF", "支付"),
    REFUND("JJTK", "退款"),
    PLATFORM_SUBSIDY("JJHC", "平台商户补贴"),
    PLATFORM_PENALTY("JJHR", "平台商户扣罚"),
    PREPAY_TRANSACTION("JJYF", "预付交易"),
    PRE_CLEARING_PAYMENT("YQZF", "预清分支付"),
    PRE_CLEARING_CANCEL("YQCX", "预清分撤销"),
    PRE_CLEARING_REFUND("YQTK", "预清分退款"),
    PRE_CLEARING_WRITE_OFF("YQHX", "预清分核销"),

    ;

    TransactionDetailTypeEnum(
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

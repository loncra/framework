package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

import java.util.List;

public enum SearchUserBalanceTypeEnum implements NameValueEnum<String> {

    // 公共登记簿标识
    PUBLIC_REGISTER("00", "公共计息收费登记簿"),
    PUBLIC_ADJUSTMENT("01", "公共调账登记簿"),
    OWN_FUNDS_REGISTER("12", "自有资金登记簿"),
    GUARANTEE_REGISTER("13", "担保登记簿"),
    SETTLEMENT_FEE_REGISTER("17", "待结算手续费登记簿"),

    // 用户登记簿标识
    SUB_MERCHANT_REGISTER("14", "子商户/用户登记簿"),

    // 交易资金账户标识
    TRANSACTION_ACCOUNT("TA", "交易资金账户"),

    // 平台剩余透支额度标识
    PLATFORM_OVERDRAFT("RO_平台剩余透支额度", "平台剩余透支额度标识");;

    SearchUserBalanceTypeEnum(
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

    public static final List<SearchUserBalanceTypeEnum> MERCHANT_INNER_TYPES = List.of(PUBLIC_ADJUSTMENT, OWN_FUNDS_REGISTER, GUARANTEE_REGISTER, SETTLEMENT_FEE_REGISTER);

    public static final List<SearchUserBalanceTypeEnum> MERCHANT_TYPES = List.of(TRANSACTION_ACCOUNT, PUBLIC_ADJUSTMENT, OWN_FUNDS_REGISTER, GUARANTEE_REGISTER, SETTLEMENT_FEE_REGISTER);
}

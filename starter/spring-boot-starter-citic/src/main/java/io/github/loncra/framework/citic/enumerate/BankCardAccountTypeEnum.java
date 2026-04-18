package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum BankCardAccountTypeEnum implements NameValueEnum<String> {

    CITIC_PERSONAL("1", "中信个人账户"),
    CITIC_CORPORATE("2", "中信企业账户"),
    OTHER_BANK_PERSONAL("3", "他行个人账户"),
    OTHER_BANK_CORPORATE("4", "他行企业账户"),
    CITIC_PERSONAL_PASSBOOK("5", "中信个人存折"),   // 新增
    OTHER_BANK_PERSONAL_PASSBOOK("6", "他行个人存折"),

    ;

    BankCardAccountTypeEnum(
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

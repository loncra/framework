package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author maurice.chen
 */
public enum TransactionStatusEnum implements NameValueEnum<String> {

    SUCCESS("01", "成功"),
    FAILED("02", "失败"),
    PROCESSING("03", "处理中"),
    REFUNDED("04", "已退款（仅21000050接口发生退款时返回，原交易状态视为成功）"),
    REVERSED("05", "已退汇"),

    ;

    private TransactionStatusEnum(
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

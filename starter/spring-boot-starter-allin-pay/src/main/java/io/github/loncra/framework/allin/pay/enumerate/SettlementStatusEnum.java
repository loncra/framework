package io.github.loncra.framework.allin.pay.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 结算状态枚举
 *
 * @author maurice.chen
 */
public enum SettlementStatusEnum implements NameValueEnum<String> {

    /**
     * 未结算
     */
    UN_SETTLED("01", "未结算"),

    /**
     * 正在结算中
     */
    SETTLING("05", "正在结算中"),

    /**
     * 结算失败
     */
    SETTLEMENT_FAILED("08", "结算失败"),

    /**
     * 已结算
     */
    SETTLED("09", "已结算"),

    /**
     * 已合并
     */
    MERGED("10", "已合并"),

    /**
     * 冻结不结算
     */
    FROZEN("11", "冻结不结算"),

    /**
     * 其它方式结算
     */
    OTHER_SETTLEMENT("12", "其它方式结算"),

    /**
     * 客户放弃结算
     */
    CUSTOMER_ABANDONED("13", "客户放弃结算"),

    /**
     * 已归集结算
     */
    COLLECTED_SETTLEMENT("14", "已归集结算");

    /**
     * 构造函数
     *
     * @param value 结算状态值
     * @param name  结算状态名称
     */
    SettlementStatusEnum(
            String value,
            String name
    ) {
        this.name = name;
        this.value = value;
    }

    /**
     * 结算状态值
     */
    private final String value;

    /**
     * 结算状态名称
     */
    private final String name;

    /**
     * 获取结算状态值
     *
     * @return 结算状态值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取结算状态名称
     *
     * @return 结算状态名称
     */
    @Override
    public String getName() {
        return name;
    }

}

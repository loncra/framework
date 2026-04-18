package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 上传文件类型
 *
 * @author maurice.chen
 */
public enum UploadFileTypeEnum implements NameValueEnum<String> {
    /**
     * 平台商户业务订单明细文件
     */
    PLATFORM_MERCHANT_ORDER_DETAILS("601", "平台商户业务订单明细文件"),
    /**
     * 通用渠道支付对账明细文件
     */
    GENERAL_CHANNEL_RECON_DETAILS("608", "通用渠道支付对账明细文件"),
    /**
     * 平台商户分账明细文件
     */
    MERCHANT_SPLIT_DETAILS("609", "平台商户分账明细文件"),
    /**
     * 支付退款明细请求文件
     */
    PAYMENT_REFUND_REQUEST("610", "支付退款明细请求文件"),
    /**
     * 平台商户业务订单明细处理结果文件
     */
    ORDER_PROCESS_RESULT("801", "平台商户业务订单明细处理结果文件"),
    /**
     * 登记簿资金变动明细文件
     */
    FUND_CHANGE_DETAILS("811", "登记簿资金变动明细文件"),
    /**
     * 用户提现结果文件
     */
    WITHDRAWAL_RESULT("812", "用户提现结果文件"),
    /**
     * 用户退汇明细文件
     */
    USER_REMITTANCE_DETAILS("813", "用户退汇明细文件"),
    /**
     * 用户余额明细文件
     */
    USER_BALANCE_DETAILS("814", "用户余额明细文件"),
    /**
     * 分账明细处理结果文件
     */
    SPLIT_DETAILS_RESULT("815", "分账明细处理结果文件"),
    /**
     * 内部户明细文件
     */
    INTERNAL_ACCOUNT_DETAILS("817", "内部户明细文件"),
    /**
     * 历史内部户明细文件
     */
    HISTORICAL_INTERNAL_DETAILS("819", "历史内部户明细文件"),
    /**
     * 支付退款处理结果明细文件
     */
    REFUND_PROCESS_RESULT("820", "支付退款处理结果明细文件"),
    /**
     * 客户账明细文件
     */
    CUSTOMER_ACCOUNT_DETAILS("821", "客户账明细文件"),
    /**
     * 人行联行号同步文件
     */
    CNAPS_SYNC_FILE("996", "人行联行号同步文件"),
    /**
     * 平台商户业务订单明细文件(V2)
     */
    PLATFORM_MERCHANT_ORDER_DETAILS_V2("616", "平台商户业务订单明细文件(V2)"),

    /**
     * 平台商户业务订单明细文件(V2)结果
     */
    PLATFORM_MERCHANT_ORDER_DETAILS_V2_RESULT("830", "平台商户业务订单明细文件(V2)结果"),

    ;

    UploadFileTypeEnum(
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

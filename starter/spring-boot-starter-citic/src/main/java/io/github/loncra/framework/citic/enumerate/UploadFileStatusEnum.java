package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * @author maurice.chen
 */
public enum UploadFileStatusEnum implements NameValueEnum<String> {
    // 通用状态
    UPLOAD_SUCCESS("0", "文件上传成功"),
    ABNORMAL_BALANCE("2", "异常长短款"),
    REVIEWED_SEPARATELY("4", "已另行核对"),
    VALIDITY_CHECK_PASSED("7", "文件合法性检查通过"),
    VALIDITY_CHECK_FAILED("8", "文件合法性检查不通过"),
    CLEARING_COMPLETED("9", "清分处理完成"),
    FUTURE_ACCOUNT("B", "支付渠道未来账"),
    RESULT_FILE_GENERATED("AA", "结果文件生成成功"),
    STATEMENT_NOT_FOUND("A3", "明细核对未找到对账单"),
    DETAIL_CHECK_FAILED("A5", "明细核对失败,结果文件推送失败"),
    DETAIL_CHECK_PARTIAL_SUCCESS("A6", "明细核对失败,结果文件生成或推送成功"),
    INSUFFICIENT_FUNDS("Q", "自有资金账户余额不足或服务内部错误"),
    USER_BALANCE_INSUFFICIENT("Q3", "用户登记簿余额不足，暂停清分"),

    // 批量提现状态
    BATCH_UPLOAD_SUCCESS("B0", "文件上传成功"),
    BATCH_PROCESSING("B1", "文件处理中"),
    BATCH_SUCCESS("B2", "文件处理成功"),
    BATCH_FAILED("B3", "文件处理失败"),
    BATCH_RESULT_GENERATED("B9", "结果文件已生成"),
    BATCH_PUSH_SUCCESS("BC", "推送商户服务器成功"),
    BATCH_PUSH_FAILED("BD", "推送商户服务器失败"),

    // 分账状态
    SPLIT_PROCESSING("60", "文件处理中"),
    SPLIT_FETCH_FAILED("61", "文件获取失败"),
    SPLIT_PARSE_FAILED("62", "文件解析失败"),
    SPLIT_COMPLETED("63", "分账完成（未生成结果文件）"),
    SPLIT_ALL_SUCCESS("64", "已生成结果文件（全部处理成功）"),
    SPLIT_PARTIAL_FAILURE("65", "已生成结果文件（存在分账失败记录）"),

    // 账单状态
    BILL_PROCESSING("6A", "文件处理中"),
    BILL_FETCH_FAILED("6B", "文件获取失败"),
    BILL_PARSE_FAILED("6C", "文件解析失败"),
    BILL_FEE_DEDUCT_FAILED("6D", "扣收手续费失败"),
    BILL_PROCESS_SUCCESS("6E", "账单处理成功"),
    BILL_PREPROCESS_SUCCESS("6F", "账单预处理成功"),
    BILL_SUMMARY_SUCCESS("6G", "账单汇总成功"),
    BILL_SUMMARY_FAILED("6H", "账单汇总失败"),
    BILL_DECOMPRESS_FAILED("6I", "账单解压失败"),

    // 支付/退款状态
    PAYMENT_UPLOAD_SUCCESS("9A", "文件上传成功"),
    PAYMENT_PROCESSING("9B", "文件处理中"),
    PAYMENT_SUCCESS("9C", "文件处理成功"),
    PAYMENT_FAILED("9D", "文件处理失败"),
    PAYMENT_IMPORT_FAILED("9E", "文件导入失败"),
    PAYMENT_VALIDATION_FAILED("9F", "文件校验未通过"),
    PAYMENT_VALIDATION_PASSED("9H", "文件校验通过"),

    // 默认处理中状态（需放在最后）
    PROCESSING("OTHER", "文件处理中"),
    ;

    UploadFileStatusEnum(
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

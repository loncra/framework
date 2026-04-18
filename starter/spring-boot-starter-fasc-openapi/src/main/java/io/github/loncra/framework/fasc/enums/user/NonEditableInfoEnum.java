package io.github.loncra.framework.fasc.enums.user;

/**
 * @author zhoufucheng
 * @date 2022/11/30 14:31
 */
public enum NonEditableInfoEnum {
    ACCOUNT_NAME("accountName", "个人用户的法大大帐号"),
    USER_NAME("userName", "姓名"),
    USER_IDENT_TYPE("userIdentType", "证件类型"),
    USER_IDENT_NO("userIdentNo", "证件号码"),
    MOBILE("mobile", "手机号"),
    BANK_ACCOUNT_NO("bankAccountNo", "银行卡号"),

    ;

    private String code;
    private String remark;

    NonEditableInfoEnum(
            String code,
            String remark
    ) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}

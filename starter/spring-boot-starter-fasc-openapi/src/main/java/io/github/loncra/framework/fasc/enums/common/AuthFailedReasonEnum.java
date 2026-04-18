package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * @date 2021/12/15 15:16:27
 */
public enum AuthFailedReasonEnum {


    /**
     * 本次授权失败原因：
     * user_ident_info_mismatch: 个人身份信息匹配失败；
     * corp_ident_info_mismatch: 企业身份信息匹配失败；
     * reject: 企业经办人操作不允许授权。
     */
    USER_IDENT_INFO_MISMATCH("user_ident_info_mismatch", "个人身份信息匹配失败"),
    CORP_IDENT_INFO_MISMATCH("corp_ident_info_mismatch", "企业身份信息匹配失败"),
    REJECT("reject", "企业经办人操作不允许授权");

    private String code;
    private String remark;

    AuthFailedReasonEnum(
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

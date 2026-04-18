package io.github.loncra.framework.fasc.enums.signtask;

public enum SignDocTypeEnum {

    CONTRACT("CONTRACT", "合同"),
    DOCUMENT("DOCUMENT", "单据"),
    CREDIT_AUTH("credit_auth", "征信授权书"),
    LEGAL_LETTER("legal_letter", "律师函"),
    ;
    private String code;
    private String remark;

    SignDocTypeEnum(
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

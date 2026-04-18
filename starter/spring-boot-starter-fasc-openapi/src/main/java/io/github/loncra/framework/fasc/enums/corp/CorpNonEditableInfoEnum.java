package io.github.loncra.framework.fasc.enums.corp;

public enum CorpNonEditableInfoEnum {

    CORP_NAME("corpName", "企业名称"),
    CORP_IDENT_TYPE("corpIdentType", "企业类型"),
    CORP_IDENT_NO("corpIdentNo", "企业统一信用代码"),
    ;

    private String code;
    private String remark;

    CorpNonEditableInfoEnum(
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

package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum CorpIdentStatusEnum {

    UNIDENTIFIED("unidentified", "未认证"),
    IDENTIFIED("identified", "已认证且有效"),
    ;

    private String code;
    private String remark;

    CorpIdentStatusEnum(
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

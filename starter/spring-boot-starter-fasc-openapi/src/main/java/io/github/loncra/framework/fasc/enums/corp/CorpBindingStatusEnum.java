package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum CorpBindingStatusEnum {

    UNAUTHORIZED("unauthorized", "未授权"),
    AUTHORIZED("authorized", "已授权"),
    ;

    private String code;
    private String remark;

    CorpBindingStatusEnum(
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

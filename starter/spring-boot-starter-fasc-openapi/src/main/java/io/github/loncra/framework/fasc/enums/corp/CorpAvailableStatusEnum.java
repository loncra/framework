package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum CorpAvailableStatusEnum {

    DISABLE("disable", "禁用状态"),
    ENABLE("enable", "启用状态"),
    ;

    private String code;
    private String remark;

    CorpAvailableStatusEnum(
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

package io.github.loncra.framework.fasc.enums.org;

/**
 * @author zhoufucheng
 * @date 2022/12/5 18:45
 */
public enum SetMemberStatusEnum {
    ENABLE("enable", "启用"),
    DISABLE("disable", "禁用"),
    ;

    private String code;
    private String remark;

    SetMemberStatusEnum(
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

package io.github.loncra.framework.fasc.enums.seal;

/**
 * @author zhoufucheng
 * @date 2022/12/5 20:21
 */
public enum SetSealStatusEnum {
    ENABLE("enable", "启用"),
    DISABLE("disable", "禁用"),
    ;

    private String code;
    private String remark;

    SetSealStatusEnum(
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

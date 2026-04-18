package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum UserAvailableStatusEnum {

    DISABLE("disable", "禁用状态"),
    ENABLE("enable", "启用状态"),
    ;

    private final String code;
    private final String remark;

    UserAvailableStatusEnum(
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

package io.github.loncra.framework.fasc.enums.org;

/**
 * @author Fadada
 * @date 2021/12/16 10:22:33
 */
public enum MemberStatusEnum {

    INACTIVE("inactive", "未激活"),
    DISABLED("disabled", "已禁用"),
    ACTIVATED("activated", "已激活"),
    ;

    private String code;
    private String remark;

    MemberStatusEnum(
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

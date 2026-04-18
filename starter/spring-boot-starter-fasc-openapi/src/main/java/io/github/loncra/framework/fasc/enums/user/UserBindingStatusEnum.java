package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum UserBindingStatusEnum {

    UNAUTHORIZED("unauthorized", "未授权"),
    AUTHORIZED("authorized", "已授权"),
    ;

    private String code;
    private String remark;

    UserBindingStatusEnum(
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

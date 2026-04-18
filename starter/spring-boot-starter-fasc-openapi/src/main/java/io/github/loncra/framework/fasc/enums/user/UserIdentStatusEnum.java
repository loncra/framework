package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/10/23 16:28:03
 */
public enum UserIdentStatusEnum {

    UNIDENTIFIED("unidentified", "未认证"),
    IDENTIFIED("identified", "已认证且有效"),
    ;

    private String code;
    private String remark;

    UserIdentStatusEnum(
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

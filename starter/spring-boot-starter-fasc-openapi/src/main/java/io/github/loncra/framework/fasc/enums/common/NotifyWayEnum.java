package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * 2021/9/23 11:20:11
 */
public enum NotifyWayEnum {
    /**
     * 通知方式：
     * mobile: 手机
     * email: 邮箱
     */
    MOBILE("mobile", "手机号"),
    EMAIL("email", "邮箱");

    private String code;
    private String remark;

    NotifyWayEnum(
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

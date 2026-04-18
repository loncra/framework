package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * 2021/9/23 11:03:29
 */
public enum ActorPermissionEnum {

    FILL("fill", "填写和确认内容"),
    SIGN("sign", "确定签署"),
    CC("cc", "抄送方"),
    ;
    private String code;
    private String remark;

    ActorPermissionEnum(
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

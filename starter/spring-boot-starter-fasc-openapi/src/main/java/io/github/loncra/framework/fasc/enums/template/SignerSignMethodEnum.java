package io.github.loncra.framework.fasc.enums.template;

/**
 * @author Fadada
 * 2021/9/23 11:03:29
 */
public enum SignerSignMethodEnum {

    UNLIMITED("unlimited", "不限制"),
    STANDARD("standard", "使用标准签名"),
    HAND_WRITE("hand_write", "使用手绘签名"),
    AI_HAND_WRITE("ai_hand_write", "AI手绘签名");

    private String code;
    private String remark;

    SignerSignMethodEnum(
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

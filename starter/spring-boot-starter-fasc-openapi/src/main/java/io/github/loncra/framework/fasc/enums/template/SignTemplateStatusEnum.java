package io.github.loncra.framework.fasc.enums.template;

/**
 * @author Fadada
 * 2021/9/23 11:04:43
 */
public enum SignTemplateStatusEnum {

    /**
     * 签署模板状态：
     * invalid: 停用
     * valid: 启用
     */
    INVALID("invalid", "停用"),
    VALID("valid", "启用"),
    DRAFT("draft", "草稿");


    private String code;
    private String remark;

    SignTemplateStatusEnum(
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

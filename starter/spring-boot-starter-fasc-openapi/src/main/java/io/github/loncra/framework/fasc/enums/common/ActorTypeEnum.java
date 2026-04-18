package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * 2021/9/23 11:03:29
 */
public enum ActorTypeEnum {

    CORP("corp", "企业"),
    PERSON("person", "个人");

    private String code;
    private String remark;

    ActorTypeEnum(
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

package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/9/23 10:12:54
 */
public enum CorpIdentTypeEnum {

    OTHER("other", "其他类型"),

    CORP("corp", "企业"),

    INDIVIDUAL_BIZ("individual_biz", "个体工商户"),

    GOVERNMENT("government", "政府和事业单位");

    private String code;
    private String remark;

    CorpIdentTypeEnum(
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

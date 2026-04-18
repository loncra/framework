package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * @date 2021/12/15 11:20:27
 */
public enum IdTypeEnum {
    /**
     * 主体类型：
     * corp: 企业
     * person: 个人
     */

    CORP("corp", "企业"),
    PERSON("person", "个人");

    private final String code;
    private final String remark;

    IdTypeEnum(
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

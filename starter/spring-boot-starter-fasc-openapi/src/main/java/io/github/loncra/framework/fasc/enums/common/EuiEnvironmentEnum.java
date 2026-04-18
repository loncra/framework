package io.github.loncra.framework.fasc.enums.common;

/**
 * @ClassName: EnvironmentEnum
 * @Description: 环境枚举
 * @Author: hukc@fadada.com
 * @Date: 2022/3/24 16:06
 */
public enum EuiEnvironmentEnum {
    UAT("UAT", "https://%s.uat-e.fadada.com"),
    PROD("PROD", "https://%s.e.fadada.com");
    /**
     * 字段值
     */
    private String value;
    /**
     * 字段值的实际意义
     */
    private String valueInFact;

    public String getValue() {
        return value;
    }

    public String getValueInFact() {
        return valueInFact;
    }

    EuiEnvironmentEnum(
            String value,
            String valueInFact
    ) {
        this.value = value;
        this.valueInFact = valueInFact;
    }
}

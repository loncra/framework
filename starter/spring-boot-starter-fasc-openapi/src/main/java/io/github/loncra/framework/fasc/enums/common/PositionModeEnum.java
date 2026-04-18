package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * @date 2021/12/6 9:50:53
 */
public enum PositionModeEnum {
    /**
     * 定位模式：
     * pixel: 像素值；计算方法参考[文档页面坐标定位计算方法](#文档页面坐标定位计算方法)。
     * keyword: 关键字定位
     */
    PIXEL("pixel", "像素值"),

    KEYWORD("keyword", "关键字定位");

    private String code;
    private String remark;

    PositionModeEnum(
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

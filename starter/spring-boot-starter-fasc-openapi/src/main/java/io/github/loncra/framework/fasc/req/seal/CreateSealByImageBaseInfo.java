package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zhupintian
 * @Date: 2023/7/25
 */
public class CreateSealByImageBaseInfo extends BaseReq {
    /**
     * 印章图片base64，图片大小5M以内
     */
    private String sealImage;

    /**
     * 印章宽（单位毫米mm），默认40mm，最大60mm，最小10mm
     */
    private Integer sealWidth;

    /**
     * 印章高（单位毫米mm），默认40mm，最大60mm，最小10mm
     */
    private Integer sealHeight;

    /**
     * 印章做旧，默认为0不做旧，数值范围0-1.85
     */
    private Double sealOldStyle;

    /**
     * 印章颜色，默认红色
     */
    private String sealColor;

    public String getSealImage() {
        return sealImage;
    }

    public void setSealImage(String sealImage) {
        this.sealImage = sealImage;
    }

    public Integer getSealWidth() {
        return sealWidth;
    }

    public void setSealWidth(Integer sealWidth) {
        this.sealWidth = sealWidth;
    }

    public Integer getSealHeight() {
        return sealHeight;
    }

    public void setSealHeight(Integer sealHeight) {
        this.sealHeight = sealHeight;
    }

    public Double getSealOldStyle() {
        return sealOldStyle;
    }

    public void setSealOldStyle(Double sealOldStyle) {
        this.sealOldStyle = sealOldStyle;
    }

    public String getSealColor() {
        return sealColor;
    }

    public void setSealColor(String sealColor) {
        this.sealColor = sealColor;
    }
}

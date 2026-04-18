package io.github.loncra.framework.fasc.req.seal;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zhupintian
 * @Date: 2023/7/26
 */
public class CreateSealByTemplateBaseInfo extends BaseReq {

    /**
     * 签名样式，默认值：矩形（rectangle）
     */
    private String sealTemplateStyle;

    /**
     * 印章规格，（单位：毫米mm，宽高）
     */
    private String sealSize;

    /**
     * 印章内容规则（印章中姓名后缀），默认值为0
     * name - 无后缀（仅显示姓名，如：赵四）
     * name_with_suffix_seal - 加“印”（姓名后添加“印”字，如：赵四印）
     * name_with_suffix_of_seal - 加“之印”（姓名后添加“之印”，如：赵四之印）
     */
    private String sealSuffix;

    /**
     * 印章颜色，默认红色
     */
    private String sealColor;

    public String getSealTemplateStyle() {
        return sealTemplateStyle;
    }

    public void setSealTemplateStyle(String sealTemplateStyle) {
        this.sealTemplateStyle = sealTemplateStyle;
    }

    public String getSealSize() {
        return sealSize;
    }

    public void setSealSize(String sealSize) {
        this.sealSize = sealSize;
    }

    public String getSealSuffix() {
        return sealSuffix;
    }

    public void setSealSuffix(String sealSuffix) {
        this.sealSuffix = sealSuffix;
    }

    public String getSealColor() {
        return sealColor;
    }

    public void setSealColor(String sealColor) {
        this.sealColor = sealColor;
    }
}

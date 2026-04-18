package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zpt
 * @Date: 2023/7/21
 */
public class CreateSealByTemplateReq extends BaseReq {

    private String openCorpId;

    /**
     * 印章主体id
     */
    private String entityId;

    /**
     * 印章名称，示例值：“深圳项目人事章”
     */
    private String sealName;

    /**
     * 企业印章类型
     */
    private String categoryType;

    /**
     * 印章标签，可以和业务系统的印章业务类型或者印章的子分类对应
     */
    private String sealTag;

    /**
     * 印章样式，默认值：圆形章（round）
     */
    private String sealTemplateStyle;

    /**
     * 印章规格，（单位：毫米mm，宽高），round圆形章才设置规格
     */
    private String sealSize;

    /**
     * 印章横排文字，最多10个字符
     */
    private String sealHorizontalText;

    /**
     * 印章下弦文（实体印章防伪码），最多15字符，数字、字母、英文符号
     */
    private String sealBottomText;

    /**
     * 印章颜色，默认红色
     */
    private String sealColor;

    /**
     * 业务系统定义的印章创建序列号：用于在印章创建后的回调事件中将sealId和createSerialNo进行对应
     */
    private String createSerialNo;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getSealTag() {
        return sealTag;
    }

    public void setSealTag(String sealTag) {
        this.sealTag = sealTag;
    }

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

    public String getSealHorizontalText() {
        return sealHorizontalText;
    }

    public void setSealHorizontalText(String sealHorizontalText) {
        this.sealHorizontalText = sealHorizontalText;
    }

    public String getSealBottomText() {
        return sealBottomText;
    }

    public void setSealBottomText(String sealBottomText) {
        this.sealBottomText = sealBottomText;
    }

    public String getSealColor() {
        return sealColor;
    }

    public void setSealColor(String sealColor) {
        this.sealColor = sealColor;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}
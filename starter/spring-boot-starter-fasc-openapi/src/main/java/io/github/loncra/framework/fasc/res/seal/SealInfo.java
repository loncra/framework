package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class SealInfo extends BaseBean {
    /**
     * 印章ID，只有系统生成或审核通过的印章才返回
     */
    private Long sealId;

    private String entityId;

    /**
     * 印章名称
     */
    private String sealName;

    /**
     * 印章标签
     */
    private String sealTag;

    /**
     * 印章类型：official_seal-法定名称章（公章）, contract_seal-合同专用章, hr_seal-人事专用章, financial_seal-财务专用章, legal_representative_seal-法定代表人名章, other-其他
     */
    private String categoryType;

    /**
     * 印章图片地址
     */
    private String picFileUrl;

    /**
     * 印章宽，单位毫米mm
     */
    private Integer sealWidth;

    /**
     * 印章高，单位毫米m
     */
    private Integer sealHeight;

    /**
     * 印章状态，只有系统生成或审核通过的印章才返回：enable-已启用, disable-已停用, cancelled-已注销
     */
    private String sealStatus;

    /**
     * 印章创建时间，精确到毫秒
     */
    private String createTime;

    /**
     * 证书颁发机构：CFCA-中国金融认证中心, ZXCA-山东豸信认证服务有限公司, CSCA-世纪数码CA
     */
    private String certCAOrg;

    /**
     * 证书加密算法类型：国密证书-SM2, 标准证书-RSA
     */
    private String certEncryptType;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Long getSealId() {
        return sealId;
    }

    public void setSealId(Long sealId) {
        this.sealId = sealId;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealTag() {
        return sealTag;
    }

    public void setSealTag(String sealTag) {
        this.sealTag = sealTag;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getPicFileUrl() {
        return picFileUrl;
    }

    public void setPicFileUrl(String picFileUrl) {
        this.picFileUrl = picFileUrl;
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

    public String getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(String sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

    public String getCertEncryptType() {
        return certEncryptType;
    }

    public void setCertEncryptType(String certEncryptType) {
        this.certEncryptType = certEncryptType;
    }
}

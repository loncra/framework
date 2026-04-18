package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetUserListSealInfo extends BaseBean {
    private Long sealId;
    private String entityId;
    private String sealName;
    private String categoryType;
    private String picFileUrl;
    private String sealStatus;
    private String certCAOrg;
    private String certEncryptType;
    private Boolean defaultSeal;
    private String grantTime;
    private String grantStartTime;
    private String grantEndTime;
    private String grantStatus;

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

    public String getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(String sealStatus) {
        this.sealStatus = sealStatus;
    }

    public Boolean getDefaultSeal() {
        return defaultSeal;
    }

    public void setDefaultSeal(Boolean defaultSeal) {
        this.defaultSeal = defaultSeal;
    }

    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
    }

    public String getGrantStartTime() {
        return grantStartTime;
    }

    public void setGrantStartTime(String grantStartTime) {
        this.grantStartTime = grantStartTime;
    }

    public String getGrantEndTime() {
        return grantEndTime;
    }

    public void setGrantEndTime(String grantEndTime) {
        this.grantEndTime = grantEndTime;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }
}

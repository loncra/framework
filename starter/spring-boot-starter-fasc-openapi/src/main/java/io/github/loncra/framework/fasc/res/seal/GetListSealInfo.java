package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetListSealInfo extends BaseBean {
    private Long sealId;
    private Long verifyId;
    private String entityId;
    private String sealName;
    private String sealTag;
    private String categoryType;
    private String picFileUrl;
    private Integer sealWidth;
    private Integer sealHeight;
    private String sealStatus;
    private String createTime;
    private String certCAOrg;
    private String certEncryptType;
    private List<GetListSealUser> sealUsers;
    private List<FreeSignInfo> freeSignInfos;

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

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

    public List<GetListSealUser> getSealUsers() {
        return sealUsers;
    }

    public void setSealUsers(List<GetListSealUser> sealUsers) {
        this.sealUsers = sealUsers;
    }

    public List<FreeSignInfo> getFreeSignInfos() {
        return freeSignInfos;
    }

    public void setFreeSignInfos(List<FreeSignInfo> freeSignInfos) {
        this.freeSignInfos = freeSignInfos;
    }
}

package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/11/30 15:47
 */
public class PersonalSealInfo extends BaseBean {
    private Long sealId;
    private String sealName;
    private String categoryType;
    private String picFileUrl;
    private Integer sealWidth;
    private Integer sealHeight;
    private String sealStatus;
    private String createTime;
    private String certCAOrg;
    private String certEncryptType;
    private Boolean defaultSeal;
    private List<FreeSignInfo> freeSignInfos;

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

    public Boolean getDefaultSeal() {
        return defaultSeal;
    }

    public void setDefaultSeal(Boolean defaultSeal) {
        this.defaultSeal = defaultSeal;
    }

    public List<FreeSignInfo> getFreeSignInfos() {
        return freeSignInfos;
    }

    public void setFreeSignInfos(List<FreeSignInfo> freeSignInfos) {
        this.freeSignInfos = freeSignInfos;
    }
}

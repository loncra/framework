package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetSealCreateUrlReq extends BaseReq {

    private String openCorpId;
    private String entityId;
    private String sealName;
    private String categoryType;
    private String sealTag;
    private String createSerialNo;
    private String clientUserId;
    private String redirectUrl;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
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

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}

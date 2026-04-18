package io.github.loncra.framework.fasc.req.draft;

import io.github.loncra.framework.fasc.bean.base.BaseReq;


public class GetInviteUrlReq extends BaseReq {

    private String contractConsultId;

    private String expireTime;

    private String innerPermission;

    private String outerPermission;

    private String touristView;

    private String redirectUrl;

    public String getContractConsultId() {
        return contractConsultId;
    }

    public void setContractConsultId(String contractConsultId) {
        this.contractConsultId = contractConsultId;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getInnerPermission() {
        return innerPermission;
    }

    public void setInnerPermission(String innerPermission) {
        this.innerPermission = innerPermission;
    }

    public String getOuterPermission() {
        return outerPermission;
    }

    public void setOuterPermission(String outerPermission) {
        this.outerPermission = outerPermission;
    }

    public String getTouristView() {
        return touristView;
    }

    public void setTouristView(String touristView) {
        this.touristView = touristView;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}

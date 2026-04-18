package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetUserThreeElementVerifyUrlReq extends BaseReq {

    private String clientUserId;

    private String userName;

    private String userIdentNo;

    private String mobile;

    private String redirectUrl;

    private Boolean idCardImage;

    private String faceSide;

    private String nationalEmblemSide;

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdentNo() {
        return userIdentNo;
    }

    public void setUserIdentNo(String userIdentNo) {
        this.userIdentNo = userIdentNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(Boolean idCardImage) {
        this.idCardImage = idCardImage;
    }

    public String getFaceSide() {
        return faceSide;
    }

    public void setFaceSide(String faceSide) {
        this.faceSide = faceSide;
    }

    public String getNationalEmblemSide() {
        return nationalEmblemSide;
    }

    public void setNationalEmblemSide(String nationalEmblemSide) {
        this.nationalEmblemSide = nationalEmblemSide;
    }
}
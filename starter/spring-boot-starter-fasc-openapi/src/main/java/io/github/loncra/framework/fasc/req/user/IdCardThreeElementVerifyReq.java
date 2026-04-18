package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class IdCardThreeElementVerifyReq extends BaseReq {
    private String userName;
    private String userIdentNo;
    private String imgBase64;

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

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}

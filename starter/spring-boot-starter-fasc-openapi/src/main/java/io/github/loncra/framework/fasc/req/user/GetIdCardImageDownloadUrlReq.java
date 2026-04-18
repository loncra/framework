package io.github.loncra.framework.fasc.req.user;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetIdCardImageDownloadUrlReq extends BaseReq {

    private String verifyId;

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }
}
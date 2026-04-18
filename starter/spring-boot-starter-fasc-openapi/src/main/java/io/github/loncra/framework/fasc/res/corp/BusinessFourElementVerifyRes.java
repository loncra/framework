package io.github.loncra.framework.fasc.res.corp;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class BusinessFourElementVerifyRes extends BaseBean {
    private String serialNo;
    private String verifyResult;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }
}

package io.github.loncra.framework.fasc.res.user;

/**
 * @author zhoufucheng
 * @date 2023/6/21 17:26
 */
public class TelecomThreeElementVerifyRes {
    private Boolean verifyResult;
    private String serialNo;

    public Boolean getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}

package io.github.loncra.framework.fasc.res.doc;

public class SignatureInfosRes {

    private String signer;
    private String signedonTime;
    private String authority;
    private Boolean timestampFlag;
    private String timestampTime;
    private Boolean timestampVerifyFlag;
    private Boolean integrityFlag;

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignedonTime() {
        return signedonTime;
    }

    public void setSignedonTime(String signedonTime) {
        this.signedonTime = signedonTime;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getTimestampFlag() {
        return timestampFlag;
    }

    public void setTimestampFlag(Boolean timestampFlag) {
        this.timestampFlag = timestampFlag;
    }

    public String getTimestampTime() {
        return timestampTime;
    }

    public void setTimestampTime(String timestampTime) {
        this.timestampTime = timestampTime;
    }

    public Boolean getTimestampVerifyFlag() {
        return timestampVerifyFlag;
    }

    public void setTimestampVerifyFlag(Boolean timestampVerifyFlag) {
        this.timestampVerifyFlag = timestampVerifyFlag;
    }

    public Boolean getIntegrityFlag() {
        return integrityFlag;
    }

    public void setIntegrityFlag(Boolean integrityFlag) {
        this.integrityFlag = integrityFlag;
    }
}

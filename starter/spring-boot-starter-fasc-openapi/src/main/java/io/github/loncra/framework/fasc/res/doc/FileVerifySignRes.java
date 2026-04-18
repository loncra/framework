package io.github.loncra.framework.fasc.res.doc;

import java.util.List;

public class FileVerifySignRes {

    private Boolean verifyResult;
    private String reason;
    private List<SignatureInfosRes> signatureInfos;

    public Boolean getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Boolean verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<SignatureInfosRes> getSignatureInfos() {
        return signatureInfos;
    }

    public void setSignatureInfos(List<SignatureInfosRes> signatureInfos) {
        this.signatureInfos = signatureInfos;
    }
}

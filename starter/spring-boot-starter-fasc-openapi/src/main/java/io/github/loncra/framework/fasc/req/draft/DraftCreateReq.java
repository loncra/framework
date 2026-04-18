package io.github.loncra.framework.fasc.req.draft;

import io.github.loncra.framework.fasc.bean.base.BaseReq;


public class DraftCreateReq extends BaseReq {

    private String openCorpId;

    private String initiatorMemberId;

    private String contractSubject;

    private String fileId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(String initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public String getContractSubject() {
        return contractSubject;
    }

    public void setContractSubject(String contractSubject) {
        this.contractSubject = contractSubject;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}

package io.github.loncra.framework.fasc.req.approval;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetApprovalDetailReq extends BaseReq {

    private String openCorpId;
    private String approvalId;
    private String approvalType;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }
}
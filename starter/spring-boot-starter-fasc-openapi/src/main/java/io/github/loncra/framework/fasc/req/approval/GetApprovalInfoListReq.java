package io.github.loncra.framework.fasc.req.approval;

import io.github.loncra.framework.fasc.bean.base.BasePageReq;

import java.util.List;

public class GetApprovalInfoListReq extends BasePageReq {

    private String openCorpId;

    private String approvalType;

    private List<String> approvalStatus;

    public List<String> getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(List<String> approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }
}

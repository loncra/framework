package io.github.loncra.framework.fasc.res.approval;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2023/9/6 11:35
 */
public class GetApprovalFlowListRes extends BaseBean {
    private String approvalType;
    private String approvalFlowId;
    private String approvalFlowName;
    private String description;
    private String status;

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getApprovalFlowId() {
        return approvalFlowId;
    }

    public void setApprovalFlowId(String approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }

    public String getApprovalFlowName() {
        return approvalFlowName;
    }

    public void setApprovalFlowName(String approvalFlowName) {
        this.approvalFlowName = approvalFlowName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

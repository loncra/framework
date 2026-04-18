package io.github.loncra.framework.fasc.req.template;

/**
 * @author zhoufucheng
 * @date 2023/9/6 11:29
 */
public class ApprovalInfo {
    private String approvalFlowId;
    private String approvalType;

    public String getApprovalFlowId() {
        return approvalFlowId;
    }

    public void setApprovalFlowId(String approvalFlowId) {
        this.approvalFlowId = approvalFlowId;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }
}

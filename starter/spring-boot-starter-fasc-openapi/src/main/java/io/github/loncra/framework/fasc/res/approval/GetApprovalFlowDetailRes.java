package io.github.loncra.framework.fasc.res.approval;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/9/6 11:36
 */
public class GetApprovalFlowDetailRes extends BaseBean {
    private String approvalType;
    private String approvalFlowId;
    private String approvalFlowName;
    private String description;
    private String lastOprMemberId;
    private String lastOprMemberName;
    private String updateTime;
    private String status;
    private List<ApprovalFlowNode> approvalNode;

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

    public String getLastOprMemberId() {
        return lastOprMemberId;
    }

    public void setLastOprMemberId(String lastOprMemberId) {
        this.lastOprMemberId = lastOprMemberId;
    }

    public String getLastOprMemberName() {
        return lastOprMemberName;
    }

    public void setLastOprMemberName(String lastOprMemberName) {
        this.lastOprMemberName = lastOprMemberName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ApprovalFlowNode> getApprovalNode() {
        return approvalNode;
    }

    public void setApprovalNode(List<ApprovalFlowNode> approvalNode) {
        this.approvalNode = approvalNode;
    }
}

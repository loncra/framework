package io.github.loncra.framework.fasc.res.approval;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class GetApprovalDetailRes extends BaseBean {

    private String approvalType;

    private String templateId;

    private List<String> signTaskIds;

    private String approvalId;

    private String approvalName;

    private String applicantName;

    private Long applicantMemberId;

    private String applicationTime;

    private String approvalStatus;

    private List<ApprovalNode> approvalNode;

    public static class ApprovalNode {

        private Long nodeId;

        private String approvalType;

        private String nodeStatus;

        private List<ApprovalInfoRes> approversInfos;

        public Long getNodeId() {
            return nodeId;
        }

        public void setNodeId(Long nodeId) {
            this.nodeId = nodeId;
        }

        public String getApprovalType() {
            return approvalType;
        }

        public void setApprovalType(String approvalType) {
            this.approvalType = approvalType;
        }

        public String getNodeStatus() {
            return nodeStatus;
        }

        public void setNodeStatus(String nodeStatus) {
            this.nodeStatus = nodeStatus;
        }

        public List<ApprovalInfoRes> getApproversInfos() {
            return approversInfos;
        }

        public void setApproversInfos(List<ApprovalInfoRes> approversInfos) {
            this.approversInfos = approversInfos;
        }
    }

    public static class ApprovalInfoRes {

        private String approverMemberName;

        private Long approverMemberId;

        private String approverStatus;

        private String operateTime;

        private String rejectNote;

        public String getApproverMemberName() {
            return approverMemberName;
        }

        public void setApproverMemberName(String approverMemberName) {
            this.approverMemberName = approverMemberName;
        }

        public Long getApproverMemberId() {
            return approverMemberId;
        }

        public void setApproverMemberId(Long approverMemberId) {
            this.approverMemberId = approverMemberId;
        }

        public String getApproverStatus() {
            return approverStatus;
        }

        public void setApproverStatus(String approverStatus) {
            this.approverStatus = approverStatus;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getRejectNote() {
            return rejectNote;
        }

        public void setRejectNote(String rejectNote) {
            this.rejectNote = rejectNote;
        }
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getSignTaskIds() {
        return signTaskIds;
    }

    public void setSignTaskIds(List<String> signTaskIds) {
        this.signTaskIds = signTaskIds;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getApplicantMemberId() {
        return applicantMemberId;
    }

    public void setApplicantMemberId(Long applicantMemberId) {
        this.applicantMemberId = applicantMemberId;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<ApprovalNode> getApprovalNode() {
        return approvalNode;
    }

    public void setApprovalNode(List<ApprovalNode> approvalNode) {
        this.approvalNode = approvalNode;
    }
}
package io.github.loncra.framework.fasc.event.approval;

import java.util.List;

/**
 * 审批变更事件
 */
public class ApprovalCreatCallBackDto {

    private String eventTime;
    private String openCorpId;
    private String clientCorpId;

    private String approvalType;

    private String templateId;

    private String signTaskId;

    private String approvalId;

    private String approvalStatus;

    private Long oprMemberId;

    private String oprMemberName;

    private List<NextApproveMember> nextApproveMembers;

    public static class NextApproveMember {

        private Long nextNodeMemberId;

        private String nextNodeMemberName;

        public Long getNextNodeMemberId() {
            return nextNodeMemberId;
        }

        public void setNextNodeMemberId(Long nextNodeMemberId) {
            this.nextNodeMemberId = nextNodeMemberId;
        }

        public String getNextNodeMemberName() {
            return nextNodeMemberName;
        }

        public void setNextNodeMemberName(String nextNodeMemberName) {
            this.nextNodeMemberName = nextNodeMemberName;
        }
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
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

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getOprMemberId() {
        return oprMemberId;
    }

    public void setOprMemberId(Long oprMemberId) {
        this.oprMemberId = oprMemberId;
    }

    public String getOprMemberName() {
        return oprMemberName;
    }

    public void setOprMemberName(String oprMemberName) {
        this.oprMemberName = oprMemberName;
    }

    public List<NextApproveMember> getNextApproveMembers() {
        return nextApproveMembers;
    }

    public void setNextApproveMembers(List<NextApproveMember> nextApproveMembers) {
        this.nextApproveMembers = nextApproveMembers;
    }
}

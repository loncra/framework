package io.github.loncra.framework.fasc.res.approval;

import io.github.loncra.framework.fasc.bean.base.BasePageRes;

import java.util.List;

public class GetApprovalInfoListRes extends BasePageRes {

    private List<ApprovalInfoRes> approvalInfos;

    public static class ApprovalInfoRes {

        private String approvalType;

        private String templateId;

        private List<String> signTaskIds;

        private String approvalId;

        private String approvalName;

        private String approvalStatus;

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

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }
    }

    public List<ApprovalInfoRes> getApprovalInfos() {
        return approvalInfos;
    }

    public void setApprovalInfos(List<ApprovalInfoRes> approvalInfos) {
        this.approvalInfos = approvalInfos;
    }
}
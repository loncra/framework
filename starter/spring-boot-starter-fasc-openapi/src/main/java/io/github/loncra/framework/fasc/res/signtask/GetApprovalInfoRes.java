package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class GetApprovalInfoRes extends BaseBean {
    private String signTaskId;

    private String signTaskSubject;

    private String approvalId;

    private String approvalSubject;

    private String applicantName;

    private String applicationTime;

    private String approvalStatus;

    private List<ApprovalInfoNode> approvalNode;


    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalSubject() {
        return approvalSubject;
    }

    public void setApprovalSubject(String approvalSubject) {
        this.approvalSubject = approvalSubject;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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

    public List<ApprovalInfoNode> getApprovalNode() {
        return approvalNode;
    }

    public void setApprovalNode(List<ApprovalInfoNode> approvalNode) {
        this.approvalNode = approvalNode;
    }
}

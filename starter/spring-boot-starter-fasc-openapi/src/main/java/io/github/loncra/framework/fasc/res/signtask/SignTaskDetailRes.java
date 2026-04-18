package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.OpenId;
import io.github.loncra.framework.fasc.req.signtask.ApprovalInfo;
import io.github.loncra.framework.fasc.req.signtask.Watermark;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/13 16:44:09
 */
public class SignTaskDetailRes extends BaseBean {
    private OpenId initiator;
    private String initiatorMemberId;
    private String initiatorMemberName;
    private String signTaskId;
    private String signTaskSubject;
    private String storageType;
    private String signDocType;
    private String signTaskStatus;
    private String createTime;
    private String signTaskSource;
    private String approvalStatus;
    private String rejectNote;
    private String businessTypeName;
    private String businessCode;
    private String templateId;
    private String startTime;
    private String finishTime;
    private String deadlineTime;
    private String terminationNote;
    private String certCAOrg;
    private Long businessTypeId;
    private String revokeReason;
    private Boolean autoFillFinalize;
    private Boolean autoFinish;
    private Boolean signInOrder;
    private String abolishedSignTaskId;
    private String originalSignTaskId;
    private String dueDate;
    private List<SignTaskDocRes> docs;
    private List<SignTaskAttachRes> attachs;
    private List<SignTaskDetailActor> actors;
    private List<Watermark> watermarks;
    private List<ApprovalInfo> approvalInfos;
    private String fileFormat;
    private String transReferenceId;

    public List<ApprovalInfo> getApprovalInfos() {
        return approvalInfos;
    }

    public void setApprovalInfos(List<ApprovalInfo> approvalInfos) {
        this.approvalInfos = approvalInfos;
    }

    public String getSignDocType() {
        return signDocType;
    }

    public void setSignDocType(String signDocType) {
        this.signDocType = signDocType;
    }

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

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

    public String getSignTaskStatus() {
        return signTaskStatus;
    }

    public void setSignTaskStatus(String signTaskStatus) {
        this.signTaskStatus = signTaskStatus;
    }

    public List<SignTaskDocRes> getDocs() {
        return docs;
    }

    public void setDocs(List<SignTaskDocRes> docs) {
        this.docs = docs;
    }

    public List<SignTaskAttachRes> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<SignTaskAttachRes> attachs) {
        this.attachs = attachs;
    }

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public String getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(String initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public String getInitiatorMemberName() {
        return initiatorMemberName;
    }

    public void setInitiatorMemberName(String initiatorMemberName) {
        this.initiatorMemberName = initiatorMemberName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<SignTaskDetailActor> getActors() {
        return actors;
    }

    public void setActors(List<SignTaskDetailActor> actors) {
        this.actors = actors;
    }

    public String getSignTaskSource() {
        return signTaskSource;
    }

    public void setSignTaskSource(String signTaskSource) {
        this.signTaskSource = signTaskSource;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRejectNote() {
        return rejectNote;
    }

    public void setRejectNote(String rejectNote) {
        this.rejectNote = rejectNote;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getTerminationNote() {
        return terminationNote;
    }

    public void setTerminationNote(String terminationNote) {
        this.terminationNote = terminationNote;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public Boolean getAutoFillFinalize() {
        return autoFillFinalize;
    }

    public void setAutoFillFinalize(Boolean autoFillFinalize) {
        this.autoFillFinalize = autoFillFinalize;
    }

    public Boolean getAutoFinish() {
        return autoFinish;
    }

    public void setAutoFinish(Boolean autoFinish) {
        this.autoFinish = autoFinish;
    }

    public Boolean getSignInOrder() {
        return signInOrder;
    }

    public void setSignInOrder(Boolean signInOrder) {
        this.signInOrder = signInOrder;
    }

    public String getAbolishedSignTaskId() {
        return abolishedSignTaskId;
    }

    public void setAbolishedSignTaskId(String abolishedSignTaskId) {
        this.abolishedSignTaskId = abolishedSignTaskId;
    }

    public String getOriginalSignTaskId() {
        return originalSignTaskId;
    }

    public void setOriginalSignTaskId(String originalSignTaskId) {
        this.originalSignTaskId = originalSignTaskId;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public List<Watermark> getWatermarks() {
        return watermarks;
    }

    public void setWatermarks(List<Watermark> watermarks) {
        this.watermarks = watermarks;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }
}

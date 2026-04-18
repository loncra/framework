package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * 2021/9/13 11:40:45
 */
public class CreateSignTaskBaseReq extends BaseReq {
    private OpenId initiator;
    private String initiatorMemberId;
    private String initiatorEntityId;
    private String signTaskSubject;
    private String signDocType;
    private String businessCode;
    private Long businessTypeId;
    private String startApprovalFlowId;
    private String finalizeApprovalFlowId;
    private String expiresTime;
    private String dueDate;
    private String catalogId;
    private Boolean autoStart;
    private Boolean autoFinish;
    private Boolean autoFillFinalize;
    private String certCAOrg;
    private String businessId;
    private String transReferenceId;
    private String fileFormat;
    private String callbackUrl;

    public String getInitiatorEntityId() {
        return initiatorEntityId;
    }

    public void setInitiatorEntityId(String initiatorEntityId) {
        this.initiatorEntityId = initiatorEntityId;
    }

    public String getStartApprovalFlowId() {
        return startApprovalFlowId;
    }

    public void setStartApprovalFlowId(String startApprovalFlowId) {
        this.startApprovalFlowId = startApprovalFlowId;
    }

    public String getFinalizeApprovalFlowId() {
        return finalizeApprovalFlowId;
    }

    public void setFinalizeApprovalFlowId(String finalizeApprovalFlowId) {
        this.finalizeApprovalFlowId = finalizeApprovalFlowId;
    }

    public String getSignDocType() {
        return signDocType;
    }

    public void setSignDocType(String signDocType) {
        this.signDocType = signDocType;
    }

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }

    public Boolean getAutoFillFinalize() {
        return autoFillFinalize;
    }

    public void setAutoFillFinalize(Boolean autoFillFinalize) {
        this.autoFillFinalize = autoFillFinalize;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }

    public Boolean getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(Boolean autoStart) {
        this.autoStart = autoStart;
    }

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

    public Boolean getAutoFinish() {
        return autoFinish;
    }

    public void setAutoFinish(Boolean autoFinish) {
        this.autoFinish = autoFinish;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(String initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}

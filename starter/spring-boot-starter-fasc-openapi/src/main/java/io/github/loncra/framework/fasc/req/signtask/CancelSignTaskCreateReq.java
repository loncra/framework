package io.github.loncra.framework.fasc.req.signtask;


import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.ActorCorpMember;
import io.github.loncra.framework.fasc.bean.common.Field;
import io.github.loncra.framework.fasc.bean.common.Notification;

import java.util.List;

public class CancelSignTaskCreateReq extends BaseReq {

    private String signTaskId;

    private AbolishedInitiator abolishedInitiator;

    private String docSource;

    private String reason;

    private List<CancelDoc> docs;

    private List<CancelSignTaskActor> actors;

    private Boolean autoStart;

    private String signTaskSubject;

    private String expiresTime;

    private Long businessTypeId;

    private String businessCode;

    private String initiatorMemberId;

    private String abolishApprovalFlowId;

    private String catalogId;

    private String businessId;

    private String transReferenceId;

    private String certCAOrg;

    private String fileFormat;

    public String getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(String initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public String getAbolishApprovalFlowId() {
        return abolishApprovalFlowId;
    }

    public void setAbolishApprovalFlowId(String abolishApprovalFlowId) {
        this.abolishApprovalFlowId = abolishApprovalFlowId;
    }

    public static class AbolishedInitiator {
        private String initiatorId;
        private String actorId;

        public String getInitiatorId() {
            return initiatorId;
        }

        public void setInitiatorId(String initiatorId) {
            this.initiatorId = initiatorId;
        }

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }
    }

    public static class CancelDoc {
        private String docId;

        private String docName;

        private String docFileId;

        private List<Field> docFields;

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public String getDocFileId() {
            return docFileId;
        }

        public void setDocFileId(String docFileId) {
            this.docFileId = docFileId;
        }

        public List<Field> getDocFields() {
            return docFields;
        }

        public void setDocFields(List<Field> docFields) {
            this.docFields = docFields;
        }
    }

    public static class CancelSignTaskActor {
        private CancelActor actor;

        private List<CancelSignActorField> signFields;

        private CancelSignConfigInfo signConfigInfo;

        public CancelActor getActor() {
            return actor;
        }

        public void setActor(CancelActor actor) {
            this.actor = actor;
        }

        public List<CancelSignActorField> getSignFields() {
            return signFields;
        }

        public void setSignFields(List<CancelSignActorField> signFields) {
            this.signFields = signFields;
        }

        public CancelSignConfigInfo getSignConfigInfo() {
            return signConfigInfo;
        }

        public void setSignConfigInfo(CancelSignConfigInfo signConfigInfo) {
            this.signConfigInfo = signConfigInfo;
        }
    }

    public static class CancelActor {
        private String actorId;

        private String actorType;

        private String actorName;

        private List<String> permissions;

        private String actorOpenId;

        private String actorFDDId;

        private List<ActorCorpMember> actorCorpMembers;

        private String identNameForMatch;

        private String certNoForMatch;

        private String certType;

        private String accountName;

        private Notification notification;

        private Boolean sendNotification;

        private List<String> notifyType;

        private String notifyAddress;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public String getActorType() {
            return actorType;
        }

        public void setActorType(String actorType) {
            this.actorType = actorType;
        }

        public String getActorName() {
            return actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions;
        }

        public String getActorOpenId() {
            return actorOpenId;
        }

        public void setActorOpenId(String actorOpenId) {
            this.actorOpenId = actorOpenId;
        }

        public String getActorFDDId() {
            return actorFDDId;
        }

        public void setActorFDDId(String actorFDDId) {
            this.actorFDDId = actorFDDId;
        }

        public List<ActorCorpMember> getActorCorpMembers() {
            return actorCorpMembers;
        }

        public void setActorCorpMembers(List<ActorCorpMember> actorCorpMembers) {
            this.actorCorpMembers = actorCorpMembers;
        }

        public String getIdentNameForMatch() {
            return identNameForMatch;
        }

        public void setIdentNameForMatch(String identNameForMatch) {
            this.identNameForMatch = identNameForMatch;
        }

        public String getCertNoForMatch() {
            return certNoForMatch;
        }

        public void setCertNoForMatch(String certNoForMatch) {
            this.certNoForMatch = certNoForMatch;
        }

        public Notification getNotification() {
            return notification;
        }

        public void setNotification(Notification notification) {
            this.notification = notification;
        }

        public String getCertType() {
            return certType;
        }

        public void setCertType(String certType) {
            this.certType = certType;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Boolean getSendNotification() {
            return sendNotification;
        }

        public void setSendNotification(Boolean sendNotification) {
            this.sendNotification = sendNotification;
        }

        public List<String> getNotifyType() {
            return notifyType;
        }

        public void setNotifyType(List<String> notifyType) {
            this.notifyType = notifyType;
        }

        public String getNotifyAddress() {
            return notifyAddress;
        }

        public void setNotifyAddress(String notifyAddress) {
            this.notifyAddress = notifyAddress;
        }
    }

    public static class CancelSignActorField {

        private String fieldDocId;

        private String fieldId;

        private String fieldName;

        private Long sealId;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldDocId() {
            return fieldDocId;
        }

        public void setFieldDocId(String fieldDocId) {
            this.fieldDocId = fieldDocId;
        }

        public String getFieldId() {
            return fieldId;
        }

        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }

        public Long getSealId() {
            return sealId;
        }

        public void setSealId(Long sealId) {
            this.sealId = sealId;
        }
    }

    public static class CancelSignConfigInfo {
        private Boolean requestVerifyFree;
        private Long freeDragSealId;
        private Boolean signAllDoc;

        public Boolean getRequestVerifyFree() {
            return requestVerifyFree;
        }

        public void setRequestVerifyFree(Boolean requestVerifyFree) {
            this.requestVerifyFree = requestVerifyFree;
        }

        public Long getFreeDragSealId() {
            return freeDragSealId;
        }

        public void setFreeDragSealId(Long freeDragSealId) {
            this.freeDragSealId = freeDragSealId;
        }

        public Boolean getSignAllDoc() {
            return signAllDoc;
        }

        public void setSignAllDoc(Boolean signAllDoc) {
            this.signAllDoc = signAllDoc;
        }
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public AbolishedInitiator getAbolishedInitiator() {
        return abolishedInitiator;
    }

    public void setAbolishedInitiator(AbolishedInitiator abolishedInitiator) {
        this.abolishedInitiator = abolishedInitiator;
    }

    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<CancelDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<CancelDoc> docs) {
        this.docs = docs;
    }

    public List<CancelSignTaskActor> getActors() {
        return actors;
    }

    public void setActors(List<CancelSignTaskActor> actors) {
        this.actors = actors;
    }

    public Boolean getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(Boolean autoStart) {
        this.autoStart = autoStart;
    }

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
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

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
}
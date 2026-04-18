package io.github.loncra.framework.fasc.res.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Field;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:30
 */
public class AppSignTemplateDetailRes extends BaseBean {
    private String appSignTemplateId;
    private String appSignTemplateName;
    private String appSignTemplateStatus;
    private String certCAOrg;
    private Boolean signInOrder;
    private List<Doc> docs;
    private List<Attach> attachs;
    private List<SignTaskActor> actors;

    public String getAppSignTemplateId() {
        return appSignTemplateId;
    }

    public void setAppSignTemplateId(String appSignTemplateId) {
        this.appSignTemplateId = appSignTemplateId;
    }

    public String getAppSignTemplateName() {
        return appSignTemplateName;
    }

    public void setAppSignTemplateName(String appSignTemplateName) {
        this.appSignTemplateName = appSignTemplateName;
    }

    public String getAppSignTemplateStatus() {
        return appSignTemplateStatus;
    }

    public void setAppSignTemplateStatus(String appSignTemplateStatus) {
        this.appSignTemplateStatus = appSignTemplateStatus;
    }

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

    public Boolean getSignInOrder() {
        return signInOrder;
    }

    public void setSignInOrder(Boolean signInOrder) {
        this.signInOrder = signInOrder;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public List<Attach> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<Attach> attachs) {
        this.attachs = attachs;
    }

    public List<SignTaskActor> getActors() {
        return actors;
    }

    public void setActors(List<SignTaskActor> actors) {
        this.actors = actors;
    }

    public static class Attach {
        private Integer attachId;
        private String attachName;

        public Integer getAttachId() {
            return attachId;
        }

        public void setAttachId(Integer attachId) {
            this.attachId = attachId;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }
    }

    public static class Doc {
        private Integer docId;
        private String docName;
        private List<Field> docFields;

        public Integer getDocId() {
            return docId;
        }

        public void setDocId(Integer docId) {
            this.docId = docId;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public List<Field> getDocFields() {
            return docFields;
        }

        public void setDocFields(List<Field> docFields) {
            this.docFields = docFields;
        }
    }

    public static class SignTaskActor {
        private ActorInfo actorInfo;
        private List<FillField> fillFields;
        private List<SignField> signFields;
        private SignConfigInfo signConfigInfo;

        public ActorInfo getActorInfo() {
            return actorInfo;
        }

        public void setActorInfo(ActorInfo actorInfo) {
            this.actorInfo = actorInfo;
        }

        public List<FillField> getFillFields() {
            return fillFields;
        }

        public void setFillFields(List<FillField> fillFields) {
            this.fillFields = fillFields;
        }

        public List<SignField> getSignFields() {
            return signFields;
        }

        public void setSignFields(List<SignField> signFields) {
            this.signFields = signFields;
        }

        public SignConfigInfo getSignConfigInfo() {
            return signConfigInfo;
        }

        public void setSignConfigInfo(SignConfigInfo signConfigInfo) {
            this.signConfigInfo = signConfigInfo;
        }
    }

    public static class ActorInfo {
        private String actorId;
        @JsonProperty("isInitiator")
        private Boolean isInitiator;
        private String actorType;
        private List<String> permissions;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public Boolean getIsInitiator() {
            return isInitiator;
        }

        public void setIsInitiator(Boolean isInitiator) {
            this.isInitiator = isInitiator;
        }

        public String getActorType() {
            return actorType;
        }

        public void setActorType(String actorType) {
            this.actorType = actorType;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions;
        }
    }

    public static class FillField {
        private String fieldDocId;
        private String fieldId;

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
    }

    public static class SignField {
        private String fieldDocId;
        private String fieldId;

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
    }

    public static class SignConfigInfo {
        private Integer orderNo;
        private Boolean requestMemberSign;
        private String signerSignMethod;
        private List<String> verifyMethods;
        private Boolean readingToEnd;
        private String readingTime;

        public Integer getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(Integer orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean getRequestMemberSign() {
            return requestMemberSign;
        }

        public void setRequestMemberSign(Boolean requestMemberSign) {
            this.requestMemberSign = requestMemberSign;
        }

        public String getSignerSignMethod() {
            return signerSignMethod;
        }

        public void setSignerSignMethod(String signerSignMethod) {
            this.signerSignMethod = signerSignMethod;
        }

        public List<String> getVerifyMethods() {
            return verifyMethods;
        }

        public void setVerifyMethods(List<String> verifyMethods) {
            this.verifyMethods = verifyMethods;
        }

        public Boolean getReadingToEnd() {
            return readingToEnd;
        }

        public void setReadingToEnd(Boolean readingToEnd) {
            this.readingToEnd = readingToEnd;
        }

        public String getReadingTime() {
            return readingTime;
        }

        public void setReadingTime(String readingTime) {
            this.readingTime = readingTime;
        }
    }
}

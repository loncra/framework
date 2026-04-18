package io.github.loncra.framework.fasc.res.voucher;

import java.util.List;

public class GetVoucherTaskDetailRes {
    private OpenId initiator;
    private String signTaskId;
    private String signTaskSubject;
    private String signTaskStatus;
    private String terminationNote;
    private String createTime;
    private String finishTime;
    private List<SignTaskDetailDoc> docs;
    private List<SignTaskDetailAttach> attachs;
    private List<SignTaskActor> actors;
    private List<SignField> signFields;

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
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

    public String getTerminationNote() {
        return terminationNote;
    }

    public void setTerminationNote(String terminationNote) {
        this.terminationNote = terminationNote;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public List<SignTaskDetailDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<SignTaskDetailDoc> docs) {
        this.docs = docs;
    }

    public List<SignTaskDetailAttach> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<SignTaskDetailAttach> attachs) {
        this.attachs = attachs;
    }

    public List<SignTaskActor> getActors() {
        return actors;
    }

    public void setActors(List<SignTaskActor> actors) {
        this.actors = actors;
    }

    public List<SignField> getSignFields() {
        return signFields;
    }

    public void setSignFields(List<SignField> signFields) {
        this.signFields = signFields;
    }

    public static class SignTaskDetailDoc {
        private String docId;
        private String docName;

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
    }

    public static class SignTaskDetailAttach {
        private String attachId;
        private String attachName;

        public String getAttachId() {
            return attachId;
        }

        public void setAttachId(String attachId) {
            this.attachId = attachId;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }
    }

    public static class SignTaskActor {
        private String actorId;
        private String actorName;
        private String signStatus;
        private String actorNote;
        private String signTime;
        private String actorSignTaskEmbedUrl;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public String getActorName() {
            return actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public String getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(String signStatus) {
            this.signStatus = signStatus;
        }

        public String getActorNote() {
            return actorNote;
        }

        public void setActorNote(String actorNote) {
            this.actorNote = actorNote;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getActorSignTaskEmbedUrl() {
            return actorSignTaskEmbedUrl;
        }

        public void setActorSignTaskEmbedUrl(String actorSignTaskEmbedUrl) {
            this.actorSignTaskEmbedUrl = actorSignTaskEmbedUrl;
        }
    }

    public static class OpenId {
        private String idType;
        private String openId;

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }
    }

    public static class SignField {
        private String fieldDocId;
        private String fieldId;
        private String fieldName;
        private Position position;

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

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }

    public static class Position {
        private Integer positionPageNo;
        private String positionX;
        private String positionY;

        public Integer getPositionPageNo() {
            return positionPageNo;
        }

        public void setPositionPageNo(Integer positionPageNo) {
            this.positionPageNo = positionPageNo;
        }

        public String getPositionX() {
            return positionX;
        }

        public void setPositionX(String positionX) {
            this.positionX = positionX;
        }

        public String getPositionY() {
            return positionY;
        }

        public void setPositionY(String positionY) {
            this.positionY = positionY;
        }
    }
}

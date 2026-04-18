package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class ListSignTaskFillField extends BaseBean {
    private String fieldId;
    private String fieldName;
    private String fieldValue;
    private String fieldType;
    private String actorId;
    private String actorName;
    private String docId;
    private String docName;
    private Position position;

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

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

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

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

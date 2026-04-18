package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class SignFieldRes extends BaseBean {
    private String signFieldStatus;
    private String fieldDocId;
    private String fieldId;
    private String fieldName;
    private Boolean moveable;
    private Position position;
    private String signRemark;
    private Long sealId;
    private String categoryType;

    public String getSignRemark() {
        return signRemark;
    }

    public void setSignRemark(String signRemark) {
        this.signRemark = signRemark;
    }

    public String getSignFieldStatus() {
        return signFieldStatus;
    }

    public void setSignFieldStatus(String signFieldStatus) {
        this.signFieldStatus = signFieldStatus;
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getMoveable() {
        return moveable;
    }

    public void setMoveable(Boolean moveable) {
        this.moveable = moveable;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getSealId() {
        return sealId;
    }

    public void setSealId(Long sealId) {
        this.sealId = sealId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}

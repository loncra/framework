package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/10/16 16:36:13
 */
public class Field extends BaseBean {
    private String fieldId;
    private String fieldName;
    private String fieldKey;
    private FieldPosition position;
    private String fieldType;
    private FieldTextSingleLine fieldTextSingleLine;
    private FieldTextMultiLine fieldTextMultiLine;
    private FieldCheckBox fieldCheckBox;
    private FieldMultiCheckbox fieldMultiCheckbox;
    private FieldRemarkSign fieldRemarkSign;
    private Boolean moveable;
    private FieldNumber fieldNumber;
    private FieldIdCard fieldIdCard;
    private FieldFillDate fieldFillDate;
    private FieldMultiRadio fieldMultiRadio;
    private FieldPicture fieldPicture;
    private FieldSelectBox fieldSelectBox;
    private FieldTable fieldTable;
    private FieldPersonSign fieldPersonSign;
    private FieldCorpSeal fieldCorpSeal;
    private FieldDateSign fieldDateSign;

    public FieldMultiCheckbox getFieldMultiCheckbox() {
        return fieldMultiCheckbox;
    }

    public void setFieldMultiCheckbox(FieldMultiCheckbox fieldMultiCheckbox) {
        this.fieldMultiCheckbox = fieldMultiCheckbox;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
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

    public FieldPosition getPosition() {
        return position;
    }

    public void setPosition(FieldPosition position) {
        this.position = position;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public FieldTextSingleLine getFieldTextSingleLine() {
        return fieldTextSingleLine;
    }

    public void setFieldTextSingleLine(FieldTextSingleLine fieldTextSingleLine) {
        this.fieldTextSingleLine = fieldTextSingleLine;
    }

    public FieldTextMultiLine getFieldTextMultiLine() {
        return fieldTextMultiLine;
    }

    public void setFieldTextMultiLine(FieldTextMultiLine fieldTextMultiLine) {
        this.fieldTextMultiLine = fieldTextMultiLine;
    }

    public FieldCheckBox getFieldCheckBox() {
        return fieldCheckBox;
    }

    public void setFieldCheckBox(FieldCheckBox fieldCheckBox) {
        this.fieldCheckBox = fieldCheckBox;
    }

    public FieldRemarkSign getFieldRemarkSign() {
        return fieldRemarkSign;
    }

    public void setFieldRemarkSign(FieldRemarkSign fieldRemarkSign) {
        this.fieldRemarkSign = fieldRemarkSign;
    }

    public Boolean getMoveable() {
        return moveable;
    }

    public void setMoveable(Boolean moveable) {
        this.moveable = moveable;
    }

    public FieldNumber getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(FieldNumber fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public FieldIdCard getFieldIdCard() {
        return fieldIdCard;
    }

    public void setFieldIdCard(FieldIdCard fieldIdCard) {
        this.fieldIdCard = fieldIdCard;
    }

    public FieldFillDate getFieldFillDate() {
        return fieldFillDate;
    }

    public void setFieldFillDate(FieldFillDate fieldFillDate) {
        this.fieldFillDate = fieldFillDate;
    }

    public FieldMultiRadio getFieldMultiRadio() {
        return fieldMultiRadio;
    }

    public void setFieldMultiRadio(FieldMultiRadio fieldMultiRadio) {
        this.fieldMultiRadio = fieldMultiRadio;
    }

    public FieldPicture getFieldPicture() {
        return fieldPicture;
    }

    public void setFieldPicture(FieldPicture fieldPicture) {
        this.fieldPicture = fieldPicture;
    }

    public FieldSelectBox getFieldSelectBox() {
        return fieldSelectBox;
    }

    public void setFieldSelectBox(FieldSelectBox fieldSelectBox) {
        this.fieldSelectBox = fieldSelectBox;
    }

    public FieldTable getFieldTable() {
        return fieldTable;
    }

    public void setFieldTable(FieldTable fieldTable) {
        this.fieldTable = fieldTable;
    }

    public FieldPersonSign getFieldPersonSign() {
        return fieldPersonSign;
    }

    public void setFieldPersonSign(FieldPersonSign fieldPersonSign) {
        this.fieldPersonSign = fieldPersonSign;
    }

    public FieldCorpSeal getFieldCorpSeal() {
        return fieldCorpSeal;
    }

    public void setFieldCorpSeal(FieldCorpSeal fieldCorpSeal) {
        this.fieldCorpSeal = fieldCorpSeal;
    }

    public FieldDateSign getFieldDateSign() {
        return fieldDateSign;
    }

    public void setFieldDateSign(FieldDateSign fieldDateSign) {
        this.fieldDateSign = fieldDateSign;
    }
}

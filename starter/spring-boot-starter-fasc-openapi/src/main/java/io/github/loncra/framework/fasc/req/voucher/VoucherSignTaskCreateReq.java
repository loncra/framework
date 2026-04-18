package io.github.loncra.framework.fasc.req.voucher;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

public class VoucherSignTaskCreateReq extends BaseReq {

    private OpenId initiator;

    private String signTaskSubject;

    private String transReferenceId;

    private List<Doc> docs;

    private List<SignTaskActor> actors;

    private List<Attach> attachs;

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public List<SignTaskActor> getActors() {
        return actors;
    }

    public void setActors(List<SignTaskActor> actors) {
        this.actors = actors;
    }

    public List<Attach> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<Attach> attachs) {
        this.attachs = attachs;
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

    public static class Doc {

        private String docId;

        private String docName;

        private String docFileId;

        private String docTemplateId;
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

        public String getDocTemplateId() {
            return docTemplateId;
        }

        public void setDocTemplateId(String docTemplateId) {
            this.docTemplateId = docTemplateId;
        }

        public List<Field> getDocFields() {
            return docFields;
        }

        public void setDocFields(List<Field> docFields) {
            this.docFields = docFields;
        }
    }

    public static class Field {

        private String fieldId;

        private String fieldName;

        private String fieldType;

        private String fieldKey;

        private FieldPosition position;

//        private Boolean moveable;

        private FieldPersonSign fieldPersonSign;

        private FieldTextSingleLineInfo fieldTextSingleLine;

        private FieldTextMultiLineInfo fieldTextMultiLine;

        private FieldTable fieldTable;

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

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldKey() {
            return fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public FieldPosition getPosition() {
            return position;
        }

        public void setPosition(FieldPosition position) {
            this.position = position;
        }

        public FieldPersonSign getFieldPersonSign() {
            return fieldPersonSign;
        }

        public void setFieldPersonSign(FieldPersonSign fieldPersonSign) {
            this.fieldPersonSign = fieldPersonSign;
        }

        public FieldTextSingleLineInfo getFieldTextSingleLine() {
            return fieldTextSingleLine;
        }

        public void setFieldTextSingleLine(FieldTextSingleLineInfo fieldTextSingleLine) {
            this.fieldTextSingleLine = fieldTextSingleLine;
        }

        public FieldTextMultiLineInfo getFieldTextMultiLine() {
            return fieldTextMultiLine;
        }

        public void setFieldTextMultiLine(FieldTextMultiLineInfo fieldTextMultiLine) {
            this.fieldTextMultiLine = fieldTextMultiLine;
        }

        public FieldTable getFieldTable() {
            return fieldTable;
        }

        public void setFieldTable(FieldTable fieldTable) {
            this.fieldTable = fieldTable;
        }
    }

    public static class SignTaskActor {

        private String actorId;

        private String actorName;

        private List<FillField> fillFields;

        private List<SignField> signFields;

        private SignConfigInfo signConfigInfo;

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

    public static class FillField {

        private String fieldDocId;

        private String fieldId;

        private String fieldName;

        private String fieldValue;

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

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }
    }

    public static class SignField {

        private String fieldDocId;

        private String fieldId;

        private String fieldName;

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
    }

    public static class SignConfigInfo {

        private String signerSignMethod;

        private Boolean readingToEnd;

        private Integer readingTime;

        public String getSignerSignMethod() {
            return signerSignMethod;
        }

        public void setSignerSignMethod(String signerSignMethod) {
            this.signerSignMethod = signerSignMethod;
        }

        public Boolean getReadingToEnd() {
            return readingToEnd;
        }

        public void setReadingToEnd(Boolean readingToEnd) {
            this.readingToEnd = readingToEnd;
        }

        public Integer getReadingTime() {
            return readingTime;
        }

        public void setReadingTime(Integer readingTime) {
            this.readingTime = readingTime;
        }
    }

    public static class Attach {

        private String attachId;

        private String attachName;

        private String attachFileId;

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

        public String getAttachFileId() {
            return attachFileId;
        }

        public void setAttachFileId(String attachFileId) {
            this.attachFileId = attachFileId;
        }
    }


    public static class FieldPosition {

        private String positionMode;

        private Integer positionPageNo;

        private String positionX;

        private String positionY;

        private String positionKeyword;

        private String keywordOffsetX;

        private String keywordOffsetY;

        public String getPositionMode() {
            return positionMode;
        }

        public void setPositionMode(String positionMode) {
            this.positionMode = positionMode;
        }

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

        public String getPositionKeyword() {
            return positionKeyword;
        }

        public void setPositionKeyword(String positionKeyword) {
            this.positionKeyword = positionKeyword;
        }

        public String getKeywordOffsetX() {
            return keywordOffsetX;
        }

        public void setKeywordOffsetX(String keywordOffsetX) {
            this.keywordOffsetX = keywordOffsetX;
        }

        public String getKeywordOffsetY() {
            return keywordOffsetY;
        }

        public void setKeywordOffsetY(String keywordOffsetY) {
            this.keywordOffsetY = keywordOffsetY;
        }
    }

    public static class FieldCorpSeal {

        private Integer width;

        private Integer height;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }

    public static class FieldPersonSign {

        private Integer width;

        private Integer height;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }


    public static class BaseFieldPropertiesInfo {

        private Boolean required;

        private String fontType;

        private Integer fontSize;

        private Integer width;

        private Integer height;

        private String alignment;

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public String getFontType() {
            return fontType;
        }

        public void setFontType(String fontType) {
            this.fontType = fontType;
        }

        public Integer getFontSize() {
            return fontSize;
        }

        public void setFontSize(Integer fontSize) {
            this.fontSize = fontSize;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getAlignment() {
            return alignment;
        }

        public void setAlignment(String alignment) {
            this.alignment = alignment;
        }
    }


    public static class FieldTextSingleLineInfo extends BaseFieldPropertiesInfo {

        private String defaultValue;

        private String tips;

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public class FieldTextMultiLineInfo extends BaseFieldPropertiesInfo {

        private String defaultValue;

        private String tips;

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public static class FieldTable {

        private Boolean required;

        private Integer requiredCount;

        private String fontType;

        private Integer fontSize;

        private String alignment;

        private String headerPosition;

        private Integer rows;

        private Integer cols;

        private Integer rowHeight;

        private List<Integer> widths;

        private Boolean dynamicFilling;

        private List<List<String>> defaultValue;

        private List<String> header;

        private Boolean hideHeader;

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }

        public Integer getRequiredCount() {
            return requiredCount;
        }

        public void setRequiredCount(Integer requiredCount) {
            this.requiredCount = requiredCount;
        }

        public String getFontType() {
            return fontType;
        }

        public void setFontType(String fontType) {
            this.fontType = fontType;
        }

        public Integer getFontSize() {
            return fontSize;
        }

        public void setFontSize(Integer fontSize) {
            this.fontSize = fontSize;
        }

        public String getAlignment() {
            return alignment;
        }

        public void setAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String getHeaderPosition() {
            return headerPosition;
        }

        public void setHeaderPosition(String headerPosition) {
            this.headerPosition = headerPosition;
        }

        public Integer getRows() {
            return rows;
        }

        public void setRows(Integer rows) {
            this.rows = rows;
        }

        public Integer getCols() {
            return cols;
        }

        public void setCols(Integer cols) {
            this.cols = cols;
        }

        public Integer getRowHeight() {
            return rowHeight;
        }

        public void setRowHeight(Integer rowHeight) {
            this.rowHeight = rowHeight;
        }

        public List<Integer> getWidths() {
            return widths;
        }

        public void setWidths(List<Integer> widths) {
            this.widths = widths;
        }

        public Boolean getDynamicFilling() {
            return dynamicFilling;
        }

        public void setDynamicFilling(Boolean dynamicFilling) {
            this.dynamicFilling = dynamicFilling;
        }

        public List<List<String>> getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(List<List<String>> defaultValue) {
            this.defaultValue = defaultValue;
        }

        public List<String> getHeader() {
            return header;
        }

        public void setHeader(List<String> header) {
            this.header = header;
        }

        public Boolean getHideHeader() {
            return hideHeader;
        }

        public void setHideHeader(Boolean hideHeader) {
            this.hideHeader = hideHeader;
        }
    }


}
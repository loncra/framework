package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author fadada
 * @date 2023/8/10 14:26
 */
public class DocTemplateFillValuesReq extends BaseReq {

    private String openCorpId;

    private String docTemplateId;

    private String fileName;

    private List<DocFieldValue> docFieldValues;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getDocTemplateId() {
        return docTemplateId;
    }

    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<DocFieldValue> getDocFieldValues() {
        return docFieldValues;
    }

    public void setDocFieldValues(List<DocFieldValue> docFieldValues) {
        this.docFieldValues = docFieldValues;
    }

    public static class DocFieldValue {

        private String fieldId;

        private String fieldValue;

        private String fieldName;

        public String getFieldId() {
            return fieldId;
        }

        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }
    }
}

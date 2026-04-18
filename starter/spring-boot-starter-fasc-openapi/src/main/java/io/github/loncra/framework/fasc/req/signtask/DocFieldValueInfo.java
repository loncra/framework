package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * @date 2021/12/6 14:24:34
 */
public class DocFieldValueInfo extends BaseBean {
    private String docId;
    private String fieldId;
    private String fieldKey;
    private String fieldValue;
    private String fieldName;

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

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

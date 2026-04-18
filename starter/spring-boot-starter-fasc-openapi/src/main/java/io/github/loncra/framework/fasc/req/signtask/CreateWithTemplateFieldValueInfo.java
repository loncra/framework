package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 17:10:56
 */
public class CreateWithTemplateFieldValueInfo extends BaseBean {
    private Integer fieldDocId;
    private String fieldId;
    private String fieldName;
    private String fieldValue;

    public Integer getFieldDocId() {
        return fieldDocId;
    }

    public void setFieldDocId(Integer fieldDocId) {
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

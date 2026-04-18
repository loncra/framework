package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/10/23 17:35:58
 */
public class AddSignActorFieldInfo extends BaseBean {
    private Integer fieldDocId;
    private String fieldId;
    private String fieldName;

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
}
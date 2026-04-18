package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/10/18 11:32:44
 */
public class FillActorFieldInfo extends BaseBean {
    private Integer fieldDocId;
    private String fieldId;

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
}
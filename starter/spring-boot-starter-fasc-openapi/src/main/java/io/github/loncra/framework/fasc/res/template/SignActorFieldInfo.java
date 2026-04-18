package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/10/18 11:31:39
 */
public class SignActorFieldInfo extends BaseBean {
    private Integer fieldDocId;
    private String fieldId;
    private Long sealId;
    private String categoryType;

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
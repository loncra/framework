package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.TemplateField;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 15:16:27
 */
public class DocTemplateDetailRes extends BaseBean {
    private String docTemplateId;
    private String docTemplateName;
    private String docTemplateStatus;
    private List<TemplateField> docFields;
    private String catalogName;
    private String createSerialNo;
    private String storageType;

    public String getDocTemplateId() {
        return docTemplateId;
    }

    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    public String getDocTemplateName() {
        return docTemplateName;
    }

    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    public String getDocTemplateStatus() {
        return docTemplateStatus;
    }

    public void setDocTemplateStatus(String docTemplateStatus) {
        this.docTemplateStatus = docTemplateStatus;
    }

    public List<TemplateField> getDocFields() {
        return docFields;
    }

    public void setDocFields(List<TemplateField> docFields) {
        this.docFields = docFields;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}

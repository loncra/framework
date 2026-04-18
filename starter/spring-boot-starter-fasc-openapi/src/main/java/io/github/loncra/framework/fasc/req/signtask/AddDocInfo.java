package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Field;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:24:04
 */
public class AddDocInfo extends BaseBean {
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

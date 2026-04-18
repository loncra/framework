package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.TemplateField;

import java.util.List;

/**
 * @author Fadada
 * 2021/10/18 11:31:59
 */
public class DocumentInfo extends BaseBean {
    private Integer docId;
    private String docName;
    private List<TemplateField> docFields;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<TemplateField> getDocFields() {
        return docFields;
    }

    public void setDocFields(List<TemplateField> docFields) {
        this.docFields = docFields;
    }
}

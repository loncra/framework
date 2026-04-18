package io.github.loncra.framework.fasc.res.template;


import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @Author： fadada
 * @Date: 2023/8/1
 */
public class CopyCreateDocTemplateRes extends BaseBean {

    private String docTemplateId;

    private String docTemplateName;

    private String createSerialNo;

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

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}

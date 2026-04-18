package io.github.loncra.framework.fasc.req;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： fadada
 * @Date: 2023年8月1日
 */
public class CopyCreateDocTemplateReq extends BaseReq {

    private String openCorpId;

    private String docTemplateId;

    private String docTemplateName;

    private String createSerialNo;

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

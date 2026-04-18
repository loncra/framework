package io.github.loncra.framework.fasc.req.template;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeleteDocTemplateReq extends BaseReq {

    /**
     * 文档模板id
     */
    private String docTemplateId;

    /**
     * 法大大平台为该企业在该应用appId范围内分配的唯一标识。长度最大64个字符
     */
    private String openCorpId;

    public String getDocTemplateId() {
        return docTemplateId;
    }

    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}

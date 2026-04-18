package io.github.loncra.framework.fasc.req.template;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class SetDocTemplateStatusReq extends BaseReq {

    /**
     * 法大大平台为该企业在该应用appId范围内分配的唯一标识。长度最大64个字符
     */
    private String openCorpId;

    /**
     * 文档模板id
     */
    private String docTemplateId;

    /**
     * 设置应用文档任务模板状态为启用或停用：invalid: 停用,valid: 启用
     */
    private String docTemplateStatus;

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

    public String getDocTemplateStatus() {
        return docTemplateStatus;
    }

    public void setDocTemplateStatus(String docTemplateStatus) {
        this.docTemplateStatus = docTemplateStatus;
    }
}

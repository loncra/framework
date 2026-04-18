package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeleteSignTemplateReq extends BaseReq {

    /**
     * 签署模板id
     */
    private String signTemplateId;

    /**
     * 法大大平台为该企业在该应用appId范围内分配的唯一标识。长度最大64个字符
     */
    private String openCorpId;

    public String getSignTemplateId() {
        return signTemplateId;
    }

    public void setSignTemplateId(String signTemplateId) {
        this.signTemplateId = signTemplateId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}
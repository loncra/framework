package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class SetSignTemplateStatusReq extends BaseReq {

    /**
     * 法大大平台为该企业在该应用appId范围内分配的唯一标识。长度最大64个字符
     */
    private String openCorpId;

    /**
     * 模板id
     */
    private String signTemplateId;

    /**
     * 设置模板状态为启用或停用：invalid: 停用,valid: 启用
     */
    private String signTemplateStatus;


    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getSignTemplateId() {
        return signTemplateId;
    }

    public void setSignTemplateId(String signTemplateId) {
        this.signTemplateId = signTemplateId;
    }

    public String getSignTemplateStatus() {
        return signTemplateStatus;
    }

    public void setSignTemplateStatus(String signTemplateStatus) {
        this.signTemplateStatus = signTemplateStatus;
    }
}

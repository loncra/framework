package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeleteSignAppTemplateReq extends BaseReq {

    private String appSignTemplateId;

    public String getAppSignTemplateId() {
        return appSignTemplateId;
    }

    public void setAppSignTemplateId(String appSignTemplateId) {
        this.appSignTemplateId = appSignTemplateId;
    }
}

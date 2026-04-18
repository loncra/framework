package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeleteAppTemplateReq extends BaseReq {

    private String appDocTemplateId;

    public String getAppDocTemplateId() {
        return appDocTemplateId;
    }

    public void setAppDocTemplateId(String appDocTemplateId) {
        this.appDocTemplateId = appDocTemplateId;
    }
}

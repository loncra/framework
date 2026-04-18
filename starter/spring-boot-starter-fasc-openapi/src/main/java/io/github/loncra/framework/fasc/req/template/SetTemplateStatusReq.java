package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class SetTemplateStatusReq extends BaseReq {

    private String appDocTemplateId;
    private String appDocTemplateStatus;

    public String getAppDocTemplateId() {
        return appDocTemplateId;
    }

    public void setAppDocTemplateId(String appDocTemplateId) {
        this.appDocTemplateId = appDocTemplateId;
    }

    public String getAppDocTemplateStatus() {
        return appDocTemplateStatus;
    }

    public void setAppDocTemplateStatus(String appDocTemplateStatus) {
        this.appDocTemplateStatus = appDocTemplateStatus;
    }
}

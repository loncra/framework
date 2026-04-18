package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class SetAppSignTemplateStatusReq extends BaseReq {

    /**
     * 签署模板id
     */
    private String appSignTemplateId;

    /**
     * 设置应用签署任务模板状态为启用或停用：invalid: 停用,valid: 启用
     */
    private String appSignTemplateStatus;

    public String getAppSignTemplateId() {
        return appSignTemplateId;
    }

    public void setAppSignTemplateId(String appSignTemplateId) {
        this.appSignTemplateId = appSignTemplateId;
    }

    public String getAppSignTemplateStatus() {
        return appSignTemplateStatus;
    }

    public void setAppSignTemplateStatus(String appSignTemplateStatus) {
        this.appSignTemplateStatus = appSignTemplateStatus;
    }
}

package io.github.loncra.framework.fasc.res.approval;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class GetApprovalUrlRes extends BaseBean {

    private String approvalUrl;

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }
}
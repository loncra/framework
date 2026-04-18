package io.github.loncra.framework.fasc.req.signtask;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeleteSignTaskReq extends BaseReq {

    private String signTaskId;

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }
}

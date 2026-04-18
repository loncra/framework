package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetCorpIdentTransactionIdReq extends BaseReq {

    private String openCorpId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}

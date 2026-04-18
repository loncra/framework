package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * 2021/9/11 14:07:49
 */
public class GetCorpReq extends BaseReq {
    private String corpIdentNo;

    private String openCorpId;

    private String clientCorpId;

    public String getCorpIdentNo() {
        return corpIdentNo;
    }

    public void setCorpIdentNo(String corpIdentNo) {
        this.corpIdentNo = corpIdentNo;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }
}

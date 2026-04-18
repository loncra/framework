package io.github.loncra.framework.fasc.req.archives;


import io.github.loncra.framework.fasc.bean.base.BasePageReq;

public class ArchivedListReq extends BasePageReq {

    private String openCorpId;

    private String contractType;

    private String signTaskId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }
}

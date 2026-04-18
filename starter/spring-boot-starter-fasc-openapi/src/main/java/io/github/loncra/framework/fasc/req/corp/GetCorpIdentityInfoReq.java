package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * 2021/10/16 17:39:36
 */
public class GetCorpIdentityInfoReq extends BaseReq {
    private String openCorpId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}

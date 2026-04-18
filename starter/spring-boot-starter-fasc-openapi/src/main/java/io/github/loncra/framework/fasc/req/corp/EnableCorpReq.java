package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @date 2021/12/6 10:20:27
 */
public class EnableCorpReq extends BaseReq {
    private String openCorpId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}

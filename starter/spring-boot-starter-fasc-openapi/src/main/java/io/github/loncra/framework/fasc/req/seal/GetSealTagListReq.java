package io.github.loncra.framework.fasc.req.seal;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zpt
 * @Date: 2023/6/26
 */
public class GetSealTagListReq extends BaseReq {

    private String openCorpId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}
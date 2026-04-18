package io.github.loncra.framework.fasc.req.seal;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetSealDetailReq extends BaseReq {

    private String openCorpId;
    private Long sealId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public Long getSealId() {
        return sealId;
    }

    public void setSealId(Long sealId) {
        this.sealId = sealId;
    }
}

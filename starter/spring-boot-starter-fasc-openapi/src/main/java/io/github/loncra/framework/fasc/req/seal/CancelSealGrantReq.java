package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @Date: 2022/10/8
 */

public class CancelSealGrantReq extends BaseReq {

    private String openCorpId;
    private Long sealId;
    private Long memberId;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}

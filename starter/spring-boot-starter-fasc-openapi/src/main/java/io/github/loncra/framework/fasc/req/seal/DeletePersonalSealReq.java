package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DeletePersonalSealReq extends BaseReq {

    private String openUserId;

    /**
     * 需要删除的签名ID
     */
    private Long sealId;

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public Long getSealId() {
        return sealId;
    }

    public void setSealId(Long sealId) {
        this.sealId = sealId;
    }
}




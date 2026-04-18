package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetSignTaskPicDocTicketReq extends BaseReq {

    private String slicingTicketId;

    public String getSlicingTicketId() {
        return slicingTicketId;
    }

    public void setSlicingTicketId(String slicingTicketId) {
        this.slicingTicketId = slicingTicketId;
    }
}
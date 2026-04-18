package io.github.loncra.framework.fasc.req.signtask;

public class AddActorSignConfigInfoReq extends SignConfigInfoBase {
    private Integer orderNo;

    @Override
    public Integer getOrderNo() {
        return orderNo;
    }

    @Override
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}

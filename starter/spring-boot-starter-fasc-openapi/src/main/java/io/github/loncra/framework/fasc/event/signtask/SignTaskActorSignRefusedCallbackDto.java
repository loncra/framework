package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务参与方拒签事件
 */
public class SignTaskActorSignRefusedCallbackDto extends BaseSignTaskActorCallbackDto {

    private String signRejectReason;

    public String getSignRejectReason() {
        return signRejectReason;
    }

    public void setSignRejectReason(String signRejectReason) {
        this.signRejectReason = signRejectReason;
    }
}

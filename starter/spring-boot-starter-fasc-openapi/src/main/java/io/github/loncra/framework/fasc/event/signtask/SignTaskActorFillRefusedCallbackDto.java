package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务参与方拒填事件
 */
public class SignTaskActorFillRefusedCallbackDto extends BaseSignTaskActorCallbackDto {

    private String fillRejectReason;

    public String getFillRejectReason() {
        return fillRejectReason;
    }

    public void setFillRejectReason(String fillRejectReason) {
        this.fillRejectReason = fillRejectReason;
    }
}

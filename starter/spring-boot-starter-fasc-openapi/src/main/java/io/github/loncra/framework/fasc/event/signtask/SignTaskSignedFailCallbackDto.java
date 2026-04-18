package io.github.loncra.framework.fasc.event.signtask;

/**
 * 签署任务签署失败事件
 */
public class SignTaskSignedFailCallbackDto extends BaseSignTaskActorCallbackDto {

    private Boolean verifyFreeSign;

    private String signFailedReason;

    public Boolean getVerifyFreeSign() {
        return verifyFreeSign;
    }

    public void setVerifyFreeSign(Boolean verifyFreeSign) {
        this.verifyFreeSign = verifyFreeSign;
    }

    public String getSignFailedReason() {
        return signFailedReason;
    }

    public void setSignFailedReason(String signFailedReason) {
        this.signFailedReason = signFailedReason;
    }
}

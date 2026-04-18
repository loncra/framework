package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务参与方签署成功事件
 */
public class SignTaskActorSignedCallbackDto extends BaseSignTaskActorCallbackDto {

    private Boolean verifyFreeSign;

    public Boolean getVerifyFreeSign() {
        return verifyFreeSign;
    }

    public void setVerifyFreeSign(Boolean verifyFreeSign) {
        this.verifyFreeSign = verifyFreeSign;
    }
}

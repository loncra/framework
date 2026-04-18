package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章审核不通过事件
 */
public class SealAuditRejectCallBackDto extends EventCallBackDto {

    private Long verifyId;

    private String reason;


    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

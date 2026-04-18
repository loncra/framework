package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章审核撤销事件
 */
public class SealAuditCancelCallBackDto extends EventCallBackDto {

    private Long verifyId;


    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }
}

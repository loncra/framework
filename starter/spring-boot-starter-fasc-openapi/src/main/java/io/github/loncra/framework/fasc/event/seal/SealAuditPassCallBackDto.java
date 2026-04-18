package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章审核通过事件
 */

public class SealAuditPassCallBackDto extends SealBasicCallBackDto {

    private Long verifyId;

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }
}

package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章创建事件
 */
public class SealCreateCallBackDto extends SealBasicCallBackDto {

    private Long verifyId;

    private String createSerialNo;

    public Long getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}

package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章授权免验证签事件
 */
public class FreeSignOpenCallBackDto extends SealBasicCallBackDto {

    private String businessId;

    private String expiresTime;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }
}

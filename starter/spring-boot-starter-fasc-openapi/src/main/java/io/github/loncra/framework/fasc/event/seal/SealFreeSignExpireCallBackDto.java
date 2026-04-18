package io.github.loncra.framework.fasc.event.seal;


/**
 * 印章授权免验证签事件
 */

public class SealFreeSignExpireCallBackDto extends SealBasicCallBackDto {

    private String businessId;

    private String grantEndTime;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getGrantEndTime() {
        return grantEndTime;
    }

    public void setGrantEndTime(String grantEndTime) {
        this.grantEndTime = grantEndTime;
    }
}

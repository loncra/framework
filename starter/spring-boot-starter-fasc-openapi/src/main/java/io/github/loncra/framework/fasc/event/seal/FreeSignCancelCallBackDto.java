package io.github.loncra.framework.fasc.event.seal;

/**
 * 印章免验证签解除事件
 */
public class FreeSignCancelCallBackDto extends SealBasicCallBackDto {

    private String businessId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}

package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class DrivingLicenseOcrReq extends BaseReq {
    private String imageBase64;
    private String backImageBase64;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getBackImageBase64() {
        return backImageBase64;
    }

    public void setBackImageBase64(String backImageBase64) {
        this.backImageBase64 = backImageBase64;
    }
}

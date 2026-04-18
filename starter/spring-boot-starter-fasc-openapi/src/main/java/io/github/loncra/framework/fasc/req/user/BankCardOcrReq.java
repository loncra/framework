package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

public class BankCardOcrReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = -8400448936161073315L;

    private String imageBase64;

    public BankCardOcrReq() {
    }

    public BankCardOcrReq(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}

package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class SignTaskBaseReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 9056652929104110600L;

    private String signTaskId;

    public SignTaskBaseReq() {
    }

    public SignTaskBaseReq(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }
}

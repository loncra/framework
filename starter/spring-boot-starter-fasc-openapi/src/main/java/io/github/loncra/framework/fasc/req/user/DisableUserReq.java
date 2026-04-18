package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

/**
 * @author Fadada
 * @date 2021/12/6 10:20:27
 */
public class DisableUserReq extends BaseReq {
    @Serial
    private static final long serialVersionUID = 3367583457316496173L;

    private String openUserId;

    public DisableUserReq(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
}

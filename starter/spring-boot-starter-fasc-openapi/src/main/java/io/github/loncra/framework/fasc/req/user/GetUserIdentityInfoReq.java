package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

/**
 * @author Fadada
 * 2021/10/16 17:39:36
 */
public class GetUserIdentityInfoReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 1932438482204030158L;

    private String openUserId;

    public GetUserIdentityInfoReq(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
}

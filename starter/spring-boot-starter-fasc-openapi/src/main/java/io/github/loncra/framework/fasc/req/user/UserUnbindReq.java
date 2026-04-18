package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

public class UserUnbindReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = -7129091564279884438L;

    private String openUserId;

    private String clientUserId;

    public static UserUnbindReq ofOpenUserId(String openUserId) {
        UserUnbindReq req = new UserUnbindReq();
        req.setOpenUserId(openUserId);

        return req;
    }

    public static UserUnbindReq ofClientUserId(String clientUserId) {
        UserUnbindReq req = new UserUnbindReq();
        req.setClientUserId(clientUserId);
        return req;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
}

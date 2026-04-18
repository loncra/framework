package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

/**
 * @author Fadada
 * 2021/9/11 14:07:49
 */
public class GetUserReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 6304658402995793229L;

    private String clientUserId;

    private String openUserId;

    public GetUserReq() {
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

    public static GetUserReq ofClientUserId(String clientUserId) {
        GetUserReq req = new GetUserReq();
        req.setClientUserId(clientUserId);
        return req;
    }

    public static GetUserReq ofOpenUserId(String openUserId) {
        GetUserReq req = new GetUserReq();
        req.setOpenUserId(openUserId);
        return req;
    }
}

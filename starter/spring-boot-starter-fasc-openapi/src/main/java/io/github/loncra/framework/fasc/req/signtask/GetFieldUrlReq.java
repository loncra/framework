package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.common.UserIdentInfo;
import io.github.loncra.framework.fasc.bean.common.UserInfoExtend;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class GetFieldUrlReq extends SignTaskBaseReq {
    private String openUserId;
    private UserIdentInfo userIdentInfo;
    private UserInfoExtend userInfoExtend;
    private String redirectUrl;

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public UserIdentInfo getUserIdentInfo() {
        return userIdentInfo;
    }

    public void setUserIdentInfo(UserIdentInfo userIdentInfo) {
        this.userIdentInfo = userIdentInfo;
    }

    public UserInfoExtend getUserInfoExtend() {
        return userInfoExtend;
    }

    public void setUserInfoExtend(UserInfoExtend userInfoExtend) {
        this.userInfoExtend = userInfoExtend;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}

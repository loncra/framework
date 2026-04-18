package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 14:31:32
 */
public class ActorUser extends BaseBean {
    private String actorUserId;
    private UserIdentInfo userIdentInfo;
    private UserInfoExtend userInfoExtend;

    public String getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(String actorUserId) {
        this.actorUserId = actorUserId;
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
}

package io.github.loncra.framework.fasc.res.user;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.UserIdentInfo;
import io.github.loncra.framework.fasc.bean.common.UserInfoExtend;

/**
 * @author Fadada
 * 2021/10/16 17:50:32
 */
public class UserIdentityInfoRes extends BaseBean {
    private String identStatus;
    private UserIdentInfo userIdentInfo;
    private UserInfoExtend userIdentInfoExtend;
    private String identMethod;
    private String identSubmitTime;
    private String identSuccessTime;
    private String openUserId;
    private String fddId;
    private String facePicture;
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFddId() {
        return fddId;
    }

    public void setFddId(String fddId) {
        this.fddId = fddId;
    }

    public String getIdentStatus() {
        return identStatus;
    }

    public void setIdentStatus(String identStatus) {
        this.identStatus = identStatus;
    }

    public UserIdentInfo getUserIdentInfo() {
        return userIdentInfo;
    }

    public void setUserIdentInfo(UserIdentInfo userIdentInfo) {
        this.userIdentInfo = userIdentInfo;
    }

    public UserInfoExtend getUserIdentInfoExtend() {
        return userIdentInfoExtend;
    }

    public void setUserIdentInfoExtend(UserInfoExtend userIdentInfoExtend) {
        this.userIdentInfoExtend = userIdentInfoExtend;
    }

    public String getIdentMethod() {
        return identMethod;
    }

    public void setIdentMethod(String identMethod) {
        this.identMethod = identMethod;
    }

    public String getIdentSubmitTime() {
        return identSubmitTime;
    }

    public void setIdentSubmitTime(String identSubmitTime) {
        this.identSubmitTime = identSubmitTime;
    }

    public String getIdentSuccessTime() {
        return identSuccessTime;
    }

    public void setIdentSuccessTime(String identSuccessTime) {
        this.identSuccessTime = identSuccessTime;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }
}

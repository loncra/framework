package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.io.Serial;
import java.util.List;

/**
 * 个人参与方对象
 */
public class UserIdentInfoReq extends BaseBean {
    @Serial
    private static final long serialVersionUID = 3706832891320294268L;
    private String userName;
    private String userIdentType;
    private String userIdentNo;
    private String mobile;
    private String bankAccountNo;
    private List<String> identMethod;
    private String faceauthMode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdentType() {
        return userIdentType;
    }

    public void setUserIdentType(String userIdentType) {
        this.userIdentType = userIdentType;
    }

    public String getUserIdentNo() {
        return userIdentNo;
    }

    public void setUserIdentNo(String userIdentNo) {
        this.userIdentNo = userIdentNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public List<String> getIdentMethod() {
        return identMethod;
    }

    public void setIdentMethod(List<String> identMethod) {
        this.identMethod = identMethod;
    }

    public String getFaceauthMode() {
        return faceauthMode;
    }

    public void setFaceauthMode(String faceauthMode) {
        this.faceauthMode = faceauthMode;
    }
}

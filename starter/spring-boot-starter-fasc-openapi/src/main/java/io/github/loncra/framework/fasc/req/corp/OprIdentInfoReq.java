package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class OprIdentInfoReq extends BaseBean {
    private String userName;
    private String userIdentType;
    private String userIdentNo;
    private String mobile;
    private String bankAccountNo;
    private List<String> oprIdentMethod;

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

    public List<String> getOprIdentMethod() {
        return oprIdentMethod;
    }

    public void setOprIdentMethod(List<String> oprIdentMethod) {
        this.oprIdentMethod = oprIdentMethod;
    }
}

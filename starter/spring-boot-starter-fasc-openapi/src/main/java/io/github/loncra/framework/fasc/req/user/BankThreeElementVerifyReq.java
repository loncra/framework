package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class BankThreeElementVerifyReq extends BaseReq {
    private String userName;
    private String userIdentNo;
    private String bankAccountNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdentNo() {
        return userIdentNo;
    }

    public void setUserIdentNo(String userIdentNo) {
        this.userIdentNo = userIdentNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
}

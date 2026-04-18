package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

/**
 * @author zhoufucheng
 * @date 2023/6/28 11:40
 */
public class BankFourElementVerifyReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = -8103354881779412016L;

    private String userName;

    private String userIdentNo;

    private String bankAccountNo;

    private String mobile;

    private String userIdentType;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserIdentType() {
        return userIdentType;
    }

    public void setUserIdentType(String userIdentType) {
        this.userIdentType = userIdentType;
    }
}

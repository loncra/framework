package io.github.loncra.framework.fasc.res.user;

import java.util.List;

public class BankCardOcrRes {
    private String serialNo;
    private String bankName;
    private String bankCardType;
    private String bankCardNo;
    private String validDate;
    private List<String> warningMsg;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public List<String> getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(List<String> warningMsg) {
        this.warningMsg = warningMsg;
    }
}

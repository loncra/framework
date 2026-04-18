package io.github.loncra.framework.allin.pay.domain.body.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结算单响应体
 *
 * @author maurice.chen
 */
public class SettleBillResponseBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -1908100204700959157L;

    /**
     * 清算分账时间
     */
    @JsonProperty("clearsplittime")
    private String clearSplitTime;

    /**
     * 预期清算日期
     */
    @JsonProperty("expectclearday")
    private String expectClearDay;

    /**
     * 是否已清算
     */
    @JsonProperty("iscleared")
    private String isCleared;

    /**
     * 手续费
     */
    @JsonProperty("fee")
    private String fee;

    /**
     * 清算金额
     */
    @JsonProperty("clearamt")
    private String clearAmount;

    /**
     * 结算手续费
     */
    @JsonProperty("settfee")
    private String settFee;

    /**
     * 账户名称
     */
    @JsonProperty("acctname")
    private String accountName;

    /**
     * 银行名称
     */
    @JsonProperty("bankname")
    private String bankName;

    /**
     * 账户号码
     */
    @JsonProperty("acctno")
    private String accountNumber;

    /**
     * 构造函数
     */
    public SettleBillResponseBody() {
    }

    /**
     * 获取清算分账时间
     *
     * @return 清算分账时间
     */
    public String getClearSplitTime() {
        return clearSplitTime;
    }

    /**
     * 设置清算分账时间
     *
     * @param clearSplitTime 清算分账时间
     */
    public void setClearSplitTime(String clearSplitTime) {
        this.clearSplitTime = clearSplitTime;
    }

    /**
     * 获取预期清算日期
     *
     * @return 预期清算日期
     */
    public String getExpectClearDay() {
        return expectClearDay;
    }

    /**
     * 设置预期清算日期
     *
     * @param expectClearDay 预期清算日期
     */
    public void setExpectClearDay(String expectClearDay) {
        this.expectClearDay = expectClearDay;
    }

    /**
     * 获取是否已清算
     *
     * @return 是否已清算
     */
    public String getIsCleared() {
        return isCleared;
    }

    /**
     * 设置是否已清算
     *
     * @param isCleared 是否已清算
     */
    public void setIsCleared(String isCleared) {
        this.isCleared = isCleared;
    }

    /**
     * 获取手续费
     *
     * @return 手续费
     */
    public String getFee() {
        return fee;
    }

    /**
     * 设置手续费
     *
     * @param fee 手续费
     */
    public void setFee(String fee) {
        this.fee = fee;
    }

    /**
     * 获取清算金额
     *
     * @return 清算金额
     */
    public String getClearAmount() {
        return clearAmount;
    }

    /**
     * 设置清算金额
     *
     * @param clearAmount 清算金额
     */
    public void setClearAmount(String clearAmount) {
        this.clearAmount = clearAmount;
    }

    /**
     * 获取结算手续费
     *
     * @return 结算手续费
     */
    public String getSettFee() {
        return settFee;
    }

    /**
     * 设置结算手续费
     *
     * @param settFee 结算手续费
     */
    public void setSettFee(String settFee) {
        this.settFee = settFee;
    }

    /**
     * 获取账户名称
     *
     * @return 账户名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账户名称
     *
     * @param accountName 账户名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取银行名称
     *
     * @return 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取账户号码
     *
     * @return 账户号码
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 设置账户号码
     *
     * @param accountNumber 账户号码
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

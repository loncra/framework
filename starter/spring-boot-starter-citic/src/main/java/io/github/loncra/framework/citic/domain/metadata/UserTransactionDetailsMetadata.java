package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author maurice.chen
 */
public class UserTransactionDetailsMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -4644551787256926110L;

    @JacksonXmlProperty(localName = "USER_NAME")
    private String username;

    /**
     * 交易日期
     */
    @JacksonXmlProperty(localName = "TRANS_DT")
    private String transactionDate;

    /**
     * 交易时间
     */
    @JacksonXmlProperty(localName = "TRANS_TM")
    private String transactionTime;

    @JacksonXmlProperty(localName = "TRANS_TYPE")
    private String transactionType;

    @JacksonXmlProperty(localName = "REQ_JRN")
    private String citicNumber;

    @JacksonXmlProperty(localName = "MCHNT_ORDER_ID")
    private String merchantOrderNumber;

    @JacksonXmlProperty(localName = "MCHNT_ORDER_SUB_ID")
    private String merchantOrderSubNumber;

    @JacksonXmlProperty(localName = "REGISTER_SSN")
    private String registerSsn;

    @JacksonXmlProperty(localName = "TRANS_AMT")
    private String transactionAmount;

    @JacksonXmlProperty(localName = "C_D_FLAG")
    private String accountTransactionType;

    @JacksonXmlProperty(localName = "GOAC")
    private String sourceAccountId;

    @JacksonXmlProperty(localName = "OANM")
    private String sourceAccountName;

    @JacksonXmlProperty(localName = "DIGEST")
    private String remark;

    public UserTransactionDetailsMetadata() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCiticNumber() {
        return citicNumber;
    }

    public void setCiticNumber(String citicNumber) {
        this.citicNumber = citicNumber;
    }

    public String getMerchantOrderNumber() {
        return merchantOrderNumber;
    }

    public void setMerchantOrderNumber(String merchantOrderNumber) {
        this.merchantOrderNumber = merchantOrderNumber;
    }

    public String getMerchantOrderSubNumber() {
        return merchantOrderSubNumber;
    }

    public void setMerchantOrderSubNumber(String merchantOrderSubNumber) {
        this.merchantOrderSubNumber = merchantOrderSubNumber;
    }

    public String getRegisterSsn() {
        return registerSsn;
    }

    public void setRegisterSsn(String registerSsn) {
        this.registerSsn = registerSsn;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getAccountTransactionType() {
        return accountTransactionType;
    }

    public void setAccountTransactionType(String accountTransactionType) {
        this.accountTransactionType = accountTransactionType;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getSourceAccountName() {
        return sourceAccountName;
    }

    public void setSourceAccountName(String sourceAccountName) {
        this.sourceAccountName = sourceAccountName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

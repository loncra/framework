package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class MerchantTransactionDetailsMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -4644551787256926110L;

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

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

    @JacksonXmlProperty(localName = "TRANS_TP")
    private String transactionType;

    @JacksonXmlProperty(localName = "JRNO")
    private String transactionRecordNumber;

    @JacksonXmlProperty(localName = "PAY_ACCNO")
    private String payAccountNumber;

    @JacksonXmlProperty(localName = "PAY_ACCNAME")
    private String payAccountName;

    @JacksonXmlProperty(localName = "TRANS_AMT")
    private BigDecimal transactionAmount;

    @JacksonXmlProperty(localName = "CUR_AMT")
    private BigDecimal currentAmount;

    @JacksonXmlProperty(localName = "C_D_FLAG")
    private String accountTransactionType;

    @JacksonXmlProperty(localName = "BKNO")
    private String bankNumber;

    @JacksonXmlProperty(localName = "ACSQ")
    private String accountSequenceNumber;

    @JacksonXmlProperty(localName = "ACTN")
    private String accountTransactionSequenceNumber;

    @JacksonXmlProperty(localName = "TSTM")
    private String financialTransactionIdentification;

    @JacksonXmlProperty(localName = "时间戳")
    private String timestamp;

    @JacksonXmlProperty(localName = "DIGEST")
    private String remark;

    public MerchantTransactionDetailsMetadata() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTransactionRecordNumber() {
        return transactionRecordNumber;
    }

    public void setTransactionRecordNumber(String transactionRecordNumber) {
        this.transactionRecordNumber = transactionRecordNumber;
    }

    public String getPayAccountNumber() {
        return payAccountNumber;
    }

    public void setPayAccountNumber(String payAccountNumber) {
        this.payAccountNumber = payAccountNumber;
    }

    public String getPayAccountName() {
        return payAccountName;
    }

    public void setPayAccountName(String payAccountName) {
        this.payAccountName = payAccountName;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getAccountTransactionType() {
        return accountTransactionType;
    }

    public void setAccountTransactionType(String accountTransactionType) {
        this.accountTransactionType = accountTransactionType;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getAccountSequenceNumber() {
        return accountSequenceNumber;
    }

    public void setAccountSequenceNumber(String accountSequenceNumber) {
        this.accountSequenceNumber = accountSequenceNumber;
    }

    public String getAccountTransactionSequenceNumber() {
        return accountTransactionSequenceNumber;
    }

    public void setAccountTransactionSequenceNumber(String accountTransactionSequenceNumber) {
        this.accountTransactionSequenceNumber = accountTransactionSequenceNumber;
    }

    public String getFinancialTransactionIdentification() {
        return financialTransactionIdentification;
    }

    public void setFinancialTransactionIdentification(String financialTransactionIdentification) {
        this.financialTransactionIdentification = financialTransactionIdentification;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

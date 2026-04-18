package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class WithdrawalRequestBody extends BasicUserIdRequestBody {

    @Serial
    private static final long serialVersionUID = 2550334570978132100L;

    @NotNull
    @JacksonXmlProperty(localName = "WITH_TYPE")
    private String userType;

    @NotNull
    @JacksonXmlProperty(localName = "BUSS_ID")
    private String businessId;

    @NotNull
    @JacksonXmlProperty(localName = "TRANS_DT")
    private String transactionDate;

    @NotNull
    @JacksonXmlProperty(localName = "TRANS_TM")
    private String transactionTime;

    @NotNull
    @JacksonXmlProperty(localName = "FEE_TYPE")
    private String feeType;

    @NotNull
    @JacksonXmlProperty(localName = "WITH_AMT")
    private BigDecimal amount;

    @JacksonXmlProperty(localName = "MEMO")
    private String remark;

    @JacksonXmlProperty(localName = "WITH_ACCOUNT")
    private String bankAccount;

    @JacksonXmlProperty(localName = "WITH_ACCNAME")
    private String accountName;

    public WithdrawalRequestBody() {
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}

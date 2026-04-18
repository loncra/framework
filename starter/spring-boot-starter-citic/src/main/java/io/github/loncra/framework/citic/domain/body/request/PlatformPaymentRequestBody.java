package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import io.github.loncra.framework.citic.enumerate.PlatformPaymentTransactionTypeEnum;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class PlatformPaymentRequestBody extends BasicRequestMetadata {
    @Serial
    private static final long serialVersionUID = 4235882660134793872L;

    /**
     * 收款用户编号（资金转入方）
     */
    @JacksonXmlProperty(localName = "USER_C_ID")
    private String userId;

    /**
     * 收款用户名称（资金转入方）
     */
    @JacksonXmlProperty(localName = "USER_C_NM")
    private String username;

    /**
     * 交易类型
     */
    @JacksonXmlProperty(localName = "DEAL_TYPE")
    private String transactionType = PlatformPaymentTransactionTypeEnum.OTHER.getValue();

    /**
     * 商户业务订单号
     */
    @JacksonXmlProperty(localName = "BUSS_ID")
    private String businessOrderId;

    /**
     * 商户业务子订单号
     */
    @JacksonXmlProperty(localName = "BUSS_SUB_ID")
    private String subOrderId;

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

    /**
     * 资金类型
     */
    @JacksonXmlProperty(localName = "FUND_TP")
    private String fundType;

    /**
     * 交易金额
     */
    @JacksonXmlProperty(localName = "AMOUNT")
    private BigDecimal amount;

    /**
     * 备注
     */
    @JacksonXmlProperty(localName = "MEMO")
    private String memo;

    @JacksonXmlProperty(localName = "CONTRACT_ID")
    private String contractId;

    public PlatformPaymentRequestBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBusinessOrderId() {
        return businessOrderId;
    }

    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId;
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId;
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

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

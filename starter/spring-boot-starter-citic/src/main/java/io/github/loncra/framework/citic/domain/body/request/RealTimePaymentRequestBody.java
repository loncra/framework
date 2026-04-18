package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import io.github.loncra.framework.citic.enumerate.RealTimePaymentPlatformFundTypeEnum;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class RealTimePaymentRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = -3357070797416783408L;

    /**
     * 付款用户编号（资金转出方）
     */
    @JacksonXmlProperty(localName = "USER_D_ID")
    private String paymentUserId;

    /**
     * 付款用户名称（资金转出方）
     */
    @JacksonXmlProperty(localName = "USER_D_NM")
    private String paymentUsername;

    /**
     * 收款用户编号（资金转入方）
     */
    @JacksonXmlProperty(localName = "USER_C_ID")
    private String receivingUserId;

    /**
     * 收款用户名称（资金转入方）
     */
    @JacksonXmlProperty(localName = "USER_C_NM")
    private String receivingUsername;

    /**
     * 收款用户收款金额
     */
    @JacksonXmlProperty(localName = "USER_C_AMT")
    private BigDecimal receiveAmount;

    /**
     * 交易金额
     */
    @JacksonXmlProperty(localName = "TRANS_AMT")
    private BigDecimal transactionAmount;

    /**
     * 平台商户自有资金交易类型
     */
    @JacksonXmlProperty(localName = "P_SELF_FLAG")
    private String platformFundType = RealTimePaymentPlatformFundTypeEnum.NO_ACCOUNT_MOVEMENT.getValue();

    /**
     * 平台商户自有资金交易金额
     */
    @JacksonXmlProperty(localName = "P_SELF_AMT")
    private BigDecimal platformAmount = BigDecimal.ZERO;

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
     * 备注
     */
    @JacksonXmlProperty(localName = "MEMO")
    private String memo;

    public RealTimePaymentRequestBody() {
    }

    public String getPaymentUserId() {
        return paymentUserId;
    }

    public void setPaymentUserId(String paymentUserId) {
        this.paymentUserId = paymentUserId;
    }

    public String getPaymentUsername() {
        return paymentUsername;
    }

    public void setPaymentUsername(String paymentUsername) {
        this.paymentUsername = paymentUsername;
    }

    public String getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(String receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public String getReceivingUsername() {
        return receivingUsername;
    }

    public void setReceivingUsername(String receivingUsername) {
        this.receivingUsername = receivingUsername;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPlatformFundType() {
        return platformFundType;
    }

    public void setPlatformFundType(String platformFundType) {
        this.platformFundType = platformFundType;
    }

    public BigDecimal getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(BigDecimal platformAmount) {
        this.platformAmount = platformAmount;
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
}

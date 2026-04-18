package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class RealTimeRefundRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = -3357070797416783408L;

    /**
     * 原支付交易付款用户编号（资金转入方）
     */
    @JacksonXmlProperty(localName = "ORI_USER_D_ID")
    private String originalPaymentUserId;

    /**
     * 原支付交易付款用户名称（资金转入方）
     */
    @JacksonXmlProperty(localName = "ORI_USER_D_NM")
    private String originalPaymentUsername;

    /**
     * 原支付交易收款用户编号（资金转出方）
     */
    @JacksonXmlProperty(localName = "ORI_USER_C_ID")
    private String originalReceivingUserId;

    /**
     * 收款用户名称（资金转入方）
     */
    @JacksonXmlProperty(localName = "ORI_USER_C_NM")
    private String originalReceivingUsername;

    /**
     * 收款用户收款金额
     */
    @JacksonXmlProperty(localName = "ORI_USER_C_AMT")
    private BigDecimal originalReceiveAmount;

    /**
     * 交易金额
     */
    @JacksonXmlProperty(localName = "TRANS_AMT")
    private BigDecimal transactionAmount;

    /**
     * 平台商户自有资金交易类型
     */
    @JacksonXmlProperty(localName = "P_SELF_FLAG")
    private String platformFundType;

    /**
     * 平台商户自有资金交易金额
     */
    @JacksonXmlProperty(localName = "P_SELF_AMT")
    private BigDecimal platformAmount;

    /**
     * 商户业务订单号
     */
    @JacksonXmlProperty(localName = "REFUND_BUSS_ID")
    private String refundBusinessOrderId;

    /**
     * 商户业务子订单号
     */
    @JacksonXmlProperty(localName = "REFUND_BUSS_SUB_ID")
    private String refundSubOrderId;

    /**
     * 原支付交易中信侧交易流水号
     */
    @JacksonXmlProperty(localName = "ORI_USER_SSN")
    private String originalUserSsn;


    @JacksonXmlProperty(localName = "ORI_USER_TRANS_DT")
    private String originalTransactionDate;

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

    public RealTimeRefundRequestBody() {
    }

    public String getOriginalPaymentUserId() {
        return originalPaymentUserId;
    }

    public void setOriginalPaymentUserId(String originalPaymentUserId) {
        this.originalPaymentUserId = originalPaymentUserId;
    }

    public String getOriginalPaymentUsername() {
        return originalPaymentUsername;
    }

    public void setOriginalPaymentUsername(String originalPaymentUsername) {
        this.originalPaymentUsername = originalPaymentUsername;
    }

    public String getOriginalReceivingUserId() {
        return originalReceivingUserId;
    }

    public void setOriginalReceivingUserId(String originalReceivingUserId) {
        this.originalReceivingUserId = originalReceivingUserId;
    }

    public String getOriginalReceivingUsername() {
        return originalReceivingUsername;
    }

    public void setOriginalReceivingUsername(String originalReceivingUsername) {
        this.originalReceivingUsername = originalReceivingUsername;
    }

    public BigDecimal getOriginalReceiveAmount() {
        return originalReceiveAmount;
    }

    public void setOriginalReceiveAmount(BigDecimal originalReceiveAmount) {
        this.originalReceiveAmount = originalReceiveAmount;
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

    public String getRefundBusinessOrderId() {
        return refundBusinessOrderId;
    }

    public void setRefundBusinessOrderId(String refundBusinessOrderId) {
        this.refundBusinessOrderId = refundBusinessOrderId;
    }

    public String getRefundSubOrderId() {
        return refundSubOrderId;
    }

    public void setRefundSubOrderId(String refundSubOrderId) {
        this.refundSubOrderId = refundSubOrderId;
    }

    public String getOriginalUserSsn() {
        return originalUserSsn;
    }

    public void setOriginalUserSsn(String originalUserSsn) {
        this.originalUserSsn = originalUserSsn;
    }

    public String getOriginalTransactionDate() {
        return originalTransactionDate;
    }

    public void setOriginalTransactionDate(String originalTransactionDate) {
        this.originalTransactionDate = originalTransactionDate;
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

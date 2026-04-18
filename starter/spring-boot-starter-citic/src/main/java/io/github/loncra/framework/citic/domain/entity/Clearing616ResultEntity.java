package io.github.loncra.framework.citic.domain.entity;

import io.github.loncra.framework.commons.annotation.Description;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
@Description(value = "616清分文件结果实体")
public class Clearing616ResultEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4003172198455695719L;

    // 基础信息字段
    /**
     * 平台商户编号
     */
    @Description(value = "平台商户编号", sort = 0)
    private String platformMerchantId;
    /**
     * 用户编号
     */
    @Description(value = "用户编号", sort = 1)
    private String userId;
    /**
     * 交易日期(格式:yyyyMMdd)
     */
    @Description(value = "交易日期", sort = 2)
    private String tradeDate;
    /**
     * 交易时间(格式:HHmmss)
     */
    @Description(value = "交易时间", sort = 3)
    private String tradeTime;

    // 支付信息字段
    /**
     * 支付渠道名称
     */
    @Description(value = "支付渠道名称", sort = 4)
    private String paymentChannelName;
    /**
     * 平台商户业务订单号
     */
    @Description(value = "平台商户业务订单号", sort = 5)
    private String platformMerchantOrderNo;
    /**
     * 平台商户支付订单号
     */
    @Description(value = "平台商户支付订单号", sort = 6)
    private String platformMerchantPayOrderNo;
    /**
     * 支付渠道交易流水号
     */
    @Description(value = "支付渠道交易流水号", sort = 7)
    private String paymentChannelSerialNo;
    /**
     * 平台商户业务子订单号
     */
    @Description(value = "平台商户业务子订单号", sort = 8)
    private String platformMerchantSubOrderNo;

    /**
     * 支付订单交易类型
     */
    @Description(value = "支付订单交易类型", sort = 9)
    private String paymentOrderType;
    /**
     * 业务订单交易类型
     */
    @Description(value = "业务订单交易类型", sort = 10)
    private String businessOrderType;
    /**
     * 清算资金来源(0:用户账户,1:平台垫资)
     */
    @Description(value = "清算资金来源", sort = 11)
    private String fundSourceType;
    /**
     * 渠道手续费承担方式(0:平台承担,1:商户承担)
     */
    @Description(value = "渠道手续费承担方式", sort = 12)
    private String feeBearer;
    /**
     * 原始订单金额
     */
    @Description(value = "原始订单金额", sort = 13)
    private BigDecimal originalOrderAmount;
    /**
     * 原始支付金额
     */
    @Description(value = "原始支付金额", sort = 14)
    private BigDecimal originalPaymentAmount;
    /**
     * 平台优惠金额
     */
    @Description(value = "平台优惠金额", sort = 15)
    private BigDecimal platformDiscountAmount;
    /**
     * 平台分成金额
     */
    @Description(value = "平台分成金额", sort = 16)
    private BigDecimal platformSplitAmount;
    /**
     * 平台垫款金额
     */
    @Description(value = "平台垫款金额", sort = 17)
    private BigDecimal platformAdvanceAmount;
    /**
     * 渠道手续费
     */
    @Description(value = "渠道手续费", sort = 18)
    private BigDecimal channelFee;

    /**
     * 资金类型
     */
    @Description(value = "资金类型", sort = 19)
    private String fundType;

    @Description(value = "备注", sort = 20)
    private String remark;

    /**
     * 处理结果(AAAAA表示成功)
     */
    @Description(value = "处理结果", sort = 21)
    private String processResult;
    /**
     * 处理描述
     */
    @Description(value = "处理描述", sort = 22)
    private String processDesc;

    public Clearing616ResultEntity() {
    }

    public String getPlatformMerchantId() {
        return platformMerchantId;
    }

    public void setPlatformMerchantId(String platformMerchantId) {
        this.platformMerchantId = platformMerchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getPaymentChannelName() {
        return paymentChannelName;
    }

    public void setPaymentChannelName(String paymentChannelName) {
        this.paymentChannelName = paymentChannelName;
    }

    public String getPlatformMerchantOrderNo() {
        return platformMerchantOrderNo;
    }

    public void setPlatformMerchantOrderNo(String platformMerchantOrderNo) {
        this.platformMerchantOrderNo = platformMerchantOrderNo;
    }

    public String getPlatformMerchantPayOrderNo() {
        return platformMerchantPayOrderNo;
    }

    public void setPlatformMerchantPayOrderNo(String platformMerchantPayOrderNo) {
        this.platformMerchantPayOrderNo = platformMerchantPayOrderNo;
    }

    public String getPaymentChannelSerialNo() {
        return paymentChannelSerialNo;
    }

    public void setPaymentChannelSerialNo(String paymentChannelSerialNo) {
        this.paymentChannelSerialNo = paymentChannelSerialNo;
    }

    public String getPlatformMerchantSubOrderNo() {
        return platformMerchantSubOrderNo;
    }

    public void setPlatformMerchantSubOrderNo(String platformMerchantSubOrderNo) {
        this.platformMerchantSubOrderNo = platformMerchantSubOrderNo;
    }

    public String getPaymentOrderType() {
        return paymentOrderType;
    }

    public void setPaymentOrderType(String paymentOrderType) {
        this.paymentOrderType = paymentOrderType;
    }

    public String getBusinessOrderType() {
        return businessOrderType;
    }

    public void setBusinessOrderType(String businessOrderType) {
        this.businessOrderType = businessOrderType;
    }

    public BigDecimal getOriginalOrderAmount() {
        return originalOrderAmount;
    }

    public void setOriginalOrderAmount(BigDecimal originalOrderAmount) {
        this.originalOrderAmount = originalOrderAmount;
    }

    public BigDecimal getOriginalPaymentAmount() {
        return originalPaymentAmount;
    }

    public void setOriginalPaymentAmount(BigDecimal originalPaymentAmount) {
        this.originalPaymentAmount = originalPaymentAmount;
    }

    public BigDecimal getPlatformDiscountAmount() {
        return platformDiscountAmount;
    }

    public void setPlatformDiscountAmount(BigDecimal platformDiscountAmount) {
        this.platformDiscountAmount = platformDiscountAmount;
    }

    public BigDecimal getPlatformSplitAmount() {
        return platformSplitAmount;
    }

    public void setPlatformSplitAmount(BigDecimal platformSplitAmount) {
        this.platformSplitAmount = platformSplitAmount;
    }

    public BigDecimal getPlatformAdvanceAmount() {
        return platformAdvanceAmount;
    }

    public void setPlatformAdvanceAmount(BigDecimal platformAdvanceAmount) {
        this.platformAdvanceAmount = platformAdvanceAmount;
    }

    public BigDecimal getChannelFee() {
        return channelFee;
    }

    public void setChannelFee(BigDecimal channelFee) {
        this.channelFee = channelFee;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }
}

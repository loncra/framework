package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicVersionRequestMetadata;

import java.io.Serial;

/**
 * 退款请求体
 *
 * @author maurice.chen
 */
public class RefundRequestBody extends BasicVersionRequestMetadata {
    @Serial
    private static final long serialVersionUID = -5534355295585190050L;

    /**
     * 退款金额（单位：分）
     */
    private Integer amount;

    /**
     * 通联订单号
     */
    private String orderNo;

    /**
     * 原商户订单号
     */
    private String oriMerchantOrderNo;

    /**
     * 交易流水号
     */
    private String trxid;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 退款类型
     */
    private String refundType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 集团商户编号
     */
    private String groupNo;

    /**
     * 构造函数
     */
    public RefundRequestBody() {
    }

    /**
     * 获取退款金额（单位：分）
     *
     * @return 退款金额
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置退款金额（单位：分）
     *
     * @param amount 退款金额
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取通联订单号
     *
     * @return 通联订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置通联订单号
     *
     * @param orderNo 通联订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取原商户订单号
     *
     * @return 原商户订单号
     */
    public String getOriMerchantOrderNo() {
        return oriMerchantOrderNo;
    }

    /**
     * 设置原商户订单号
     *
     * @param oriMerchantOrderNo 原商户订单号
     */
    public void setOriMerchantOrderNo(String oriMerchantOrderNo) {
        this.oriMerchantOrderNo = oriMerchantOrderNo;
    }

    /**
     * 获取交易流水号
     *
     * @return 交易流水号
     */
    public String getTrxid() {
        return trxid;
    }

    /**
     * 设置交易流水号
     *
     * @param trxid 交易流水号
     */
    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    /**
     * 获取商户订单号
     *
     * @return 商户订单号
     */
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    /**
     * 设置商户订单号
     *
     * @param merchantOrderNo 商户订单号
     */
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    /**
     * 获取退款类型
     *
     * @return 退款类型
     */
    public String getRefundType() {
        return refundType;
    }

    /**
     * 设置退款类型
     *
     * @param refundType 退款类型
     */
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取集团商户编号
     *
     * @return 集团商户编号
     */
    public String getGroupNo() {
        return groupNo;
    }

    /**
     * 设置集团商户编号
     *
     * @param groupNo 集团商户编号
     */
    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }
}

package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicVersionRequestMetadata;

import java.io.Serial;

/**
 * 查询订单请求体
 *
 * @author maurice.chen
 */
public class QueryOrderRequestBody extends BasicVersionRequestMetadata {
    @Serial
    private static final long serialVersionUID = -1950483859711218175L;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 通联订单号
     */
    private String orderNo;

    /**
     * 集团商户编号
     */
    private String groupNo;

    /**
     * 交易流水号
     */
    private String trxid;

    /**
     * 构造函数
     */
    public QueryOrderRequestBody() {
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
}

package io.github.loncra.framework.allin.pay.domain.body.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.loncra.framework.allin.pay.domain.metadata.BasicOrderRequestMetadata;
import io.github.loncra.framework.allin.pay.service.AllInPayService;

import java.io.Serial;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 统一支付请求体
 *
 * @author maurice.chen
 */
public class UnifiedPayRequestBody extends BasicOrderRequestMetadata {

    @Serial
    private static final long serialVersionUID = 2672681383541187192L;

    /**
     * 金额（单位：分）
     */
    private Integer amount;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 支付完成后的跳转地址
     */
    private String backUrl;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 有效期
     */
    @JsonFormat(pattern = AllInPayService.DATE_TIME_FORMAT)
    private Instant validTime;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 商品信息列表
     */
    private List<Map<String, Object>> goods = new LinkedList<>();

    /**
     * 分期期数
     */
    private String fqnum;

    /**
     * 增值服务信息
     */
    private String asinfo;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 子应用 ID
     */
    private String subAppid;

    /**
     * 渠道门店 ID
     */
    private String chnlstoreid;

    /**
     * 商品标签
     */
    private String goodsTag;

    /**
     * 终端IP
     */
    private String mchCreateIp;

    /**
     * 构造函数
     */
    public UnifiedPayRequestBody() {
    }

    /**
     * 获取支付类型
     *
     * @return 支付类型
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 设置支付类型
     *
     * @param payType 支付类型
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    /**
     * 获取子应用 ID
     *
     * @return 子应用 ID
     */
    public String getSubAppid() {
        return subAppid;
    }

    /**
     * 设置子应用 ID
     *
     * @param subAppid 子应用 ID
     */
    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    /**
     * 获取渠道门店 ID
     *
     * @return 渠道门店 ID
     */
    public String getChnlstoreid() {
        return chnlstoreid;
    }

    /**
     * 设置渠道门店 ID
     *
     * @param chnlstoreid 渠道门店 ID
     */
    public void setChnlstoreid(String chnlstoreid) {
        this.chnlstoreid = chnlstoreid;
    }

    /**
     * 获取商品标签
     *
     * @return 商品标签
     */
    public String getGoodsTag() {
        return goodsTag;
    }

    /**
     * 设置商品标签
     *
     * @param goodsTag 商品标签
     */
    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getMchCreateIp() {
        return mchCreateIp;
    }

    public void setMchCreateIp(String mchCreateIp) {
        this.mchCreateIp = mchCreateIp;
    }

    /**
     * 获取金额（单位：分）
     *
     * @return 金额
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置金额（单位：分）
     *
     * @param amount 金额
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取异步通知地址
     *
     * @return 异步通知地址
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * 设置异步通知地址
     *
     * @param notifyUrl 异步通知地址
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * 获取支付完成后的跳转地址
     *
     * @return 跳转地址
     */
    public String getBackUrl() {
        return backUrl;
    }

    /**
     * 设置支付完成后的跳转地址
     *
     * @param backUrl 跳转地址
     */
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    /**
     * 获取订单名称
     *
     * @return 订单名称
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     * 设置订单名称
     *
     * @param orderName 订单名称
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * 获取有效期
     *
     * @return 有效期
     */
    public Instant getValidTime() {
        return validTime;
    }

    /**
     * 设置有效期
     *
     * @param validTime 有效期
     */
    public void setValidTime(Instant validTime) {
        this.validTime = validTime;
    }

    /**
     * 获取备注信息
     *
     * @return 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取商品信息列表
     *
     * @return 商品信息列表
     */
    public List<Map<String, Object>> getGoods() {
        return goods;
    }

    /**
     * 设置商品信息列表
     *
     * @param goods 商品信息列表
     */
    public void setGoods(List<Map<String, Object>> goods) {
        this.goods = goods;
    }

    /**
     * 获取分期期数
     *
     * @return 分期期数
     */
    public String getFqnum() {
        return fqnum;
    }

    /**
     * 设置分期期数
     *
     * @param fqnum 分期期数
     */
    public void setFqnum(String fqnum) {
        this.fqnum = fqnum;
    }

    /**
     * 获取增值服务信息
     *
     * @return 增值服务信息
     */
    public String getAsinfo() {
        return asinfo;
    }

    /**
     * 设置增值服务信息
     *
     * @param asinfo 增值服务信息
     */
    public void setAsinfo(String asinfo) {
        this.asinfo = asinfo;
    }
}

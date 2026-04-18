package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicVersionRequestMetadata;
import io.github.loncra.framework.allin.pay.domain.metadata.BatchRefundMetadata;

import java.io.Serial;
import java.util.List;

/**
 * 批量退款请求体
 *
 * @author maurice.chen
 */
public class BatchRefundRequestBody extends BasicVersionRequestMetadata {

    @Serial
    private static final long serialVersionUID = -6186389361346106502L;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 集团商户编号
     */
    private String groupNo;

    /**
     * 退款订单列表
     */
    private List<BatchRefundMetadata> orders;

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
     * 获取退款订单列表
     *
     * @return 退款订单列表
     */
    public List<BatchRefundMetadata> getOrders() {
        return orders;
    }

    /**
     * 设置退款订单列表
     *
     * @param orders 退款订单列表
     */
    public void setOrders(List<BatchRefundMetadata> orders) {
        this.orders = orders;
    }
}

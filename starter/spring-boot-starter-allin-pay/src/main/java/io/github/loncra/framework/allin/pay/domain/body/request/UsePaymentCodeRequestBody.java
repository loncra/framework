package io.github.loncra.framework.allin.pay.domain.body.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.loncra.framework.allin.pay.domain.metadata.BasicVersionOrderRequestMetadata;
import io.github.loncra.framework.allin.pay.service.AllInPayService;

import java.io.Serial;
import java.time.Instant;

/**
 * 支付码支付请求体
 *
 * @author maurice.chen
 */
public class UsePaymentCodeRequestBody extends BasicVersionOrderRequestMetadata {

    @Serial
    private static final long serialVersionUID = 3541609112459058591L;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = AllInPayService.DATE_TIME_FORMAT)
    private Instant payTime = Instant.now();

    /**
     * 构造函数
     */
    public UsePaymentCodeRequestBody() {
    }

    /**
     * 获取支付时间
     *
     * @return 支付时间
     */
    public Instant getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Instant payTime) {
        this.payTime = payTime;
    }
}

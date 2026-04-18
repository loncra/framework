package io.github.loncra.framework.allin.pay.domain.metadata;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础请求元数据
 *
 * @author maurice.chen
 */
public class BasicRequestMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -3074384801046731289L;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 签名
     */
    private String sign;

    /**
     * 构造函数
     */
    public BasicRequestMetadata() {
    }

    /**
     * 获取商户号
     *
     * @return 商户号
     */
    public String getMerchantNo() {
        return merchantNo;
    }

    /**
     * 设置商户号
     *
     * @param merchantNo 商户号
     */
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    /**
     * 获取签名
     *
     * @return 签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置签名
     *
     * @param sign 签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
}

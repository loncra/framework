package io.github.loncra.framework.allin.pay.domain.metadata;

import java.io.Serial;

/**
 * 基础订单请求元数据
 *
 * @author maurice.chen
 */
public class BasicOrderRequestMetadata extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 1867322369882303765L;

    /**
     * 门店编号
     */
    private String storeNo;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 备注
     */
    private String note;

    /**
     * 集团商户编号
     */
    private String groupNo;

    /**
     * 用户标识，当交易方式为W02和W06时为用户的openid不能为空，当交易方式为A02时，为用户的userid，云闪付支付时，为银联userId不能为空
     */
    private String acct;

    /**
     * 构造函数
     */
    public BasicOrderRequestMetadata() {
    }

    /**
     * 获取门店编号
     *
     * @return 门店编号
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * 设置门店编号
     *
     * @param storeNo 门店编号
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
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
     * 获取备注
     *
     * @return 备注
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置备注
     *
     * @param note 备注
     */
    public void setNote(String note) {
        this.note = note;
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
     * 获取支付账号
     *
     * @return 支付账号
     */
    public String getAcct() {
        return acct;
    }

    /**
     * 设置支付账号
     *
     * @param acct 支付账号
     */
    public void setAcct(String acct) {
        this.acct = acct;
    }

}

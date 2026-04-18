package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicOrderRequestMetadata;

import java.io.Serial;

/**
 * 交易回调确认请求
 *
 * @author maurice.chen
 */
public class TransactionConfirmRequestBody extends BasicOrderRequestMetadata {

    @Serial
    private static final long serialVersionUID = 8397441807275320519L;

    /** 渠道商户号 */
    private String cusid;

    /** 交易类型，详见接入说明 */
    private String trxcode;

    /** 金额，单位：分 */
    private String trxamt;

    /** 交易完成时间，格式：yyyyMMddHHmmss */
    private String payTime;

    /** 通道流水号，如支付宝、微信平台订单号 */
    private String chnltrxid;

    /** 交易状态，详见接入说明 */
    private String trxstatus;

    /** 渠道交易单号 */
    private String trxid;

    /** 云合慧通订单号 */
    private String orderNo;

    /** 手续费，单位：分 */
    private String fee;

    /** 标签（JSON 格式） */
    private String label;

    /** 原始交易金额，单位：分 */
    private String initamt;

    /** 员工码牌信息，格式：员工名(电话号码)，使用小通云缴员工码时存在 */
    private String employee;

    /** sign校验码 **/
    private String sign;

    public TransactionConfirmRequestBody() {
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getTrxcode() {
        return trxcode;
    }

    public void setTrxcode(String trxcode) {
        this.trxcode = trxcode;
    }

    public String getTrxamt() {
        return trxamt;
    }

    public void setTrxamt(String trxamt) {
        this.trxamt = trxamt;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getChnltrxid() {
        return chnltrxid;
    }

    public void setChnltrxid(String chnltrxid) {
        this.chnltrxid = chnltrxid;
    }

    public String getTrxstatus() {
        return trxstatus;
    }

    public void setTrxstatus(String trxstatus) {
        this.trxstatus = trxstatus;
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInitamt() {
        return initamt;
    }

    public void setInitamt(String initamt) {
        this.initamt = initamt;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public String getSign() {
        return sign;
    }

    @Override
    public void setSign(String sign) {
        this.sign = sign;
    }
}

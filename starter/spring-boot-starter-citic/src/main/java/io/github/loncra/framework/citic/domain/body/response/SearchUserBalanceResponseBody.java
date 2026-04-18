package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class SearchUserBalanceResponseBody extends BasicResponseMetadata {
    @Serial
    private static final long serialVersionUID = -6875845534669079424L;

    /**
     * 上一日可提现金额, 单位（元）
     */
    @JacksonXmlProperty(localName = "PRE_AMOUNT")
    private BigDecimal lastDayAmount;

    /**
     * 可用余额, 单位（元），可用余额包含当前可提现金额 + 待转可提现金额两部分
     */
    @JacksonXmlProperty(localName = "AVL_AMOUNT")
    private BigDecimal availableAmount;

    /**
     * 可提现金额	, 单位（元），可提现金额为当前可用于支付、退款、提现等交易的金额
     */
    @JacksonXmlProperty(localName = "AMOUNT")
    private BigDecimal amount;

    /**
     * 可提现金额	, 单位（元），可提现金额为当前可用于支付、退款、提现等交易的金额
     */
    //@JacksonXmlProperty(localName = "PLFM_SPLS_OVDF_AMT")
    //private BigDecimal platformOverdraftAmount;
    public SearchUserBalanceResponseBody() {
    }

    public BigDecimal getLastDayAmount() {
        return lastDayAmount;
    }

    public void setLastDayAmount(BigDecimal lastDayAmount) {
        this.lastDayAmount = lastDayAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /*public BigDecimal getPlatformOverdraftAmount() {
        return platformOverdraftAmount;
    }

    public void setPlatformOverdraftAmount(BigDecimal platformOverdraftAmount) {
        this.platformOverdraftAmount = platformOverdraftAmount;
    }*/
}

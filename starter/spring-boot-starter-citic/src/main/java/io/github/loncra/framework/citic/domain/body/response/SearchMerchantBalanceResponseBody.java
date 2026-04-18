package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.SimpleResponseMetadata;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * @author maurice.chen
 */
public class SearchMerchantBalanceResponseBody extends SimpleResponseMetadata {
    @Serial
    private static final long serialVersionUID = -9195915231420569870L;

    /**
     * 可提现金额	, 单位（元），可提现金额为当前可用于支付、退款、提现等交易的金额
     */
    @JacksonXmlProperty(localName = "AMOUNT")
    private BigDecimal amount;

    public SearchMerchantBalanceResponseBody() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

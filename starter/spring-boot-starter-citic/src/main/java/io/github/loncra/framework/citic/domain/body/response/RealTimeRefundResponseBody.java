package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.SimpleResponseMetadata;

import java.io.Serial;

/**
 *
 * @author olale
 */
public class RealTimeRefundResponseBody extends SimpleResponseMetadata {

    @Serial
    private static final long serialVersionUID = 2986414111112016574L;

    @JacksonXmlProperty(localName = "USER_SSN")
    private String userSsn;

    /**
     * 交易日期
     */
    @JacksonXmlProperty(localName = "USER_TRANS_DT")
    private String userTransactionDate;

    /**
     * 交易时间
     */
    @JacksonXmlProperty(localName = "USER_TRANS_TM")
    private String userTransactionTime;

    public RealTimeRefundResponseBody() {
    }

    public String getUserSsn() {
        return userSsn;
    }

    public void setUserSsn(String userSsn) {
        this.userSsn = userSsn;
    }

    public String getUserTransactionDate() {
        return userTransactionDate;
    }

    public void setUserTransactionDate(String userTransactionDate) {
        this.userTransactionDate = userTransactionDate;
    }

    public String getUserTransactionTime() {
        return userTransactionTime;
    }

    public void setUserTransactionTime(String userTransactionTime) {
        this.userTransactionTime = userTransactionTime;
    }
}

package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.SignResponseMetadata;

import java.io.Serial;

/**
 *
 * @author olale
 */
public class UserSsnResponseBody extends SignResponseMetadata {

    @Serial
    private static final long serialVersionUID = 2986414111112016574L;

    @JacksonXmlProperty(localName = "USER_SSN")
    private String userTransactionSsn;

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

    public UserSsnResponseBody() {
    }

    public String getUserTransactionSsn() {
        return userTransactionSsn;
    }

    public void setUserTransactionSsn(String userTransactionSsn) {
        this.userTransactionSsn = userTransactionSsn;
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

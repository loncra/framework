package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class QueryUserTransactionStatusResponseBody extends BasicResponseMetadata {
    @Serial
    private static final long serialVersionUID = 8160413411681937898L;

    @JacksonXmlProperty(localName = "STATE")
    private String status;

    @JacksonXmlProperty(localName = "TRANS_DATE")
    private String transactionDate;

    @JacksonXmlProperty(localName = "TRANS_TIME")
    private String transactionTime;

    @JacksonXmlProperty(localName = "USER_SSN")
    private String userTransactionSsn;

    @JacksonXmlProperty(localName = "RESULT_CODE")
    private String resultCode;

    @JacksonXmlProperty(localName = "RESULT_MSG")
    private String resultMessage;

    public QueryUserTransactionStatusResponseBody() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUserTransactionSsn() {
        return userTransactionSsn;
    }

    public void setUserTransactionSsn(String userTransactionSsn) {
        this.userTransactionSsn = userTransactionSsn;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}

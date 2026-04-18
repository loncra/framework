package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class QueryUserTransactionStatusRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = -8644523162440385735L;

    @JacksonXmlProperty(localName = "ORI_USER_SSN")
    private String userTransactionSsn;

    @JacksonXmlProperty(localName = "ORI_REQ_SSN")
    private String requestSsn;

    @JacksonXmlProperty(localName = "USER_TRANS_DT")
    private String transactionDate;

    @JacksonXmlProperty(localName = "TRANS_TYPE")
    private String transactionType;

    @JacksonXmlProperty(localName = "BUSS_ID")
    private String businessId;

    @JacksonXmlProperty(localName = "BUSS_SUB_ID")
    private String businessSubId;

    public QueryUserTransactionStatusRequestBody() {
    }

    public String getUserTransactionSsn() {
        return userTransactionSsn;
    }

    public void setUserTransactionSsn(String userTransactionSsn) {
        this.userTransactionSsn = userTransactionSsn;
    }

    public String getRequestSsn() {
        return requestSsn;
    }

    public void setRequestSsn(String requestSsn) {
        this.requestSsn = requestSsn;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessSubId() {
        return businessSubId;
    }

    public void setBusinessSubId(String businessSubId) {
        this.businessSubId = businessSubId;
    }
}

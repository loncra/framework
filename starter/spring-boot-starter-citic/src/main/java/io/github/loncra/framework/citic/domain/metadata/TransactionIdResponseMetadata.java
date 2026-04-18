package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class TransactionIdResponseMetadata extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = 735253894696994210L;

    /**
     * 交易标识
     */
    @JacksonXmlProperty(localName = "TRANS_ID")
    private String transactionId;

    public TransactionIdResponseMetadata() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class PasswordIdAndTransactionIdResponseMetadata extends PasswordIdResponseMetadata {

    @Serial
    private static final long serialVersionUID = -3515676562609686375L;

    /**
     * 交易标识
     */
    @JacksonXmlProperty(localName = "TRANS_ID")
    private String transactionId;

    public PasswordIdAndTransactionIdResponseMetadata() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

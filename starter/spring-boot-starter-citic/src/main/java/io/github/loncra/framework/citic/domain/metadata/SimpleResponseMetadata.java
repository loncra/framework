package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SimpleResponseMetadata extends SignResponseMetadata {

    @Serial
    private static final long serialVersionUID = -5125652587836613316L;

    /**
     * 交易标识
     */
    @JacksonXmlProperty(localName = "TRANS_ID")
    private String transactionId;

    /**
     * 动态密码句柄
     */
    @JacksonXmlProperty(localName = "PWDID")
    private String passwordId;

    public SimpleResponseMetadata() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }
}

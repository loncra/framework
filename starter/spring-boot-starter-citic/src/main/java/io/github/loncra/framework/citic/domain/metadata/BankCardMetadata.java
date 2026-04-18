package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author maurice.chen
 */
public class BankCardMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -8754969517447326863L;

    @JacksonXmlProperty(localName = "PAN")
    private String cardNumber;

    @JacksonXmlProperty(localName = "DEFAULT_FLAG")
    private String defaultFlag;

    @JacksonXmlProperty(localName = "STT")
    private String status;

    public BankCardMetadata() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class QueryBankCardRequestBody extends BasicUserIdRequestBody {


    @Serial
    private static final long serialVersionUID = 4966176317988694156L;

    @JacksonXmlProperty(localName = "PAN")
    private String cardNumber;

    public QueryBankCardRequestBody() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}

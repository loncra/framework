package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 *
 * @author maurice.chen
 */
public class DefaultBankCardRequestBody extends BasicUserIdRequestBody {

    @Serial
    private static final long serialVersionUID = 4784529697370822363L;

    @JacksonXmlProperty(localName = "SETTLE_ACCT_NM")
    private String accountName;

    @JacksonXmlProperty(localName = "SETTLE_ACCT")
    private String accountNumber;

    public DefaultBankCardRequestBody() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

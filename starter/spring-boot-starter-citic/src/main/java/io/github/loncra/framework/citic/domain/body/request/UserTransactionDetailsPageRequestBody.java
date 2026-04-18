package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.enumerate.UserTransactionDetailsTypeEnum;
import io.github.loncra.framework.citic.service.CiticService;

import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author maurice.chen
 */
public class UserTransactionDetailsPageRequestBody extends BasicUserIdRequestBody {

    @Serial
    private static final long serialVersionUID = -5410841412570463696L;

    @JacksonXmlProperty(localName = "REGISTER_ATTR")
    private String registerType;

    @JacksonXmlProperty(localName = "TRANS_DATE")
    private String transactionDate = LocalDate.now().format(DateTimeFormatter.ofPattern(CiticService.DATE_FORMAT));

    @JacksonXmlProperty(localName = "PAGE")
    private String page;

    @JacksonXmlProperty(localName = "TRANS_TYPE")
    private String transactionType = UserTransactionDetailsTypeEnum.ALL_DETAIL.getValue();


    public UserTransactionDetailsPageRequestBody() {
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}

package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicMerchantMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SearchMerchantBalanceRequestBody extends BasicMerchantMetadata {

    @Serial
    private static final long serialVersionUID = 7249088410723199235L;

    @JacksonXmlProperty(localName = "REGISTER_ATTR")
    private String type;

    public SearchMerchantBalanceRequestBody() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

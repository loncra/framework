package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SearchUserBalanceRequestBody extends BasicUserIdRequestBody {

    @Serial
    private static final long serialVersionUID = 7044344529517279182L;

    @NotNull
    @JacksonXmlProperty(localName = "REGISTER_ATTR")
    private String type = "14";

    public SearchUserBalanceRequestBody() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class BasicUserIdRequestBody extends BasicRequestMetadata {

    public static final String USER_ID_FIELD = "userId";

    @Serial
    private static final long serialVersionUID = -7816076399964435909L;

    @NotNull
    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    public BasicUserIdRequestBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

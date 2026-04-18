package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.SimpleResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class UserRegistrationResponseBody extends SimpleResponseMetadata {

    @Serial
    private static final long serialVersionUID = -9185644851678554525L;

    /**
     * 用户 id
     */
    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    /**
     * 是否需要审核
     */
    @JacksonXmlProperty(localName = "IS_NEED_CHECK")
    private String check;

    public UserRegistrationResponseBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}

package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.SimpleResponseMetadata;

import java.io.Serial;

/**
 * @author olale
 */
public class UpdateUserResponseBody extends SimpleResponseMetadata {

    @Serial
    private static final long serialVersionUID = 3689294275650108527L;

    /**
     * 用户等级
     */
    @JacksonXmlProperty(localName = "USER_REGN_LEVL")
    private String level;

    /**
     * 是否需要审核
     */
    @JacksonXmlProperty(localName = "IS_NEED_CHECK")
    private String check;

    public UpdateUserResponseBody() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}

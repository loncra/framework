package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class WithdrawalResponseBody extends UserSsnResponseBody {

    @Serial
    private static final long serialVersionUID = -1630520203254214558L;

    @JacksonXmlProperty(localName = "WITH_CHANNEL")
    private String channel;

    public WithdrawalResponseBody() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

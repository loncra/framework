package io.github.loncra.framework.citic.domain.metadata;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author maurice.chen
 */
public class BasicResponseMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 7720848186082816691L;

    /**
     * 应答码
     */
    @JacksonXmlProperty(localName = "RSP_CODE")
    private String code;

    /**
     * 应答码描述
     */
    @JacksonXmlProperty(localName = "RSP_MSG")
    private String message;

    /**
     * 发起方流水号
     */
    @JacksonXmlProperty(localName = "REQ_SSN")
    private String requestSsn;

    public BasicResponseMetadata() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestSsn() {
        return requestSsn;
    }

    public void setRequestSsn(String requestSsn) {
        this.requestSsn = requestSsn;
    }

}

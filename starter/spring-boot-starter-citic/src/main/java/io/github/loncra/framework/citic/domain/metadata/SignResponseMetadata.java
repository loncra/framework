package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SignResponseMetadata extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = 76840038366390789L;

    /**
     * 签名
     */
    @JacksonXmlProperty(localName = "SIGN_INFO")
    private String sign;

    public SignResponseMetadata() {
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

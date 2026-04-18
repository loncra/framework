package io.github.loncra.framework.citic.domain.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serial;

/**
 * @author olale
 */
public class PasswordIdResponseMetadata extends BasicResponseMetadata {


    @Serial
    private static final long serialVersionUID = 7623744838351413183L;
    /**
     * 动态密码句柄
     */
    @JacksonXmlProperty(localName = "PWDID")
    private String passwordId;

    public PasswordIdResponseMetadata() {
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }
}

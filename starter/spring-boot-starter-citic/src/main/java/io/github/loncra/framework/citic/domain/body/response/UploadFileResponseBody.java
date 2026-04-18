package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class UploadFileResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = -568217927575175415L;

    @JacksonXmlProperty(localName = "FILE_NAME")
    private String filename;

    @JacksonXmlProperty(localName = "ENCRYPTION_FLAG")
    private String encryption;

    @JacksonXmlProperty(localName = "RESULT_CODE")
    private String resultCode;

    @JacksonXmlProperty(localName = "RESULT_MSG")
    private String resultMessage;

    public UploadFileResponseBody() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}

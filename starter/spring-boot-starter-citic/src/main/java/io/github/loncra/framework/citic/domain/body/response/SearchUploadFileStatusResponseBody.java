package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SearchUploadFileStatusResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = 5312541707426453155L;

    @JacksonXmlProperty(localName = "RESULT_CODE")
    private String searchCode;

    @JacksonXmlProperty(localName = "RESULT_MSG")
    private String searchMessage;

    @JacksonXmlProperty(localName = "FILE_ST")
    private String status;

    public SearchUploadFileStatusResponseBody() {
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

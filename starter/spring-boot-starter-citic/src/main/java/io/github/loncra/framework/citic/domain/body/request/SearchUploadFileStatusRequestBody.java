package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SearchUploadFileStatusRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 3448983757613382512L;

    @JacksonXmlProperty(localName = "FILE_NAME")
    private String filename;

    public SearchUploadFileStatusRequestBody() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class FileSignResponseMetadata extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = -98158271098222480L;

    @JacksonXmlProperty(localName = "FILE_CONTENT")
    private String fileContent;

    @JacksonXmlProperty(localName = "FILE_NAME")
    private String filename;

    public FileSignResponseMetadata() {
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

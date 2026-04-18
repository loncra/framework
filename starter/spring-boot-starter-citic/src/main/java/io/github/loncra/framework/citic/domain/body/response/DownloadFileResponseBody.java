package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class DownloadFileResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = 2684653080420685927L;

    @JacksonXmlProperty(localName = "FILE_CONTENT")
    private String fileContent;

    @JacksonXmlProperty(localName = "FILE_TYPE")
    private String fileType;

    @JacksonXmlProperty(localName = "SETTLE_DT")
    private String settleDate;

    @JacksonXmlProperty(localName = "RESULT_CODE")
    private String searchCode;

    @JacksonXmlProperty(localName = "RESULT_MSG")
    private String searchMessage;

    public DownloadFileResponseBody() {
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
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
}

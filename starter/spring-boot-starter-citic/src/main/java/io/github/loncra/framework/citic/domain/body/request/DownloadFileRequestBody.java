package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import io.github.loncra.framework.citic.enumerate.UploadFileTransportTypeEnum;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class DownloadFileRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 8772450414047676886L;

    @JacksonXmlProperty(localName = "FILE_NAME")
    private String fileName;

    @JacksonXmlProperty(localName = "FILE_TYPE")
    private String fileType;

    @JacksonXmlProperty(localName = "SETTLE_DT")
    private String settleDate;

    @JacksonXmlProperty(localName = "TRANS_TYPE")
    private String transmissionType = UploadFileTransportTypeEnum.MSG.toString();

    public DownloadFileRequestBody() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }
}

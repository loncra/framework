package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 *
 */
public class ElectronicReceiptRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = -6340378067977148760L;


    @JacksonXmlProperty(localName = "USER_SSN")
    private String userTransactionSsn;

    @JacksonXmlProperty(localName = "USER_TRANS_DT")
    private String transactionDate;

    @JacksonXmlProperty(localName = "TRANS_TYPE")
    private String transactionType;

    @JacksonXmlProperty(localName = "OPERATE_TYPE")
    private String operateType = "N";

    @JacksonXmlProperty(localName = "FILE_TRANS_TYPE")
    private String fileType = "MSG";

    public ElectronicReceiptRequestBody() {
    }

    public String getUserTransactionSsn() {
        return userTransactionSsn;
    }

    public void setUserTransactionSsn(String userTransactionSsn) {
        this.userTransactionSsn = userTransactionSsn;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

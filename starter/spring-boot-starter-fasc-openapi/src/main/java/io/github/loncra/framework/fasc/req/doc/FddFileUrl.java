package io.github.loncra.framework.fasc.req.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class FddFileUrl extends BaseBean {
    private String fileType;
    private String fddFileUrl;
    private String fileName;
    private String fileFormat;

    public FddFileUrl(
            String fileType,
            String fddFileUrl,
            String fileName
    ) {
        this.fileType = fileType;
        this.fddFileUrl = fddFileUrl;
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFddFileUrl() {
        return fddFileUrl;
    }

    public void setFddFileUrl(String fddFileUrl) {
        this.fddFileUrl = fddFileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
}

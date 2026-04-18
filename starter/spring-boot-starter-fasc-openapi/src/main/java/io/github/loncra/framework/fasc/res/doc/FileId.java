package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class FileId extends BaseBean {
    private String fileId;
    private String fileType;
    private String fddFileUrl;
    private String fileName;
    private String fileTotalPages;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public String getFileTotalPages() {
        return fileTotalPages;
    }

    public void setFileTotalPages(String fileTotalPages) {
        this.fileTotalPages = fileTotalPages;
    }
}

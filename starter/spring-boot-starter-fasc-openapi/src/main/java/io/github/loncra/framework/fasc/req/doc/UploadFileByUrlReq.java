package io.github.loncra.framework.fasc.req.doc;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class UploadFileByUrlReq extends BaseReq {

    private String fileType;

    private String fileUrl;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

package io.github.loncra.framework.fasc.req.doc;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.io.Serial;

public class GetUploadUrlReq extends BaseReq {

    @Serial
    private static final long serialVersionUID = 7331258856975013779L;

    private String fileType;

    private String storageType;

    public GetUploadUrlReq(String fileType) {
        this.fileType = fileType;
    }

    public GetUploadUrlReq() {
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

package io.github.loncra.framework.fasc.req.doc;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class OfdFileMergeReq extends BaseReq {
    private String fileId;
    private String insertFileId;
    private String mergeFileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getInsertFileId() {
        return insertFileId;
    }

    public void setInsertFileId(String insertFileId) {
        this.insertFileId = insertFileId;
    }

    public String getMergeFileName() {
        return mergeFileName;
    }

    public void setMergeFileName(String mergeFileName) {
        this.mergeFileName = mergeFileName;
    }
}

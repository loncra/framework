package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 15:11:32
 */
public class UploadInstantRes extends BaseBean {
    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}

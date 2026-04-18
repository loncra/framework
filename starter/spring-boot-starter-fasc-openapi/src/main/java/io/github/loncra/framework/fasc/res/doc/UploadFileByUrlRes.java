package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class UploadFileByUrlRes extends BaseBean {

    private String fddFileUrl;

    public String getFddFileUrl() {
        return fddFileUrl;
    }

    public void setFddFileUrl(String fddFileUrl) {
        this.fddFileUrl = fddFileUrl;
    }
}

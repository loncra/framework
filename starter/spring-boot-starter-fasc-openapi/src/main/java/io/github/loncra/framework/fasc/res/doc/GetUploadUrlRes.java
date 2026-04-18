package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class GetUploadUrlRes extends BaseBean {

    private String fddFileUrl;

    private String uploadUrl;

    public String getFddFileUrl() {
        return fddFileUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }
}

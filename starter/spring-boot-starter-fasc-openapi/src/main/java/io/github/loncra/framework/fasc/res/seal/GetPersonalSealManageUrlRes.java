package io.github.loncra.framework.fasc.res.seal;

public class GetPersonalSealManageUrlRes {

    /**
     * 签名管理的页面链接，链接有效期10分钟
     */
    private String resourceUrl;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}

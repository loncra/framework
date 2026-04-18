package io.github.loncra.framework.fasc.res.seal;

public class GetPersonalSealCreateUrlRes {

    /**
     * 创建签名的页面链接，链接有效期10分钟
     */
    private String sealCreateUrl;

    public String getSealCreateUrl() {
        return sealCreateUrl;
    }

    public void setSealCreateUrl(String sealCreateUrl) {
        this.sealCreateUrl = sealCreateUrl;
    }
}

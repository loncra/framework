package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2022/11/30 15:34
 */
public class GetSealFreeSignUrlRes extends BaseBean {
    private String freeSignUrl;
    private String freeSignShortUrl;

    public String getFreeSignUrl() {
        return freeSignUrl;
    }

    public void setFreeSignUrl(String freeSignUrl) {
        this.freeSignUrl = freeSignUrl;
    }

    public String getFreeSignShortUrl() {
        return freeSignShortUrl;
    }

    public void setFreeSignShortUrl(String freeSignShortUrl) {
        this.freeSignShortUrl = freeSignShortUrl;
    }
}

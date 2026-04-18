package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:45
 */
public class GetAppTemplatePreviewUrlRes extends BaseBean {
    private String appTemplatePreviewUrl;

    public String getAppTemplatePreviewUrl() {
        return appTemplatePreviewUrl;
    }

    public void setAppTemplatePreviewUrl(String appTemplatePreviewUrl) {
        this.appTemplatePreviewUrl = appTemplatePreviewUrl;
    }
}

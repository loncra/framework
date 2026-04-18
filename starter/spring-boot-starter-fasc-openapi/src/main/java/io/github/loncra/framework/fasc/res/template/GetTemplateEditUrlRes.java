package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * @date 2022/11/3 11:07:53
 */
public class GetTemplateEditUrlRes extends BaseBean {

    private String templateEditUrl;

    public String getTemplateEditUrl() {
        return templateEditUrl;
    }

    public void setTemplateEditUrl(String templateEditUrl) {
        this.templateEditUrl = templateEditUrl;
    }
}

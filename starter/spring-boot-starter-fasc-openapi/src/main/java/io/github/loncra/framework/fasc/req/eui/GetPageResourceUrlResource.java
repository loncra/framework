package io.github.loncra.framework.fasc.req.eui;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetPageResourceUrlResource extends BaseBean {
    private String resourceId;
    private String action;
    private String params;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}

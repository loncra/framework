package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 15:17:57
 */
public class SignTemplateListFilterInfo extends BaseBean {
    private String signTemplateName;
    private String signTemplateStatus;

    public String getSignTemplateName() {
        return signTemplateName;
    }

    public void setSignTemplateName(String signTemplateName) {
        this.signTemplateName = signTemplateName;
    }

    public String getSignTemplateStatus() {
        return signTemplateStatus;
    }

    public void setSignTemplateStatus(String signTemplateStatus) {
        this.signTemplateStatus = signTemplateStatus;
    }
}

package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BasePageRes;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 15:16:27
 */
public class SignTemplateListRes extends BasePageRes {
    private List<SignTemplateListInfo> signTemplates;

    public List<SignTemplateListInfo> getSignTemplates() {
        return signTemplates;
    }

    public void setSignTemplates(List<SignTemplateListInfo> signTemplates) {
        this.signTemplates = signTemplates;
    }
}

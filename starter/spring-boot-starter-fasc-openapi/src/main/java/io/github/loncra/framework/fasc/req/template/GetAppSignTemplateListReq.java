package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BasePageReq;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:25
 */
public class GetAppSignTemplateListReq extends BasePageReq {
    private ListFilterInfo listFilter;

    public ListFilterInfo getListFilter() {
        return listFilter;
    }

    public void setListFilter(ListFilterInfo listFilter) {
        this.listFilter = listFilter;
    }

    public static class ListFilterInfo {
        private String appSignTemplateName;

        public String getAppSignTemplateName() {
            return appSignTemplateName;
        }

        public void setAppSignTemplateName(String appSignTemplateName) {
            this.appSignTemplateName = appSignTemplateName;
        }
    }
}

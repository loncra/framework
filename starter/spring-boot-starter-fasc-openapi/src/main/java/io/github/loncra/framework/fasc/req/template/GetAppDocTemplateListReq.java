package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BasePageReq;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:06
 */
public class GetAppDocTemplateListReq extends BasePageReq {
    private ListFilterInfo listFilter;

    public ListFilterInfo getListFilter() {
        return listFilter;
    }

    public void setListFilter(ListFilterInfo listFilter) {
        this.listFilter = listFilter;
    }

    public static class ListFilterInfo {
        private String appDocTemplateName;

        public String getAppDocTemplateName() {
            return appDocTemplateName;
        }

        public void setAppDocTemplateName(String appDocTemplateName) {
            this.appDocTemplateName = appDocTemplateName;
        }
    }
}


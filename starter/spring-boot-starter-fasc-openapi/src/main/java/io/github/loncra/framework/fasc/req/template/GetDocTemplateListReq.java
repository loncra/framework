package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BasePageReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * 2021/9/11 15:16:27
 */
public class GetDocTemplateListReq extends BasePageReq {
    private OpenId ownerId;
    private DocTemplateListFilterInfo listFilter;

    public OpenId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OpenId ownerId) {
        this.ownerId = ownerId;
    }

    public DocTemplateListFilterInfo getListFilter() {
        return listFilter;
    }

    public void setListFilter(DocTemplateListFilterInfo listFilter) {
        this.listFilter = listFilter;
    }
}

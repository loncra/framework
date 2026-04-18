package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class SignTaskCatalogListRes extends BaseBean {
    private String catalogId;
    private String catalogName;
    private String parentCatalogId;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(String parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }
}

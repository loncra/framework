package io.github.loncra.framework.fasc.res.archives;


public class ArchivesCatalogListRes {

    /**
     * 文件夹Id
     */
    private String catalogId;

    /**
     * 文件夹名称
     */
    private String catalogName;

    /**
     * 文件夹名称
     */
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

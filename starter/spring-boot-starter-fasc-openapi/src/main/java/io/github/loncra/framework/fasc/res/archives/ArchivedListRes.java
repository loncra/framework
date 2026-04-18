package io.github.loncra.framework.fasc.res.archives;

import java.util.List;

public class ArchivedListRes {

    /**
     * 归档列表
     */
    private List<ArchivedDetailInfo> archives;

    /**
     * 当前页码
     */
    private Integer listPageNo;

    /**
     * 分页大小
     */
    private Integer listPageSize;

    /**
     * 总页数
     */
    private Integer listPageCount;

    /**
     * 总条数
     */
    private Integer total;

    public List<ArchivedDetailInfo> getArchives() {
        return archives;
    }

    public void setArchives(List<ArchivedDetailInfo> archives) {
        this.archives = archives;
    }

    public Integer getListPageNo() {
        return listPageNo;
    }

    public void setListPageNo(Integer listPageNo) {
        this.listPageNo = listPageNo;
    }

    public Integer getListPageSize() {
        return listPageSize;
    }

    public void setListPageSize(Integer listPageSize) {
        this.listPageSize = listPageSize;
    }

    public Integer getListPageCount() {
        return listPageCount;
    }

    public void setListPageCount(Integer listPageCount) {
        this.listPageCount = listPageCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

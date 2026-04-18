package io.github.loncra.framework.fasc.req.doc;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class FileProcessReq extends BaseReq {
    private List<FddFileUrl> fddFileUrlList;

    public FileProcessReq() {
    }

    public FileProcessReq(List<FddFileUrl> fddFileUrlList) {
        this.fddFileUrlList = fddFileUrlList;
    }

    public List<FddFileUrl> getFddFileUrlList() {
        return fddFileUrlList;
    }

    public void setFddFileUrlList(List<FddFileUrl> fddFileUrlList) {
        this.fddFileUrlList = fddFileUrlList;
    }
}

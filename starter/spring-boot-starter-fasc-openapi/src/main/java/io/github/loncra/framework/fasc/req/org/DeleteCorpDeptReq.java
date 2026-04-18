package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @date 2022/11/23 15:51
 */
public class DeleteCorpDeptReq extends BaseReq {

    private String openCorpId;

    private Long deptId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}

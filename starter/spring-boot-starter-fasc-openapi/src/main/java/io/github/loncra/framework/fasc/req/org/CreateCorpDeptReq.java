package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @date 2022/11/23 15:22
 */
public class CreateCorpDeptReq extends BaseReq {

    private String openCorpId;

    private Long parentDeptId;

    private String deptName;

    private String identifier;

    private Integer deptOrderNum;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public Long getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Long parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptOrderNum() {
        return deptOrderNum;
    }

    public void setDeptOrderNum(Integer deptOrderNum) {
        this.deptOrderNum = deptOrderNum;
    }
}

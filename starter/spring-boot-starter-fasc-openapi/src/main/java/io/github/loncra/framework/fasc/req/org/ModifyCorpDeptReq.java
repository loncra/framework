package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @date 2022/11/23 15:23
 */
public class ModifyCorpDeptReq extends BaseReq {

    private String openCorpId;

    private Long deptId;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

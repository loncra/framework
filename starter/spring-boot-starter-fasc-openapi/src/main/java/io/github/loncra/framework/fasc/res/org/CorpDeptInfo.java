package io.github.loncra.framework.fasc.res.org;

/**
 * @author Fadada
 * @date 2022/11/23 15:53
 */
public class CorpDeptInfo {

    private Long deptId;

    private String deptName;

    private String identifier;

    private Integer deptOrderNum;

    private Long parentDeptId;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public Long getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(Long parentDeptId) {
        this.parentDeptId = parentDeptId;
    }
}

package io.github.loncra.framework.fasc.res.org;

/**
 * @author Fadada
 * @date 2022/11/23 15:55
 */
public class CreateCorpDeptRes {

    private Long deptId;
    private Integer deptOrderNum;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptOrderNum() {
        return deptOrderNum;
    }

    public void setDeptOrderNum(Integer deptOrderNum) {
        this.deptOrderNum = deptOrderNum;
    }
}

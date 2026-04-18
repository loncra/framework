package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetMemberListFilter extends BaseBean {
    private String roleType;
    private Long deptId;
    private Boolean fetchChild;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Boolean getFetchChild() {
        return fetchChild;
    }

    public void setFetchChild(Boolean fetchChild) {
        this.fetchChild = fetchChild;
    }
}

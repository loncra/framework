package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.res.org.EmployeeInfo;

import java.util.List;

/**
 * @author Fadada
 * @date 2022/11/23 17:22
 */
public class CreateCorpMemberReq extends BaseReq {

    private String openCorpId;
    private List<EmployeeInfo> employeeInfos;
    private Boolean notifyActiveByEmail;
    private Boolean mergeMemberInfo;
    private String redirectUrl;

    public Boolean getMergeMemberInfo() {
        return mergeMemberInfo;
    }

    public void setMergeMemberInfo(Boolean mergeMemberInfo) {
        this.mergeMemberInfo = mergeMemberInfo;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public List<EmployeeInfo> getEmployeeInfos() {
        return employeeInfos;
    }

    public void setEmployeeInfos(List<EmployeeInfo> employeeInfos) {
        this.employeeInfos = employeeInfos;
    }

    public Boolean getNotifyActiveByEmail() {
        return notifyActiveByEmail;
    }

    public void setNotifyActiveByEmail(Boolean notifyActiveByEmail) {
        this.notifyActiveByEmail = notifyActiveByEmail;
    }
}

package io.github.loncra.framework.fasc.res.corp;

public class GetCorpBusinessInfoRes {
    // 序列号
    private String serialNo;
    // 组织机构名称
    private String orgName;
    // 统一社会信用代码/组织机构代码
    private String organizationNo;
    // 工商注册号
    private String companyCode;
    // 法定代表人/负责人姓名
    private String legalRepName;
    // 组织类型
    private String orgType;
    // 营业状态
    private String orgStatus;
    // 登记机关
    private String authority;
    // 注册资本
    private String capital;
    // 注册地址
    private String address;
    // 经营范围
    private String businessScope;
    // 注册日期/成立日期，格式为yyyy-mm-dd
    private String establishDate;
    // 营业执照开始时间，格式为yyyy-mm-dd
    private String startDate;
    // 营业执照结束时间，格式为yyyy-mm-dd，当营业执照为长期时返回“长期”
    private String endDate;
    // 核准日期，格式为yyyy-mm-dd
    private String issueDate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrganizationNo() {
        return organizationNo;
    }

    public void setOrganizationNo(String organizationNo) {
        this.organizationNo = organizationNo;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}

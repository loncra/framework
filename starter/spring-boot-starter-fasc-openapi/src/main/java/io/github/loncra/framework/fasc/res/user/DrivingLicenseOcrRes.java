package io.github.loncra.framework.fasc.res.user;

import java.util.List;

public class DrivingLicenseOcrRes {
    private String serialNo;
    private String name;
    private String nationality;
    private String sex;
    private String address;
    private String birthday;
    private String firstIssueDate;
    private String number;
    private String quasiDrivingType;
    private String startDate;
    private String endDate;
    private String archivesCode;
    private String record;
    private List<String> warningMsg;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstIssueDate() {
        return firstIssueDate;
    }

    public void setFirstIssueDate(String firstIssueDate) {
        this.firstIssueDate = firstIssueDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuasiDrivingType() {
        return quasiDrivingType;
    }

    public void setQuasiDrivingType(String quasiDrivingType) {
        this.quasiDrivingType = quasiDrivingType;
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

    public String getArchivesCode() {
        return archivesCode;
    }

    public void setArchivesCode(String archivesCode) {
        this.archivesCode = archivesCode;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public List<String> getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(List<String> warningMsg) {
        this.warningMsg = warningMsg;
    }
}

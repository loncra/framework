package io.github.loncra.framework.fasc.req.archives;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

public class CreateOrModifyPerformanceReq extends BaseReq {
    private String openCorpId;

    private String archivesId;

    private String performanceId;

    private String performanceType;

    private String performanceName;

    private String expireTime;

    private String remindStartDate;

    private String remindFrequency;

    private Integer cycleDays;

    private String remindTime;

    private String amount;

    private List<String> reminder;

    private Boolean isRemind;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(String performanceType) {
        this.performanceType = performanceType;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getRemindStartDate() {
        return remindStartDate;
    }

    public void setRemindStartDate(String remindStartDate) {
        this.remindStartDate = remindStartDate;
    }

    public String getRemindFrequency() {
        return remindFrequency;
    }

    public void setRemindFrequency(String remindFrequency) {
        this.remindFrequency = remindFrequency;
    }

    public Integer getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<String> getReminder() {
        return reminder;
    }

    public void setReminder(List<String> reminder) {
        this.reminder = reminder;
    }

    public Boolean getRemind() {
        return isRemind;
    }

    public void setRemind(Boolean remind) {
        isRemind = remind;
    }
}

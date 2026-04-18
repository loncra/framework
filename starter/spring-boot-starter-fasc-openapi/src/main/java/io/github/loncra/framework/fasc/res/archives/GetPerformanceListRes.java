package io.github.loncra.framework.fasc.res.archives;

import java.util.List;


public class GetPerformanceListRes {
    private String archivesId;

    private String performanceId;

    private String performanceType;

    private String performanceName;

    private String expireTime;

    private String remindFrequency;

    private Integer cycleDays;

    private String remindTime;

    private String remindStartDate;

    private String amount;

    private List<String> reminder;


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

    public String getRemindStartDate() {
        return remindStartDate;
    }

    public void setRemindStartDate(String remindStartDate) {
        this.remindStartDate = remindStartDate;
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
}

package io.github.loncra.framework.fasc.res.corp;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/9/25 13:34
 */
public class GetUsageAvailablenumRes {
    private String usageCode;
    private String usageName;
    private String usageUnit;
    private String availableNum;
    private List<UsageShareDetail> usageShareDetail;

    public String getUsageCode() {
        return usageCode;
    }

    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }

    public String getUsageName() {
        return usageName;
    }

    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }

    public String getUsageUnit() {
        return usageUnit;
    }

    public void setUsageUnit(String usageUnit) {
        this.usageUnit = usageUnit;
    }

    public String getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(String availableNum) {
        this.availableNum = availableNum;
    }

    public List<UsageShareDetail> getUsageShareDetail() {
        return usageShareDetail;
    }

    public void setUsageShareDetail(List<UsageShareDetail> usageShareDetail) {
        this.usageShareDetail = usageShareDetail;
    }
}

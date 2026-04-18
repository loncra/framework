package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author zhoufucheng
 * @date 2023/9/25 11:25
 */
public class GetUsageAvailablenumReq extends BaseReq {
    private String openCorpId;
    private String usageCode;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getUsageCode() {
        return usageCode;
    }

    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }
}

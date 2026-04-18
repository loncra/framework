package io.github.loncra.framework.fasc.req.draft;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author zhoufucheng
 * @date 2023/11/30 13:56
 */
public class GetFinishedFileReq extends BaseReq {
    private String contractConsultId;

    public String getContractConsultId() {
        return contractConsultId;
    }

    public void setContractConsultId(String contractConsultId) {
        this.contractConsultId = contractConsultId;
    }
}

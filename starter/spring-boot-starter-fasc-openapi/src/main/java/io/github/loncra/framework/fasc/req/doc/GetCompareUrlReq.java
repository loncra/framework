package io.github.loncra.framework.fasc.req.doc;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author zhoufucheng
 * @date 2022/12/28 0028 15:46
 */
public class GetCompareUrlReq extends BaseReq {
    private OpenId initiator;
    private String originFileId;
    private String targetFileId;

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public String getOriginFileId() {
        return originFileId;
    }

    public void setOriginFileId(String originFileId) {
        this.originFileId = originFileId;
    }

    public String getTargetFileId() {
        return targetFileId;
    }

    public void setTargetFileId(String targetFileId) {
        this.targetFileId = targetFileId;
    }
}

package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author Fadada
 * @date 2022/11/23 17:39
 */
public class SetCorpMemberDeptReq extends BaseReq {

    private String openCorpId;
    private List<Long> memberIds;
    private List<Long> memberDeptIds;
    private String model;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public List<Long> getMemberDeptIds() {
        return memberDeptIds;
    }

    public void setMemberDeptIds(List<Long> memberDeptIds) {
        this.memberDeptIds = memberDeptIds;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

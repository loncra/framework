package io.github.loncra.framework.fasc.res.approval;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/9/6 11:36
 */
public class ApprovalFlowNode {
    private Integer sortNum;

    private String approvalNodeType;

    private List<ApproversFlowInfo> approversInfos;

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getApprovalNodeType() {
        return approvalNodeType;
    }

    public void setApprovalNodeType(String approvalNodeType) {
        this.approvalNodeType = approvalNodeType;
    }

    public List<ApproversFlowInfo> getApproversInfos() {
        return approversInfos;
    }

    public void setApproversInfos(List<ApproversFlowInfo> approversInfos) {
        this.approversInfos = approversInfos;
    }
}

package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class ApprovalInfoNode extends BaseBean {
    private Long nodeId;
    private String approvalType;
    private String nodeStatus;
    private List<NodeApproverInfo> approversInfo;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public List<NodeApproverInfo> getApproversInfo() {
        return approversInfo;
    }

    public void setApproversInfo(List<NodeApproverInfo> approversInfo) {
        this.approversInfo = approversInfo;
    }
}

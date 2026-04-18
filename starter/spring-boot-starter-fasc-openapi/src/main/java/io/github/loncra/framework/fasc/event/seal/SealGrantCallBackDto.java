package io.github.loncra.framework.fasc.event.seal;

import java.util.List;

/**
 * 印章授权解除事件
 */

public class SealGrantCallBackDto extends EventCallBackDto {

    private List<Long> sealIds;

    private List<Long> memberIds;

    public List<Long> getSealIds() {
        return sealIds;
    }

    public void setSealIds(List<Long> sealIds) {
        this.sealIds = sealIds;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}

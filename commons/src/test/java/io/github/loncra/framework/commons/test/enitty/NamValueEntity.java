package io.github.loncra.framework.commons.test.enitty;

import io.github.loncra.framework.commons.annotation.Description;
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Description("键值对数据")
public class NamValueEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2901080212503878142L;

    @Description("是或否")
    private YesOrNo yesOrNo;

    @JsonCollectionGenericType(ExecuteStatus.class)
    private List<ExecuteStatus> executeStatuses;

    public NamValueEntity() {
    }

    public YesOrNo getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(YesOrNo yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public List<ExecuteStatus> getExecuteStatuses() {
        return executeStatuses;
    }

    public void setExecuteStatuses(List<ExecuteStatus> executeStatuses) {
        this.executeStatuses = executeStatuses;
    }
}

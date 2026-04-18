package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 14:31:32
 */
public class ActorCorp extends BaseBean {
    private String actorCorpId;
    private CorpIdentInfo corpIdentInfo;
    private CorpInfoExtend corpInfoExtend;
    private String operatorId;
    private UserIdentInfo operatorIdentInfo;
    private UserInfoExtend operatorInfoExtend;

    public String getActorCorpId() {
        return actorCorpId;
    }

    public void setActorCorpId(String actorCorpId) {
        this.actorCorpId = actorCorpId;
    }

    public CorpIdentInfo getCorpIdentInfo() {
        return corpIdentInfo;
    }

    public void setCorpIdentInfo(CorpIdentInfo corpIdentInfo) {
        this.corpIdentInfo = corpIdentInfo;
    }

    public CorpInfoExtend getCorpInfoExtend() {
        return corpInfoExtend;
    }

    public void setCorpInfoExtend(CorpInfoExtend corpInfoExtend) {
        this.corpInfoExtend = corpInfoExtend;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public UserIdentInfo getOperatorIdentInfo() {
        return operatorIdentInfo;
    }

    public void setOperatorIdentInfo(UserIdentInfo operatorIdentInfo) {
        this.operatorIdentInfo = operatorIdentInfo;
    }

    public UserInfoExtend getOperatorInfoExtend() {
        return operatorInfoExtend;
    }

    public void setOperatorInfoExtend(UserInfoExtend operatorInfoExtend) {
        this.operatorInfoExtend = operatorInfoExtend;
    }
}

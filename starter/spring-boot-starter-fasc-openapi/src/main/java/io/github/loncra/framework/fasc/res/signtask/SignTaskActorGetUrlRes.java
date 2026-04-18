package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class SignTaskActorGetUrlRes extends BaseBean {
    private String actorSignTaskUrl;
    private String actorSignTaskEmbedUrl;
    private MiniAppInfo actorSignTaskMiniAppInfo;

    public String getActorSignTaskUrl() {
        return actorSignTaskUrl;
    }

    public void setActorSignTaskUrl(String actorSignTaskUrl) {
        this.actorSignTaskUrl = actorSignTaskUrl;
    }

    public String getActorSignTaskEmbedUrl() {
        return actorSignTaskEmbedUrl;
    }

    public void setActorSignTaskEmbedUrl(String actorSignTaskEmbedUrl) {
        this.actorSignTaskEmbedUrl = actorSignTaskEmbedUrl;
    }

    public MiniAppInfo getActorSignTaskMiniAppInfo() {
        return actorSignTaskMiniAppInfo;
    }

    public void setActorSignTaskMiniAppInfo(MiniAppInfo actorSignTaskMiniAppInfo) {
        this.actorSignTaskMiniAppInfo = actorSignTaskMiniAppInfo;
    }
}

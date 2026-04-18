package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class CreateWithTemplateReq extends CreateSignTaskBaseReq {
    private String signTemplateId;
    private List<AddActorsTempInfo> actors;
    private List<Watermark> watermarks;

    public String getSignTemplateId() {
        return signTemplateId;
    }

    public void setSignTemplateId(String signTemplateId) {
        this.signTemplateId = signTemplateId;
    }

    public List<AddActorsTempInfo> getActors() {
        return actors;
    }

    public void setActors(List<AddActorsTempInfo> actors) {
        this.actors = actors;
    }

    public List<Watermark> getWatermarks() {
        return watermarks;
    }

    public void setWatermarks(List<Watermark> watermarks) {
        this.watermarks = watermarks;
    }
}

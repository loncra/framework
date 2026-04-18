package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class AddActorsTempInfo extends BaseBean {

    private Actor actor;

    private List<AddFillFieldInfo> fillFields;

    private List<AddSignFieldInfo> signFields;

    private TemplateSignConfigInfoReq signConfigInfo;


    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<AddFillFieldInfo> getFillFields() {
        return fillFields;
    }

    public void setFillFields(List<AddFillFieldInfo> fillFields) {
        this.fillFields = fillFields;
    }

    public TemplateSignConfigInfoReq getSignConfigInfo() {
        return signConfigInfo;
    }

    public void setSignConfigInfo(TemplateSignConfigInfoReq signConfigInfo) {
        this.signConfigInfo = signConfigInfo;
    }

    public List<AddSignFieldInfo> getSignFields() {
        return signFields;
    }

    public void setSignFields(List<AddSignFieldInfo> signFields) {
        this.signFields = signFields;
    }
}

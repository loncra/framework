package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/13 11:40:45
 */
public class CreateSignTaskReq extends CreateSignTaskBaseReq {
    private Boolean signInOrder;
    private List<AddDocInfo> docs;
    private List<AddAttachInfo> attachs;
    private List<AddActorsInfo> actors;
    private DocSignConfigInfoReq signConfigInfo;
    private List<Watermark> watermarks;
    private Boolean isAllowInsertFile;

    public DocSignConfigInfoReq getSignConfigInfo() {
        return signConfigInfo;
    }

    public void setSignConfigInfo(DocSignConfigInfoReq signConfigInfo) {
        this.signConfigInfo = signConfigInfo;
    }

    public Boolean getSignInOrder() {
        return signInOrder;
    }

    public void setSignInOrder(Boolean signInOrder) {
        this.signInOrder = signInOrder;
    }

    public List<AddDocInfo> getDocs() {
        return docs;
    }

    public void setDocs(List<AddDocInfo> docs) {
        this.docs = docs;
    }

    public List<AddAttachInfo> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<AddAttachInfo> attachs) {
        this.attachs = attachs;
    }

    public List<AddActorsInfo> getActors() {
        return actors;
    }

    public void setActors(List<AddActorsInfo> actors) {
        this.actors = actors;
    }

    public List<Watermark> getWatermarks() {
        return watermarks;
    }

    public void setWatermarks(List<Watermark> watermarks) {
        this.watermarks = watermarks;
    }

    public Boolean getAllowInsertFile() {
        return isAllowInsertFile;
    }

    public void setAllowInsertFile(Boolean allowInsertFile) {
        isAllowInsertFile = allowInsertFile;
    }
}

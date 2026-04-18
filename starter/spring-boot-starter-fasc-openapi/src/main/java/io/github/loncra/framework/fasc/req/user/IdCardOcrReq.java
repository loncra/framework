package io.github.loncra.framework.fasc.req.user;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class IdCardOcrReq extends BaseReq {

    private String faceSide;

    private String nationalEmblemSide;

    public String getFaceSide() {
        return faceSide;
    }

    public void setFaceSide(String faceSide) {
        this.faceSide = faceSide;
    }

    public String getNationalEmblemSide() {
        return nationalEmblemSide;
    }

    public void setNationalEmblemSide(String nationalEmblemSide) {
        this.nationalEmblemSide = nationalEmblemSide;
    }
}

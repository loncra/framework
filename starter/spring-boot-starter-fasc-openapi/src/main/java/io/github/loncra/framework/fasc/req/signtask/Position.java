package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

public class Position extends BaseBean {
    private Integer positionPageNo;
    private String positionX;
    private String positionY;

    public Integer getPositionPageNo() {
        return positionPageNo;
    }

    public void setPositionPageNo(Integer positionPageNo) {
        this.positionPageNo = positionPageNo;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }
}

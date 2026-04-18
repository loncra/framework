package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2022/11/30 15:49
 */
public class FreeSignInfo extends BaseBean {
    private String businessId;
    private String businessScene;
    private String businessSceneExp;
    private String expiresTime;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(String businessScene) {
        this.businessScene = businessScene;
    }

    public String getBusinessSceneExp() {
        return businessSceneExp;
    }

    public void setBusinessSceneExp(String businessSceneExp) {
        this.businessSceneExp = businessSceneExp;
    }

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }
}

package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetAppointedSealUrlRes extends BaseBean {

    /**
     * 指定印章的管理页面链接
     */
    private String appointedSealUrl;

    public String getAppointedSealUrl() {
        return appointedSealUrl;
    }

    public void setAppointedSealUrl(String appointedSealUrl) {
        this.appointedSealUrl = appointedSealUrl;
    }
}

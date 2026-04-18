package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2022/12/28 0028 15:48
 */
public class GetCompareUrlRes extends BaseBean {
    private String compareId;
    private String compareUrl;

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }
}

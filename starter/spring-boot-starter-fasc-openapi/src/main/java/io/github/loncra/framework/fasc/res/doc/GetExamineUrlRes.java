package io.github.loncra.framework.fasc.res.doc;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author zhoufucheng
 * @date 2022/12/28 0028 16:02
 */
public class GetExamineUrlRes extends BaseBean {
    private String examineId;
    private String examineUrl;

    public String getExamineId() {
        return examineId;
    }

    public void setExamineId(String examineId) {
        this.examineId = examineId;
    }

    public String getExamineUrl() {
        return examineUrl;
    }

    public void setExamineUrl(String examineUrl) {
        this.examineUrl = examineUrl;
    }
}

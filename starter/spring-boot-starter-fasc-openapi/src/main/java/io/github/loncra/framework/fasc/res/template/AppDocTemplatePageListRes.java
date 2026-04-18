package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BasePageRes;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:09
 */
public class AppDocTemplatePageListRes extends BasePageRes {
    private List<DocAppTemplate> appDocTemplates;

    public List<DocAppTemplate> getAppDocTemplates() {
        return appDocTemplates;
    }

    public void setAppDocTemplates(List<DocAppTemplate> appDocTemplates) {
        this.appDocTemplates = appDocTemplates;
    }

    public static class DocAppTemplate {
        private String appDocTemplateId;
        private String appDocTemplateName;
        private String appDocTemplateStatus;
        private String createTime;
        private String updateTime;

        public String getAppDocTemplateId() {
            return appDocTemplateId;
        }

        public void setAppDocTemplateId(String appDocTemplateId) {
            this.appDocTemplateId = appDocTemplateId;
        }

        public String getAppDocTemplateName() {
            return appDocTemplateName;
        }

        public void setAppDocTemplateName(String appDocTemplateName) {
            this.appDocTemplateName = appDocTemplateName;
        }

        public String getAppDocTemplateStatus() {
            return appDocTemplateStatus;
        }

        public void setAppDocTemplateStatus(String appDocTemplateStatus) {
            this.appDocTemplateStatus = appDocTemplateStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}

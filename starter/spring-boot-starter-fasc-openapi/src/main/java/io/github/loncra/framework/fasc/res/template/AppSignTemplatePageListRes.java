package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BasePageRes;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:27
 */
public class AppSignTemplatePageListRes extends BasePageRes {
    private List<AppSignAppTemplate> appSignTemplates;

    public List<AppSignAppTemplate> getAppSignTemplates() {
        return appSignTemplates;
    }

    public void setAppSignTemplates(List<AppSignAppTemplate> appSignTemplates) {
        this.appSignTemplates = appSignTemplates;
    }

    public static class AppSignAppTemplate {
        private String appSignTemplateId;
        private String appSignTemplateName;
        private String appSignTemplateStatus;
        private String createTime;
        private String updateTime;

        public String getAppSignTemplateId() {
            return appSignTemplateId;
        }

        public void setAppSignTemplateId(String appSignTemplateId) {
            this.appSignTemplateId = appSignTemplateId;
        }

        public String getAppSignTemplateName() {
            return appSignTemplateName;
        }

        public void setAppSignTemplateName(String appSignTemplateName) {
            this.appSignTemplateName = appSignTemplateName;
        }

        public String getAppSignTemplateStatus() {
            return appSignTemplateStatus;
        }

        public void setAppSignTemplateStatus(String appSignTemplateStatus) {
            this.appSignTemplateStatus = appSignTemplateStatus;
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

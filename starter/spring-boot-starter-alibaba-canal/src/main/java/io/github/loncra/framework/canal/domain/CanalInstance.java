package io.github.loncra.framework.canal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.IdEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.Serial;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * canal 实例 dto
 *
 * @author maurice.chen
 */
public class CanalInstance extends IdEntity<Long> {

    @Serial
    private static final long serialVersionUID = 5124204804701344110L;

    /**
     * 集群 id
     */
    private Long clusterId;

    /**
     * 集群信息
     */
    private CanalCluster canalCluster;

    /**
     * 服务节点配置信息
     */
    private CanalNodeServer nodeServer;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 实例是否正已驱动
     */
    private YesOrNo runningStatus;

    /**
     * 最后修改时间
     */
    private Instant modifiedTime;

    /**
     * 实例配置信息
     */
    private String content;

    /**
     * 内容 md5 值
     */
    private String contentMd5;

    /**
     * 是否订阅此实例
     */
    private YesOrNo subscribeStatus;

    /**
     * 配置内容
     *
     * @return 配置内容
     */
    public Properties properties() {
        if (StringUtils.isEmpty(content)) {
            return new Properties();
        }
        try {
            return PropertiesLoaderUtils.loadProperties(new ByteArrayResource(content.getBytes()));
        }
        catch (Exception e) {
            throw new SystemException("读取 canal 实例配置内容出现异常", e);
        }
    }

    @JsonIgnore
    public List<String> getSubscribeAssertValue() {
        List<String> result = new LinkedList<>();

        if (StringUtils.isNotEmpty(name)) {
            result.add(name);
        }

        if (Objects.nonNull(getId())) {
            result.add(getId().toString());
        }

        return result;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public CanalCluster getCanalCluster() {
        return canalCluster;
    }

    public void setCanalCluster(CanalCluster canalCluster) {
        this.canalCluster = canalCluster;
    }

    public CanalNodeServer getNodeServer() {
        return nodeServer;
    }

    public void setNodeServer(CanalNodeServer nodeServer) {
        this.nodeServer = nodeServer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YesOrNo getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(YesOrNo runningStatus) {
        this.runningStatus = runningStatus;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public YesOrNo getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(YesOrNo subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }
}

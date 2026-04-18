package io.github.loncra.framework.canal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.id.IdEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * canal 节点服务 dto
 *
 * @author maurice.chen
 */
public class CanalNodeServer extends IdEntity<Long> {

    @Serial
    private static final long serialVersionUID = -6776019605471544293L;
    /**
     * 集群信息
     */
    private CanalCluster canalCluster;
    /**
     * 集群 id
     */
    private Long clusterId;

    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点 ip
     */
    private String ip;
    /**
     * 开放给管理段调用的端口号
     */
    private Integer adminPort;
    /**
     * 开发给监听性能监控的端口号
     */
    private Integer metricPort;
    /**
     * 开放给客户链接获取 binlog 日志信息的段口号
     */
    private Integer tcpPort;
    /**
     * 服务状态
     */
    private String status;
    /**
     * 修改时间
     */
    private Instant modifiedTime;

    @JsonIgnore
    public List<String> getSubscribeAssertValue() {
        List<String> result = new LinkedList<>();

        if (StringUtils.isNotEmpty(ip)) {
            result.add(ip);
        }

        if (StringUtils.isNotEmpty(name)) {
            result.add(name);
        }

        if (Objects.nonNull(getId())) {
            result.add(getId().toString());
        }

        return result;
    }

    public CanalCluster getCanalCluster() {
        return canalCluster;
    }

    public void setCanalCluster(CanalCluster canalCluster) {
        this.canalCluster = canalCluster;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(Integer adminPort) {
        this.adminPort = adminPort;
    }

    public Integer getMetricPort() {
        return metricPort;
    }

    public void setMetricPort(Integer metricPort) {
        this.metricPort = metricPort;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}

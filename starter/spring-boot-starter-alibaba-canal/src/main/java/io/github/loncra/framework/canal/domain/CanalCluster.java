package io.github.loncra.framework.canal.domain;

import io.github.loncra.framework.commons.id.IdEntity;

import java.io.Serial;
import java.time.Instant;

/**
 * canal 集群数据 dto
 *
 * @author maurice.chen
 */
public class CanalCluster extends IdEntity<Long> {

    @Serial
    private static final long serialVersionUID = 7325407486272402604L;

    /**
     * 集群名称
     */
    private String name;
    /**
     * zk hosts
     */
    private String zkHosts;
    /**
     * 修改时间
     */
    private Instant modifiedTime;

    public CanalCluster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZkHosts() {
        return zkHosts;
    }

    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}

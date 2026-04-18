package io.github.loncra.framework.nacos.event;

import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.client.naming.listener.NamingChangeEvent;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.TimeProperties;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * 服务监听器实现
 *
 * @author maurice.chen
 */
public class NacosServiceEventListener implements EventListener {
    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 最后访问时间
     */
    private Instant lastAccessTime = Instant.now();

    /**
     * 过去时间
     */
    private final TimeProperties expirationTime;

    private final String serviceName;

    private final String group;

    private final String namespace;

    /**
     * spring 事件推送着
     */
    private final ApplicationEventPublisher applicationEventPublisher;

    public NacosServiceEventListener(
            TimeProperties expirationTime,
            String serviceName,
            String group,
            String namespace,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.expirationTime = expirationTime;
        this.serviceName = serviceName;
        this.group = group;
        this.namespace = namespace;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof NamingChangeEvent namingChangeEvent) {
            applicationEventPublisher.publishEvent(new NacosInstancesChangeEvent(namingChangeEvent));
        }
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Instant getCreationTime() {
        return creationTime;
    }

    /**
     * 获取最后访问时间
     *
     * @return 最后访问时间
     */
    public Instant getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * 设置最后访问时间
     *
     * @param lastAccessTime 最后访问时间
     */
    public void setLastAccessTime(Instant lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * 设置监听器为超时
     */
    public void expired() {
        setLastAccessTime(Instant.now().minusMillis(expirationTime.toMillis()));
    }

    /**
     * 是否过期
     *
     * @return true 是，否则 false
     */
    public boolean isExpired() {
        return Duration.between(lastAccessTime, Instant.now()).toMillis() >= expirationTime.toMillis();
    }

    /**
     * 获取服务名称
     *
     * @return 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 获取租名称
     *
     * @return 组名称
     */
    public String getGroup() {
        return group;
    }

    /**
     * 获取命名空间
     *
     * @return 命名空间
     */
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String toString() {
        return NacosSpringEventManager.toUniqueValue(getServiceName(), getGroup(), getNamespace());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NacosServiceEventListener that = CastUtils.cast(o);
        return Objects.equals(toString(), that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}

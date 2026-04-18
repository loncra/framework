package io.github.loncra.framework.socketio.api;

import io.github.loncra.framework.socketio.api.enumerate.ConnectStatus;
import io.github.loncra.framework.spring.security.core.entity.support.MobileSecurityPrincipal;

import java.io.Serial;
import java.time.Instant;

/**
 * Socket 主体信息
 *
 * @author maurice.chen
 */
public class SocketPrincipal extends MobileSecurityPrincipal {

    /**
     * 持有客户端连接的 id 字段名称
     */
    public static final String DEFAULT_SOCKET_CLIENT_ID_NAME = "socketClientIds";

    @Serial
    private static final long serialVersionUID = -5635703490557755253L;

    /**
     * 创建时间
     */
    private Instant creationTime = Instant.now();

    /**
     * 连接状态
     *
     * @see ConnectStatus
     */
    private ConnectStatus connectStatus;

    /**
     * 连接成功时间
     */
    private Instant connectionTime;

    public SocketPrincipal() {
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
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 获取连接状态
     *
     * @return 连接状态
     */
    public ConnectStatus getConnectStatus() {
        return connectStatus;
    }

    /**
     * 设置连接状态
     *
     * @param connectStatus 连接状态
     */
    public void setConnectStatus(ConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }

    /**
     * 获取连接成功时间
     *
     * @return 连接成功时间
     */
    public Instant getConnectionTime() {
        return connectionTime;
    }

    /**
     * 设置连接成功时间
     *
     * @param connectionTime 连接成功时间
     */
    public void setConnectionTime(Instant connectionTime) {
        this.connectionTime = connectionTime;
    }

}

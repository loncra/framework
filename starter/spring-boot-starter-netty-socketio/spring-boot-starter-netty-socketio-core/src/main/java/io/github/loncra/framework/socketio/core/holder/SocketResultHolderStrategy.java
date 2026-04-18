package io.github.loncra.framework.socketio.core.holder;

import io.github.loncra.framework.socketio.api.SocketResult;

/**
 * socket 结果集持有者策略
 *
 * @author maurice.chen
 */
public interface SocketResultHolderStrategy {

    /**
     * 清除 socket 结果集
     */
    void clear();

    /**
     * 获取 socket 结果集
     *
     * @return socket 结果集
     */
    SocketResult get();

    /**
     * 设置 socket 结果集
     *
     * @param result socket 结果集
     */
    void set(SocketResult result);

    /**
     * 创建 socket 结果集
     *
     * @return socket 结果集
     */
    SocketResult create();
}

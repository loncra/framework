package io.github.loncra.framework.socketio.api.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * Socket 连接状态枚举
 *
 * @author maurice
 */
public enum ConnectStatus implements NameValueEnum<Integer> {

    /**
     * 断开连接
     */
    Disconnected("断开", 10),

    /**
     * 连接中
     */
    Connecting("连接中", 20),

    /**
     * 已连接
     */
    Connected("已连接", 30);

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 状态值
     */
    private final Integer value;

    /**
     * 构造函数
     *
     * @param name  状态名称
     * @param value 状态值
     */
    ConnectStatus(
            String name,
            int value
    ) {
        this.name = name;
        this.value = value;
    }

    /**
     * 获取状态名称
     *
     * @return 状态名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取状态值
     *
     * @return 状态值
     */
    @Override
    public Integer getValue() {
        return value;
    }
}

package io.github.loncra.framework.commons.enumerate.basic;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 协议类型枚举
 *
 * @author maurice.chen
 */
public enum Protocol implements NameValueEnum<Integer> {

    /**
     * http 协议
     */
    HTTP_OR_HTTPS("http(s)", 10),

    /**
     * websocket 协议
     */
    WS_OR_WSS("ws(s)", 20),

    ;

    /**
     * 名称
     */
    private final String name;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 协议类型枚举
     *
     * @param name  名称
     * @param value 值
     */
    Protocol(
            String name,
            Integer value
    ) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}

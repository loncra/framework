package io.github.loncra.framework.nacos.event;

import com.alibaba.nacos.api.naming.listener.NamingEvent;

/**
 * 服务订阅校验，用于是否过滤订阅服务使用
 *
 * @author maurice.chen
 */
public interface NacosServiceListenerValidator {

    /**
     * 是否支持此服务
     *
     * @param nacosService nacos 服务
     *
     * @return true 是，否则 false，返回 true 时，会触发 {@link #subscribeValid(NamingEvent)} 方法
     */
    boolean isSupport(NamingEvent nacosService);

    /**
     * 订阅服务校验
     *
     * @param nacosService nacos 服务
     *
     * @return true 订阅服务，否则 false
     */
    boolean subscribeValid(NamingEvent nacosService);
}

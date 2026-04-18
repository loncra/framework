package io.github.loncra.framework.nacos.event;

import com.alibaba.nacos.api.naming.listener.NamingEvent;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;


/**
 * 服务取消订阅事件
 *
 * @author maurice.chen
 */
public class NacosServiceUnsubscribeEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -7678924793531762050L;

    /**
     * 创建一个服务取消订阅事件
     *
     * @param service 服务
     */
    public NacosServiceUnsubscribeEvent(NamingEvent service) {
        super(service);
    }

}

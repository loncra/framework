package io.github.loncra.framework.nacos.event;

import com.alibaba.nacos.api.naming.listener.NamingEvent;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;


/**
 * 服务订阅事件
 *
 * @author maurice.chen
 */
public class NacosServiceSubscribeEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -7678924793531762050L;

    /**
     * 创建一个服务订阅事件
     *
     * @param nacosService Nacos 服务
     */
    public NacosServiceSubscribeEvent(NamingEvent nacosService) {
        super(nacosService);
    }

}

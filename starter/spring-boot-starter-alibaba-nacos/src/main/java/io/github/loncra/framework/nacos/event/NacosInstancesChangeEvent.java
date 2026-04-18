package io.github.loncra.framework.nacos.event;

import com.alibaba.nacos.client.naming.listener.NamingChangeEvent;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;


/**
 * nacos 实例改变事件
 *
 * @author maurice.chen
 */
public class NacosInstancesChangeEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 3631201898764427564L;

    /**
     * 创建一个 Nacos 实例改变事件
     *
     * @param nacosService Nacos 服务
     */
    public NacosInstancesChangeEvent(NamingChangeEvent nacosService) {
        super(nacosService);
    }
}

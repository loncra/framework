package io.github.loncra.framework.nacos.event;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.nacos.task.annotation.NacosCronScheduled;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;

import java.time.Instant;
import java.util.*;

/**
 * nacos 的 spring 事件管理器
 *
 * @author maurice.chen
 */
public class NacosSpringEventManager implements ApplicationEventPublisherAware, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosSpringEventManager.class);

    /**
     * nacos 服务发现事件配置
     */
    private final NacosDiscoveryEventProperties discoveryEventProperties;

    private final NacosDiscoveryProperties discoveryProperties;
    /**
     * nacos 服务管理器
     */
    private final NacosServiceManager nacosServiceManager;
    /**
     * 服务订阅校验集合
     */
    private final List<NacosServiceListenerValidator> nacosServiceListenerValidators;
    /**
     * spring 事件发布者
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 所有监听的缓存，key 为服务组，值为该组下的所有服务监听器
     */
    private final Map<String, List<NacosServiceEventListener>> listenerCache = new LinkedHashMap<>();

    /**
     * 创建一个 Nacos Spring 事件管理器
     *
     * @param discoveryProperties            Nacos 服务配置
     * @param nacosServiceManager            Nacos 服务管理器
     * @param discoveryEventProperties       Nacos 服务发现事件配置
     * @param nacosServiceListenerValidators Nacos 服务监听器校验器列表
     */
    public NacosSpringEventManager(
            NacosServiceManager nacosServiceManager,
            NacosDiscoveryProperties discoveryProperties,
            NacosDiscoveryEventProperties discoveryEventProperties,
            List<NacosServiceListenerValidator> nacosServiceListenerValidators
    ) {

        this.nacosServiceManager = nacosServiceManager;
        this.discoveryProperties = discoveryProperties;
        this.discoveryEventProperties = discoveryEventProperties;
        this.nacosServiceListenerValidators = nacosServiceListenerValidators;
    }

    /**
     * 取消订阅服务
     */
    @NacosCronScheduled(cron = "${spring.cloud.nacos.discovery.event.unsubscribe-service-cron:0 0/1 * * * ?}")
    public void unsubscribeService() {

        NamingService namingService = nacosServiceManager.getNamingService();

        List<NacosServiceEventListener> listeners = listenerCache
                .values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
        for (NacosServiceEventListener sel : listeners) {
            if (!sel.isExpired()) {
                continue;
            }
            try {

                LOGGER.info("对服务 [{}] [{}] 取消订阅", sel.getGroup(), sel.getServiceName());

                namingService.unsubscribe(sel.getServiceName(), sel.getGroup(), sel);

                List<NacosServiceEventListener> list = listenerCache.get(sel.getGroup());
                list.remove(sel);
                // 通过服务名获取所有服务实例
                List<Instance> instanceList = namingService.getAllInstances(sel.getServiceName(), sel.getGroup());
                NamingEvent namingEvent = new NamingEvent(
                        sel.getServiceName(),
                        sel.getGroup(),
                        discoveryProperties.getClusterName(),
                        instanceList
                );
                applicationEventPublisher.publishEvent(new NacosServiceUnsubscribeEvent(namingEvent));
            }
            catch (Exception e) {
                LOGGER.error("取消订阅 [{}] 服务失败", sel.getServiceName(), e);
            }
        }
    }

    @NacosCronScheduled(cron = "${spring.cloud.nacos.discovery.event.subscribe-service-cron:30 0/1 * * * ?}")
    public void subscribeService() throws Exception {
        NamingService namingService = nacosServiceManager.getNamingService();
        ListView<String> view = namingService.getServicesOfServer(1, Integer.MAX_VALUE, discoveryProperties.getGroup());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("订阅 nacos 组 [{}] 中的所有服务为 {}", discoveryProperties.getGroup(), view.getData());
        }

        for (String serviceName : view.getData()) {
            subscribeService(serviceName, discoveryProperties.getGroup(), discoveryProperties.getNamespace());
        }
    }

    public static String toUniqueValue(String serviceName, String group, String namespace) {
        List<String> strings = List.of(
                Objects.toString(serviceName, StringUtils.EMPTY),
                Objects.toString(group, StringUtils.EMPTY),
                Objects.toString(namespace, StringUtils.EMPTY)
        );
        return StringUtils.join(strings.stream().filter(StringUtils::isNotEmpty).toList(), CacheProperties.DEFAULT_SEPARATOR);
    }

    /**
     * 订阅服务
     *
     * @param serviceName 服务名称
     * @param group 所在租
     * @param namespace 所在命名空间
     */
    public void subscribeService(String serviceName, String group, String namespace) {
        NamingService namingService = nacosServiceManager.getNamingService();
        try {

            // 通过服务名获取所有服务实例
            List<Instance> instanceList = namingService.getAllInstances(serviceName, group);
            // 创建一组监听缓存，如果存在取当前的数据，否则创建一个
            List<NacosServiceEventListener> listeners = listenerCache.computeIfAbsent(
                    discoveryProperties.getGroup(),
                    k -> new LinkedList<>()
            );

            Optional<NacosServiceEventListener> optional = listeners
                    .stream()
                    .filter(l -> l.toString().equals(toUniqueValue(serviceName, group, namespace)))
                    .findFirst();
            // 如果当前服务已经被监听过，更新一次最后访问时间
            if (optional.isPresent()) {

                optional.get().setLastAccessTime(Instant.now());

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("[{}] 服务已订阅，更新创建时间。", serviceName);
                }

                return;
            }

            // 创建监听器
            NacosServiceEventListener listener = new NacosServiceEventListener(
                    discoveryEventProperties.getExpireUnsubscribeTime(),
                    serviceName,
                    group,
                    namespace,
                    applicationEventPublisher
            );
            NamingEvent namingEvent = new NamingEvent(
                    serviceName,
                    group,
                    discoveryProperties.getClusterName(),
                    instanceList
            );

            if (CollectionUtils.isNotEmpty(nacosServiceListenerValidators)) {

                List<NacosServiceListenerValidator> validators = nacosServiceListenerValidators
                        .stream()
                        .filter(v -> v.isSupport(namingEvent))
                        .toList();

                boolean isReturn = false;

                for (NacosServiceListenerValidator v : validators) {
                    if (!v.subscribeValid(namingEvent)) {
                        isReturn = true;
                        break;
                    }
                }

                if (isReturn) {
                    return;
                }
            }

            // 添加到缓存中
            listeners.add(listener);

            LOGGER.info("订阅组为 [{}] 的 [{}] 服务", discoveryProperties.getGroup(), discoveryProperties.getService());
            // 订阅服务
            namingService.subscribe(discoveryProperties.getService(), discoveryProperties.getGroup(), listener);
            // 推送订阅事件
            applicationEventPublisher.publishEvent(new NacosServiceSubscribeEvent(namingEvent));

        }
        catch (Exception e) {
            LOGGER.error("扫描服务信息出错", e);
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void destroy() {
        LOGGER.info("解除所有服务监听");

        listenerCache.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(NacosServiceEventListener::expired);

        unsubscribeService();

        listenerCache.clear();
    }

    /**
     * 监听本服务注册完成事件，当注册完成时候，同步所有插件菜单。
     *
     * @param event 事件原型
     */
    @EventListener
    public void onInstanceRegisteredEvent(InstanceRegisteredEvent<NacosDiscoveryProperties> event) {
        if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("开始 {} 监听 nacos 服务注册信息。",toUniqueValue(event.getConfig().getGroup(), event.getConfig().getNamespace(), event.getConfig().getService()));
        }
        try {
            subscribeService();
        } catch (Exception e) {
            LOGGER.error("订阅服务失败", e);
        }
    }

    /**
     * 获取服务管理
     *
     * @return 服务管理
     */
    public NacosServiceManager getNacosServiceManager() {
        return nacosServiceManager;
    }
}

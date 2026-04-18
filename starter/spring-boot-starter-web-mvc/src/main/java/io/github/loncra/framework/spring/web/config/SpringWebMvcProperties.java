package io.github.loncra.framework.spring.web.config;


import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.web.device.DeviceResolverRequestFilter;
import io.github.loncra.framework.spring.web.result.RestResponseBodyAdvice;
import io.github.loncra.framework.spring.web.result.RestResultErrorAttributes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * spring web 扩展支持的配置类
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.mvc")
public class SpringWebMvcProperties {

    /**
     * 支持格式化的客户端集合，默认为 "SPRING_GATEWAY"
     */
    private List<String> restResultFormatSupportClients = RestResponseBodyAdvice.DEFAULT_SUPPORT_CLIENT;

    /**
     * 是否所有请求都响应 {@link RestResult} 结果集
     */
    private boolean allRestResultFormat = false;

    /**
     * 支持的异常抛出消息的类
     */
    private List<Class<? extends Exception>> supportException = RestResultErrorAttributes.DEFAULT_MESSAGE_EXCEPTION;

    /**
     * 支持的 http 响应状态
     */
    private List<HttpStatus> supportHttpStatus = RestResultErrorAttributes.DEFAULT_HTTP_STATUSES_MESSAGE;

    /**
     * {@link DeviceResolverRequestFilter} 的排序值
     */
    private int deviceFilterOrderValue = Ordered.HIGHEST_PRECEDENCE + 60;

    /**
     * 是否启用 {@link DeviceResolverRequestFilter}
     */
    private boolean enabledDeviceFilter = true;

    /**
     * 枚举类需要扫描的包路径
     */
    private List<String> enumerateEndpointBasePackages = new ArrayList<>(16);

    public SpringWebMvcProperties() {
    }


    /**
     * 获取可支持格式化的客户端信息
     *
     * @return 可支持格式化的客户端信息
     */
    public List<String> getRestResultFormatSupportClients() {
        return restResultFormatSupportClients;
    }

    /**
     * 设置可支持格式化的客户端信息
     *
     * @param restResultFormatSupportClients 客户端信息
     */
    public void setRestResultFormatSupportClients(List<String> restResultFormatSupportClients) {
        this.restResultFormatSupportClients = restResultFormatSupportClients;
    }

    /**
     * 是否支持客户端格式化
     *
     * @param client 客户端
     *
     * @return true 是，否则 false
     */
    public boolean isSupportClient(String client) {
        return restResultFormatSupportClients.contains(client);
    }

    /**
     * 获取支持的异常抛出消息的类
     *
     * @return 支持的异常抛出消息的类
     */
    public List<Class<? extends Exception>> getSupportException() {
        return supportException;
    }

    /**
     * 设置支持的异常抛出消息的类
     *
     * @param supportException 支持的异常抛出消息的类
     */
    public void setSupportException(List<Class<? extends Exception>> supportException) {
        this.supportException = supportException;
    }

    /**
     * 获取支持的 http 响应状态
     *
     * @return 支持的 http 响应状态
     */
    public List<HttpStatus> getSupportHttpStatus() {
        return supportHttpStatus;
    }

    /**
     * 设置支持的 http 响应状态
     *
     * @param supportHttpStatus 支持的 http 响应状态
     */
    public void setSupportHttpStatus(List<HttpStatus> supportHttpStatus) {
        this.supportHttpStatus = supportHttpStatus;
    }

    /**
     * 是否所有请求都响应 {@link RestResult} 结果集
     *
     * @return true 是，否则 false
     */
    public boolean isAllRestResultFormat() {
        return allRestResultFormat;
    }

    /**
     * 设置是否所有请求都响应 {@link RestResult} 结果集
     *
     * @param allRestResultFormat true 是，否则 false
     */
    public void setAllRestResultFormat(boolean allRestResultFormat) {
        this.allRestResultFormat = allRestResultFormat;
    }

    /**
     * 获取 {@link DeviceResolverRequestFilter} 的排序值
     *
     * @return 排序值
     */
    public int getDeviceFilterOrderValue() {
        return deviceFilterOrderValue;
    }

    /**
     * 设置 {@link DeviceResolverRequestFilter} 的排序值
     *
     * @param deviceFilterOrderValue 排序值
     */
    public void setDeviceFilterOrderValue(int deviceFilterOrderValue) {
        this.deviceFilterOrderValue = deviceFilterOrderValue;
    }

    /**
     * 是否启用 {@link DeviceResolverRequestFilter}
     *
     * @return true 是，否则 false
     */
    public boolean isEnabledDeviceFilter() {
        return enabledDeviceFilter;
    }

    /**
     * 设置是否启用 {@link DeviceResolverRequestFilter}
     *
     * @param enabledDeviceFilter true 是，否则 false
     */
    public void setEnabledDeviceFilter(boolean enabledDeviceFilter) {
        this.enabledDeviceFilter = enabledDeviceFilter;
    }

    /**
     * 获取枚举类需要扫描的包路径
     *
     * @return 枚举类需要扫描的包路径
     */
    public List<String> getEnumerateEndpointBasePackages() {
        return enumerateEndpointBasePackages;
    }

    /**
     * 设置枚举类需要扫描的包路径
     *
     * @param enumerateEndpointBasePackages 枚举类需要扫描的包路径
     */
    public void setEnumerateEndpointBasePackages(List<String> enumerateEndpointBasePackages) {
        this.enumerateEndpointBasePackages = enumerateEndpointBasePackages;
    }
}

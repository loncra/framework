package io.github.loncra.framework.socketio.core;

import com.corundumstudio.socketio.Configuration;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Socket.IO 配置属性。
 * <p>
 * 继承 netty-socketio 原生 {@link Configuration}，并额外扩展业务相关配置
 * （如用户缓存、单端登录、ACK 超时等）。
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.socketio")
public class SocketProperties extends Configuration {


    /**
     * 用户 socket client 缓存
     */
    private CacheProperties userCache = CacheProperties.of("loncra:framework:socketio:socket-user", TimeProperties.ofHours(2));


    /**
     * 是否只支持单端连接（同一账号仅允许一个在线端）。
     */
    private boolean singleEnded = true;

    /**
     * 请求返回结果时要忽略的字段内容
     */
    private Map<String, List<String>> ignoreResultMap = new LinkedHashMap<>();

    /**
     * 请求返回结果时要脱敏的数据内容映射
     */
    private Map<String, List<String>> desensitizeResultMap = new LinkedHashMap<>();

    /**
     * 应答模式发送消息的默认超时时间
     */
    private TimeProperties ackSendMessageTimeout = TimeProperties.ofSeconds(10);

    /**
     * 构造函数
     */
    public SocketProperties() {
    }

    /**
     * 获取用户缓存配置
     *
     * @return 用户缓存配置
     */
    public CacheProperties getUserCache() {
        return userCache;
    }

    /**
     * 设置用户缓存配置
     *
     * @param userCache 用户缓存配置
     */
    public void setUserCache(CacheProperties userCache) {
        this.userCache = userCache;
    }

    /**
     * 获取请求返回结果时要忽略的字段内容映射
     *
     * @return 忽略字段内容映射
     */
    public Map<String, List<String>> getIgnoreResultMap() {
        return ignoreResultMap;
    }

    /**
     * 设置请求返回结果时要忽略的字段内容映射
     *
     * @param ignoreResultMap 忽略字段内容映射
     */
    public void setIgnoreResultMap(Map<String, List<String>> ignoreResultMap) {
        this.ignoreResultMap = ignoreResultMap;
    }

    /**
     * 获取请求返回结果时要脱敏的数据内容映射
     *
     * @return 脱敏数据内容映射
     */
    public Map<String, List<String>> getDesensitizeResultMap() {
        return desensitizeResultMap;
    }

    /**
     * 设置请求返回结果时要脱敏的数据内容映射
     *
     * @param desensitizeResultMap 脱敏数据内容映射
     */
    public void setDesensitizeResultMap(Map<String, List<String>> desensitizeResultMap) {
        this.desensitizeResultMap = desensitizeResultMap;
    }

    /**
     * 是否只允许单端连接。
     *
     * @return true 表示仅允许单端连接，否则允许多端并存
     */
    public boolean isSingleEnded() {
        return singleEnded;
    }

    /**
     * 设置是否只允许单端连接。
     *
     * @param singleEnded true 表示仅允许单端连接，否则允许多端并存
     */
    public void setSingleEnded(boolean singleEnded) {
        this.singleEnded = singleEnded;
    }

    /**
     * 获取 ACK 发送默认超时时间配置。
     * <p>
     * 支持 {@link TimeProperties} 或可转换为 {@link TimeProperties} 的配置值。
     *
     * @return ACK 超时配置
     */
    public TimeProperties getAckSendMessageTimeout() {
        return ackSendMessageTimeout;
    }

    /**
     * 设置 ACK 发送默认超时时间配置。
     *
     * @param ackSendMessageTimeout ACK 超时配置
     */
    public void setAckSendMessageTimeout(TimeProperties ackSendMessageTimeout) {
        this.ackSendMessageTimeout = ackSendMessageTimeout;
    }
}

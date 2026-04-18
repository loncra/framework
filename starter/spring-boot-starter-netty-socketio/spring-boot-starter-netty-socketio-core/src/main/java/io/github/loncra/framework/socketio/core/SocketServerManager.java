package io.github.loncra.framework.socketio.core;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.ServiceException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.socketio.api.SocketPrincipal;
import io.github.loncra.framework.socketio.api.SocketUserMessage;
import io.github.loncra.framework.socketio.api.enumerate.ConnectStatus;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.core.interceptor.AuthorizationInterceptor;
import io.github.loncra.framework.socketio.core.interceptor.SocketServerInterceptor;
import io.github.loncra.framework.socketio.core.resolver.AckMessageSenderResolver;
import io.github.loncra.framework.socketio.core.resolver.MessageSenderResolver;
import io.github.loncra.framework.spring.security.core.authentication.AccessTokenContextRepository;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import io.github.loncra.framework.spring.web.device.DeviceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Socket 服务统一管理器。
 * <p>
 * 负责 Socket 握手认证、连接生命周期管理、用户会话缓存以及消息派发。
 *
 * @author maurice.chen
 */
public class SocketServerManager implements AuthorizationListener, ConnectListener, DisconnectListener, CommandLineRunner, DisposableBean {

    /**
     * 日志记录器
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(SocketServerManager.class);

    /**
     * 当 socket 服务主动断开时的通知事件名称
     */
    public static final String SERVER_DISCONNECT_EVENT_NAME = "socket_server_disconnect";

    /**
     * 默认标识请求头名称
     */
    public static final String DEFAULT_IDENTIFIED_HEADER_NAME = "io";
    /**
     * Socket 配置属性
     */
    private final SocketProperties socketProperties;

    /**
     * Redisson 客户端
     */
    private final RedissonClient redissonClient;

    /**
     * 认证拦截器列表
     */
    private final List<AuthorizationInterceptor> authorizationInterceptors;

    /**
     * Socket 服务器拦截器列表
     */
    private final List<SocketServerInterceptor> socketServerInterceptors;

    /**
     * socket io 服务
     */
    private final SocketIOServer socketIoServer;

    /**
     * 普通消息发送解析器集合（按消息类型匹配）。
     */
    private final List<MessageSenderResolver> messageSenderResolvers;

    /**
     * 应答模式的消息发送解析器
     */
    private final List<AckMessageSenderResolver<?>>  ackMessageSenderResolvers;

    /**
     * 访问令牌上下问仓库
     */
    private final AccessTokenContextRepository accessTokenContextRepository;

    /**
     * 构造函数
     *
     * @param socketProperties Socket 配置属性
     * @param redissonClient Redisson 客户端
     * @param authorizationInterceptors 认证拦截器列表
     */
    public SocketServerManager(
            SocketProperties socketProperties,
            RedissonClient redissonClient,
            AccessTokenContextRepository accessTokenContextRepository,
            List<MessageSenderResolver>  messageSenderResolvers,
            List<AckMessageSenderResolver<?>>  ackMessageSenderResolvers,
            List<SocketServerInterceptor> socketServerInterceptors,
            List<AuthorizationInterceptor> authorizationInterceptors
    ) {
        this.socketProperties = socketProperties;
        this.redissonClient = redissonClient;
        this.accessTokenContextRepository = accessTokenContextRepository;
        this.messageSenderResolvers = messageSenderResolvers;
        this.ackMessageSenderResolvers = ackMessageSenderResolvers;
        this.authorizationInterceptors = authorizationInterceptors;
        this.socketServerInterceptors = socketServerInterceptors;

        socketProperties.setAuthorizationListener(this);
        socketProperties.setStoreFactory(new RedissonStoreFactory(redissonClient));

        this.socketIoServer = new SocketIOServer(socketProperties);
        this.socketIoServer.addConnectListener(this);
        this.socketIoServer.addDisconnectListener(this);
    }

    /**
     * Socket 客户端连接时的回调
     *
     * @param client Socket IO 客户端
     */
    @Override
    public void onConnect(SocketIOClient client) {

        AuditAuthenticationToken socketToken = getSocketAuditAuthenticationToken(Objects.toString(client.getSessionId()));

        if (Objects.isNull(socketToken)) {
            LOGGER.warn("在认证通过后连接 socket 时出现获取用户信息为 null 的情况, deviceIdentified 为:{}", client.getSessionId());
            client.sendEvent(SERVER_DISCONNECT_EVENT_NAME, SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(RestResult.of("找不到当前认证的用户"))));
            client.disconnect();
        }

        Optional<AuthorizationInterceptor> optional = authorizationInterceptors.stream()
                .filter(s -> s.isSupport(new SecurityContextImpl(socketToken)))
                .findFirst();
        if (socketProperties.isSingleEnded()) {

            SecurityContext securityContext = accessTokenContextRepository.getSecurityContext(socketToken);
            List<SocketPrincipal> socketPrincipals = new LinkedList<>(getSocketPrincipals(securityContext));

            socketPrincipals.stream()
                    .filter(s -> !Strings.CS.equals(s.getDeviceIdentified(), Objects.toString(client.getSessionId())))
                    .map(s -> SocketUserMessage.of(s, SERVER_DISCONNECT_EVENT_NAME, RestResult.of("您在其他设备已登录，当前设备自动下线")))
                    .forEach(this::sendMessage);
        }

        optional.ifPresent(s -> s.onConnect(client, socketToken));

        if (!LOGGER.isDebugEnabled()) {
            return ;
        }

        LOGGER.debug(
                "设备: {} 建立连接成功, IP 为: {} , 用户为: {} 拥有的房间为: {}" ,
                client.getSessionId(),
                client.getRemoteAddress().toString(),
                socketToken.getFullName(),
                client.getAllRooms()
        );
    }

    public List<SocketPrincipal> getSocketPrincipals(SecurityContext context) {
        if (Objects.isNull(context) || Objects.isNull(context.getAuthentication())) {
            return Collections.emptyList();
        }

        if (context.getAuthentication().getDetails() instanceof AuditAuthenticationSuccessDetails details) {
            Object clientSetObject = details.getMetadata().computeIfAbsent(SocketPrincipal.DEFAULT_SOCKET_CLIENT_ID_NAME, k -> new LinkedHashSet<>());
            Set<String> clientIds = CastUtils.cast(clientSetObject);
            return clientIds.stream()
                    .map(this::getSocketAuditAuthenticationToken)
                    .map(AuditAuthenticationToken::getPrincipal)
                    .map(s -> CastUtils.cast(s, SocketPrincipal.class))
                    .toList();
        }
        return Collections.emptyList();
    }

    /**
     * Socket 客户端断开连接时的回调
     *
     * @param client Socket IO 客户端
     */
    @Override
    public void onDisconnect(SocketIOClient client) {
        String sessionId = Objects.toString(client.getSessionId());
        AuditAuthenticationToken auditAuthenticationToken = getSocketAuditAuthenticationToken(sessionId);

        if (Objects.isNull(auditAuthenticationToken)) {
            return;
        }

        deleteSocketAuditAuthenticationToken(sessionId);
        authorizationInterceptors.stream()
                .filter(s -> s.isSupport(new SecurityContextImpl(auditAuthenticationToken)))
                .findFirst()
                .ifPresent(interceptor -> interceptor.onDisconnect(client));
        if (!LOGGER.isDebugEnabled()) {
            return ;
        }

        LOGGER.debug(
                "IP: {} UUID: {} 设备断开连接, 用户为: {}",
                client.getRemoteAddress().toString(),
                client.getSessionId(),
                auditAuthenticationToken.getFullName()
        );
    }

    public SecurityContext getSecurityContext(HandshakeData handshakeData) {
        String headerName = accessTokenContextRepository.getAuthenticationProperties()
                .getAccessToken()
                .getHeaderName();
        SecurityContext socketContext = accessTokenContextRepository.getSecurityContext(handshakeData.getHttpHeaders().get(headerName));
        if (Objects.isNull(socketContext) || Objects.isNull(socketContext.getAuthentication())) {
            String paramName = accessTokenContextRepository.getAuthenticationProperties()
                    .getAccessToken()
                    .getParamName();
            socketContext = accessTokenContextRepository.getSecurityContext(handshakeData.getSingleUrlParam(paramName));
        }
        return socketContext;
    }

    /**
     * 获取认证结果
     *
     * @param data 握手数据
     * @return 认证结果
     */
    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData data) {

        SecurityContext securityContext = getSecurityContext(data);
        if (Objects.isNull(securityContext) || Objects.isNull(securityContext.getAuthentication())) {
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }

        if (!AuditAuthenticationToken.class.isAssignableFrom(securityContext.getAuthentication().getClass())) {
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }

        AuditAuthenticationToken auditAuthenticationToken = CastUtils.cast(securityContext.getAuthentication());
        SocketPrincipal socketPrincipal = CastUtils.of(auditAuthenticationToken.getSecurityPrincipal(), SocketPrincipal.class);
        String deviceIdentified = data.getHttpHeaders().get(DeviceUtils.REQUEST_DEVICE_IDENTIFIED_HEADER_NAME);
        if (StringUtils.isEmpty(deviceIdentified)) {
            deviceIdentified = data.getSingleUrlParam(DeviceUtils.REQUEST_DEVICE_IDENTIFIED_PARAM_NAME);
        }
        socketPrincipal.setDeviceIdentified(deviceIdentified);
        socketPrincipal.setConnectStatus(ConnectStatus.Connected);
        // 设置最后连接时间
        socketPrincipal.setConnectionTime(Instant.now());

        Set<GrantedAuthority> grantedAuthorities = auditAuthenticationToken.getGrantedAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        AuditAuthenticationToken socketAuditAuthenticationToken = new AuditAuthenticationToken(
                socketPrincipal,
                auditAuthenticationToken.getType(),
                grantedAuthorities,
                Instant.now()
        );
        socketAuditAuthenticationToken.setAuthenticated(true);

        if (auditAuthenticationToken.getDetails() instanceof AuditAuthenticationSuccessDetails details) {
            Object clientSetObject = details.getMetadata().computeIfAbsent(SocketPrincipal.DEFAULT_SOCKET_CLIENT_ID_NAME, k -> new LinkedHashSet<>());
            Set<String> clientIds = CastUtils.cast(clientSetObject);
            clientIds.add(socketPrincipal.getDeviceIdentified());
            details.getMetadata().put(SocketPrincipal.DEFAULT_SOCKET_CLIENT_ID_NAME, clientIds);
        }

        WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetails(Objects.toString(data.getLocal()), deviceIdentified);
        Map<String, Object> httpHeaders = new LinkedHashMap<>();
        data.getHttpHeaders().forEach(entry -> httpHeaders.put(entry.getKey(),entry.getValue()));

        AuditAuthenticationSuccessDetails socketSuccessDetails = new AuditAuthenticationSuccessDetails(webAuthenticationDetails, httpHeaders);
        socketAuditAuthenticationToken.setDetails(socketSuccessDetails);

        saveSocketAuditAuthenticationToken(socketAuditAuthenticationToken);
        accessTokenContextRepository.saveContext(securityContext, null, null);

        data.getHttpHeaders().add(DEFAULT_IDENTIFIED_HEADER_NAME, socketPrincipal.getDeviceIdentified());

        return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
    }

    /**
     * 销毁时关闭 Socket 服务器
     *
     * @throws Exception 销毁异常
     */
    @Override
    public void destroy() throws Exception {
        for (SocketServerInterceptor socketServerInterceptor : socketServerInterceptors) {
            socketServerInterceptor.destroy();
        }
        socketIoServer.stop();
    }

    /**
     * 启动 Socket 服务器
     *
     * @param args 命令行参数
     *
     * @throws Exception 启动异常
     */
    @Override
    public void run(String... args) throws Exception {
        for (SocketServerInterceptor socketServerInterceptor : socketServerInterceptors) {
            socketServerInterceptor.run(args);
        }
        socketIoServer.startAsync();
    }

    /**
     * 删除 socket 认证 token
     *
     * @param deviceIdentified 设备唯一识别 (uuid)
     */
    public void deleteSocketAuditAuthenticationToken(String deviceIdentified) {
        redissonClient.getBucket(socketProperties.getUserCache().getName(deviceIdentified)).delete();
    }

    /**
     * 根据类型ID名称元数据获取 Socket 主体
     *
     * @param deviceIdentified 设备唯一识别 (uuid)
     * @return Socket 主体
     */
    public AuditAuthenticationToken getSocketAuditAuthenticationToken(String deviceIdentified) {
        RBucket<AuditAuthenticationToken> token = redissonClient.getBucket(socketProperties.getUserCache().getName(deviceIdentified));
        return token.get();
    }

    /**
     * 保存 socket 认证信息
     *
     * @param token 认证信息
     */
    public void saveSocketAuditAuthenticationToken(AuditAuthenticationToken token) {
        SocketPrincipal principal = CastUtils.cast(token.getPrincipal(), SocketPrincipal.class);
        String key = socketProperties.getUserCache().getName(principal.getDeviceIdentified());
        RBucket<AuditAuthenticationToken> bucket = redissonClient.getBucket(key);
        if (Objects.isNull(socketProperties.getUserCache().getExpiresTime())) {
            bucket.set(token);
        } else {
            bucket.set(token, socketProperties.getUserCache().getExpiresTime().toDuration());
        }
    }

    /**
     * 发送消息
     *
     * @param socketMessage 抽象的 socket 消息元数据实现类
     */
    public void sendMessage(AbstractSocketMessageMetadata<?> socketMessage) {
        messageSenderResolvers.stream()
                .filter(s -> s.isSupport(socketMessage))
                .findFirst()
                .orElseThrow(() -> new ServiceException("找不到消息类型为 [" + socketMessage.getClass() + "] 的支持"))
                .sendMessage(socketMessage, socketIoServer);
    }

    /**
     * 发送带 ACK 确认的消息。
     *
     * @param socketMessage 抽象的 socket 消息元数据实现类
     * @return ACK 应答结果（单播返回单个结果，多播/群发可返回结果集合）
     * @throws InterruptedException 当前线程等待 ACK 期间被中断
     * @throws ExecutionException 等待 ACK 结果时发生执行异常
     */
    public Object ackSendMessage(AbstractSocketMessageMetadata<?> socketMessage) throws InterruptedException, ExecutionException {
        return ackMessageSenderResolvers.stream()
                .filter(s -> s.isSupport(socketMessage))
                .findFirst()
                .orElseThrow(() -> new ServiceException("找不到消息类型为 [" + socketMessage.getClass() + "] 的支持"))
                .ackSendMessage(socketMessage, socketIoServer);
    }

    /**
     * 获取客户端
     *
     * @param uuid uuid
     *
     * @return socket 客户端
     */
    public SocketIOClient getClient(String uuid) {
        return socketIoServer.getClient(UUID.fromString(uuid));
    }

    /**
     * 获取 socket 服务
     *
     * @return socket 服务
     */
    public SocketIOServer getSocketServer() {
        return socketIoServer;
    }

    /**
     * 获取 Redisson 客户端
     *
     * @return Redisson 客户端
     */
    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    /**
     * 获取 Socket 配置属性
     *
     * @return Socket 配置属性
     */
    public SocketProperties getSocketProperties() {
        return socketProperties;
    }

    /**
     * 获取 access token 上下文仓库信息
     *
     * @return access token 上下文仓库信息
     */
    public AccessTokenContextRepository getAccessTokenContextRepository() {
        return accessTokenContextRepository;
    }
}

package io.github.loncra.framework.socketio.core.interceptor;

import com.corundumstudio.socketio.SocketIOClient;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;

/**
 * Socket 认证拦截器接口。
 * <p>
 * 用于扩展握手鉴权、连接/断开回调以及认证上下文恢复逻辑。
 *
 * @author maurice.chen
 */
public interface AuthorizationInterceptor {

    /**
     * 是否支持当前上下文
     *
     * @param securityContext spring security 上下文
     *
     * @return true 是， 否则 false
     */
    boolean isSupport(SecurityContext securityContext);

    /**
     * 当客户端连接断开时触发此方法
     *
     * @param client 被断开的客户端
     */
    default void onDisconnect(SocketIOClient client) {

    }

    /**
     * 当客户端发生连接时触发此方法。
     *
     * @param client 连接成功的客户端
     * @param socketAuthenticationToken socket 认证 token
     */
    default void onConnect(SocketIOClient client, AuditAuthenticationToken socketAuthenticationToken) {

    }
}

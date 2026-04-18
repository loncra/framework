package io.github.loncra.framework.socketio.core.interceptor;

/**
 * Socket 服务生命周期拦截器。
 * <p>
 * 可在服务启动前后或销毁前后接入自定义逻辑（如资源初始化、清理等）。
 *
 * @author maurice.chen
 */
public interface SocketServerInterceptor {

    /**
     * 销毁服务时触发此方法。
     *
     * @throws Exception 销毁异常信息
     */
    void destroy() throws Exception;

    /**
     * 启动服务时触发此方法
     *
     * @param args 启动参数
     * @throws Exception 启动异常信息
     */
    void run(String... args) throws Exception;
}

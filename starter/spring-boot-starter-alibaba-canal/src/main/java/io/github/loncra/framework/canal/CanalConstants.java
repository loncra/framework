package io.github.loncra.framework.canal;

import io.github.loncra.framework.commons.CastUtils;


/**
 * Canal 常量接口
 *
 * @author maurice.chen
 */
public interface CanalConstants {

    /**
     * API Token 请求头名称
     */
    String API_TOKEN_HEADER_NAME = "X-TOKEN";

    /**
     * 结果代码字段名
     */
    String RESULT_CODE = "code";

    /**
     * API 成功状态码
     */
    String API_SUCCESS_CODE = "20000";

    /**
     * 登录 API 路径
     */
    String LOGIN_API = "/api/v1/user/login";

    /**
     * 查找节点服务 API 路径
     */
    String FIND_NODE_SERVERS_API = "/api/v1/nodeServers";

    /**
     * 节点服务配置 API 路径
     */
    String NODE_SERVER_CONFIG_API = "/api/v1/canal/config/{0}/{1}";

    /**
     * 查找活跃实例 API 路径
     */
    String FIND_ACTIVE_INSTANCES_API = "api/v1/canal/active/instances/{0}";

    /**
     * 查找实例 API 路径
     */
    String FIND_INSTANCES_API = "api/v1/canal/instances?name={0}&page={1}&size={2}";

    /**
     * 获取和删除实例 API 路径
     */
    String GET_AND_DELETE_INSTANCE_API = "api/v1/canal/instance/{0}";

    /**
     * 获取集群信息 API 路径
     */
    String GET_CLUSTERS_API = "api/v1/canal/clustersAndServers";

    /**
     * 获取实例日志 API 路径
     */
    String GET_INSTANCE_LOG = "api/v1/canal/instance/log/{0}/{1}";

    /**
     * Canal 配置根路径
     */
    String ROOT = "canal";

    /**
     * Canal 端口配置键
     */
    String CANAL_PORT = ROOT + CastUtils.DOT + "port";

    /**
     * Canal 用户名配置键
     */
    String CANAL_USERNAME = ROOT + CastUtils.DOT + "user";

    /**
     * Canal 密码配置键
     */
    String CANAL_PASSWORD = ROOT + CastUtils.DOT + "passwd";

    /**
     * Canal TCP 模式连接的 host 地址，该配置主要是应用在本地 docker ip 变来变去的问题
     */
    String CANAL_TCP_HOST = ROOT + CastUtils.DOT + "tcp.host";

    /**
     * Canal Admin 用户名配置键
     */
    String CANAL_ADMIN_USERNAME = "username";

    /**
     * Canal Admin 密码配置键
     */
    String CANAL_ADMIN_PASSWORD = "password";

}

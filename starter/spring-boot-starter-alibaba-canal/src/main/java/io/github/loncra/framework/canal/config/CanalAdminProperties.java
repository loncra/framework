package io.github.loncra.framework.canal.config;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * canal admin 配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.canal.admin")
public class CanalAdminProperties {

    /**
     * admin 地址
     */
    private String uri = "http://localhost:9097";

    /**
     * canal admin 登陆账户
     */
    private String username = "admin";

    /**
     * canal admin 登陆密码
     */
    private String password = "123456";

    /**
     * 登陆后的 login 名称
     */
    private String tokenParamName = "token";

    /**
     * 认证 token 缓存
     */
    private CacheProperties loginTokenCache = CacheProperties.of(
            "loncra:framework:alibaba:canal:admin-login-token",
            TimeProperties.ofMinutes(5)
    );

    /**
     * 构造函数
     */
    public CanalAdminProperties() {
    }

    /**
     * 获取 Admin 地址
     *
     * @return Admin 地址
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置 Admin 地址
     *
     * @param uri Admin 地址
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取 Canal Admin 登录账户
     *
     * @return Canal Admin 登录账户
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置 Canal Admin 登录账户
     *
     * @param username Canal Admin 登录账户
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 Canal Admin 登录密码
     *
     * @return Canal Admin 登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置 Canal Admin 登录密码
     *
     * @param password Canal Admin 登录密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取认证 token 缓存
     *
     * @return 认证 token 缓存
     */
    public CacheProperties getLoginTokenCache() {
        return loginTokenCache;
    }

    /**
     * 设置认证 token 缓存
     *
     * @param loginTokenCache 认证 token 缓存
     */
    public void setLoginTokenCache(CacheProperties loginTokenCache) {
        this.loginTokenCache = loginTokenCache;
    }

    /**
     * 获取登录后的 token 参数名称
     *
     * @return 登录后的 token 参数名称
     */
    public String getTokenParamName() {
        return tokenParamName;
    }

    /**
     * 设置登录后的 token 参数名称
     *
     * @param tokenParamName 登录后的 token 参数名称
     */
    public void setTokenParamName(String tokenParamName) {
        this.tokenParamName = tokenParamName;
    }
}

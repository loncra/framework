package io.github.loncra.framework.spring.security.session.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

/**
 * 记住我配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.session.remember-me")
public class RememberMeProperties {


    /**
     * 记住我 cookie 名称
     */
    private String cookieName = AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;

    /**
     * 是否启用记住我功能
     */
    private boolean enabled = false;
    /**
     * 是否永远记住我，设置该值为 true 时 不管 {@link #paramName} 参数提交 true 或 false，都会执行记住我
     */
    private boolean always = false;
    /**
     * cookie 有效性时间，秒为单位。
     */
    private int tokenValiditySeconds = AbstractRememberMeServices.TWO_WEEKS_S;
    /**
     * 记住我域名
     */
    private String domain;
    /**
     * 对于本次执行登录是否记住我的参数名称
     */
    private String paramName = AbstractRememberMeServices.DEFAULT_PARAMETER;
    /**
     * 是否使用受保护的 cookie
     */
    private boolean useSecureCookie;

    /**
     * cookie 加密密钥
     */
    private String key;

    /**
     * 记住我登录处理 url，用于记录说在记住我认证的请求时，调用到了哪个登录记录审计事件使用。
     */
    private String loginProcessingUrl;

    /**
     * 获取记住我 cookie 参数名称
     *
     * @return 记住我 cookie 参数名称
     */
    public String getCookieName() {
        return cookieName;
    }

    /**
     * 设置记住我 cookie 参数名称
     *
     * @param cookieName 记住我 cookie 参数名称
     */
    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     * 是否启用记住我功能
     *
     * @return true 是，否则 false
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用记住我功能
     *
     * @param enabled true 是，否则 false
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 是否永远记住我
     *
     * @return true 是，否则 false
     */
    public boolean isAlways() {
        return always;
    }

    /**
     * 设置是否永远记住我
     *
     * @param always true 是，否则 false
     */
    public void setAlways(boolean always) {
        this.always = always;
    }

    /**
     * 获取 cookie 有效性时间，秒为单位。
     *
     * @return cookie 有效性时间，秒为单位。
     */
    public int getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    /**
     * 设置 cookie 有效性时间，秒为单位。
     *
     * @param tokenValiditySeconds cookie 有效性时间，秒为单位。
     */
    public void setTokenValiditySeconds(int tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    /**
     * 获取记住我域名
     *
     * @return 记住我域名
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置记住我域名
     *
     * @param domain 记住我域名
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取对于本次执行登录是否记住我的参数名称
     *
     * @return 记住我的参数名称
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置对于本次执行登录是否记住我的参数名称
     *
     * @param paramName 对于本次执行登录是否记住我的参数名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 是否使用受保护的 cookie
     *
     * @return true 是，否则 false
     */
    public boolean isUseSecureCookie() {
        return useSecureCookie;
    }

    /**
     * 设置是否使用受保护的 cookie
     *
     * @param useSecureCookie true 是，否则 false
     */
    public void setUseSecureCookie(boolean useSecureCookie) {
        this.useSecureCookie = useSecureCookie;
    }

    /**
     * 获取 cookie 加密密钥
     *
     * @return cookie 加密密钥
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置 cookie 加密密钥
     *
     * @param key cookie 加密密钥
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取记住我登录处理 url
     *
     * @return 记住我登录处理 url
     */
    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    /**
     * 设置记住我登录处理 url
     *
     * @param loginProcessingUrl 记住我登录处理 url
     */
    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }
}


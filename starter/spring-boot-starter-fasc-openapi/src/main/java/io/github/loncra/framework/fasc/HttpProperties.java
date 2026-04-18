package io.github.loncra.framework.fasc;

/**
 * @author Fadada
 * 2021/9/8 17:53:20
 */
public class HttpProperties {
    /**
     * http连接超时时间
     */
    private Integer connectTimeout;
    /**
     * http读超时时间
     */
    private Integer readTimeout;
    /**
     * httpclient 每路由(域名)最大的连接数
     */
    private Integer defaultMaxPerRoute;
    /**
     * httpclient socket最大的连接数
     */
    private Integer defaultSocketMaxTotal;
    /**
     * http是否打开代理
     */
    private Boolean proxyFlag = false;
    /**
     * http代理ip
     */
    private String proxyHost;
    /**
     * http代理端口号
     */
    private Integer proxyPort;


    public HttpProperties() {
    }

    public HttpProperties(
            int connectTimeout,
            int readTimeout,
            Boolean proxyFlag,
            String proxyHost,
            Integer proxyPort
    ) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.proxyFlag = proxyFlag;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public HttpProperties(
            int connectTimeout,
            int readTimeout,
            Boolean proxyFlag,
            String proxyHost,
            Integer proxyPort,
            Integer defaultMaxPerRoute,
            Integer defaultSocketMaxTotal
    ) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.proxyFlag = proxyFlag;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        this.defaultSocketMaxTotal = defaultSocketMaxTotal;
    }

    public Integer getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public Integer getDefaultSocketMaxTotal() {
        return defaultSocketMaxTotal;
    }

    public void setDefaultSocketMaxTotal(Integer defaultSocketMaxTotal) {
        this.defaultSocketMaxTotal = defaultSocketMaxTotal;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Boolean getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(Boolean proxyFlag) {
        this.proxyFlag = proxyFlag;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }
}

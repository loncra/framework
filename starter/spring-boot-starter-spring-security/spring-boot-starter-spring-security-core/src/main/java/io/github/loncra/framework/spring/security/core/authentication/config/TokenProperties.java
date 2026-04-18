package io.github.loncra.framework.spring.security.core.authentication.config;

import io.github.loncra.framework.commons.CacheProperties;

/**
 * token 配置
 *
 * @author maurice.chen
 */
public class TokenProperties {

    /**
     * 缓存配置
     */
    private CacheProperties cache;

    /**
     * 令牌请求头名称
     */
    private String headerName;

    /**
     * 令牌参数名称
     */
    private String paramName;

    public TokenProperties(
            CacheProperties cache,
            String headerName,
            String paramName
    ) {
        this.cache = cache;
        this.headerName = headerName;
        this.paramName = paramName;
    }

    public TokenProperties() {
    }

    public CacheProperties getCache() {
        return cache;
    }

    public void setCache(CacheProperties cache) {
        this.cache = cache;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}

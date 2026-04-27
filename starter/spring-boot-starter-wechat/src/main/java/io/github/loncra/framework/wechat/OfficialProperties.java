package io.github.loncra.framework.wechat;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信公众号配置属性
 *
 * @author maurice.chen
 */
@Component
@ConfigurationProperties("loncra.framework.wechat.official")
public class OfficialProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -7783628591893033115L;

    /**
     * 是否注册 {@link io.github.loncra.framework.wechat.service.WechatOfficialService}。未开启时不创建 Bean，避免与仅使用小程序的部署冲突。
     */
    private boolean enabled = false;

    /**
     * 公众号账户配置
     */
    private RefreshAccessTokenMetadata accessToken = new RefreshAccessTokenMetadata(
            CacheProperties.of("loncra:framework:wechat:official:access-token")
    );

    /**
     * 构造函数
     */
    public OfficialProperties() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取公众号账户配置
     *
     * @return 刷新访问令牌元数据
     */
    public RefreshAccessTokenMetadata getAccessToken() {
        return accessToken;
    }

    /**
     * 设置公众号账户配置
     *
     * @param accessToken 刷新访问令牌元数据
     */
    public void setAccessToken(RefreshAccessTokenMetadata accessToken) {
        this.accessToken = accessToken;
    }
}

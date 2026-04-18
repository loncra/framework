package io.github.loncra.framework.wechat;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信小程序配置属性
 *
 * @author maurice.chen
 */
@Component
@ConfigurationProperties("loncra.framework.wechat.applet")
public class AppletProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -5139843287077090780L;

    /**
     * 默认手机号码参数名称
     */
    public static final String DEFAULT_PHONE_NUMBER_CODE_PARAM_NAME = "phoneNumberCode";

    /**
     * 手机号码参数名称
     */
    private String phoneNumberCodeParamName = DEFAULT_PHONE_NUMBER_CODE_PARAM_NAME;

    /**
     * 小程序账户
     */
    private RefreshAccessTokenMetadata accessToken = new RefreshAccessTokenMetadata(
            TimeProperties.ofMinutes(10),
            CacheProperties.of("loncra:framework:wechat:applet:access-token")
    );

    /**
     * 构造函数
     */
    public AppletProperties() {
    }

    /**
     * 获取手机号码参数名称
     *
     * @return 手机号码参数名称
     */
    public String getPhoneNumberCodeParamName() {
        return phoneNumberCodeParamName;
    }

    /**
     * 设置手机号码参数名称
     *
     * @param phoneNumberCodeParamName 手机号码参数名称
     */
    public void setPhoneNumberCodeParamName(String phoneNumberCodeParamName) {
        this.phoneNumberCodeParamName = phoneNumberCodeParamName;
    }

    /**
     * 获取小程序账户配置
     *
     * @return 刷新访问令牌元数据
     */
    public RefreshAccessTokenMetadata getAccessToken() {
        return accessToken;
    }

    /**
     * 设置小程序账户配置
     *
     * @param accessToken 刷新访问令牌元数据
     */
    public void setAccessToken(RefreshAccessTokenMetadata accessToken) {
        this.accessToken = accessToken;
    }
}

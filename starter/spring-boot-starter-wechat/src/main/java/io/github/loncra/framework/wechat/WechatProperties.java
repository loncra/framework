package io.github.loncra.framework.wechat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信配置类
 *
 * @author maurice.chen
 */
@Component
@ConfigurationProperties("loncra.framework.wechat")
public class WechatProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -2546539338401618009L;

    /**
     * 默认二维码票据参数名称
     */
    public static final String DEFAULT_QRCODE_TICKET_PARAM_NAME = "ticket";

    /**
     * 请求状态字段名称
     */
    private String statusCodeFieldName = "errcode";

    /**
     * 请求错误消息字段
     */
    private String statusMessageFieldName = "errmsg";

    /**
     * 请求成功匹配值
     */
    private String successCodeValue = "0";

    /**
     * 成功认证后绑定微信参数名称
     */
    private String successAuthenticationBuildParamName = "wechatCode";

    /**
     * 构造函数
     */
    public WechatProperties() {
    }

    /**
     * 获取请求状态字段名称
     *
     * @return 状态字段名称
     */
    public String getStatusCodeFieldName() {
        return statusCodeFieldName;
    }

    /**
     * 设置请求状态字段名称
     *
     * @param statusCodeFieldName 状态字段名称
     */
    public void setStatusCodeFieldName(String statusCodeFieldName) {
        this.statusCodeFieldName = statusCodeFieldName;
    }

    /**
     * 获取请求错误消息字段名称
     *
     * @return 错误消息字段名称
     */
    public String getStatusMessageFieldName() {
        return statusMessageFieldName;
    }

    /**
     * 设置请求错误消息字段名称
     *
     * @param statusMessageFieldName 错误消息字段名称
     */
    public void setStatusMessageFieldName(String statusMessageFieldName) {
        this.statusMessageFieldName = statusMessageFieldName;
    }

    /**
     * 获取请求成功匹配值
     *
     * @return 成功代码值
     */
    public String getSuccessCodeValue() {
        return successCodeValue;
    }

    /**
     * 设置请求成功匹配值
     *
     * @param successCodeValue 成功代码值
     */
    public void setSuccessCodeValue(String successCodeValue) {
        this.successCodeValue = successCodeValue;
    }

    /**
     * 获取成功认证后绑定微信参数名称
     *
     * @return 参数名称
     */
    public String getSuccessAuthenticationBuildParamName() {
        return successAuthenticationBuildParamName;
    }

    /**
     * 设置成功认证后绑定微信参数名称
     *
     * @param successAuthenticationBuildParamName 参数名称
     */
    public void setSuccessAuthenticationBuildParamName(String successAuthenticationBuildParamName) {
        this.successAuthenticationBuildParamName = successAuthenticationBuildParamName;
    }

}

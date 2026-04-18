package io.github.loncra.framework.allin.pay.config;

import io.github.loncra.framework.commons.domain.metadata.CloudSecretMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 通联支付配置属性
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.allin-pay")
public class AllInPayProperties {

    /**
     * 统一支付 API 路径
     */
    public static final String UNIFIED_PAY_API = "/api/access/payInterface/unifiedPay";

    /**
     * 查询订单 API 路径
     */
    public static final String QUERY_ORDER_API = "/api/access/payInterface/findOrderInfo";

    /**
     * 退款 API 路径
     */
    public static final String REFUND_API = "/api/access/payInterface/refund";

    /**
     * 批量退款 API 路径
     */
    public static final String BATCH_REFUND_API = "/api/access/payInterface/batchRefund";

    /**
     * 支付码支付 API 路径
     */
    public static final String PAYMENT_IN_QR_CODE_API = "/api/access/payInterface/usePaymentCode";

    /**
     * 结算单 API 路径
     */
    public static final String SETTLE_BILL_API = "/api/access/payInterface/getSettleBill";

    /**
     * 文件 URL API 路径
     */
    public static final String FILE_URL_API = "/api/access/accountStatement/getFileUrl";

    /**
     * 密钥配置
     */
    private CloudSecretMetadata secret = new CloudSecretMetadata();

    /**
     * 基础 URL
     */
    private String baseUrl = "https://interfacetest.allinpaygx.com";

    /**
     * API 响应结果代码字段名
     */
    private String apiResultCodeField = "status";

    /**
     * API 成功代码值
     */
    private String apiCodeSuccessValue = "0000";

    /**
     * API 响应错误代码字段名
     */
    private String apiResultErrorCodeField = "errorCode";

    /**
     * API 响应消息字段名
     */
    private String apiResultMessageField = "errorMessage";

    /**
     * 支付类型参数名称
     */
    private String payTypeParamName = "payType";

    /**
     * 构造函数
     */
    public AllInPayProperties() {
    }

    /**
     * 获取密钥配置
     *
     * @return 密钥配置
     */
    public CloudSecretMetadata getSecret() {
        return secret;
    }

    /**
     * 设置密钥配置
     *
     * @param secret 密钥配置
     */
    public void setSecret(CloudSecretMetadata secret) {
        this.secret = secret;
    }

    /**
     * 获取基础 URL
     *
     * @return 基础 URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 设置基础 URL
     *
     * @param baseUrl 基础 URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取 API 响应结果代码字段名
     *
     * @return API 响应结果代码字段名
     */
    public String getApiResultCodeField() {
        return apiResultCodeField;
    }

    /**
     * 设置 API 响应结果代码字段名
     *
     * @param apiResultCodeField API 响应结果代码字段名
     */
    public void setApiResultCodeField(String apiResultCodeField) {
        this.apiResultCodeField = apiResultCodeField;
    }

    /**
     * 获取 API 成功代码值
     *
     * @return API 成功代码值
     */
    public String getApiCodeSuccessValue() {
        return apiCodeSuccessValue;
    }

    /**
     * 设置 API 成功代码值
     *
     * @param apiCodeSuccessValue API 成功代码值
     */
    public void setApiCodeSuccessValue(String apiCodeSuccessValue) {
        this.apiCodeSuccessValue = apiCodeSuccessValue;
    }

    /**
     * 获取 API 响应错误代码字段名
     *
     * @return API 响应错误代码字段名
     */
    public String getApiResultErrorCodeField() {
        return apiResultErrorCodeField;
    }

    /**
     * 设置 API 响应错误代码字段名
     *
     * @param apiResultErrorCodeField API 响应错误代码字段名
     */
    public void setApiResultErrorCodeField(String apiResultErrorCodeField) {
        this.apiResultErrorCodeField = apiResultErrorCodeField;
    }

    /**
     * 获取 API 响应消息字段名
     *
     * @return API 响应消息字段名
     */
    public String getApiResultMessageField() {
        return apiResultMessageField;
    }

    /**
     * 设置 API 响应消息字段名
     *
     * @param apiResultMessageField API 响应消息字段名
     */
    public void setApiResultMessageField(String apiResultMessageField) {
        this.apiResultMessageField = apiResultMessageField;
    }

    /**
     * 获取支付类型参数名称
     *
     * @return 支付类型参数名称
     */
    public String getPayTypeParamName() {
        return payTypeParamName;
    }

    /**
     * 设置支付类型参数名称
     *
     * @param payTypeParamName 支付类型参数名称
     */
    public void setPayTypeParamName(String payTypeParamName) {
        this.payTypeParamName = payTypeParamName;
    }
}

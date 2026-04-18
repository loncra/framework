package io.github.loncra.framework.allin.pay.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.loncra.framework.allin.pay.config.AllInPayProperties;
import io.github.loncra.framework.allin.pay.domain.body.request.*;
import io.github.loncra.framework.allin.pay.domain.body.response.SettleBillResponseBody;
import io.github.loncra.framework.allin.pay.domain.metadata.BasicRequestMetadata;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 通联支付服务
 *
 * @author maurice.chen
 */
@Service
public class AllInPayService {

    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 时间格式
     */
    public static final String TIME_FORMAT = "HHmmss";

    /**
     * 日期时间格式
     */
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT;

    /**
     * 交易列表键名
     */
    public static final String TRX_LIST_KEY = "trxlist";

    /**
     * 订单号键名
     */
    public static final String ORDER_NUMBER_KEY = "orderNo";

    /**
     * 日志记录器
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(AllInPayService.class);

    /**
     * 通联支付配置
     */
    private final AllInPayProperties allInPayConfig;

    /**
     * REST 模板
     */
    private final RestTemplate restTemplate;

    /**
     * 构造函数
     *
     * @param allInPayConfig 通联支付配置
     * @param restTemplate   REST 模板
     */
    public AllInPayService(
            AllInPayProperties allInPayConfig,
            RestTemplate restTemplate
    ) {
        this.allInPayConfig = allInPayConfig;
        this.restTemplate = restTemplate;
    }

    /**
     * 对请求元数据签名
     *
     * @param data 请求元数据
     *
     * @return 签名字符串
     */
    private String sign(BasicRequestMetadata data) {
        return sign(CastUtils.convertValue(data, CastUtils.MAP_TYPE_REFERENCE));
    }

    /**
     * 对数据签名
     *
     * @param data 数据 Map
     *
     * @return 签名字符串（MD5 大写）
     */
    public String sign(Map<String, Object> data) {
        Map<String, Object> singData = new TreeMap<>(data);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : singData.keySet()) {
            stringBuilder.append(key).append(singData.get(key));
        }
        stringBuilder.append(allInPayConfig.getSecret().getSecretKey());
        return DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

    /**
     * 统一支付
     *
     * @param body 统一支付请求体
     *
     * @return 支付响应结果
     */
    public Map<String, Object> unifiedPay(UnifiedPayRequestBody body) {
        String param = getQueryParam(body);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.UNIFIED_PAY_API + HttpRequestParameterMapUtils.QUESTION_MARK + param, HttpMethod.GET, HttpEntity.EMPTY);
    }

    /**
     * 查询订单
     *
     * @param body 查询订单请求体
     *
     * @return 订单查询响应结果
     */
    public Map<String, Object> queryOrder(QueryOrderRequestBody body) {
        String param = getQueryParam(body);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.QUERY_ORDER_API + HttpRequestParameterMapUtils.QUESTION_MARK + param, HttpMethod.GET, HttpEntity.EMPTY);
    }

    /**
     * 退款
     *
     * @param body 退款请求体
     *
     * @return 退款响应结果
     */
    public Map<String, Object> refund(RefundRequestBody body) {
        String param = getQueryParam(body);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.REFUND_API + HttpRequestParameterMapUtils.QUESTION_MARK + param, HttpMethod.GET, HttpEntity.EMPTY);
    }

    /**
     * 批量退款
     *
     * @param body 批量退款请求体
     *
     * @return 批量退款响应结果
     */
    public Map<String, Object> batchRefund(BatchRefundRequestBody body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.BATCH_REFUND_API, HttpMethod.POST, new HttpEntity<>(body, headers));
    }

    /**
     * 支付码支付
     *
     * @param body 支付码支付请求体
     *
     * @return 支付码支付响应结果
     */
    public Map<String, Object> paymentInQrCode(UsePaymentCodeRequestBody body) {
        String param = getQueryParam(body);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.PAYMENT_IN_QR_CODE_API + HttpRequestParameterMapUtils.QUESTION_MARK + param, HttpMethod.GET, HttpEntity.EMPTY);
    }

    /**
     * 获取结算单
     *
     * @param body 结算单请求体
     *
     * @return 结算单响应列表
     */
    public List<SettleBillResponseBody> getSettleBill(SettleBillRequestBody body) {

        String param = getQueryParam(body);

        Map<String, Object> result = exchange(
                allInPayConfig.getBaseUrl() + AllInPayProperties.SETTLE_BILL_API + HttpRequestParameterMapUtils.QUESTION_MARK + param,
                HttpMethod.GET,
                HttpEntity.EMPTY
        );

        return SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(result.get(TRX_LIST_KEY).toString(), new TypeReference<>() {}));
    }

    /**
     * 获取文件 URL
     *
     * @param body 获取文件 URL 请求体
     *
     * @return 文件 URL 响应结果
     */
    public Map<String, Object> getFileUrl(GetFileUrlRequestBody body) {
        String param = getQueryParam(body);
        return exchange(allInPayConfig.getBaseUrl() + AllInPayProperties.FILE_URL_API + HttpRequestParameterMapUtils.QUESTION_MARK + param, HttpMethod.GET, HttpEntity.EMPTY);
    }

    /**
     * 获取查询参数字符串
     *
     * @param metadata 请求元数据
     *
     * @return 查询参数字符串
     */
    public String getQueryParam(BasicRequestMetadata metadata) {
        metadata.setMerchantNo(allInPayConfig.getSecret().getSecretId());
        metadata.setSign(sign(metadata));
        Map<String, Object> map = CastUtils.convertValue(metadata, CastUtils.MAP_TYPE_REFERENCE);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        map.entrySet().stream().filter(e -> Objects.nonNull(e.getValue())).forEach(e -> data.add(e.getKey(), e.getValue().toString()));

        return HttpRequestParameterMapUtils.castRequestBodyMapToString(data);
    }

    /**
     * 调用 API 接口
     *
     * @param url    接口 URL
     * @param method HTTP 方法
     * @param entity HTTP 实体
     *
     * @return 响应结果 Map
     */
    public Map<String, Object> exchange(
            String url,
            HttpMethod method,
            HttpEntity<?> entity
    ) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[通联支付]: 调用 {} 接口, 提交的参数为:{}", url, Objects.nonNull(entity.getBody()) ? CastUtils.convertValue(entity.getBody(), CastUtils.MAP_TYPE_REFERENCE) : CastUtils.convertValue(entity, CastUtils.MAP_TYPE_REFERENCE));
        }

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                method,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[通联支付]: 调用 {} 接口完成，响应数据为:{}", url, CastUtils.convertValue(response.getBody(), CastUtils.MAP_TYPE_REFERENCE));
        }

        SystemException.isTrue(response.getStatusCode().is2xxSuccessful(), HttpStatus.valueOf(response.getStatusCode().value()).getReasonPhrase());

        Map<String, Object> body = response.getBody();
        SystemException.isTrue(MapUtils.isNotEmpty(body), "[通联支付]: 找不到 " + url + " 接口的响应体信息");
        String code = Objects.toString(body.get(allInPayConfig.getApiResultCodeField()), StringUtils.EMPTY);
        String message = Objects.toString(body.get(allInPayConfig.getApiResultMessageField()), StringUtils.EMPTY);
        String errorCode = Objects.toString(body.get(allInPayConfig.getApiResultErrorCodeField()), StringUtils.EMPTY);

        SystemException.isTrue(Strings.CS.equals(code, allInPayConfig.getApiCodeSuccessValue()), () -> new ErrorCodeException("[通联支付]:" + message, errorCode));

        return body;
    }

    /**
     * 获取通联支付配置
     *
     * @return 通联支付配置
     */
    public AllInPayProperties getAllInPayConfig() {
        return allInPayConfig;
    }
}

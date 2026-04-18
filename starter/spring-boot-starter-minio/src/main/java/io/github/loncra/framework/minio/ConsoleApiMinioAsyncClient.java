package io.github.loncra.framework.minio;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.minio.config.MinioProperties;
import io.minio.MinioAsyncClient;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * 支持管理端 api 的 minio 客户端
 *
 * @author maurice.chen
 */
public class ConsoleApiMinioAsyncClient extends MinioAsyncClient implements InitializingBean, SchedulingConfigurer {

    public static final String BUCKETS_API_NAME = "buckets";

    public static final String LOGIN_CONSOLE_API_NAME = "login";

    private final RestTemplate restTemplate;

    private final MinioProperties minioProperties;

    private Cookie cookie;

    /**
     * 构造函数
     *
     * @param client          MinIO 异步客户端
     * @param minioProperties MinIO 配置属性
     */
    protected ConsoleApiMinioAsyncClient(
            MinioAsyncClient client,
            MinioProperties minioProperties
    ) {
        super(client);
        this.minioProperties = minioProperties;
        this.restTemplate = new RestTemplate();
    }

    /**
     * 获取桶信息
     *
     * @param name 桶名称，如果不未 “” 或者 null，讲返回 api buckets 的所有内容，否则返回 api buckets 接口的 buckets 单个对象信息
     *
     * @return 桶信息
     */
    public CompletableFuture<Map<String, Object>> buckets(String name) {
        return CompletableFuture
                .supplyAsync(() -> this.<Map<String, Object>>exchangeConsoleApi(BUCKETS_API_NAME, HttpMethod.GET, new LinkedHashMap<>()))
                .thenApply(result -> {
                    if (StringUtils.isNotEmpty(name)) {
                        List<Map<String, Object>> buckets = CastUtils.cast(result.get(BUCKETS_API_NAME));
                        return buckets
                                .stream()
                                .filter(m -> name.equals(m.get(NameEnum.FIELD_NAME)))
                                .findFirst()
                                .orElse(null);
                    }
                    return result;
                });

    }

    @Override
    public void afterPropertiesSet() {
        setCookieValue();
    }

    private void setCookieValue() {
        if (StringUtils.isEmpty(minioProperties.getEndpoint())) {
            return;
        }

        Map<String, Object> param = Map.of(
                minioProperties.getAccessKeyBodyName(), minioProperties.getAccessKey(),
                minioProperties.getSecretKeyBodyName(), minioProperties.getSecretKey()
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(param, httpHeaders);
        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(
                minioProperties.getConsoleApiAddress(LOGIN_CONSOLE_API_NAME),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        Assert.isTrue(
                result.getStatusCode().is2xxSuccessful(),
                HttpStatus.valueOf(result.getStatusCode().value()).getReasonPhrase()
        );

        String cookieValue = result.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        Assert.isTrue(StringUtils.isNotEmpty(cookieValue), "header " + HttpHeaders.SET_COOKIE + " is empty");

        cookie = Cookie.parse(Objects.requireNonNull(HttpUrl.parse(minioProperties.getEndpoint())), cookieValue);
    }

    /**
     * 调用控制台 API
     *
     * @param apiName API 名称
     * @param method  HTTP 方法
     * @param body    请求体
     * @param <T>     返回类型
     *
     * @return API 响应结果
     */
    public <T> T exchangeConsoleApi(
            String apiName,
            HttpMethod method,
            Map<String, Object> body
    ) {
        Assert.notNull(cookie, "cookie is null");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.COOKIE, cookie.name() + HttpRequestParameterMapUtils.EQ + cookie.value());
        ResponseEntity<T> result = restTemplate.exchange(
                minioProperties.getConsoleApiAddress(apiName),
                method,
                new HttpEntity<>(body, httpHeaders),
                new ParameterizedTypeReference<>() {}
        );

        Assert.isTrue(
                result.getStatusCode().is2xxSuccessful(),
                HttpStatus.valueOf(result.getStatusCode().value()).getReasonPhrase()
        );

        return result.getBody();

    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // FIXME 怎么做成可动态的配置
        taskRegistrar.addCronTask(this::refreshCookie, minioProperties.getRefreshCookieCron());
    }

    private void refreshCookie() {
        if (cookie.expiresAt() + minioProperties.getCookieMinRemainingBeforeRefresh().toMillis() < System.currentTimeMillis()) {
            return;
        }

        setCookieValue();
    }

    /**
     * 获取 MinIO 配置属性
     *
     * @return MinIO 配置属性
     */
    public MinioProperties getMinioProperties() {
        return minioProperties;
    }
}

package io.github.loncra.framework.minio.config;

import io.github.loncra.framework.commons.TimeProperties;
import org.apache.commons.lang3.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.AntPathMatcher;

import java.util.concurrent.TimeUnit;

/**
 * minio 配置信息
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.minio")
public class MinioProperties {

    /**
     * 终端地址
     */
    private String endpoint;

    /**
     * 管理端 api 地址
     */
    private String consoleEndpoint;

    /**
     * 管理端 api 前缀
     */
    private String consoleApiPrefix = "/api/v1";

    /**
     * 管理端登录请求体 accessKey 名称
     */
    private String accessKeyBodyName = "accessKey";

    /**
     * 管理端登录请求体 secretKey 名称
     */
    private String secretKeyBodyName = "secretKey";

    /**
     * 大文件上传的临时文件存储位置
     */
    private String uploadPartTempFilePath = "./upload_part";

    /**
     * 用于隔离大文件上传，批量删除等多现成操作的专有线程池储量，如果为 null 默认取值为: Runtime.getRuntime().availableProcessors() * 2
     */
    private Integer ioExecutorNumberOfThreads = null;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 安全密钥
     */
    private String secretKey;

    /**
     * admin 后台 cookie 剩余最小时间后进行刷新，默认为 5 分钟
     */
    private TimeProperties cookieMinRemainingBeforeRefresh = TimeProperties.of(5, TimeUnit.MINUTES);

    /**
     * 刷新 cookie 时间
     */
    private String refreshCookieCron = "0 1 * * * ?";

    /**
     * minio 配置信息
     */
    public MinioProperties() {
    }

    /**
     * 获取终端地址
     *
     * @return 终端地址
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 设置终端地址
     *
     * @param endpoint 终端地址
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 获取访问密钥
     *
     * @return 访问密钥
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * 设置访问密钥
     *
     * @param accessKey 访问密钥
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * 获取安全密钥
     *
     * @return 安全密钥
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 设置安全密钥
     *
     * @param secretKey 安全密钥
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 获取管理端登录请求体 accessKey 名称
     *
     * @return accessKey 名称
     */
    public String getAccessKeyBodyName() {
        return accessKeyBodyName;
    }

    /**
     * 设置管理端登录请求体 accessKey 名称
     *
     * @param accessKeyBodyName accessKey 名称
     */
    public void setAccessKeyBodyName(String accessKeyBodyName) {
        this.accessKeyBodyName = accessKeyBodyName;
    }

    /**
     * 获取管理端登录请求体 secretKey 名称
     *
     * @return secretKey 名称
     */
    public String getSecretKeyBodyName() {
        return secretKeyBodyName;
    }

    /**
     * 设置管理端登录请求体 secretKey 名称
     *
     * @param secretKeyBodyName secretKey 名称
     */
    public void setSecretKeyBodyName(String secretKeyBodyName) {
        this.secretKeyBodyName = secretKeyBodyName;
    }

    /**
     * 设置控制台终端地址
     *
     * @return 控制台终端地址
     */
    public String getConsoleEndpoint() {
        return consoleEndpoint;
    }

    /**
     * 设置控制台终端地址
     *
     * @param consoleEndpoint 控制台终端地址
     */
    public void setConsoleEndpoint(String consoleEndpoint) {
        this.consoleEndpoint = consoleEndpoint;
    }

    /**
     * 获取管理端 api 前缀
     *
     * @return 管理端 api 前缀
     */
    public String getConsoleApiPrefix() {
        return consoleApiPrefix;
    }

    /**
     * 设置管理端 api 前缀
     *
     * @param consoleApiPrefix 管理端 api 前缀
     */
    public void setConsoleApiPrefix(String consoleApiPrefix) {
        this.consoleApiPrefix = consoleApiPrefix;
    }

    /**
     * 获取控制台 API 地址
     *
     * @param apiName API 名称
     *
     * @return 完整的控制台 API 地址
     */
    public String getConsoleApiAddress(String apiName) {
        return getConsoleEndpoint()
                + Strings.CS.prependIfMissing(getConsoleApiPrefix(), AntPathMatcher.DEFAULT_PATH_SEPARATOR)
                + Strings.CS.prependIfMissing(apiName, AntPathMatcher.DEFAULT_PATH_SEPARATOR);
    }

    /**
     * 获取 admin 后台 cookie 剩余最小时间
     *
     * @return cookie 剩余最小时间配置
     */
    public TimeProperties getCookieMinRemainingBeforeRefresh() {
        return cookieMinRemainingBeforeRefresh;
    }

    /**
     * 设置 admin 后台 cookie 剩余最小时间
     *
     * @param cookieMinRemainingBeforeRefresh cookie 剩余最小时间配置
     */
    public void setCookieMinRemainingBeforeRefresh(TimeProperties cookieMinRemainingBeforeRefresh) {
        this.cookieMinRemainingBeforeRefresh = cookieMinRemainingBeforeRefresh;
    }

    /**
     * 获取刷新 cookie 的 Cron 表达式
     *
     * @return Cron 表达式
     */
    public String getRefreshCookieCron() {
        return refreshCookieCron;
    }

    /**
     * 设置刷新 cookie 的 Cron 表达式
     *
     * @param refreshCookieCron Cron 表达式
     */
    public void setRefreshCookieCron(String refreshCookieCron) {
        this.refreshCookieCron = refreshCookieCron;
    }

    /**
     * 获取大文件上传的临时文件存储位置
     *
     * @return 临时文件存储路径
     */
    public String getUploadPartTempFilePath() {
        return uploadPartTempFilePath;
    }

    /**
     * 设置大文件上传的临时文件存储位置
     *
     * @param uploadPartTempFilePath 临时文件存储路径
     */
    public void setUploadPartTempFilePath(String uploadPartTempFilePath) {
        this.uploadPartTempFilePath = uploadPartTempFilePath;
    }

    /**
     * 获取 IO 执行器线程数
     *
     * @return 线程数，如果为 null 则使用默认值
     */
    public Integer getIoExecutorNumberOfThreads() {
        return ioExecutorNumberOfThreads;
    }

    /**
     * 设置 IO 执行器线程数
     *
     * @param ioExecutorNumberOfThreads 线程数，如果为 null 则使用默认值（Runtime.getRuntime().availableProcessors() * 2）
     */
    public void setIoExecutorNumberOfThreads(Integer ioExecutorNumberOfThreads) {
        this.ioExecutorNumberOfThreads = ioExecutorNumberOfThreads;
    }
}

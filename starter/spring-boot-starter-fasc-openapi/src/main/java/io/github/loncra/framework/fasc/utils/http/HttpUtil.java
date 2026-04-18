package io.github.loncra.framework.fasc.utils.http;

import io.github.loncra.framework.fasc.HttpProperties;
import io.github.loncra.framework.fasc.bean.base.BaseResponseEntity;
import io.github.loncra.framework.fasc.bean.base.HttpInfoRes;
import io.github.loncra.framework.fasc.constants.RequestConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static int DEFAULT_MAX_PER_ROUTE = 350;

    private static int DEFAULT_SOCKET_MAX_TOTAL = 400;

    public static HttpProperties httpProperties;

    private static volatile CloseableHttpClient closeableHttpClient;

    private static Lock lock = new ReentrantLock();

    private HttpUtil() {
    }

    private static HttpGet getHttpGet(
            String url,
            Map<String, String> params,
            String encode
    ) {
        StringBuilder buf = new StringBuilder(url);
        if (params != null) {
            // 地址增加?或者&
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            // 添加参数
            for (Entry<String, String> entry : params.entrySet()) {
                buf.append(flag);
                buf.append(entry.getKey());
                buf.append("=");
                try {
                    String param = entry.getValue();
                    if (param == null) {
                        param = "";
                    }
                    buf.append(URLEncoder.encode(param, encode));
                }
                catch (UnsupportedEncodingException e) {
                    log.error("URLEncoder Error,encode=" + encode + ",param=" + entry.getValue(), e);
                }
                flag = "&";

            }
        }
        return new HttpGet(buf.toString());
    }

    /**
     * 上传文件 post
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param files  请求文件
     *
     * @return HttpPost
     */
    private static HttpPost getHttpPost(
            String url,
            Map<String, String> params,
            Map<String, File> files
    ) {
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (files != null && !files.isEmpty()) {
            for (Entry<String, File> kv : files.entrySet()) {
                multipartEntityBuilder.addBinaryBody(kv.getKey(), kv.getValue());
            }
        }
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset(RequestConstants.CHARSET_UTF8));
            }
            multipartEntityBuilder.setMode(HttpMultipartMode.RFC6532);
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    /**
     * post请求
     *
     * @param url    请求路径
     * @param params 请求参数
     *
     * @return HttpPost
     */
    private static HttpPost getHttpPost(
            String url,
            Map<String, String> params,
            String charset
    ) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>();
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                list.add(basicNameValuePair);
            }
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list, charset);
            httpPost.setEntity(httpEntity);
        }
        return httpPost;
    }

    public static HttpInfoRes post(
            String url,
            Map<String, String> reqHeader,
            Map<String, String> params,
            Map<String, File> files
    ) throws ApiException {
        try {
            // 初始化httpClient
            CloseableHttpClient httpClient = getHttpClient(httpProperties);
            // 创建http请求 设置请求参数
            HttpPost httpPost;
            if (files == null || files.isEmpty()) {
                httpPost = getHttpPost(url, params, RequestConstants.CHARSET_UTF8);
            }
            else {
                httpPost = getHttpPost(url, params, files);
            }
            httpPost.setConfig(getRequestConfig(httpProperties));
            return executeHttpRequest(httpClient, httpPost, reqHeader);
        }
        catch (ApiException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("url=[{}] http请求失败：{}", url, e.getMessage(), e);
            throw new ApiException("请求失败");
        }
    }

    /**
     * 获取client
     *
     * @param httpProperties http配置
     *
     * @return CloseableHttpClient client
     *
     * @throws ApiException 异常
     */
    public static CloseableHttpClient getHttpClient(HttpProperties httpProperties) throws ApiException {
        if (closeableHttpClient != null) {
            return closeableHttpClient;
        }
        try {
            lock.lock();
            if (closeableHttpClient != null) {
                return closeableHttpClient;
            }
            Registry<ConnectionSocketFactory> sfr = null;
            if (httpProperties != null && Boolean.TRUE.equals(httpProperties.getProxyFlag())) {
                sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build();
            }
            else {
                SSLContext ctx = SSLContexts.custom().setProtocol("TLSv1.2").build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
                sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslsf != null ? sslsf : SSLConnectionSocketFactory.getSocketFactory())
                        .build();
            }
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(sfr);
            Integer defaultMaxPerRoute = DEFAULT_MAX_PER_ROUTE;
            Integer defaultSocketMaxTotal = DEFAULT_SOCKET_MAX_TOTAL;
            if (httpProperties != null && httpProperties.getDefaultMaxPerRoute() != null && httpProperties.getDefaultMaxPerRoute() > 0) {
                defaultMaxPerRoute = httpProperties.getDefaultMaxPerRoute();
            }
            if (httpProperties != null && httpProperties.getDefaultSocketMaxTotal() != null && httpProperties.getDefaultSocketMaxTotal() > 0) {
                defaultSocketMaxTotal = httpProperties.getDefaultSocketMaxTotal();
            }
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
            connectionManager.setMaxTotal(defaultSocketMaxTotal);
            connectionManager.setValidateAfterInactivity(3000);
            connectionManager.closeExpiredConnections();
            closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        }
        catch (Exception e) {
            log.error("HttpClient生成失败：{}", e.getMessage(), e);
            throw new ApiException("HttpClient生成失败");
        }
        finally {
            lock.unlock();
        }
        return closeableHttpClient;
    }


    public static HttpInfoRes get(
            String url,
            Map<String, String> reqHeader,
            Map<String, String> params
    ) throws ApiException {
        // 初始化httpClient
        CloseableHttpClient httpClient = getHttpClient(httpProperties);
        // 创建http请求 设置请求参数
        HttpGet httpGet = getHttpGet(url, params, RequestConstants.CHARSET_UTF8);
        // 设置超时时间、代理配置
        httpGet.setConfig(getRequestConfig(httpProperties));
        return executeHttpRequest(httpClient, httpGet, reqHeader);
    }


    public static HttpInfoRes executeHttpRequest(
            CloseableHttpClient client,
            HttpUriRequest request,
            Map<String, String> reqHeader
    ) throws ApiException {
        CloseableHttpResponse response = null;
        try {
            if (reqHeader != null && !reqHeader.isEmpty()) {
                for (Entry<String, String> entry : reqHeader.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            response = client.execute(request);
            reqesutAndResponseLog(request, response);
            HttpInfoRes httpInfoRes = HttpInfoRes.getInstance();
            if (response.getEntity() != null) {
                httpInfoRes.setBody(EntityUtils.toString(response.getEntity()));
            }
            httpInfoRes.setHttpStatusCode(response.getStatusLine().getStatusCode());
            return httpInfoRes;
        }
        catch (SocketTimeoutException eto) {
            log.error("请求链接超时：{}", eto.getMessage(), eto);
            throw new ApiException("请求超时");
        }
        catch (Exception e) {
            log.error("executeHttpRequest请求失败：{}", e.getMessage(), e);
            throw new ApiException("请求失败");
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                }
                catch (IOException e) {
                    log.error("CloseableHttpResponse关闭失败：{}", e.getMessage(), e);
                }
            }
        }
    }


    /**
     * 下载文件
     *
     * @param url       请求路径
     * @param reqHeader 请求头
     * @param params    请求参数
     *
     * @return ApiResponseEntity
     *
     * @throws ApiException 异常
     */
    public static BaseResponseEntity downLoadFiles(
            String url,
            Map<String, String> reqHeader,
            Map<String, String> params
    ) throws ApiException {
        BaseResponseEntity entity = new BaseResponseEntity();

        CloseableHttpResponse response = null;
        // 初始化httpClient
        CloseableHttpClient httpClient = getHttpClient(httpProperties);
        try {
            // 创建http请求 设置请求参数
            HttpPost httpPost = getHttpPost(url, params, RequestConstants.CHARSET_UTF8);
            if (reqHeader != null) {
                for (Entry<String, String> entry : reqHeader.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setConfig(getRequestConfig(httpProperties));
            response = httpClient.execute(httpPost);
            reqesutAndResponseLog(httpPost, response);
            entity.setHttpStatusCode(response.getStatusLine().getStatusCode());
            HttpEntity respEntity = response.getEntity();
            Header contentType = respEntity.getContentType();

            if (contentType.getValue().contains(ContentType.APPLICATION_JSON.getMimeType())) {
                entity.setData(EntityUtils.toString(respEntity));
            }
            else {
                byte[] bytes = EntityUtils.toByteArray(response.getEntity());
                entity.setContent(bytes);
                entity.setContentType(contentType.getValue());
            }
        }
        catch (Exception e) {
            log.error("文件下载失败：{}", e.getMessage(), e);
            throw new ApiException("文件下载失败");
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                }
                catch (IOException e) {
                    log.error("CloseableHttpResponse关闭失败：{}", e.getMessage(), e);
                }
            }
        }

        return entity;
    }


    /**
     * 请求响应日志打印
     *
     * @param request  请求
     * @param response 响应
     */
    private static void reqesutAndResponseLog(
            HttpUriRequest request,
            HttpResponse response
    ) {

        // 请求url
        URI uri = request.getURI();
        if (uri != null) {
            log.info("request url = [{}]", uri.toString());
        }

        // 获取请求头里面的nonce
        Header[] requestHeaders = request.getHeaders(RequestConstants.NONCE);

        if (requestHeaders != null && requestHeaders.length > 0) {
            log.info("request header {}= [{}]", RequestConstants.NONCE, requestHeaders[0].getValue());
        }

        // 获取响应头里面的requestId
        Header[] responseHeaders = response.getHeaders(RequestConstants.FDD_REQEUST_ID);
        if (responseHeaders != null && responseHeaders.length > 0) {
            log.info("response header {}= [{}]", RequestConstants.FDD_REQEUST_ID, responseHeaders[0].getValue());
        }
    }

    /**
     * 设置超时时间和代理
     *
     * @return 请求配置
     */
    private static RequestConfig getRequestConfig(HttpProperties httpProperties) {
        RequestConfig.Builder custom = RequestConfig.custom();
        if (httpProperties != null) {
            if (httpProperties.getReadTimeout() != null) {
                custom.setSocketTimeout(httpProperties.getReadTimeout());
            }
            if (httpProperties.getConnectTimeout() != null) {
                custom.setConnectTimeout(httpProperties.getConnectTimeout());
            }
            // 设置代理
            if (Boolean.TRUE.equals(httpProperties.getProxyFlag())) {
                HttpHost proxy = new HttpHost(httpProperties.getProxyHost(), httpProperties.getProxyPort(), "http");
                custom.setProxy(proxy);
            }
        }
        return custom.build();
    }
}

package io.github.loncra.framework.canal.domain.meta;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * http 形式的 canal 行数据变更通知元数据
 *
 * @author maurice.chen
 */
public class HttpCanalRowDataChangeNoticeMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -463158192265749962L;

    private String url;

    private Map<String, String> queryParams = new LinkedHashMap<>();

    private Map<String, String> headers = new LinkedHashMap<>();

    private Map<String, Object> body = new LinkedHashMap<>();

    /**
     * 创建一个 HTTP 形式的 canal 行数据变更通知元数据对象
     */
    public HttpCanalRowDataChangeNoticeMetadata() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }
}

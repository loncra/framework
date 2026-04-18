package io.github.loncra.framework.fasc.res.user;

/**
 * @author zhoufucheng
 * @date 2023/8/8 17:56
 */
public class GetFaceRecognitionUrlRes {
    private String serialNo;
    private String shortUrl;
    private String url;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

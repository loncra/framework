package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * 2021/9/9 21:34:22
 */
public class BaseResponseEntity extends BaseHttpRes {
    private byte[] content;
    private String contentType;
    private String data;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

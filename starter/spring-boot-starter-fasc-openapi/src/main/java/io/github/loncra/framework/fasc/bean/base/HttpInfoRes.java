package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * @date 2021/12/17 10:55:15
 */
public class HttpInfoRes extends BaseHttpRes {
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static HttpInfoRes getInstance() {
        return new HttpInfoRes();
    }
}

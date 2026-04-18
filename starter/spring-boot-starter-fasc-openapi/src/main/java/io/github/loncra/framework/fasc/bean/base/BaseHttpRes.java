package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * @date 2021/12/17 10:28:55
 */
public class BaseHttpRes extends BaseBean {
    private Integer httpStatusCode;

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}

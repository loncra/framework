package io.github.loncra.framework.fasc.bean.base;

import io.github.loncra.framework.fasc.constants.RequestConstants;
import org.apache.http.HttpStatus;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class BaseRes<T> extends BaseHttpRes {

    private String code;

    private String msg;

    private T data;

    public BaseRes() {
    }

    public boolean isSuccess() {
        return getHttpStatusCode() != null
                && getHttpStatusCode().equals(HttpStatus.SC_OK)
                && RequestConstants.SUCCESS_CODE.equals(code);
    }

    public BaseRes(
            String code,
            String msg
    ) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

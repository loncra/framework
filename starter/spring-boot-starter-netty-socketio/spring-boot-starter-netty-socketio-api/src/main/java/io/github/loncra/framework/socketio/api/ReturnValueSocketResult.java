package io.github.loncra.framework.socketio.api;

import java.io.Serial;

/**
 * 但返回值的 socket 结果集
 *
 * @param <R> 返回值类型
 *
 * @author maurice
 */
public class ReturnValueSocketResult<R> extends SocketResult {

    @Serial
    private static final long serialVersionUID = 7934586207040058796L;
    /**
     * 返回值
     */
    private R returnValue;

    public ReturnValueSocketResult() {

    }

    public ReturnValueSocketResult(R returnValue) {
        this.returnValue = returnValue;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(R returnValue) {
        this.returnValue = returnValue;
    }
}

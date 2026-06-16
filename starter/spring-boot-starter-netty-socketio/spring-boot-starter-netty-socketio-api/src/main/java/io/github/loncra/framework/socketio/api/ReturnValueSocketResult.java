package io.github.loncra.framework.socketio.api;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;

import java.io.Serial;
import java.util.List;

/**
 * 带返回值的 socket 结果集
 *
 * @param <R> 返回值类型
 * @author maurice
 */
public class ReturnValueSocketResult<R> extends SocketResult {

    @Serial
    private static final long serialVersionUID = 7934586207040058796L;
    /**
     * 返回值
     */
    private R returnValue;

    private String message;

    private String executeCode;

    private Integer status;

    public ReturnValueSocketResult() {

    }

    public ReturnValueSocketResult(R returnValue) {
        this.returnValue = returnValue;
    }

    public ReturnValueSocketResult(
            List<AbstractSocketMessageMetadata<?>> messages,
            R returnValue,
            String message,
            String executeCode,
            Integer status
    ) {
        super(messages);
        this.returnValue = returnValue;
        this.message = message;
        this.executeCode = executeCode;
        this.status = status;
    }

    /**
     * 创建一个 带返回值的 socket 结果集实体类
     *
     * @param message        响应信息
     * @param socketMessages socket 消息
     * @return 带返回值的 socket 结果集
     */
    public static <T> ReturnValueSocketResult<T> of(
            String message,
            List<AbstractSocketMessageMetadata<?>> socketMessages
    ) {
        return ReturnValueSocketResult.of(message, Integer.parseInt(RestResult.SUCCESS_EXECUTE_CODE), socketMessages);
    }

    /**
     * 创建一个 带返回值的 socket 结果集实体类
     *
     * @param message        响应信息
     * @param status         执行状态
     * @param socketMessages socket 消息
     * @return 带返回值的 socket 结果集
     */
    public static <T> ReturnValueSocketResult<T> of(
            String message,
            int status,
            List<AbstractSocketMessageMetadata<?>> socketMessages
    ) {
        return ReturnValueSocketResult.of(message, status, RestResult.SUCCESS_EXECUTE_CODE, socketMessages);
    }

    /**
     * 创建一个 带返回值的 socket 结果集实体类
     *
     * @param message        响应信息
     * @param status         执行状态
     * @param executeCode    执行代码
     * @param socketMessages socket 消息
     * @return 带返回值的 socket 结果集
     */
    public static <T> ReturnValueSocketResult<T> of(
            String message,
            int status,
            String executeCode,
            List<AbstractSocketMessageMetadata<?>> socketMessages
    ) {
        return ReturnValueSocketResult.of(message, status, executeCode, null, socketMessages);
    }

    /**
     * 创建一个 带返回值的 socket 结果集实体类
     *
     * @param message        响应信息
     * @param status         执行状态
     * @param executeCode    执行代码
     * @param data           响应数据
     * @param socketMessages socket 消息
     * @return 带返回值的 socket 结果集
     */
    public static <T> ReturnValueSocketResult<T> of(
            String message,
            int status,
            String executeCode,
            T data,
            List<AbstractSocketMessageMetadata<?>> socketMessages
    ) {
        return new ReturnValueSocketResult<T>(socketMessages, data, message, executeCode, status);
    }

    public R getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(R returnValue) {
        this.returnValue = returnValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(String executeCode) {
        this.executeCode = executeCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

package io.github.loncra.framework.fasc.utils;


import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.stratey.JsonStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class ResultUtil {

    private static final Logger log = LoggerFactory.getLogger(ResultUtil.class);

    private ResultUtil() {
    }

    /**
     * 打日志
     *
     * @param baseRes      响应base
     * @param jsonStrategy 策率
     *
     * @throws ApiException 异常
     */
    public static void printLog(
            BaseRes baseRes,
            JsonStrategy jsonStrategy
    ) throws ApiException {
        String json = null;

        if (baseRes != null) {
            json = jsonStrategy.toJson(baseRes);
        }

        if (baseRes != null && baseRes.isSuccess()) {
            log.info("请求成功：{}", json);
        }
        else {
            log.info("请求失败：{}", json);
        }
    }

}

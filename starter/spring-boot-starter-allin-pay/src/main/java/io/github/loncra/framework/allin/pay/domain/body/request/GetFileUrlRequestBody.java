package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicRequestMetadata;

import java.io.Serial;

/**
 * 获取文件 URL 请求体
 *
 * @author maurice.chen
 */
public class GetFileUrlRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 712172247419667328L;

    /**
     * 日期（格式：yyyyMMdd）
     */
    private String date;

    /**
     * 随机数
     */
    private String randomNumber;

    /**
     * 构造函数
     */
    public GetFileUrlRequestBody() {
    }

    /**
     * 获取日期
     *
     * @return 日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置日期
     *
     * @param date 日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取随机数
     *
     * @return 随机数
     */
    public String getRandomNumber() {
        return randomNumber;
    }

    /**
     * 设置随机数
     *
     * @param randomNumber 随机数
     */
    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }
}

package io.github.loncra.framework.allin.pay.domain.body.request;

import io.github.loncra.framework.allin.pay.domain.metadata.BasicVersionOrderRequestMetadata;

import java.io.Serial;

/**
 * 结算单请求体
 *
 * @author maurice.chen
 */
public class SettleBillRequestBody extends BasicVersionOrderRequestMetadata {

    @Serial
    private static final long serialVersionUID = -5302866122679253093L;

    /**
     * 日期字符串（格式：yyyyMMdd）
     */
    private String dateStr;

    /**
     * 构造函数
     */
    public SettleBillRequestBody() {
    }

    /**
     * 获取日期字符串
     *
     * @return 日期字符串
     */
    public String getDateStr() {
        return dateStr;
    }

    /**
     * 设置日期字符串
     *
     * @param dateStr 日期字符串
     */
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}

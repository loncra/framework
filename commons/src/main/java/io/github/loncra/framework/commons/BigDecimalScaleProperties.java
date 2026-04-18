package io.github.loncra.framework.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 大小数对象的保留位数配置
 *
 * @author maurice.chen
 */
public class BigDecimalScaleProperties {

    /**
     * 保留小数位数
     */
    private int scale = 2;

    /**
     * 四舍五入规则
     */
    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    /**
     * 构造函数
     */
    public BigDecimalScaleProperties() {
    }

    /**
     * 构造函数
     *
     * @param scale        保留小数位数
     * @param roundingMode 四舍五入规则
     */
    public BigDecimalScaleProperties(
            int scale,
            RoundingMode roundingMode
    ) {
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    /**
     * 获取保留小数位数
     *
     * @return 保留小数位数
     */
    public int getScale() {
        return scale;
    }

    /**
     * 设置保留小数位数
     *
     * @param scale 保留小数位数
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * 获取四舍五入规则
     *
     * @return 四舍五入规则
     */
    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    /**
     * 设置四舍五入规则
     *
     * @param roundingMode 四舍五入规则
     */
    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    /**
     * 创建大小数对象的保留位数配置
     *
     * @param scale        保留小数位数
     * @param roundingMode 四舍五入规则
     *
     * @return BigDecimal 保留位数配置
     */
    public static BigDecimalScaleProperties of(
            int scale,
            RoundingMode roundingMode
    ) {
        return new BigDecimalScaleProperties(scale, roundingMode);
    }

    /**
     * 对 double 值进行精度处理
     *
     * @param value 原始值
     *
     * @return 处理后的值
     */
    public double valueOf(double value) {
        return BigDecimal.valueOf(value).setScale(scale, roundingMode).doubleValue();
    }

    /**
     * 对 long 值进行精度处理
     *
     * @param value 原始值
     *
     * @return 处理后的值
     */
    public long valueOf(long value) {
        return BigDecimal.valueOf(value).setScale(scale, roundingMode).longValue();
    }
}

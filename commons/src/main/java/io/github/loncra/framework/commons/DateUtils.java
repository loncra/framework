package io.github.loncra.framework.commons;

import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author maurice.chen
 */
public abstract class DateUtils {

    /**
     * 默认日期格式化模式
     */
    public static final String DEFAULT_DATE_FORMATTER_PATTERN = "yyyy-MM-dd";

    /**
     * 默认时间格式化模式
     */
    public static final String DEFAULT_TIME_FORMATTER_PATTERN = "HH:mm:ss";

    /**
     * 默认日期时间格式化模式
     */
    public static final String DEFAULT_DATE_TIME_FORMATTER_PATTERN = DEFAULT_DATE_FORMATTER_PATTERN + StringUtils.SPACE + DEFAULT_TIME_FORMATTER_PATTERN;

    /**
     * 将日期对象格式化为日期时间字符串（使用默认格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param date 日期对象
     *
     * @return 格式化后的字符串
     */
    public static String dateTimeFormat(Object date) {
        return dateFormat(date, DEFAULT_DATE_TIME_FORMATTER_PATTERN);
    }

    /**
     * 将日期对象格式化为日期字符串（使用默认格式：yyyy-MM-dd）
     *
     * @param date 日期对象
     *
     * @return 格式化后的字符串
     */
    public static String dateFormat(Object date) {
        return dateFormat(date, DEFAULT_DATE_FORMATTER_PATTERN);
    }

    /**
     * 将日期对象格式化为指定格式的字符串
     *
     * @param date    日期对象（支持 Date、LocalDateTime、Instant、Long、BigDecimal）
     * @param pattern 格式化模式
     *
     * @return 格式化后的字符串
     *
     * @throws SystemException 如果日期类型不支持
     */
    public static String dateFormat(
            Object date,
            String pattern
    ) {
        Assert.notNull(date, "date must not be null");

        if (ChronoLocalDateTime.class.isAssignableFrom(date.getClass())) {
            ChronoLocalDateTime<?> time = CastUtils.cast(date);
            return time.format(DateTimeFormatter.ofPattern(pattern));
        }
        else if (Date.class.isAssignableFrom(date.getClass())) {
            Date d = CastUtils.cast(date);
            return new SimpleDateFormat(pattern).format(d);
        }
        else if (Instant.class.isAssignableFrom(date.getClass())) {
            Instant i = CastUtils.cast(date);
            return i.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
        }
        else if (Long.class.isAssignableFrom(date.getClass())) {
            Long time = CastUtils.cast(date);
            return dateFormat(new Date(time), pattern);
        }
        else if (BigDecimal.class.isAssignableFrom(date.getClass())) {
            BigDecimal time = CastUtils.cast(date);
            return dateFormat(CastUtils.convertValue(time, Instant.class), pattern);
        }
        else {
            throw new SystemException("不支持对象 [" + date.getClass().getName() + "] 的日期类型转换");
        }
    }
}


package io.github.loncra.framework.fasc.utils.string;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 为空
     *
     * @param cs 字符串
     *
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 不为空
     *
     * @param cs 字符串
     *
     * @return 是否不为空
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 为null或者空
     *
     * @param cs 字符串
     *
     * @return 是否为null或空
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
        else {
            return true;
        }
    }

    /**
     * 不为null或者空
     *
     * @param cs 字符串
     *
     * @return 是否不为null或空
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 去除前后空格
     *
     * @param str 字符串
     *
     * @return 字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 去除空格后长度为0就返回null
     *
     * @param str 字符串
     *
     * @return 字符串
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 去除空格，为null返回空字符串
     *
     * @param str 字符串
     *
     * @return 字符串
     */
    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

}

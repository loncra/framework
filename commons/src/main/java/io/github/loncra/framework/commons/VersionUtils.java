package io.github.loncra.framework.commons;

import java.math.BigDecimal;

/**
 * 版本号工具类
 *
 * @author maurice.chen
 */
public abstract class VersionUtils {

    /**
     * 版本号分割正则表达式
     */
    public static final String VERSION_SPLIT_REGEX = "\\.";

    /**
     * 点符号
     */
    public static final String DOT = ".";

    /**
     * 增加版本号的修订号（revision），如果修订号达到上限则进位到次版本号
     *
     * @param version 版本号（格式：major.minor.revision，如 1.0.0）
     *
     * @return 增加后的版本号
     */
    public static String incrementVersionRevision(String version) {
        String[] parts = version.split(VERSION_SPLIT_REGEX);
        int revision = Integer.parseInt(parts[2]) + 1;

        // 修订号进位逻辑, 假设修订号上限为99
        if (revision >= 100) {
            revision = 0;
            int minor = Integer.parseInt(parts[1]) + 1;
            // 次版本号上限为9
            if (minor >= 10) {
                minor = 0;
                int major = Integer.parseInt(parts[0]) + 1;
                return major + DOT + minor + DOT + BigDecimal.ZERO;
            }
            parts[1] = String.valueOf(minor);
        }
        parts[2] = String.valueOf(revision);
        return String.join(DOT, parts);
    }
}


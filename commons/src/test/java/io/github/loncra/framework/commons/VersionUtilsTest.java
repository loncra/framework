package io.github.loncra.framework.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * VersionUtils 测试类
 *
 * @author maurice.chen
 */
public class VersionUtilsTest {

    @Test
    public void testIncrementVersionRevision() {
        // 测试正常增加修订号
        String version = "1.0.0";
        String result = VersionUtils.incrementVersionRevision(version);
        Assertions.assertEquals("1.0.1", result);

        // 测试修订号达到 99
        String version99 = "1.0.99";
        String result99 = VersionUtils.incrementVersionRevision(version99);
        Assertions.assertEquals("1.1.0", result99);

        // 测试修订号达到 100（进位）
        String version100 = "1.0.99";
        String result100 = VersionUtils.incrementVersionRevision(version100);
        Assertions.assertEquals("1.1.0", result100);

        // 测试次版本号达到 9 时的进位
        String versionMinor9 = "1.9.99";
        String resultMinor9 = VersionUtils.incrementVersionRevision(versionMinor9);
        Assertions.assertEquals("2.0.0", resultMinor9);

        // 测试主版本号增加
        String versionMajor = "2.9.99";
        String resultMajor = VersionUtils.incrementVersionRevision(versionMajor);
        Assertions.assertEquals("3.0.0", resultMajor);

        // 测试从 0.0.0 开始
        String versionZero = "0.0.0";
        String resultZero = VersionUtils.incrementVersionRevision(versionZero);
        Assertions.assertEquals("0.0.1", resultZero);

        // 测试较大的修订号
        String versionLarge = "1.0.50";
        String resultLarge = VersionUtils.incrementVersionRevision(versionLarge);
        Assertions.assertEquals("1.0.51", resultLarge);
    }

}


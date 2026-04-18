/**
 * com.yq365.utils.random
 * UUIDGenetrator.java
 */
package io.github.loncra.framework.fasc.utils.random;

import java.util.UUID;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class UUIDGenerator {
    private UUIDGenerator() {
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUuid() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号 
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     *
     * @return String[] UUID数组
     */
    public static String[] getUuid(int number) {
        if (number < 1) {
            return new String[0];
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUuid();
        }
        return ss;
    }

}

package io.github.loncra.framework.crypto;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

/**
 * 加密算法枚举
 * <p>
 * 定义所有支持的加密算法，并管理算法提供者和 Bouncy Castle Provider 的注册
 * </p>
 *
 * @author maurice
 */
public enum CipherAlgorithm implements NameValueEnum<Boolean> {

    /**
     * AES 对称加密算法
     */
    AES("AES", false),

    /**
     * DES 对称加密算法
     */
    DES("DES", false),

    /**
     * RSA 非对称加密算法
     */
    RSA("RSA", false),

    /**
     * SM2 国密非对称加密算法
     */
    SM2("SM2", true),

    /**
     * SM4 国密对称加密算法
     */
    SM4("SM4", true),

    /**
     * SM3 国密哈希算法
     */
    SM3("SM3", true);

    /**
     * 算法名称
     */
    private final String name;

    /**
     * 是否需要 Bouncy Castle Provider
     */
    private final boolean requiresBC;

    /**
     * 加密算法
     *
     * @param name       算法名称
     * @param requiresBC 是否需要 Bouncy Castle Provider
     */
    CipherAlgorithm(String name, boolean requiresBC) {
        this.name = name;
        this.requiresBC = requiresBC;
    }

    /**
     * 获取算法名称
     *
     * @return 算法名称
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean getValue() {
        return requiresBC;
    }

    /**
     * 通过算法名称获取枚举
     *
     * @param algorithmName 算法名称
     * @return 对应的枚举值，如果不存在返回 null
     */
    public static CipherAlgorithm fromName(String algorithmName) {
        if (algorithmName == null) {
            return null;
        }
        for (CipherAlgorithm algorithm : values()) {
            if (algorithm.name.equalsIgnoreCase(algorithmName)) {
                return algorithm;
            }
        }
        return null;
    }

    /**
     * 判断指定算法是否需要 Bouncy Castle Provider
     *
     * @param algorithmName 算法名称
     * @return true 表示需要
     */
    public static boolean requiresBC(String algorithmName) {
        CipherAlgorithm algorithm = fromName(algorithmName);
        return algorithm != null && algorithm.requiresBC;
    }
}

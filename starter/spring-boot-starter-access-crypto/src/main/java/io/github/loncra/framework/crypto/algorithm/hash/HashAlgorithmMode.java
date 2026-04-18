
package io.github.loncra.framework.crypto.algorithm.hash;

/**
 * hash 算法模型
 *
 * @author maurice
 */
public enum HashAlgorithmMode {
    /**
     * MD5 hash
     */
    MD5("MD5"),
    /**
     * SHA-1 hash
     */
    SHA1("SHA-1"),
    /**
     * SHA-256 hash
     */
    SHA256("SHA-256"),
    /**
     * SHA-384 hash
     */
    SHA384("SHA-384"),
    /**
     * SHA-512 hash
     */
    SHA512("SHA-512"),
    /**
     * SM3 hash - 中国国家密码标准（GB/T 32907-2016）
     * <p>
     * SM3 是中国自主设计的密码杂凑算法，适用于商用密码应用。
     * 特点：
     * <ul>
     *     <li>消息分组长度：512 比特</li>
     *     <li>摘要输出长度：256 比特</li>
     *     <li>安全性与 SHA-256 相当</li>
     * </ul>
     * </p>
     */
    SM3("SM3");

    /**
     * hash 算法模型
     *
     * @param name 算法名称
     */
    HashAlgorithmMode(String name) {
        this.name = name;
    }

    /**
     * 算法名称
     */
    private final String name;

    /**
     * 获取算法名称
     *
     * @return 算法名称
     */
    public String getName() {
        return name;
    }
}

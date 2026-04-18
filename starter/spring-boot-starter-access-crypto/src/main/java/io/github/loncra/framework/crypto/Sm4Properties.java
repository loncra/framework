
package io.github.loncra.framework.crypto;

import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;

/**
 * SM4 配置信息（国密对称加密）
 * <p>
 * SM4 是中国国家密码标准（GB/T 32907-2016）规定的分组密码算法。
 * 本配置类用于存储和管理 SM4 加密/解密所需的密钥。
 * </p>
 * <p>
 * SM4 算法特点：
 * <ul>
 *     <li>分组长度：128 比特</li>
 *     <li>密钥长度：128 比特</li>
 *     <li>加密算法与密钥扩展算法均采用 32 轮非线性迭代结构</li>
 * </ul>
 * </p>
 *
 * @author maurice
 */
public class Sm4Properties {

    /**
     * 密钥（Base64 编码或十六进制字符串）
     */
    private String key;

    /**
     * 创建一个 SM4 配置信息
     */
    public Sm4Properties() {
    }

    /**
     * 创建一个 SM4 配置信息
     *
     * @param key 密钥
     */
    public Sm4Properties(String key) {
        this.key = key;
    }

    /**
     * 获取密钥
     *
     * @return 密钥
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置密钥
     *
     * @param key 密钥
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 创建一个 SM4 配置信息
     *
     * @param key 密钥
     *
     * @return SM4 配置信息
     */
    public static Sm4Properties of(String key) {
        return new Sm4Properties(key);
    }

    /**
     * 获取密钥字节原
     *
     * @return 密钥字节原
     */
    public ByteSource getKeyByteSource() {
        return new SimpleByteSource(key);
    }

}

package io.github.loncra.framework.mybatis.plus;

import io.github.loncra.framework.mybatis.plus.annotation.Decryption;
import io.github.loncra.framework.mybatis.plus.annotation.Encryption;

/**
 * 加解密 null 类实现，用于在 {@link Decryption}
 * 和 {@link Encryption} 中 default 编译不报错使用
 *
 * @author maurice.chen
 */
public class CryptoNullClass implements DecryptService, EncryptService {

    /**
     * 解密方法（不支持操作）
     * <p>此方法用于在注解的 default 值中使用，实际调用时会抛出异常</p>
     *
     * @param cipherText 密文
     *
     * @return 永远不会返回，总是抛出异常
     *
     * @throws UnsupportedOperationException 总是抛出此异常，因为这是一个空对象实现
     */
    @Override
    public String decrypt(String cipherText) {
        throw new UnsupportedOperationException("CryptoNullClass 为空对象在注解使用，不支持解密操作");
    }

    /**
     * 加密方法（不支持操作）
     * <p>此方法用于在注解的 default 值中使用，实际调用时会抛出异常</p>
     *
     * @param plainText 明文
     *
     * @return 永远不会返回，总是抛出异常
     *
     * @throws UnsupportedOperationException 总是抛出此异常，因为这是一个空对象实现
     */
    @Override
    public String encrypt(String plainText) {
        throw new UnsupportedOperationException("CryptoNullClass 为空对象在注解使用，不支持加密操作");
    }
}

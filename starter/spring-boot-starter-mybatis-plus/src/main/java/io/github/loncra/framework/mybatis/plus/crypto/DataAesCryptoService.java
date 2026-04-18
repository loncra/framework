package io.github.loncra.framework.mybatis.plus.crypto;

import io.github.loncra.framework.crypto.CipherAlgorithmService;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.CodecUtils;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;
import io.github.loncra.framework.mybatis.plus.DecryptService;
import io.github.loncra.framework.mybatis.plus.EncryptService;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * aes 的数据加解密服务实现
 *
 * @author maurice.chen
 */
public class DataAesCryptoService implements DecryptService, EncryptService {

    /**
     * AES 加密服务
     */
    private final AesCipherService aesCipherService;

    /**
     * 加密密钥（Base64 编码）
     */
    private final String key;

    /**
     * 构造函数
     *
     * @param aesCipherService AES 加密服务
     * @param key              加密密钥（Base64 编码）
     */
    public DataAesCryptoService(
            AesCipherService aesCipherService,
            String key
    ) {
        this.aesCipherService = aesCipherService;
        this.key = key;
    }

    /**
     * 生成 16 字节的密钥
     * <p>通过将输入密钥的字节进行循环异或操作，生成固定长度的 16 字节密钥</p>
     *
     * @param key 原始密钥
     *
     * @return 16 字节的密钥
     */
    public static ByteSource generate16ByteKey(String key) {
        byte[] finalKey = new byte[16];
        int i = 0;

        for (byte b : key.getBytes(StandardCharsets.UTF_8)) {
            finalKey[i++ % 16] ^= b;
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(finalKey, CipherAlgorithmService.AES_ALGORITHM);

        return new SimpleByteSource(secretKeySpec.getEncoded());
    }

    /**
     * 解密数据
     *
     * @param cipherText Base64 编码的密文
     *
     * @return 解密后的明文
     */
    @Override
    public String decrypt(String cipherText) {
        ByteSource byteSource = aesCipherService.decrypt(Base64.decode(cipherText), Base64.decode(key));
        return byteSource.obtainString();
    }

    /**
     * 加密数据
     *
     * @param plainText 明文
     *
     * @return Base64 编码的密文
     */
    @Override
    public String encrypt(String plainText) {
        ByteSource byteSource = aesCipherService.encrypt(CodecUtils.toBytes(plainText), Base64.decode(key));
        return byteSource.getBase64();
    }
}

package io.github.loncra.framework.mybatis.plus.crypto;

import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.CodecUtils;
import io.github.loncra.framework.crypto.algorithm.cipher.RsaCipherService;
import io.github.loncra.framework.mybatis.plus.DecryptService;
import io.github.loncra.framework.mybatis.plus.EncryptService;

/**
 * RSA 数据加解密服务实现
 * <p>使用 RSA 算法对数据进行加密和解密操作</p>
 *
 * @author maurice.chen
 */
public class DataRsaCryptoService implements DecryptService, EncryptService {

    /**
     * RSA 加密服务
     */
    private final RsaCipherService rsaCipherService;

    /**
     * 公钥（Base64 编码）
     */
    private final String publicKey;

    /**
     * 私钥（Base64 编码）
     */
    private final String privateKey;

    /**
     * 构造函数
     *
     * @param rsaCipherService RSA 加密服务
     * @param publicKey        公钥（Base64 编码）
     * @param privateKey       私钥（Base64 编码）
     */
    public DataRsaCryptoService(
            RsaCipherService rsaCipherService,
            String publicKey,
            String privateKey
    ) {
        this.rsaCipherService = rsaCipherService;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
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
        ByteSource byteSource = rsaCipherService.decrypt(Base64.decode(cipherText), Base64.decode(privateKey));
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
        ByteSource byteSource = rsaCipherService.encrypt(CodecUtils.toBytes(plainText), Base64.decode(publicKey));
        return byteSource.getBase64();
    }
}

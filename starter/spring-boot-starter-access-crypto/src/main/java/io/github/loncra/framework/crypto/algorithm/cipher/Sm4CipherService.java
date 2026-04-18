
package io.github.loncra.framework.crypto.algorithm.cipher;

import io.github.loncra.framework.crypto.CipherAlgorithmService;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.exception.CryptoException;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

/**
 * SM4 对称加密实现，中国国家密码标准（GB/T 32907-2016）
 * <p>
 * SM4 是中国自主设计的无线局域网标准的分组密码算法，现已成为国家密码标准。
 * 特点：
 * <ul>
 *     <li>分组长度：128 比特</li>
 *     <li>密钥长度：128 比特</li>
 *     <li>加密算法与密钥扩展算法均采用 32 轮非线性迭代结构</li>
 *     <li>安全性高，性能优异，适合硬件和软件实现</li>
 * </ul>
 * </p>
 *
 * @author maurice
 */
public class Sm4CipherService extends SymmetricCipherService {

    /**
     * SM4 密钥长度固定为 128 位
     */
    private static final int DEFAULT_KEY_SIZE = 128;

    /**
     * SM4 分组长度固定为 128 位
     */
    private static final int DEFAULT_INITIALIZATION_VECTOR_SIZE = 128;

    /**
     * SM4 对称加密实现
     */
    public Sm4CipherService() {
        super(CipherAlgorithmService.SM4_ALGORITHM);

        setKeySize(DEFAULT_KEY_SIZE);
        setInitializationVectorSize(DEFAULT_INITIALIZATION_VECTOR_SIZE);

        // 默认使用 CBC 模式，PKCS5 填充
        setMode(OperationMode.CBC);
        setPaddingScheme(PaddingScheme.PKCS5);

        // 流加密配置
        setStreamingMode(OperationMode.CBC);
        setStreamingPaddingScheme(PaddingScheme.PKCS5);
    }

    @Override
    public ByteSource encrypt(
            byte[] plaintext,
            byte[] key,
            byte[] iv
    ) {
        return super.encrypt(plaintext, key, iv);
    }

    @Override
    public void encrypt(
            InputStream in,
            OutputStream out,
            byte[] key,
            byte[] iv
    ) throws CryptoException {
        super.encrypt(in, out, key, iv);
    }

    @Override
    public ByteSource decrypt(
            byte[] cipherText,
            byte[] key,
            byte[] iv
    ) {
        return super.decrypt(cipherText, key, iv);
    }

    @Override
    public void decrypt(
            InputStream in,
            OutputStream out,
            byte[] key,
            byte[] iv
    ) throws CryptoException {
        super.decrypt(in, out, key, iv);
    }

    @Override
    public Key generateKey() {
        return super.generateKey();
    }
}

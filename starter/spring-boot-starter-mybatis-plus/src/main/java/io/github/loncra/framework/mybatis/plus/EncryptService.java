package io.github.loncra.framework.mybatis.plus;

/**
 * 加密服务，用于 insert 或者 update 时加密使用
 *
 * @author maurice.chen
 */
public interface EncryptService extends CryptoService {

    /**
     * 加密数据
     *
     * @param plainText 明文
     *
     * @return 密文
     */
    String encrypt(String plainText);
}

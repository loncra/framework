package io.github.loncra.framework.mybatis.plus;

/**
 * 解密服务接口，用于 select 时是否解密使用
 *
 * @author maurice.chen
 */
public interface DecryptService extends CryptoService {

    /**
     * 解密
     *
     * @param cipherText 密文
     *
     * @return 明文
     */
    String decrypt(String cipherText);
}

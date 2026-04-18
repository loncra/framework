
package io.github.loncra.framework.crypto.algorithm.test.cipher;

import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.CodecUtils;
import io.github.loncra.framework.crypto.algorithm.cipher.Sm4CipherService;
import io.github.loncra.framework.crypto.algorithm.test.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

/**
 * SM4 对称加密单元测试
 *
 * @author maurice
 */
public class Sm4CipherServiceTest {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void testEncryptAndDecrypt() {
        Sm4CipherService cipherService = new Sm4CipherService();

        // 生成密钥
        Key key = cipherService.generateKey();

        // 加密
        ByteSource encrypted = cipherService.encrypt(TestData.TEXT.getBytes(), key.getEncoded());
        
        // 解密
        ByteSource decrypted = cipherService.decrypt(encrypted.obtainBytes(), key.getEncoded());

        // 验证解密后的内容与原文一致
        Assert.assertArrayEquals(TestData.TEXT.getBytes(), decrypted.obtainBytes());
        
        System.out.println("SM4 加密测试通过");
        System.out.println("原文：" + TestData.TEXT);
        System.out.println("密文 (Hex): " + encrypted.getHex());
        System.out.println("密文 (Base64): " + encrypted.getBase64());
    }

    @Test
    public void testStreamEncryptAndDecrypt() throws IOException {
        Sm4CipherService cipherService = new Sm4CipherService();
        Key key = cipherService.generateKey();

        // 流加密
        ByteArrayOutputStream encryptOut = new ByteArrayOutputStream();
        InputStream encryptIn = resourceLoader.getResource("classpath:/data.test").getInputStream();
        cipherService.encrypt(encryptIn, encryptOut, key.getEncoded());

        // 流解密
        InputStream decryptIn = new ByteArrayInputStream(encryptOut.toByteArray());
        ByteArrayOutputStream decryptOut = new ByteArrayOutputStream();
        cipherService.decrypt(decryptIn, decryptOut, key.getEncoded());

        // 验证解密后的内容与原文一致
        byte[] originalText = CodecUtils.toBytes(
                resourceLoader.getResource("classpath:/data.test").getInputStream()
        );
        Assert.assertArrayEquals(originalText, decryptOut.toByteArray());
        
        System.out.println("SM4 流加密测试通过");
    }
}

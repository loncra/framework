
package io.github.loncra.framework.crypto.algorithm.test.cipher;

import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.Sm2CipherService;
import io.github.loncra.framework.crypto.algorithm.test.TestData;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * SM2 非对称加密单元测试
 *
 * @author maurice
 */
public class Sm2CipherServiceTest {

    @Test
    public void testEncryptAndDecryptWithEngine() {
        Sm2CipherService cipherService = new Sm2CipherService();

        // 生成密钥对
        KeyPair keyPair = cipherService.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 获取密钥字节（SM2 专用格式）
        byte[] publicKeyBytes = cipherService.getPublicKeyBytes(publicKey);
        byte[] privateKeyBytes = cipherService.getPrivateKeyBytes(privateKey);

        // 使用 SM2 引擎加密
        byte[] encrypted = cipherService.encryptWithEngine(TestData.TEXT.getBytes(), publicKeyBytes);

        // 使用 SM2 引擎解密
        byte[] decrypted = cipherService.decryptWithEngine(encrypted, privateKeyBytes);

        // 验证解密后的内容与原文一致
        Assert.assertArrayEquals(TestData.TEXT.getBytes(), decrypted);

        System.out.println("SM2 引擎加密测试通过");
        System.out.println("原文：" + TestData.TEXT);
        System.out.println("公钥 (Hex): " + new SimpleByteSource(publicKeyBytes).getHex());
        System.out.println("密文 (Hex): " + new SimpleByteSource(encrypted).getHex());
        System.out.println("密文 (Base64): " + new SimpleByteSource(encrypted).getBase64());
    }

    @Test
    public void testSignAndVerify() {
        Sm2CipherService cipherService = new Sm2CipherService();

        // 生成密钥对
        KeyPair keyPair = cipherService.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 获取私钥字节（SM2 签名需要使用原始私钥字节）
        byte[] privateKeyBytes = cipherService.getPrivateKeyBytes(privateKey);

        // 签名（使用私钥）
        ByteSource sign = cipherService.sign(TestData.TEXT.getBytes(), privateKeyBytes);

        // 获取公钥字节
        byte[] publicKeyBytes = cipherService.getPublicKeyBytes(publicKey);

        // 验证签名（使用公钥）
        boolean verified = cipherService.verify(TestData.TEXT.getBytes(), publicKeyBytes, sign.obtainBytes());

        // 验证签名成功
        Assert.assertTrue("签名验证应该成功", verified);

        System.out.println("SM2 签名测试通过");
        System.out.println("签名 (Hex): " + sign.getHex());
        System.out.println("签名 (Base64): " + sign.getBase64());
    }

    @Test
    public void testSignAndVerifyWithWrongData() {
        Sm2CipherService cipherService = new Sm2CipherService();

        // 生成密钥对
        KeyPair keyPair = cipherService.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 获取密钥字节
        byte[] privateKeyBytes = cipherService.getPrivateKeyBytes(privateKey);
        byte[] publicKeyBytes = cipherService.getPublicKeyBytes(publicKey);

        // 签名（使用私钥）
        ByteSource sign = cipherService.sign(TestData.TEXT.getBytes(), privateKeyBytes);

        // 使用错误的数据验证签名（应该失败）
        boolean verified = cipherService.verify("错误的数据".getBytes(), publicKeyBytes, sign.obtainBytes());

        // 验证签名失败
        Assert.assertFalse("签名验证应该失败", verified);

        System.out.println("SM2 错误数据签名验证测试通过");
    }
}

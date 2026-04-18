
package io.github.loncra.framework.crypto.algorithm.test.hash;

import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.crypto.algorithm.hash.Hash;
import io.github.loncra.framework.crypto.algorithm.hash.HashAlgorithmMode;
import io.github.loncra.framework.crypto.algorithm.hash.HashRequest;
import io.github.loncra.framework.crypto.algorithm.hash.HashService;
import io.github.loncra.framework.crypto.algorithm.test.TestData;
import org.junit.Assert;
import org.junit.Test;

/**
 * SM3 哈希算法单元测试
 *
 * @author maurice
 */
public class Sm3HashTest {

    @Test
    public void testSm3Hash() {
        // 创建 SM3 哈希
        Hash hash = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT);

        // SM3 输出 256 位 (32 字节) 哈希值，16 进制表示为 64 个字符
        Assert.assertEquals(64, hash.getHex().length());
        
        System.out.println("SM3 哈希测试通过");
        System.out.println("原文：" + TestData.TEXT);
        System.out.println("SM3 (Hex): " + hash.getHex());
        System.out.println("SM3 (Base64): " + hash.getBase64());
    }

    @Test
    public void testSm3HashWithSalt() {
        ByteSource salt = new SimpleByteSource(TestData.SALT);
        
        // 创建带盐的 SM3 哈希
        Hash hash = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT, salt.obtainBytes(), 1);

        // SM3 输出 256 位 (32 字节) 哈希值
        Assert.assertEquals(64, hash.getHex().length());
        
        System.out.println("SM3 加盐哈希测试通过");
        System.out.println("盐：" + TestData.SALT);
        System.out.println("SM3 (Hex): " + hash.getHex());
    }

    @Test
    public void testSm3HashWithIterations() {
        int iterations = 3;
        
        // 创建多次迭代的 SM3 哈希
        Hash hash = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT, null, iterations);

        Assert.assertEquals(64, hash.getHex().length());
        Assert.assertEquals(iterations, hash.getIterations());
        
        System.out.println("SM3 多次迭代哈希测试通过");
        System.out.println("迭代次数：" + iterations);
        System.out.println("SM3 (Hex): " + hash.getHex());
    }

    @Test
    public void testSm3HashService() {
        HashService hashService = new HashService();
        hashService.setAlgorithmMode(HashAlgorithmMode.SM3);
        hashService.setPrivateSalt(new SimpleByteSource(TestData.SALT));
        hashService.setIterations(3);

        HashRequest request = new HashRequest(
                new SimpleByteSource(TestData.TEXT),
                null,
                3,
                HashAlgorithmMode.SM3.getName()
        );

        Hash hash = hashService.computeHash(request);

        Assert.assertNotNull(hash);
        Assert.assertEquals(64, hash.getHex().length());
        
        System.out.println("SM3 HashService 测试通过");
        System.out.println("SM3 (Hex): " + hash.getHex());
    }

    @Test
    public void testSm3Consistency() {
        // 测试相同输入产生相同输出
        Hash hash1 = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT);
        Hash hash2 = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT);

        Assert.assertEquals(hash1, hash2);
        Assert.assertEquals(hash1.getHex(), hash2.getHex());
        
        System.out.println("SM3 一致性测试通过");
    }

    @Test
    public void testSm3DifferentInput() {
        // 测试不同输入产生不同输出
        Hash hash1 = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT);
        Hash hash2 = new Hash(HashAlgorithmMode.SM3.getName(), TestData.TEXT + "额外内容");

        Assert.assertNotEquals(hash1, hash2);
        Assert.assertNotEquals(hash1.getHex(), hash2.getHex());
        
        System.out.println("SM3 差异性测试通过");
    }
}

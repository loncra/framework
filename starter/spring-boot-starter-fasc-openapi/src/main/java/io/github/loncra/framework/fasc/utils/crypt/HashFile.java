/**
 * 包名：com.yq365.utils.crypt
 * 文件名：com.yq365.utils.crypt
 * 创建者：zyb
 * 创建日：2015-3-9
 * <p>
 * CopyRight 2015 ShenZhen Fabigbig Technology Co.Ltd All Rights Reserved
 */
package io.github.loncra.framework.fasc.utils.crypt;

import io.github.loncra.framework.fasc.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class HashFile {
    private static Logger log = LoggerFactory.getLogger(HashFile.class);
    private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private HashFile() {
    }

    /**
     * @param filename 文件名称
     *
     * @return 文件名称md值
     *
     * @throws ApiException 异常
     */
    public static String getFileMd5(String filename) throws ApiException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            return getHash(fis, "MD5");
        }
        catch (Exception e) {
            log.error("文件名MD5失败：{}", e.getMessage(), e);
            throw new ApiException("文件名MD5失败");
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    log.error("文件流关闭异常 {}", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * @param file 对象
     *
     * @return 文件名称md值
     *
     * @throws ApiException 异常
     */
    public static String getFileMd5(File file) throws ApiException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return getHash(fis, "MD5");
        }
        catch (Exception e) {
            log.error("文件MD5失败：{}", e.getMessage(), e);
            throw new ApiException("文件MD5失败");
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    log.error("文件流关闭异常 {}", e.getMessage(), e);
                }
            }
        }
    }

    public static String getFileMd5(FileInputStream fis) throws ApiException {
        return getHash(fis, "MD5");
    }

    public static String getFileMd5(byte[] dataBytes) throws ApiException {
        return getHash(dataBytes, "MD5");
    }

    public static String getFileSha256(String filename) throws ApiException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            return getHash(fis, "SHA-256");
        }
        catch (Exception e) {
            log.error("文件名称SHA256失败：{}", e.getMessage(), e);
            throw new ApiException("文件名称SHA256失败");
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    log.error("文件流关闭异常 {}", e.getMessage(), e);
                }
            }
        }
    }

    public static String getFileSha256(File file) throws ApiException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return getHash(fis, "SHA-256");
        }
        catch (Exception e) {
            log.error("文件SHA256失败：{}", e.getMessage(), e);
            throw new ApiException("文件SHA256失败");
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    log.error("文件流关闭异常 {}", e.getMessage(), e);
                }
            }
        }
    }

    public static String getFileSha256(FileInputStream fis) throws ApiException {
        return getHash(fis, "SHA-256");
    }

    public static String getFileSha256(byte[] dataBytes) throws ApiException {
        return getHash(dataBytes, "SHA-256");
    }

    private static String getHash(
            InputStream inputStream,
            String hashType
    ) throws ApiException {
        MessageDigest messageDigest = null;
        try {
            byte[] buffer = new byte[1024];
            messageDigest = MessageDigest.getInstance(hashType);
            int numRead;
            while ((numRead = inputStream.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, numRead);
            }
            return toHexString(messageDigest.digest());
        }
        catch (Exception e) {
            log.error("输入流获取Hash值失败：{}", e.getMessage(), e);
            throw new ApiException("输入流获取Hash值失败");
        }
    }

    private static String getHash(
            byte[] dataBytes,
            String hashType
    ) throws ApiException {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(hashType);
            return toHexString(messageDigest.digest(dataBytes));
        }
        catch (Exception e) {
            log.error("字节数据获取Hash值失败：{}", e.getMessage(), e);
            throw new ApiException("字节数据获取Hash值失败");
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
            sb.append(hexChar[(b[i] & 0xF)]);
        }
        return sb.toString();
    }
}
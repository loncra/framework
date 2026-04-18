package io.github.loncra.framework.fasc.utils.file;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class ZipUtil {
    private ZipUtil() {
    }

    /**
     * 解压压缩文件 (返回文件名 对应bytes)(GBK编码类型)
     *
     * @param bytes 字节数组
     *
     * @return 文件名称对应字节
     *
     * @throws Exception 异常
     */
    public static Map<String, byte[]> unZipByGbk(byte[] bytes) throws Exception {
        Map<String, byte[]> fileNameByteMap = new HashMap<>();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(bytes), Charset.forName("GBK"));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                break;
            }
            else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                String fileName = zipEntry.getName();
                byte[] bufferBytes = new byte[1024];
                int len;
                while ((len = zipInputStream.read(bufferBytes, 0, bufferBytes.length)) != -1) {
                    bos.write(bufferBytes, 0, len);
                }
                bos.flush();
                bos.close();
                zipInputStream.closeEntry();
                fileNameByteMap.put(fileName, bos.toByteArray());
                zipEntry = zipInputStream.getNextEntry();
            }
        }
        zipInputStream.close();
        return fileNameByteMap;
    }


    /**
     * 解压到指定目录(GBK编码类型)
     *
     * @param bytes    字节数组
     * @param filePath 文件绝对路径
     *
     * @throws Exception 异常
     */
    public static void unZipByGbk(
            byte[] bytes,
            String filePath
    ) throws Exception {
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(bytes), Charset.forName("GBK"));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                continue;
            }
            else {
                String unZipFilePath = filePath + File.separator + zipEntry.getName();
                try (
                        BufferedOutputStream bufferedOutputStream =
                                new BufferedOutputStream(new FileOutputStream(unZipFilePath));
                ) {
                    byte[] bufferBytes = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(bufferBytes, 0, bufferBytes.length)) != -1) {
                        bufferedOutputStream.write(bufferBytes, 0, len);
                    }
                }
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }


}

package io.github.loncra.framework.fasc.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class FileUtil {
    private FileUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 文件写入
     *
     * @param fileBytes 文件字节数组
     * @param path      文件绝对路径
     * @param fileName  文件名称
     */
    public static void fileSink(
            byte[] fileBytes,
            String path,
            String fileName
    ) {
        File f = new File(path + fileName);
        if (f.exists()) {
            log.warn("文件已存在:{}", f.getAbsolutePath());
            return;
        }
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        BufferedOutputStream bw = null;
        try {
            fos = new FileOutputStream(f);
            bw = new BufferedOutputStream(fos);
            bw.write(fileBytes);
            bw.flush();
        }
        catch (Exception e) {
            log.error("文件写入失败：{}", e.getMessage(), e);
        }
        finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ex) {
                log.error("文件流关闭异常：{}", ex.getMessage(), ex);
            }
        }
    }
}

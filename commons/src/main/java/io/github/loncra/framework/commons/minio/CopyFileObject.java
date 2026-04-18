package io.github.loncra.framework.commons.minio;

import java.io.Serial;
import java.io.Serializable;

/**
 * 复制文件对象
 * <p>用于描述文件复制操作的源文件和目标文件信息</p>
 *
 * @author maurice.chen
 */
public class CopyFileObject implements Serializable {

    @Serial
    private static final long serialVersionUID = -8412856701078392621L;

    /**
     * 原文件对象
     */
    private FileObject source;

    /**
     * 目标文件对象
     */
    private FileObject target;

    /**
     * 构造函数
     */
    public CopyFileObject() {
    }

    /**
     * 构造函数
     *
     * @param source 源文件对象
     * @param target 目标文件对象
     */
    public CopyFileObject(
            FileObject source,
            FileObject target
    ) {
        this.source = source;
        this.target = target;
    }

    /**
     * 获取源文件对象
     *
     * @return 源文件对象
     */
    public FileObject getSource() {
        return source;
    }

    /**
     * 设置源文件对象
     *
     * @param source 源文件对象
     */
    public void setSource(FileObject source) {
        this.source = source;
    }

    /**
     * 获取目标文件对象
     *
     * @return 目标文件对象
     */
    public FileObject getTarget() {
        return target;
    }

    /**
     * 设置目标文件对象
     *
     * @param target 目标文件对象
     */
    public void setTarget(FileObject target) {
        this.target = target;
    }
}

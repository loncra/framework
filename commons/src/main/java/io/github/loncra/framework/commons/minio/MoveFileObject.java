package io.github.loncra.framework.commons.minio;

import java.io.Serial;

/**
 * 移动文件对象，用于拷贝或剪切文件对象使用
 *
 * @author maurice.chen
 */
public class MoveFileObject extends CopyFileObject {

    @Serial
    private static final long serialVersionUID = -8637215027233473463L;

    /**
     * 删除源文件后是否删除空桶
     */
    private boolean deleteBucketIfEmpty;

    /**
     * 构造函数
     */
    public MoveFileObject() {
    }

    /**
     * 构造函数
     *
     * @param source 源文件对象
     * @param target 目标文件对象
     */
    public MoveFileObject(
            FileObject source,
            FileObject target
    ) {
        this(source, target, false);
    }

    /**
     * 构造函数
     *
     * @param source              源文件对象
     * @param target              目标文件对象
     * @param deleteBucketIfEmpty 删除源文件后是否删除空桶
     */
    public MoveFileObject(
            FileObject source,
            FileObject target,
            boolean deleteBucketIfEmpty
    ) {
        super(source, target);
        this.deleteBucketIfEmpty = deleteBucketIfEmpty;
    }

    /**
     * 获取删除源文件后是否删除空桶
     *
     * @return true 删除空桶，false 不删除
     */
    public boolean isDeleteBucketIfEmpty() {
        return deleteBucketIfEmpty;
    }

    /**
     * 设置删除源文件后是否删除空桶
     *
     * @param deleteBucketIfEmpty 删除源文件后是否删除空桶
     */
    public void setDeleteBucketIfEmpty(boolean deleteBucketIfEmpty) {
        this.deleteBucketIfEmpty = deleteBucketIfEmpty;
    }
}

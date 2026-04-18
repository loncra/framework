package io.github.loncra.framework.commons.minio;

import org.springframework.util.DigestUtils;

import java.io.Serial;

/**
 * 对象写入成功后的结果集
 *
 * @author maurice.chen
 */
public class ObjectWriteResult extends FileObject {

    @Serial
    private static final long serialVersionUID = -2136072754715740876L;

    /**
     * minio e 标签名称
     */
    public static final String MINIO_ETAG = "etag";

    /**
     * minio e 标签引号标记
     */
    public static final String MINIO_ETAG_QUOTATION_MARKS = "\"";

    /**
     * minio e 标签
     */
    private String etag;

    /**
     * 构造函数
     */
    public ObjectWriteResult() {
    }

    /**
     * 构造函数
     *
     * @param etag e 标签
     */
    public ObjectWriteResult(String etag) {
        this.etag = etag;
    }

    /**
     * 构造函数
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param etag       e 标签
     */
    public ObjectWriteResult(
            String bucketName,
            String objectName,
            String etag
    ) {
        super(bucketName, objectName);
        this.etag = etag;
    }

    /**
     * 构造函数
     *
     * @param bucket     桶描述
     * @param objectName 对象名称
     * @param etag       e 标签
     */
    public ObjectWriteResult(
            Bucket bucket,
            String objectName,
            String etag
    ) {
        super(bucket, objectName);
        this.etag = etag;
    }

    /**
     * 构造函数
     *
     * @param bucketName 桶名称
     * @param region     桶所属区域
     * @param objectName 对象名称
     * @param etag       e 标签
     */
    public ObjectWriteResult(
            String bucketName,
            String region,
            String objectName,
            String etag
    ) {
        super(bucketName, region, objectName);
        this.etag = etag;
    }

    /**
     * 构造函数
     *
     * @param fileObject 文件对象描述
     * @param etag       e 标签
     */
    public ObjectWriteResult(
            FileObject fileObject,
            String etag
    ) {
        this(fileObject.getBucketName(), fileObject.getRegion(), fileObject.getObjectName(), etag);
    }

    /**
     * 创建对象写入结果
     *
     * @param fileObject 文件对象描述
     * @param etag       e 标签
     *
     * @return 对象写入结果
     */
    public static ObjectWriteResult of(
            FileObject fileObject,
            String etag
    ) {
        return new ObjectWriteResult(fileObject, etag);
    }

    /**
     * 获取 e 标签
     *
     * @return e 标签
     */
    public String getEtag() {
        return etag;
    }

    /**
     * 设置 e 标签
     *
     * @param etag e 标签
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * 获取对象 ID
     * <p>通过桶名称和对象名称的 MD5 值生成</p>
     *
     * @return 对象 ID
     */
    public String getId() {
        return DigestUtils.md5DigestAsHex((getBucketName() + getObjectName()).getBytes());
    }
}

package io.github.loncra.framework.minio;

import io.github.loncra.framework.commons.minio.ObjectWriteResult;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * 对象项类，用于在获取的内容 {@link Item} 时，能够序列化成 json 使用
 *
 * @author maurice.chen
 */
public class ObjectItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 5808561181950704489L;

    /**
     * MinIO 对象项
     */
    private final Item item;

    /**
     * 构造函数
     *
     * @param item MinIO 对象项
     */
    public ObjectItem(Item item) {
        this.item = item;
    }

    /**
     * 获取对象名称
     *
     * @return 对象名称
     */
    public String getObjectName() {
        return item.objectName();
    }

    /**
     * 获取 ETag（去除引号）
     *
     * @return ETag 值
     */
    public String getEtag() {
        String etag = item.etag();
        if (Strings.CS.startsWith(etag, ObjectWriteResult.MINIO_ETAG_QUOTATION_MARKS) && (Strings.CS.endsWith(etag, ObjectWriteResult.MINIO_ETAG_QUOTATION_MARKS))) {
            etag = StringUtils.unwrap(etag, ObjectWriteResult.MINIO_ETAG_QUOTATION_MARKS);
        }
        return etag;
    }

    /**
     * 获取最后修改时间
     *
     * @return 最后修改时间，转换失败时返回 null
     */
    public LocalDateTime getLastModified() {
        try {
            return item.lastModified().toLocalDateTime();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取对象大小
     *
     * @return 对象大小（字节）
     */
    public long getSize() {
        return item.size();
    }

    /**
     * 获取用户元数据
     *
     * @return 用户元数据映射
     */
    public Map<String, String> getUserMetadata() {
        return item.userMetadata();
    }

    /**
     * 获取所有者 ID
     *
     * @return 所有者 ID，如果不存在则返回 null
     */
    public String getOwnerId() {
        if (Objects.isNull(item.owner())) {
            return null;
        }
        return item.owner().id();
    }

    /**
     * 获取所有者名称
     *
     * @return 所有者显示名称，如果不存在则返回 null
     */
    public String getOwnerName() {
        if (Objects.isNull(item.owner())) {
            return null;
        }
        return item.owner().displayName();
    }

    /**
     * 获取存储类
     *
     * @return 存储类
     */
    public String getStorageClass() {
        return item.storageClass();
    }

    /**
     * 判断是否为最新版本
     *
     * @return true 表示是最新版本，否则 false
     */
    public boolean isLatest() {
        return item.isLatest();
    }

    /**
     * 获取版本 ID
     *
     * @return 版本 ID
     */
    public String getVersionId() {
        return item.versionId();
    }

    /**
     * 获取用户标签
     *
     * @return 用户标签
     */
    public String getUserTags() {
        return item.userTags();
    }

    /**
     * 判断是否为目录
     *
     * @return true 表示是目录，否则 false
     */
    public boolean isDir() {
        return item.isDir();
    }
}

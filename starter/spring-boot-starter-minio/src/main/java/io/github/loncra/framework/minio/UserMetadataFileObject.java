package io.github.loncra.framework.minio;

import io.github.loncra.framework.commons.minio.FileObject;
import io.github.loncra.framework.commons.minio.FilenameObject;
import org.apache.commons.collections4.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 带用户元数据的文件对象
 *
 * @author maurice.chen
 */
public class UserMetadataFileObject extends FileObject {

    /**
     * 用户元数据
     */
    private Map<String, String> userMetadata = new LinkedHashMap<>();

    /**
     * 构造函数
     *
     * @param fileObject   文件对象
     * @param userMetadata 用户元数据
     */
    public UserMetadataFileObject(
            FileObject fileObject,
            Map<String, String> userMetadata
    ) {
        this.setObjectName(fileObject.getObjectName());
        this.setBucketName(fileObject.getBucketName());

        if (MapUtils.isNotEmpty(fileObject.getExtraHeaders())) {
            userMetadata.putAll(fileObject.getExtraHeaders());
        }
        if (MapUtils.isNotEmpty(fileObject.getExtraQueryParams())) {
            userMetadata.putAll(fileObject.getExtraQueryParams());
        }

        setUserMetadata(userMetadata);
    }

    /**
     * 构造函数（从文件名对象创建）
     *
     * @param fileObject 文件名对象
     */
    public UserMetadataFileObject(FilenameObject fileObject) {
        this.setObjectName(fileObject.getObjectName());
        this.setBucketName(fileObject.getBucketName());

        Map<String, String> userMetadata = new LinkedHashMap<>();

        userMetadata.put(FilenameObject.MINIO_ORIGINAL_FILE_NAME, fileObject.getFilename());
        if (MapUtils.isNotEmpty(fileObject.getExtraHeaders())) {
            userMetadata.putAll(fileObject.getExtraHeaders());
        }
        if (MapUtils.isNotEmpty(fileObject.getExtraQueryParams())) {
            userMetadata.putAll(fileObject.getExtraQueryParams());
        }

        setUserMetadata(userMetadata);
    }

    public Map<String, String> getUserMetadata() {
        return userMetadata;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }
}

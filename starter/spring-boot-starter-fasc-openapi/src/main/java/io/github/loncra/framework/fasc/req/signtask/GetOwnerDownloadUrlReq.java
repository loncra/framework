package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class GetOwnerDownloadUrlReq extends SignTaskBaseReq {
    private OpenId ownerId;
    private String id;
    private String fileType;
    private String customName;
    private Boolean compression;

    public Boolean getCompression() {
        return compression;
    }

    public void setCompression(Boolean compression) {
        this.compression = compression;
    }

    public OpenId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OpenId ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
}

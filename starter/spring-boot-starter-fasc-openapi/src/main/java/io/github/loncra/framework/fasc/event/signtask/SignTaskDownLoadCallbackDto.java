package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务下载事件
 */
public class SignTaskDownLoadCallbackDto {

    private String eventTime;

    private String downloadId;

    private String downloadUrl;

    private String status;


    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

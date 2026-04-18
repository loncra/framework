package io.github.loncra.framework.fasc.req.signtask;

/**
 * @author Fadada
 * 2021/9/11 16:10:08
 */
public class DownloadFilesReq extends SignTaskBaseReq {
    private Integer id;
    private String fileType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

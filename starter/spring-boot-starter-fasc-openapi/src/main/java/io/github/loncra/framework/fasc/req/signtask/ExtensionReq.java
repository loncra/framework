package io.github.loncra.framework.fasc.req.signtask;

/**
 * @author Fadada
 * @date 2023年7月26日 15:32:18
 */
public class ExtensionReq extends SignTaskBaseReq {

    private String extensionTime;

    public String getExtensionTime() {
        return extensionTime;
    }

    public void setExtensionTime(String extensionTime) {
        this.extensionTime = extensionTime;
    }
}
package io.github.loncra.framework.commons.domain.metadata;

import java.io.Serial;
import java.io.Serializable;

/**
 * 云安全元数据信息
 *
 * @author maurice.chen
 */
public class CloudSecretMetadata implements Serializable {

    public static final String SECRET_ID_NAME = "secretId";

    public static final String SECURITY_KEY_NAME = "secretKey";

    @Serial
    private static final long serialVersionUID = 932055947756264921L;

    /**
     * 密钥 id
     */
    private String secretId;

    /**
     * 密钥
     */
    private String secretKey;

    public CloudSecretMetadata() {
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

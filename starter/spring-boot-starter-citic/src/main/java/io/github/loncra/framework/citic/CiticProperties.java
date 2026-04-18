package io.github.loncra.framework.citic;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("loncra.framework.citic")
public class CiticProperties {

    private String merchantId;

    private String baseUrl;

    private String fileUploadUrl;

    private String signPassword;

    private String jksPassword;

    private String privateKeyPath;

    private String publicKeyPath;

    private String certFilePath;

    private String sslPrivateKeyPath;

    private String sslTrustStoreKeyPath;

    private List<String> ignoreSignProperties = List.of("sign", "fileContent");

    private String apiVersionValue = "v1.0";

    private Integer randomRequestSsnNumber = 8;

    private String apiSuccessCodeValue = "00000";

    public CiticProperties() {
    }

    public String getFileUploadUrl() {
        return fileUploadUrl;
    }

    public void setFileUploadUrl(String fileUploadUrl) {
        this.fileUploadUrl = fileUploadUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSignPassword() {
        return signPassword;
    }

    public void setSignPassword(String signPassword) {
        this.signPassword = signPassword;
    }

    public String getJksPassword() {
        return jksPassword;
    }

    public void setJksPassword(String jksPassword) {
        this.jksPassword = jksPassword;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getCertFilePath() {
        return certFilePath;
    }

    public void setCertFilePath(String certFilePath) {
        this.certFilePath = certFilePath;
    }

    public String getSslPrivateKeyPath() {
        return sslPrivateKeyPath;
    }

    public void setSslPrivateKeyPath(String sslPrivateKeyPath) {
        this.sslPrivateKeyPath = sslPrivateKeyPath;
    }

    public String getSslTrustStoreKeyPath() {
        return sslTrustStoreKeyPath;
    }

    public void setSslTrustStoreKeyPath(String sslTrustStoreKeyPath) {
        this.sslTrustStoreKeyPath = sslTrustStoreKeyPath;
    }

    public List<String> getIgnoreSignProperties() {
        return ignoreSignProperties;
    }

    public void setIgnoreSignProperties(List<String> ignoreSignProperties) {
        this.ignoreSignProperties = ignoreSignProperties;
    }

    public String getApiVersionValue() {
        return apiVersionValue;
    }

    public void setApiVersionValue(String apiVersionValue) {
        this.apiVersionValue = apiVersionValue;
    }

    public Integer getRandomRequestSsnNumber() {
        return randomRequestSsnNumber;
    }

    public void setRandomRequestSsnNumber(Integer randomRequestSsnNumber) {
        this.randomRequestSsnNumber = randomRequestSsnNumber;
    }

    public String getApiSuccessCodeValue() {
        return apiSuccessCodeValue;
    }

    public void setApiSuccessCodeValue(String apiSuccessCodeValue) {
        this.apiSuccessCodeValue = apiSuccessCodeValue;
    }
}

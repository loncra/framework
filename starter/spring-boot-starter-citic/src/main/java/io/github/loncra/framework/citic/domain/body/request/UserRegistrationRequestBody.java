package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * 用户注册请求体
 *
 * @author maurice.chen
 */
public class UserRegistrationRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 2972344656839463605L;

    /**
     * 商户端用户编号
     */
    @NotNull
    @JacksonXmlProperty(localName = "MCHNT_USER_ID")
    private String merchantUserId;

    /**
     * 用户类型
     */
    @NotNull
    @JacksonXmlProperty(localName = "USER_TYPE")
    private String userType;

    /**
     * 用户姓名
     */
    @NotNull
    @JacksonXmlProperty(localName = "USER_NM")
    private String userRealName;

    /**
     * 用户角色
     */
    @NotNull
    @JacksonXmlProperty(localName = "USER_ROLE")
    private String userRole;

    /**
     * 签约类型
     */
    @JacksonXmlProperty(localName = "SIGN_TYPE")
    private String signType = "00";

    /**
     * 证件类型
     */
    @JacksonXmlProperty(localName = "USER_ID_TYPE")
    private String userIdCardType;

    /**
     * 证件号码
     */
    @JacksonXmlProperty(localName = "USER_ID_NO")
    private String userIdNumber;

    /**
     * 用户手机号
     */
    @JacksonXmlProperty(localName = "USER_PHONE")
    private String userPhoneNumber;

    /**
     * 企业法人姓名
     */
    @JacksonXmlProperty(localName = "CORP_NM")
    private String legalPersonName;

    /**
     * 企业法人身份证号码
     */
    @JacksonXmlProperty(localName = "CORP_ID_NO")
    private String legalPersonIdNumber;

    /**
     * 企业法人证件类型
     */
    @JacksonXmlProperty(localName = "CORP_ID_TYPE")
    private String legalPersonIdCardType;

    /**
     * 用户地址
     */
    @JacksonXmlProperty(localName = "USER_ADD")
    private String address;

    public UserRegistrationRequestBody() {
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getUserIdCardType() {
        return userIdCardType;
    }

    public void setUserIdCardType(String userIdCardType) {
        this.userIdCardType = userIdCardType;
    }

    public String getUserIdNumber() {
        return userIdNumber;
    }

    public void setUserIdNumber(String userIdNumber) {
        this.userIdNumber = userIdNumber;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonIdNumber() {
        return legalPersonIdNumber;
    }

    public void setLegalPersonIdNumber(String legalPersonIdNumber) {
        this.legalPersonIdNumber = legalPersonIdNumber;
    }

    public String getLegalPersonIdCardType() {
        return legalPersonIdCardType;
    }

    public void setLegalPersonIdCardType(String legalPersonIdCardType) {
        this.legalPersonIdCardType = legalPersonIdCardType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

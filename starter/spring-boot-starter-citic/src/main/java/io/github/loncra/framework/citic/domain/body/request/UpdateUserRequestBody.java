package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class UpdateUserRequestBody extends BasicUserIdRequestBody {

    @Serial
    private static final long serialVersionUID = -6563059953096145369L;

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
     * 证件类型
     */
    @JacksonXmlProperty(localName = "USER_CARD_TP")
    private String userIdCardType;

    /**
     * 证件号码
     */
    @JacksonXmlProperty(localName = "USER_CARD_NO")
    private String userIdNumber;

    /**
     * 用户手机号
     */
    @JacksonXmlProperty(localName = "USER_PHONE")
    private String userPhoneNumber;

    /**
     * 用户地址
     */
    @JacksonXmlProperty(localName = "USER_ADD")
    private String address;

    /**
     * 企业法人姓名
     */
    @JacksonXmlProperty(localName = "CORP_NM")
    private String legalPersonName;

    /**
     * 企业法人身份证号码
     */
    @JacksonXmlProperty(localName = "CORP_ID_NUM_NEW")
    private String legalPersonIdNumber;

    /**
     * 企业法人证件类型
     */
    @JacksonXmlProperty(localName = "CORP_ID_TYPE_NEW")
    private String legalPersonIdCardType;

    public UpdateUserRequestBody() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}

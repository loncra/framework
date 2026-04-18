package io.github.loncra.framework.citic.domain.body.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicResponseMetadata;

import java.io.Serial;

/**
 * @author maurice.chen
 */
public class SearchUserStatusResponseBody extends BasicResponseMetadata {

    @Serial
    private static final long serialVersionUID = 1402600926033955985L;

    /**
     * 审核失败原因
     */
    @JacksonXmlProperty(localName = "CHECK_FAIL_REASON")
    private String checkFailReason;

    @JacksonXmlProperty(localName = "USER_CHECK_ST")
    private String userCheckStatus;

    @JacksonXmlProperty(localName = "MCHNT_ID")
    private String merchantId;

    /**
     * 用户 id
     */
    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    /**
     * 真实姓名
     */
    @JacksonXmlProperty(localName = "USER_NM")
    private String realName;

    /**
     * 用户状态
     */
    @JacksonXmlProperty(localName = "USER_ST")
    private String userStatus;

    public SearchUserStatusResponseBody() {
    }

    public String getCheckFailReason() {
        return checkFailReason;
    }

    public void setCheckFailReason(String checkFailReason) {
        this.checkFailReason = checkFailReason;
    }

    public String getUserCheckStatus() {
        return userCheckStatus;
    }

    public void setUserCheckStatus(String userCheckStatus) {
        this.userCheckStatus = userCheckStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}

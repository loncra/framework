package io.github.loncra.framework.citic.domain.body.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.loncra.framework.citic.domain.metadata.BasicRequestMetadata;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

/**
 * 用户绑卡/解绑申请(多张卡）请求体
 *
 * @author maurice.chen
 */
public class BankCardRequestBody extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = 5019015045775860966L;

    /**
     * 用户编号
     */
    @NotNull
    @JsonProperty("USER_ID")
    private String userId;

    /**
     * 绑卡类型: 1-绑定 2-解绑
     */
    @NotNull
    @JsonProperty("OP_TYPE")
    private String operateType;

    /**
     * 开户银行联行号,个人用户跨行提现通道为银联代付”开关打开时，个人用户绑卡时 “开户银行联行号”非必输，对公用户绑卡时“开户银行联行号”仍为必输项。“个人用户跨行提现通道为银联代付”开关关闭时，与现有规则保持一致：个人用户和对公用户绑卡时 “开户银行联行号”均为必输项。详见该接口注意事项。
     */
    @JsonProperty("PAN_NUM")
    private String bankClearingCode;

    /**
     * 账户名称
     */
    @NotNull
    @JsonProperty("ACCT_NM")
    private String accountName;

    /**
     * 银行账号，绑定银行卡的卡号
     */
    @NotNull
    @JsonProperty("PAN")
    private String cardNumber;

    /**
     * 绑定个人卡类型： 01-个人身份证 22-户口簿 23-外国护照 25-军人军官证 26-军人士兵证 27-武警军官证 28-港澳居民往来内地通行证（香港） 29-台湾居民往来大陆通行证 30-临时居民身份证 31-外国人永久居留证 32-中国护照 33-武警士兵证 34-港澳居民往来内地通行证（澳门） 35-边民出入境通行证 36-台湾居民旅行证 绑定企业卡： 02-组织机构代码 03-统一社会信用代码 04-民办非企业登记证书 05-社会团体法人登记证书 06-事业单位法人登记证 07-营业执照号码
     */
    @NotNull
    @JsonProperty("USER_ID_TYPE")
    private String userIdCardType;

    /**
     * 用户证件号码
     */
    @NotNull
    @JsonProperty("BANK_CARD_NO")
    private String useridCardNumber;

    /**
     * 账户类型:	若用户类型是个体工商户，需上送以下账户类型且必填 1-中信个人账户 2-中信企业账户 3-他行个人账户 4-他行企业账户 个人用户绑定存折时，账户类型需上送以下账户类型，且必填。 账户类型为存折时，证件类型只支持身份证。 如不上送正确存折类型，可能会导致绑卡或提现失败。 存折提现只能通过智能提现接口，提现通道只支持人行二代。 5-中信个人存折（必填） 6-他行个人存折（必填）
     */
    @NotNull
    @JsonProperty("ACCT_TYPE")
    private String accountType;

    /**
     * 银行预留手机号, 绑定且用户类型为个人时必填，解绑时非必填，可以不用和注册时使用的手机号一致，银联鉴权以绑卡接口上送的手机号为准
     */
    @NotNull
    @JsonProperty("BANK_PHONE")
    private String verifiedBankMobile;

    /**
     * 用户授权协议版本号, 与个人用户签约的电子协议版本号，通过该版本号能够确定协议的具体内容 该字段在绑定个人账户时必填。【用户授权协议版本号和用户授权协议流水号，管家透传给银联，管家不做验证，商户自己定义即可】
     */
    @JsonProperty("AUTH_PROTOCOL_VERSION")
    private String authProtocolVersion;

    /**
     * 用户授权协议流水号, 与个人用户签约的授权交易流水号，通过该流水号应能确定电子协议版本号、签约人、签约时间 该字段在绑定个人账户时必填。【用户授权协议版本号和用户授权协议流水号，管家透传给银联，管家不做验证，商户自己定义即可】
     */
    @JsonProperty("AUTH_PROTOCOL_NO")
    private String authProtocolNumber;

    /**
     * 交易识别，验证申请接口或打款验证接口生成的交易标识,开通短信验证或随机打款验证功能时必填
     */
    @JsonProperty("TRANS_ID")
    private String transactionId;

    /**
     * 短信验证码
     */
    @JsonProperty("VERI_CD")
    private String transactionVerifiedPhoneCaptcha;

    /**
     * 随机打款金额, 待绑定卡收到的随机打款金额（单位：元）,开通随机打款验证功能时对公用户必填
     */
    @JsonProperty("VERI_AMT")
    private String transactionVerifiedAtm;

    public BankCardRequestBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getBankClearingCode() {
        return bankClearingCode;
    }

    public void setBankClearingCode(String bankClearingCode) {
        this.bankClearingCode = bankClearingCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserIdCardType() {
        return userIdCardType;
    }

    public void setUserIdCardType(String userIdCardType) {
        this.userIdCardType = userIdCardType;
    }

    public String getUseridCardNumber() {
        return useridCardNumber;
    }

    public void setUseridCardNumber(String useridCardNumber) {
        this.useridCardNumber = useridCardNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getVerifiedBankMobile() {
        return verifiedBankMobile;
    }

    public void setVerifiedBankMobile(String verifiedBankMobile) {
        this.verifiedBankMobile = verifiedBankMobile;
    }

    public String getAuthProtocolVersion() {
        return authProtocolVersion;
    }

    public void setAuthProtocolVersion(String authProtocolVersion) {
        this.authProtocolVersion = authProtocolVersion;
    }

    public String getAuthProtocolNumber() {
        return authProtocolNumber;
    }

    public void setAuthProtocolNumber(String authProtocolNumber) {
        this.authProtocolNumber = authProtocolNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionVerifiedPhoneCaptcha() {
        return transactionVerifiedPhoneCaptcha;
    }

    public void setTransactionVerifiedPhoneCaptcha(String transactionVerifiedPhoneCaptcha) {
        this.transactionVerifiedPhoneCaptcha = transactionVerifiedPhoneCaptcha;
    }

    public String getTransactionVerifiedAtm() {
        return transactionVerifiedAtm;
    }

    public void setTransactionVerifiedAtm(String transactionVerifiedAtm) {
        this.transactionVerifiedAtm = transactionVerifiedAtm;
    }
}

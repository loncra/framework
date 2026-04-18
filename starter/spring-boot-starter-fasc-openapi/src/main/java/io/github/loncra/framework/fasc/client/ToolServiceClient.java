package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.corp.BusinessFourElementVerifyReq;
import io.github.loncra.framework.fasc.req.corp.BusinessThreeElementVerifyReq;
import io.github.loncra.framework.fasc.req.corp.GetCorpBusinessInfoReq;
import io.github.loncra.framework.fasc.req.user.*;
import io.github.loncra.framework.fasc.res.corp.BusinessFourElementVerifyRes;
import io.github.loncra.framework.fasc.res.corp.BusinessThreeElementVerifyRes;
import io.github.loncra.framework.fasc.res.corp.GetCorpBusinessInfoRes;
import io.github.loncra.framework.fasc.res.user.*;

/**
 * @author zhoufucheng
 * @date 2023/6/14 14:10
 */
public class ToolServiceClient {
    private final OpenApiClient openApiClient;

    public ToolServiceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    /**
     * 获取三要素校验接口
     **/
    public BaseRes<GetThreeElementsVerifyUrlRes> getUserThreeElementVerifyUrl(GetUserThreeElementVerifyUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_THREE_ELEMENT_VERIFY_URL, GetThreeElementsVerifyUrlRes.class);
    }

    /**
     * 获取四要素校验接口
     **/
    public BaseRes<GetFourElementsVerifyUrlRes> getUserFourElementVerifyUrl(GetUserFourElementVerifyUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_FOUR_ELEMENT_VERIFY_URL, GetFourElementsVerifyUrlRes.class);
    }

    /**
     * 获取要素校验身份证图片下载链接
     **/
    public BaseRes<GetIdCardImageDownloadUrlRes> getUserIdcardImageDownloadUrl(GetIdCardImageDownloadUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_IDCARD_IMAGE_DOWNLOAD_URL, GetIdCardImageDownloadUrlRes.class);
    }

    /**
     * 个人认证授权管理-身份证OCR
     **/
    public BaseRes<IdCardOcrRes> IdCardOcr(IdCardOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_ID_CARD, IdCardOcrRes.class);
    }

    /**
     * 个人认证授权管理-银行卡OCR
     **/
    public BaseRes<BankCardOcrRes> bankCardOcr(BankCardOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_BANK_CARD, BankCardOcrRes.class);
    }

    /**
     * 个人认证授权管理-营业执照OCR
     **/
    public BaseRes<BizLicenseOcrRes> bizLicenseOcr(BizLicenseOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_BIZ_LICENSE, BizLicenseOcrRes.class);
    }

    /**
     * 个人认证授权管理-驾驶证OCR
     **/
    public BaseRes<DrivingLicenseOcrRes> drivingLicenseOcr(DrivingLicenseOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_DRIVING_LICENSE, DrivingLicenseOcrRes.class);
    }

    /**
     * 个人认证授权管理-行驶证OCR
     **/
    public BaseRes<VehicleLicenseOcrRes> vehicleLicenseOcr(VehicleLicenseOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_VEHICLE_LICENSE, VehicleLicenseOcrRes.class);
    }

    /**
     * 个人认证授权管理-港澳通行证OCR
     **/
    public BaseRes<MainlandPermitOcrRes> mainlandPermitOcr(MainlandPermitOcrReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_OCR_MAINLAND_PERMIT, MainlandPermitOcrRes.class);
    }


    // 个人二要素校验
    public BaseRes<IdentityTwoElementVerifyRes> identityTwoElementVerify(IdentityTwoElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.IDENTITY_TWO_ELEMENT_VERIFY, IdentityTwoElementVerifyRes.class);
    }

    // 个人运营商三要素校验
    public BaseRes<TelecomThreeElementVerifyRes> telecomThereElementVerify(TelecomThreeElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.TELECOM_THREE_ELEMENT_VERIFY, TelecomThreeElementVerifyRes.class);
    }

    // 个人银行卡四要素校验
    public BaseRes<BankFourElementVerifyRes> bankFourElementVerify(BankFourElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.BANK_FOUR_ELEMENT_VERIFY, BankFourElementVerifyRes.class);
    }

    // 个人银行卡三要素校验
    public BaseRes<BankThreeElementVerifyRes> bankThreeElementVerify(BankThreeElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.BANK_THREE_ELEMENT_VERIFY, BankThreeElementVerifyRes.class);
    }

    // 个人身份证三要素校验
    public BaseRes<IdCardThreeElementVerifyRes> idCardThreeElementVerify(IdCardThreeElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.ID_CARD_THREE_ELEMENT_VERIFY, IdCardThreeElementVerifyRes.class);
    }

    // 获取人脸核验链接
    public BaseRes<GetFaceRecognitionUrlRes> getFaceRecognitionUrl(GetFaceRecognitionUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.FACE_RECOGNITION_GET_URL, GetFaceRecognitionUrlRes.class);
    }

    // 获取人脸核验状态
    public BaseRes<GetFaceRecognitionStatusRes> getFaceRecognitionStatus(GetFaceRecognitionStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.FACE_RECOGNITION_GET_STATUS, GetFaceRecognitionStatusRes.class);
    }

    // 查询工商信息
    public BaseRes<GetCorpBusinessInfoRes> getCorpBusinessInfo(GetCorpBusinessInfoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_BUSINESS_INFO, GetCorpBusinessInfoRes.class);
    }

    /**
     * 企业三要素校验接口
     **/
    public BaseRes<BusinessThreeElementVerifyRes> businessThreeElementVerify(BusinessThreeElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_BUSINESS_THREE_ELEMENT_VERIFY, BusinessThreeElementVerifyRes.class);
    }

    /**
     * 企业四要素校验接口
     **/
    public BaseRes<BusinessFourElementVerifyRes> businessFourElementVerify(BusinessFourElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_BUSINESS_FOUR_ELEMENT_VERIFY, BusinessFourElementVerifyRes.class);
    }

    /**
     * 银行卡四要素核验-创建订单
     **/
    public BaseRes<GetBankcardFourElementTokenRes> getBankcardFourElementToken(GetBankcardFourElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_BANKCARD_FOUR_ELEMENT_TOKEN, GetBankcardFourElementTokenRes.class);
    }

    /**
     * 手机号三要素核验-创建订单
     **/
    public BaseRes<GetBankcardFourElementTokenRes> getTelecomThreeElementToken(GetTelecomThreeElementVerifyReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_TELECOM_THREE_ELEMENT_TOKEN, GetBankcardFourElementTokenRes.class);
    }

    /**
     * 验证码校验
     **/
    public BaseRes<VerifyAuthCodeRes> verifyAuthCode(VerifyAuthCodeReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_VERIFY_AUTH_CODE, VerifyAuthCodeRes.class);
    }

    /**
     * 获取验证码
     **/
    public BaseRes<GetAuthCodeRes> getAuthCode(GetAuthCodeReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_AUTH_CODE, GetAuthCodeRes.class);
    }

    /**
     * 获取身份核验详情
     **/
    public BaseRes<GetVerifyDetailRes> getVerifyDetail(GetVerifyDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_GET_VERIFY_DETAIL, GetVerifyDetailRes.class);
    }

}

package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.seal.*;
import io.github.loncra.framework.fasc.req.template.CancelPersonalSealFreeSignReq;
import io.github.loncra.framework.fasc.req.template.CancelSealFreeSignReq;
import io.github.loncra.framework.fasc.res.seal.*;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/12
 */
public class SealClient {

    private final OpenApiClient openApiClient;

    public SealClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<CreateSealByTemplateRes> createSealByTemplate(CreateSealByTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_CREATE_BY_TEMPLATE, CreateSealByTemplateRes.class);
    }

    public BaseRes<CreateSealByImageRes> createSealByImage(CreateSealByImageReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_CREATE_BY_IMAGE, CreateSealByImageRes.class);
    }

    public BaseRes<CreateLegalRepresentativeSealByTemplateRes> createLegalRepresentativeSealByTemplate(CreateLegalRepresentativeSealByTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_CREATE_LEGAL_REPRESENTATIVE_BY_TEMPLATE, CreateLegalRepresentativeSealByTemplateRes.class);
    }

    public BaseRes<CreateLegalRepresentativeSealByImageRes> createLegalRepresentativeSealByImage(CreateLegalRepresentativeSealByImageReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_CREATE_LEGAL_REPRESENTATIVE_BY_IMAGE, CreateLegalRepresentativeSealByImageRes.class);
    }

    public BaseRes<GetSealListRes> getSealList(GetSealListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_LIST, GetSealListRes.class);
    }

    public BaseRes<GetSealDetailRes> getSealDetail(GetSealDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_DETAIL, GetSealDetailRes.class);
    }

    public BaseRes<GetAppointedSealUrlRes> getAppointedSealUrl(GetAppointedSealUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_APPOINTED_URL, GetAppointedSealUrlRes.class);
    }

    public BaseRes<GetSealUserListRes> getSealUserList(GetSealUserListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_USER_LIST, GetSealUserListRes.class);
    }

    public BaseRes<GetUserSealListRes> getUserSealList(GetUserSealListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_USER_SEAL_LIST, GetUserSealListRes.class);
    }

    public BaseRes<GetSealCreateUrlRes> getSealCreateUrl(GetSealCreateUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_CREATE_URL, GetSealCreateUrlRes.class);
    }

    public BaseRes<GetSealVerifyListRes> getSealVerifyList(GetSealVerifyListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_VERIFY_LIST, GetSealVerifyListRes.class);
    }

    public BaseRes<Void> modifySeal(ModifySealReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_MODIFY, Void.class);
    }

    public BaseRes<GetSealGrantUrlRes> getSealGrantUrl(GetSealGrantUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_GRANT_URL, GetSealGrantUrlRes.class);
    }

    public BaseRes<Void> cancelSealGrant(CancelSealGrantReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_CANCEL_GRANT, Void.class);
    }

    public BaseRes<Void> setSealStatus(SetSealStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_SET_STATUS, Void.class);
    }

    public BaseRes<Void> deleteSeal(SealDeleteReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_DELETE, Void.class);
    }

    public BaseRes<GetSealManageUrlRes> getSealManageUrl(GetSealManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_MANAGE_URL, GetSealManageUrlRes.class);
    }

    public BaseRes<GetSealFreeSignUrlRes> getSealFreeSignUrl(GetSealFreeSignUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SEAL_GET_FREE_SIGN_URL, GetSealFreeSignUrlRes.class);
    }

    public BaseRes<CreatePersonalSealByTemplateRes> createPersonalSealByTemplate(CreatePersonalSealByTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_CREATE_BY_TEMPLATE, CreatePersonalSealByTemplateRes.class);
    }

    public BaseRes<CreatePersonalSealByImageRes> createPersonalSealByImage(CreatePersonalSealByImageReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_CREATE_BY_IMAGE, CreatePersonalSealByImageRes.class);
    }

    public BaseRes<GetPersonalSealListRes> getPersonalSealList(GetPersonalSealListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_GET_LIST, GetPersonalSealListRes.class);
    }

    public BaseRes<GetSealFreeSignUrlRes> getPersonalSealFreeSignUrl(GetPersonalSealFreeSignUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_GET_FREE_SIGN_URL, GetSealFreeSignUrlRes.class);
    }

    /**
     * 解除印章免验证签
     **/
    public BaseRes<Void> cancelSealFreeSign(CancelSealFreeSignReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CANCEL_SEAL_FREE_SIGN, Void.class);
    }

    /**
     * 解除签名免验证签
     **/
    public BaseRes<Void> cancelPersonalSealFreeSign(CancelPersonalSealFreeSignReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CANCEL_PERSONAL_SEAL_FREE_SIGN, Void.class);
    }

    /**
     * 获取签名管理链接
     **/
    public BaseRes<GetPersonalSealManageUrlRes> getPersonalSealManageUrl(GetPersonalSealManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_GET_MANAGE_URL, GetPersonalSealManageUrlRes.class);
    }

    /**
     * 获取签名创建链接
     **/
    public BaseRes<GetPersonalSealCreateUrlRes> getPersonalSealCreateUrl(GetPersonalSealCreateUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_GET_CREATE_URL, GetPersonalSealCreateUrlRes.class);
    }

    /**
     * 删除个人签名
     **/
    public BaseRes<Void> deletePersonalSeal(DeletePersonalSealReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.PERSONAL_SEAL_DELETE, Void.class);
    }

    /**
     * 查询印章标签列表
     **/
    public BaseRes<List<String>> getSealTagList(GetSealTagListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.SEAL_TAG_GET_LIST, String.class);
    }
}

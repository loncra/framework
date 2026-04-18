package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.CopyCreateDocTemplateReq;
import io.github.loncra.framework.fasc.req.template.*;
import io.github.loncra.framework.fasc.res.template.*;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class TemplateClient {
    private final OpenApiClient openApiClient;

    public TemplateClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<DocTemplateListRes> getDocTemplateList(GetDocTemplateListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DOC_TEMPLATE_GET_LIST, DocTemplateListRes.class);
    }

    public BaseRes<DocTemplateDetailRes> getDocTemplateDetail(DocTemplateDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DOC_TEMPLATE_GET_DETAIL, DocTemplateDetailRes.class);
    }

    public BaseRes<CreateDocTemplateRes> createDocTemplate(CreateDocTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DOC_TEMPLATE_CREATE, CreateDocTemplateRes.class);
    }

    public BaseRes<CopyCreateDocTemplateRes> copyCreateDocTemplate(CopyCreateDocTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DOC_TEMPLATE_COPY_CREATE, CopyCreateDocTemplateRes.class);
    }

    public BaseRes<SignTemplateListRes> getSignTemplateList(GetSignTemplateListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_LIST, SignTemplateListRes.class);
    }

    public BaseRes<SignTemplateDetailRes> getSignTemplateDetail(SignTemplateDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_DETAIL, SignTemplateDetailRes.class);
    }

    public BaseRes<GetTemplateCreateUrlRes> getTemplateCreateUrl(GetTemplateCreateUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_CREATE_URL, GetTemplateCreateUrlRes.class);
    }

    public BaseRes<GetTemplateEditUrlRes> getTemplateEditUrl(GetTemplateEditUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_EDIT_URL, GetTemplateEditUrlRes.class);
    }

    public BaseRes<GetTemplatePreviewUrlRes> getTemplatePreviewUrl(GetTemplatePreviewUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_PREVIEW_URL, GetTemplatePreviewUrlRes.class);
    }

    public BaseRes<GetTemplateManageUrlRes> getTemplateManageUrl(GetTemplateManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TEMPLATE_GET_MANAGE_URL, GetTemplateManageUrlRes.class);
    }

    public BaseRes<AppDocTemplatePageListRes> getAppDocTemplates(GetAppDocTemplateListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_TEMPLATE_GET_LIST, AppDocTemplatePageListRes.class);
    }

    public BaseRes<AppDocTemplateDetailRes> getDocTemplateDetail(GetAppDocTemplateDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_TEMPLATE_GET_DETAIL, AppDocTemplateDetailRes.class);
    }

    public BaseRes<AppSignTemplatePageListRes> getAppSignTemplates(GetAppSignTemplateListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_SIGN_TEMPLATE_GET_LIST, AppSignTemplatePageListRes.class);
    }

    public BaseRes<AppSignTemplateDetailRes> getAppSignTemplateDetail(GetAppSignTemplateDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_SIGN_TEMPLATE_GET_DETAIL, AppSignTemplateDetailRes.class);
    }

    public BaseRes<GetAppTemplateCreateUrlRes> getAppTemplateCreateUrl(GetAppTemplateCreateUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_TEMPLATE_CREATE_GET_URL, GetAppTemplateCreateUrlRes.class);
    }

    public BaseRes<GetAppTemplateEditUrlRes> getAppTemplateEditUrl(GetAppTemplateEditUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_TEMPLATE_EDIT_GET_URL, GetAppTemplateEditUrlRes.class);
    }

    public BaseRes<GetAppTemplatePreviewUrlRes> getAppTemplatePreviewUrl(GetAppTemplatePreviewUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_TEMPLATE_PREVIEW_GET_URL, GetAppTemplatePreviewUrlRes.class);
    }

    public BaseRes<Void> createAppField(CreateAppFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_FIELD_CREATE, Void.class);
    }

    public BaseRes<Void> modifyAppField(ModifyAppFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_FIELD_MODIFY, Void.class);
    }

    public BaseRes<Void> setAppFieldStatus(SetAppFieldStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_FIELD_SET_STATUS, Void.class);
    }

    public BaseRes<List<GetAppFieldListRes>> getAppFieldList(GetAppFieldListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.APP_FIELD_GET_LIST, GetAppFieldListRes.class);
    }

    /**
     * 启用/停用应用文档模板
     **/
    public BaseRes<Void> setAppTemplateSetStatus(SetTemplateStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_TEMPLATE_SET_STATUS, Void.class);
    }

    /**
     * 删除应用文档模板
     **/
    public BaseRes<Void> deleteAppTemplate(DeleteAppTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_TEMPLATE_DELETE, Void.class);
    }

    /**
     * 启用/停用应用签署任务模板
     **/
    public BaseRes<Void> setAppSignTemplateStatus(SetAppSignTemplateStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_SIGN_TEMPLATE_SET_STATUS, Void.class);
    }

    /**
     * 删除应用文档模板
     **/
    public BaseRes<Void> deleteSignAppTemplate(DeleteSignAppTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_DOC_SIGN_TEMPLATE_DELETE, Void.class);
    }

    /**
     * 启用/停用文档模板
     **/
    public BaseRes<Void> setDocTemplateStatus(SetDocTemplateStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SET_DOC_TEMPLATE_STATUS, Void.class);
    }

    /**
     * 删除文档模板
     **/
    public BaseRes<Void> deleteDocTemplate(DeleteDocTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DELETE_DOC_TEMPLATE, Void.class);
    }

    /**
     * 启用/停用签署模板
     **/
    public BaseRes<Void> setSignTemplateStatus(SetSignTemplateStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SET_SIGN_TEMPLATE_STATUS, Void.class);
    }

    /**
     * 签署模板删除
     **/
    public BaseRes<Void> deleteSignTemplate(DeleteSignTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DELETE_SIGN_TEMPLATE, Void.class);
    }

    /**
     * 创建企业业务控件
     **/
    public BaseRes<CreateCorpFieldRes> createCorpField(CreateCorpFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_FIELD_CREATE, CreateCorpFieldRes.class);
    }

    /**
     * 删除企业业务控件
     **/
    public BaseRes<Void> deleteCorpField(DeleteCorpFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_FIELD_DELETE, Void.class);
    }

    /**
     * 获取企业业务控件列表
     **/
    public BaseRes<List<GetCorpFieldListRes>> getCorpFieldList(GetCorpFieldListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.CORP_FIELD_GET_LIST, GetCorpFieldListRes.class);
    }

    /**
     * 填写文件模板生成文件
     **/
    public BaseRes<DocTemplateFillValuesRes> docTemplateFillValues(DocTemplateFillValuesReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DOC_TEMPLATE_FILL_VALUES, DocTemplateFillValuesRes.class);
    }
}

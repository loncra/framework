package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.draft.*;
import io.github.loncra.framework.fasc.res.draft.*;

/**
 * @author zhoufucheng
 * @date 2023/6/14 14:10
 */
public class DraftClient {
    private final OpenApiClient openApiClient;

    public DraftClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<DraftCreateRes> draftCreate(DraftCreateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_CREATE_URL, DraftCreateRes.class);
    }

    public BaseRes<GetInviteUrlRes> getDraftInviteUrl(GetInviteUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_GET_INVITE_URL, GetInviteUrlRes.class);
    }

    public BaseRes<GetEditUrlRes> getDraftEditUrl(GetEditUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_GET_EDIT_URL, GetEditUrlRes.class);
    }

    public BaseRes<GetManageUrlRes> getDraftManageUrl(GetManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_GET_MANAGE_URL, GetManageUrlRes.class);
    }

    public BaseRes<Void> draftDocFinalize(DocFinalizeReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_DOC_FINALIZE_URL, Void.class);
    }

    public BaseRes<GetFinishedFileRes> getDraftFinishedFile(GetFinishedFileReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.DRAFT_GET_FINISHED_FILE_URL, GetFinishedFileRes.class);
    }
}

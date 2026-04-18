package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.corp.*;
import io.github.loncra.framework.fasc.res.common.ECorpAuthUrlRes;
import io.github.loncra.framework.fasc.res.corp.*;

/**
 * @author Fadada
 * 2021/10/16 16:48:09
 */
public class CorpClient {
    private final OpenApiClient openApiClient;

    public CorpClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<ECorpAuthUrlRes> getCorpAuthUrl(GetCorpAuthResourceUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET_AUTH_URL, ECorpAuthUrlRes.class);
    }

    public BaseRes<Void> disable(DisableCorpReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_DISABLE, Void.class);
    }

    public BaseRes<Void> enable(EnableCorpReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_ENABLE, Void.class);
    }

    public BaseRes<CorpRes> get(GetCorpReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET, CorpRes.class);
    }

    public BaseRes<CorpIdentityInfoRes> getIdentityInfo(GetCorpIdentityInfoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET_IDENTITY_INFO, CorpIdentityInfoRes.class);
    }

    public BaseRes<Void> unbind(CorpUnbindReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_UNBIND, Void.class);
    }

    public BaseRes<GetIdentifiedStatusRes> getIdentifiedStatus(GetIdentifiedStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET_IDENTIFIED_STATUS, GetIdentifiedStatusRes.class);
    }

    /**
     * 查询相对方
     **/
    public BaseRes<GetCounterpartListRes> getCounterpartList(GetCounterpartListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.COUNTERPART_GET_LIST, GetCounterpartListRes.class);
    }

    public BaseRes<GetCorpIdentTransactionIdRes> getCorpIdentTransactionId(GetCorpIdentTransactionIdReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET_IDENT_TRANSACTION_ID, GetCorpIdentTransactionIdRes.class);
    }

    public BaseRes<GetChangeCorpIdentityInfoUrlRes> getChangeCorpIdentityInfoUrl(GetChangeCorpIdentityInfoUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_IDENTITY_CHANGE_INFO, GetChangeCorpIdentityInfoUrlRes.class);
    }

    public BaseRes<GetUsageAvailablenumRes> getUsageAvailablenum(GetUsageAvailablenumReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_GET_USAGE_AVAILABLENUM, GetUsageAvailablenumRes.class);
    }
}

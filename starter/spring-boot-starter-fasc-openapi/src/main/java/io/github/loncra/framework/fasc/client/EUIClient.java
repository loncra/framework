package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.eui.GetAppPageResourceUrlReq;
import io.github.loncra.framework.fasc.req.eui.GetBillUrlReq;
import io.github.loncra.framework.fasc.req.eui.GetUserPageResourceUrlReq;
import io.github.loncra.framework.fasc.res.common.EUrlRes;
import io.github.loncra.framework.fasc.res.eui.GetPageResourceUrlRes;

public class EUIClient {
    private final OpenApiClient openApiClient;

    public EUIClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<EUrlRes> getBillUrl(GetBillUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.BILLING_GET_BILL_URL, EUrlRes.class);
    }

    public BaseRes<GetPageResourceUrlRes> getAppPageResourceUrl(GetAppPageResourceUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.APP_PAGE_RESOURCE_GET_URL, GetPageResourceUrlRes.class);
    }

    public BaseRes<GetPageResourceUrlRes> getUserPageResourceUrl(GetUserPageResourceUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.USER_PAGE_RESOURCE_GET_URL, GetPageResourceUrlRes.class);
    }
}

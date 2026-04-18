package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.app.GetAppOpenIdListReq;
import io.github.loncra.framework.fasc.res.app.GetAppOpenIdListRes;

/**
 * @author zhoufucheng
 * @date 2023/12/19 14:42
 */
public class AppClient {
    private final OpenApiClient openApiClient;

    public AppClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<GetAppOpenIdListRes> getOpenIdList(GetAppOpenIdListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_OPEN_ID_LIST, GetAppOpenIdListRes.class);
    }
}

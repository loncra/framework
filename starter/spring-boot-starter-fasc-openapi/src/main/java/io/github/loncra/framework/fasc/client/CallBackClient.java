package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.callback.GetCallBackListReq;
import io.github.loncra.framework.fasc.res.callback.GetCallBackListRes;

/**
 * @author zhoufucheng
 * @date 2023/6/14 14:10
 */
public class CallBackClient {
    private final OpenApiClient openApiClient;

    public CallBackClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<GetCallBackListRes> getCallBackList(GetCallBackListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_CALL_BACK_LIST, GetCallBackListRes.class);
    }
}

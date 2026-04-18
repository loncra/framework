package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.doc.*;
import io.github.loncra.framework.fasc.res.doc.*;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class OCRClient {
    private final OpenApiClient openApiClient;

    public OCRClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<GetCompareUrlRes> getCompareUrl(GetCompareUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_COMPARE_URL, GetCompareUrlRes.class);
    }

    public BaseRes<GetCompareResultUrlRes> getCompareResultUrl(GetCompareResultUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_RESULT_COMPARE_URL, GetCompareResultUrlRes.class);
    }

    public BaseRes<GetCompareResultDataRes> getCompareResultData(GetCompareResultDataReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_RESULT_COMPARE_DATA, GetCompareResultDataRes.class);
    }

    public BaseRes<GetExamineUrlRes> getExamineUrl(GetExamineUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_EXAMINE_URL, GetExamineUrlRes.class);
    }

    public BaseRes<GetExamineResultUrlRes> getExamineResultUrl(GetExamineResultUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_RESULT_EXAMINE_URL, GetExamineResultUrlRes.class);
    }

    public BaseRes<RemoteGetExamineDataRes> getExamineResultData(GetExamineDataReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.OCR_EDIT_GET_EXAMINE_RESULT_DATA, RemoteGetExamineDataRes.class);
    }
}

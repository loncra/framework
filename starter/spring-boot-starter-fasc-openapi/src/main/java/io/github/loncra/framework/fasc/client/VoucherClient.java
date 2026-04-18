package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.voucher.*;
import io.github.loncra.framework.fasc.res.voucher.*;

public class VoucherClient {

    private final OpenApiClient openApiClient;

    public VoucherClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    /**
     * 单据创建
     **/
    public BaseRes<VoucherSignTaskCreateRes> voucherTaskCreate(VoucherSignTaskCreateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_CREATE, VoucherSignTaskCreateRes.class);
    }

    /**
     * 单据详情
     **/
    public BaseRes<GetVoucherTaskDetailRes> getVoucherTaskDetail(GetVoucherTaskDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_DETAIL, GetVoucherTaskDetailRes.class);
    }

    /**
     * 单据列表
     **/
    public BaseRes<VoucherPageRes> getVoucherTaskList(GetVoucherTaskListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_LIST, VoucherPageRes.class);
    }

    /**
     * 单据文件下载
     **/
    public BaseRes<VoucherTaskDownloadUrlRes> getVoucherTaskDownloadURL(VoucherTaskDownloadUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_DOWNLOAD, VoucherTaskDownloadUrlRes.class);
    }

    /**
     * 单据撤销
     **/
    public BaseRes<Void> voucherTaskCancel(VoucherCancelReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_CANCEL, Void.class);
    }

    /**
     * 单据签署链接
     **/
    public BaseRes<VoucherGetURLInfoRes> voucherTaskActorGetUrl(VoucherGetURLInfoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.VOUCHER_TASK_ACTOR_GET_URL, VoucherGetURLInfoRes.class);
    }

}

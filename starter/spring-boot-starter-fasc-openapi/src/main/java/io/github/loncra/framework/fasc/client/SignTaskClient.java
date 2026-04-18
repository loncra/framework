package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.signtask.*;
import io.github.loncra.framework.fasc.res.signtask.*;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/13 17:10:09
 */
public class SignTaskClient {
    private final OpenApiClient openApiClient;

    public SignTaskClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    public BaseRes<CreateSignTaskRes> create(CreateSignTaskReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_CREATE, CreateSignTaskRes.class);
    }

    public BaseRes<CreateSignTaskRes> createWithTemplate(CreateWithTemplateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_CREATE_WITH_TEMPLATE, CreateSignTaskRes.class);
    }

    public BaseRes<Void> addDoc(AddDocReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_DOC_ADD, Void.class);
    }

    public BaseRes<Void> deleteDoc(DeleteDocReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_DOC_DELETE, Void.class);
    }

    public BaseRes<Void> addField(AddFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FIELD_ADD, Void.class);
    }

    public BaseRes<Void> deleteField(DeleteFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FIELD_DELETE, Void.class);
    }

    public BaseRes<Void> addAttach(AddAttachReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ATTACH_ADD, Void.class);
    }

    public BaseRes<Void> deleteAttach(DeleteAttachReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ATTACH_DELETE, Void.class);
    }

    public BaseRes<Void> addActor(AddActorsReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_ADD, Void.class);
    }

    public BaseRes<Void> deleteActor(DeleteActorReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_DELETE, Void.class);
    }

    public BaseRes<Void> start(SignTaskBaseReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_START, Void.class);
    }

    public BaseRes<Void> fillFieldValues(FillFieldValuesReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FIELD_FILL_VALUES, Void.class);
    }

    public BaseRes<Void> finalizeDoc(SignTaskBaseReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_DOC_FINALIZE, Void.class);
    }

    public BaseRes<Void> block(BlockReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_BLOCK, Void.class);
    }

    public BaseRes<Void> unblock(UnblockReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_UNBLOCK, Void.class);
    }

    public BaseRes<Void> cancel(SignTaskCancelReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_CANCEL, Void.class);
    }

    public BaseRes<SignTaskDetailRes> getDetail(SignTaskBaseReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_DETAIL, SignTaskDetailRes.class);
    }

    public BaseRes<GetSignTaskListRes> getOwnerList(GetOwnerSignTaskListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_OWNER_GET_LIST, GetSignTaskListRes.class);
    }

    public BaseRes<OwnerDownloadUrlRes> getOwnerDownloadUrl(GetOwnerDownloadUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_OWNER_GET_DOWNLOAD_URL, OwnerDownloadUrlRes.class);
    }

    public BaseRes<SignTaskActorGetUrlRes> signTaskActorGetUrl(SignTaskActorGetUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_GET_URL, SignTaskActorGetUrlRes.class);
    }

    public BaseRes<List<SignTaskCatalogListRes>> signTaskCataloglist(SignTaskCatalogListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.SIGN_TASK_CATALOG_LIST, SignTaskCatalogListRes.class);
    }

    public BaseRes<ListSignTaskFieldRes> listSignTaskField(ListSignTaskFieldReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FIELD_LIST, ListSignTaskFieldRes.class);
    }

    public BaseRes<List<ListSignTaskActorRes>> listSignTaskActor(ListSignTaskActorReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_LIST, ListSignTaskActorRes.class);
    }

    public BaseRes<GetApprovalInfoRes> getApprovalInfo(GetApprovalInfoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_APPROVAL_INFO, GetApprovalInfoRes.class);
    }

    public BaseRes<BatchSignUrlRes> getBatchSignUrl(GetBatchSignUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_BATCH_SIGN_URL, BatchSignUrlRes.class);
    }

    public BaseRes<GetSignTaskEditUrlRes> getSignTaskEditUrl(GetSignTaskUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_EDIT_URL, GetSignTaskEditUrlRes.class);
    }

    public BaseRes<GetSignTaskPreviewUrlRes> getSignTaskPreviewUrl(GetSignTaskUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_PREVIEW_URL, GetSignTaskPreviewUrlRes.class);
    }

    public BaseRes<Void> signTaskUrge(SignTaskBaseReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_URGE, Void.class);
    }

    /**
     * 获取签署任务公证处保全报告
     **/
    public BaseRes<GetDownloadEvidenceReportRes> getDownloadEvidenceReport(GetDownloadEvidenceReportUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_SIGN_TASK_DOWNLOAD_EVIDENCE_REPORT_URL, GetDownloadEvidenceReportRes.class);
    }

    /**
     * 删除签署任务
     **/
    public BaseRes<Void> signTaskDelete(DeleteSignTaskReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_DELETE, Void.class);
    }

    /**
     * 结束签署任务
     **/
    public BaseRes<Void> signTaskFinish(FinishSignTaskReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FINISH, Void.class);
    }

    /**
     * 查询签署业务类型列表
     **/
    public BaseRes<List<GetSignTaskBusinessTypeListRes>> getSignTaskBusinessTypeList(GetSignTaskBusinessTypeListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.SIGN_TASK_GET_BUSINESS_TYPE_LIST, GetSignTaskBusinessTypeListRes.class);
    }

    /**
     * 查询参与方签署底图
     **/
    public BaseRes<GetSignTaskFacePictureRes> getSignTaskFacePicture(GetSignTaskFacePictureReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_GET_FACE_PICTURE, GetSignTaskFacePictureRes.class);
    }

    /**
     * 作废签署任务
     **/
    public BaseRes<CancelSignTaskCreateRes> abolishSignTask(CancelSignTaskCreateReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ABOLISH, CancelSignTaskCreateRes.class);
    }

    /**
     * 签署文档切图
     **/
    public BaseRes<GetSignTaskPicDocTicketRes> getSignTaskOwnerSlicingTicketId(GetSignTaskSlicingDocReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_SIGN_TASK_OWNER_SLICING_TICKET_ID, GetSignTaskPicDocTicketRes.class);
    }

    /**
     * 获取图片版签署文档下载地址
     **/
    public BaseRes<GetSignTaskDocRes> getSignTaskOwnerPicDownloadUrl(GetSignTaskPicDocTicketReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_SIGN_TASK_OWNER_PIC_DOWNLOAD_URL, GetSignTaskDocRes.class);
    }

    /**
     * 获取参与方签署音视频下载地址
     **/
    public BaseRes<GetActorAudioVideoRes> getAudioVideoDownloadUrl(GetActorAudioVideoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_AUDIO_VIDEO_DOWNLOAD_URL, GetActorAudioVideoRes.class);
    }

    /**
     * 获取V3签署任务链接
     **/
    public BaseRes<GetV3ActorSignTaskUrlRes> signTaskActorV3GetUrl(GetV3ActorSignTaskUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_ACTOR_V3_GET_URL, GetV3ActorSignTaskUrlRes.class);
    }

    /**
     * 修改签署任务参与方
     **/
    public BaseRes<Void> modifyActor(ModifyActorReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_MODIFY_ACTOR, Void.class);
    }

    /**
     * 签署任务延期
     **/
    public BaseRes<Void> signTaskExtension(ExtensionReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_EXTENSION, Void.class);
    }

    /**
     * 签署任务驳回填写
     **/
    public BaseRes<Void> signTaskIgnore(SignTaskIgnoreReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_FIELD_IGNORE, Void.class);
    }

    /**
     * 获取送达查看报告下载地址
     **/
    public BaseRes<GetSignTaskReportUrlRes> signTaskMessageReportGetDownloadUrl(GetSignTaskReportUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_MESSAGE_REPORT_GET_DOWNLOAD_URL, GetSignTaskReportUrlRes.class);
    }

    /**
     * 获取预填充链接
     **/
    public BaseRes<SignTaskPreFillUrlRes> signTaskGetPrefillUrl(SignTaskPreFillUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_PREFILL_URL, SignTaskPreFillUrlRes.class);
    }

    /**
     * 查询签署完成的文件
     **/
    public BaseRes<SignTaskGetFileRes> signTaskGetFile(SignTaskGetFileReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_FILE, SignTaskGetFileRes.class);
    }

    /**
     * 证据报告申请
     **/
    public BaseRes<SignTaskApplyReportRes> applyReport(SignTaskApplyReportReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_APPLY_REPORT, SignTaskApplyReportRes.class);
    }

    /**
     * 证据报告下载
     **/
    public BaseRes<SignTaskDownloadReportRes> downloadReport(SignTaskDownloadReportReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_DOWNLOAD_REPORT, SignTaskDownloadReportRes.class);
    }

    /**
     * 获取证书信息
     **/
    public BaseRes<SignTaskGetCerInfoRes> getCerInfo(SignTaskGetCerInfoReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.SIGN_TASK_GET_CER_INFO, SignTaskGetCerInfoRes.class);
    }
}

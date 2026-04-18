package io.github.loncra.framework.fasc.client;

import io.github.loncra.framework.fasc.bean.base.BaseRes;
import io.github.loncra.framework.fasc.constants.OpenApiUrlConstants;
import io.github.loncra.framework.fasc.exception.ApiException;
import io.github.loncra.framework.fasc.req.org.*;
import io.github.loncra.framework.fasc.res.org.*;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/12
 */
public class OrgClient {

    private final OpenApiClient openApiClient;

    public OrgClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    /**
     * 部门相关接口
     */
    public BaseRes<List<CorpDeptInfo>> getCorpDeptList(GetCorpDeptListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.CORP_DEPT_GET_LIST, CorpDeptInfo.class);
    }

    public BaseRes<GetCorpDeptDetailRes> getCorpDeptDetail(GetCorpDeptDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_DEPT_GET_DETAIL, GetCorpDeptDetailRes.class);
    }

    public BaseRes<CreateCorpDeptRes> createCorpDept(CreateCorpDeptReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_DEPT_CREATE, CreateCorpDeptRes.class);
    }

    public BaseRes<Void> modifyCorpDept(ModifyCorpDeptReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_DEPT_MODIFY, Void.class);
    }

    public BaseRes<Void> deleteCorpDept(DeleteCorpDeptReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_DEPT_DELETE, Void.class);
    }

    /**
     * 企业成员相关接口
     */
    public BaseRes<GetMemberListRes> getMemberList(GetMemberListReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_GET_LIST, GetMemberListRes.class);
    }

    public BaseRes<GetCorpMemberDetailRes> getCorpMemberDetail(GetCorpMemberDetailReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_GET_DETAIL, GetCorpMemberDetailRes.class);
    }

    public BaseRes<List<SimpleEmployeeInfo>> createCorpMember(CreateCorpMemberReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.CORP_MEMBER_CREATE, SimpleEmployeeInfo.class);
    }

    public BaseRes<List<SimpleEmployeeInfo>> getCorpMemberActiveUrl(GetCorpMemberActiveUrlReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.CORP_MEMBER_ACTIVE_URL, SimpleEmployeeInfo.class);
    }

    public BaseRes<Void> modifyCorpMember(ModifyCorpMemberReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_MODIFY, Void.class);
    }

    public BaseRes<Void> setCorpMemberDept(SetCorpMemberDeptReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_SET_DEPT, Void.class);
    }

    public BaseRes<Void> setCorpMemberStatus(SetCorpMemberStatusReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_SET_STATUS, Void.class);
    }

    public BaseRes<Void> deleteCorpMember(DeleteCorpMemberReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.CORP_MEMBER_DELETE, Void.class);
    }

    /**
     * 企业管理链接
     */
    public BaseRes<GetCorpOrgManageUrlRes> getCorpOrgManageUrl(GetCorpOrgManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_ORG_MANAGER_URL, GetCorpOrgManageUrlRes.class);
    }

    /**
     * 企业主体列表
     */
    public BaseRes<List<GetEntityListRes>> getCorpEntityList(GetEntityListReq req) throws ApiException {
        return openApiClient.invokeApiList(req, OpenApiUrlConstants.GET_ORG_ENTITY_LIST, GetEntityListRes.class);
    }

    /**
     * 获取成员企业管理链接
     */
    public BaseRes<GetCorpEntityManageUrlRes> getCorpEntityManageUrl(GetCorpEntityManageUrlReq req) throws ApiException {
        return openApiClient.invokeApi(req, OpenApiUrlConstants.GET_ORG_ENTITY_MANAGE_URL, GetCorpEntityManageUrlRes.class);
    }

}

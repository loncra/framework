package io.github.loncra.framework.fasc.constants;

/**
 * @author Fadada
 * 2021/9/8 15:24:46
 */
public class OpenApiUrlConstants {
    /*ServiceClient*/
    public static final String SERVICE_GET_ACCESS_TOKEN = "POST /service/get-access-token";

    /*UserClient*/
    public static final String USER_DISABLE = "POST /user/disable";
    public static final String USER_ENABLE = "POST /user/enable";
    public static final String USER_UNBIND = "POST /user/unbind";
    public static final String USER_GET = "POST /user/get";
    public static final String USER_GET_IDENTITY_INFO = "POST /user/get-identity-info";
    public static final String USER_GET_IDENT_TRANSACTION_ID = "POST /user/get-ident-transaction-id";
    public static final String USER_GET_THREE_ELEMENT_VERIFY_URL = "POST /user/three-element-verify/get-url";
    public static final String USER_GET_FOUR_ELEMENT_VERIFY_URL = "POST /user/four-element-verify/get-url";
    public static final String USER_GET_IDCARD_IMAGE_DOWNLOAD_URL = "POST /user/element-verify/get-idcard-image-download-url";
    public static final String OCR_EDIT_GET_EXAMINE_RESULT_DATA = "POST /ocr/edit/examine-result-data";
    public static final String USER_GET_OCR_ID_CARD = "POST /user/ocr/id-card";
    public static final String USER_GET_OCR_BANK_CARD = "POST /user/ocr/bankcard";
    public static final String USER_GET_OCR_BIZ_LICENSE = "POST /user/ocr/license";
    public static final String USER_GET_OCR_DRIVING_LICENSE = "POST /user/ocr/drivinglicense";
    public static final String USER_GET_OCR_VEHICLE_LICENSE = "POST /user/ocr/vehiclelicense";
    public static final String USER_GET_OCR_MAINLAND_PERMIT = "POST /user/ocr/mainland-permit";
    public static final String IDENTITY_TWO_ELEMENT_VERIFY = "POST /user/identity/two-element-verify";
    public static final String TELECOM_THREE_ELEMENT_VERIFY = "POST /user/telecom/three-element-verify";
    public static final String BANK_FOUR_ELEMENT_VERIFY = "POST /user/bank/four-element-verify";
    public static final String BANK_THREE_ELEMENT_VERIFY = "POST /user/identity/bank-three-element-verify";
    public static final String ID_CARD_THREE_ELEMENT_VERIFY = "POST /user/identity/idcard-three-element-verify";
    public static final String FACE_RECOGNITION_GET_URL = "POST /user/verify/face-recognition";
    public static final String FACE_RECOGNITION_GET_STATUS = "POST /user/verify/face-status-query";
    public static final String USER_GET_BANKCARD_FOUR_ELEMENT_TOKEN = "POST /user/verify/bankcard-four-element/create";
    public static final String USER_GET_TELECOM_THREE_ELEMENT_TOKEN = "POST /user/verify/telecom-three-element/create";
    public static final String USER_VERIFY_AUTH_CODE = "POST /user/verify/auth-code/check";
    public static final String USER_GET_AUTH_CODE = "POST /user/verify/auth-code/get";
    public static final String USER_GET_VERIFY_DETAIL = "POST /user/verify/get-detail";
    public static final String USER_GET_CHANGE_ACCOUNT_URL = "POST /user/account-name/get-change-url";


    /*CorpClient*/
    public static final String CORP_DISABLE = "POST /corp/disable";
    public static final String CORP_ENABLE = "POST /corp/enable";
    public static final String CORP_GET = "POST /corp/get";
    public static final String CORP_GET_IDENTITY_INFO = "POST /corp/get-identity-info";
    public static final String CORP_UNBIND = "POST /corp/unbind";
    public static final String CORP_GET_IDENTIFIED_STATUS = "POST /corp/get-identified-status";
    public static final String CORP_GET_IDENT_TRANSACTION_ID = "POST /corp/get-ident-transaction-id";
    public static final String CORP_BUSINESS_THREE_ELEMENT_VERIFY = "POST /corp/identity/business-three-element-verify";
    public static final String CORP_BUSINESS_FOUR_ELEMENT_VERIFY = "POST /corp/identity/business-four-element-verify";
    public static final String COUNTERPART_GET_LIST = "POST /counterpart/get-list";
    public static final String CORP_BUSINESS_INFO = "POST /corp/identity/business-info-query";
    public static final String CORP_IDENTITY_CHANGE_INFO = "POST /corp/change-identity-info";
    public static final String CORP_GET_USAGE_AVAILABLENUM = "POST /bill-account/get-usage-availablenum";

    /*VoucherClient*/
    public static final String VOUCHER_TASK_CREATE = "POST /voucher-sign-task/create";
    public static final String VOUCHER_TASK_DETAIL = "POST /voucher-sign-task/app/get-detail";
    public static final String VOUCHER_TASK_LIST = "POST /voucher-sign-task/owner/get-list";
    public static final String VOUCHER_TASK_DOWNLOAD = "POST /voucher-sign-task/owner/get-download-url";
    public static final String VOUCHER_TASK_CANCEL = "POST /voucher-sign-task/cancel";
    public static final String VOUCHER_TASK_ACTOR_GET_URL = "POST /voucher-sign-task/actor/get-url";

    /*DocClient*/
    public static final String FILE_UPLOAD_BY_URL = "POST /file/upload-by-url";
    public static final String FILE_GET_UPLOAD_URL = "POST /file/get-upload-url";
    public static final String FILE_PROCESS = "POST /file/process";
    public static final String OCR_EDIT_GET_COMPARE_URL = "POST /ocr/edit/get-compare-url";
    public static final String OCR_EDIT_GET_RESULT_COMPARE_URL = "POST /ocr/edit/compare-result-url";
    public static final String OCR_EDIT_GET_RESULT_COMPARE_DATA = "POST /ocr/edit/compare-result-data";
    public static final String OCR_EDIT_GET_EXAMINE_URL = "POST /ocr/edit/get-examine-url";
    public static final String OCR_EDIT_GET_RESULT_EXAMINE_URL = "POST /ocr/edit/examine-result-url";
    public static final String DOC_POST_FILE_VERIFY_SIGN = "POST /file/verify-sign";
    public static final String GET_KEYWORD_POSITIONS = "POST /file/get-keyword-positions";
    public static final String OFD_FILE_MERGE = "POST /file/ofd-file-merge";

    /*TemplateClient*/
    public static final String DOC_TEMPLATE_GET_LIST = "POST /doc-template/get-list";
    public static final String DOC_TEMPLATE_GET_DETAIL = "POST /doc-template/get-detail";
    public static final String DOC_TEMPLATE_CREATE = "POST /doc-template/create";
    public static final String DOC_TEMPLATE_COPY_CREATE = "POST /doc-template/copy-create";
    public static final String SIGN_TEMPLATE_GET_LIST = "POST /sign-template/get-list";
    public static final String SIGN_TEMPLATE_GET_DETAIL = "POST /sign-template/get-detail";
    public static final String SIGN_TEMPLATE_GET_CREATE_URL = "POST /template/create/get-url";
    public static final String SIGN_TEMPLATE_GET_EDIT_URL = "POST /template/edit/get-url";
    public static final String SIGN_TEMPLATE_GET_PREVIEW_URL = "POST /template/preview/get-url";
    public static final String SIGN_TEMPLATE_GET_MANAGE_URL = "POST /template/manage/get-url";
    public static final String APP_DOC_TEMPLATE_GET_LIST = "POST /app-doc-template/get-list";
    public static final String APP_DOC_TEMPLATE_GET_DETAIL = "POST /app-doc-template/get-detail";
    public static final String APP_SIGN_TEMPLATE_GET_LIST = "POST /app-sign-template/get-list";
    public static final String APP_SIGN_TEMPLATE_GET_DETAIL = "POST /app-sign-template/get-detail";
    public static final String APP_TEMPLATE_CREATE_GET_URL = "POST /app-template/create/get-url";
    public static final String APP_TEMPLATE_EDIT_GET_URL = "POST /app-template/edit/get-url";
    public static final String APP_TEMPLATE_PREVIEW_GET_URL = "POST /app-template/preview/get-url";
    public static final String APP_FIELD_CREATE = "POST /app-field/create";
    public static final String APP_FIELD_MODIFY = "POST /app-field/modify";
    public static final String APP_FIELD_SET_STATUS = "POST /app-field/set-status";
    public static final String APP_FIELD_GET_LIST = "POST /app-field/get-list";
    public static final String APP_DOC_TEMPLATE_SET_STATUS = "POST /app-doc-template/set-status";
    public static final String APP_DOC_TEMPLATE_DELETE = "POST /app-doc-template/delete";
    public static final String APP_DOC_SIGN_TEMPLATE_SET_STATUS = "POST /app-sign-template/set-status";
    public static final String APP_DOC_SIGN_TEMPLATE_DELETE = "POST /app-sign-template/delete";
    public static final String SET_DOC_TEMPLATE_STATUS = "POST /doc-template/set-status";
    public static final String DELETE_DOC_TEMPLATE = "POST /doc-template/delete";
    public static final String SET_SIGN_TEMPLATE_STATUS = "POST /sign-template/set-status";
    public static final String DELETE_SIGN_TEMPLATE = "POST /sign-template/delete";
    public static final String CORP_FIELD_CREATE = "POST /corp-field/create";
    public static final String CORP_FIELD_DELETE = "POST /corp-field/delete";
    public static final String CORP_FIELD_GET_LIST = "POST /corp-field/get-list";
    public static final String DOC_TEMPLATE_FILL_VALUES = "POST /doc-template/fill-values";

    /*SignTaskClient*/
    public static final String SIGN_TASK_CREATE = "POST /sign-task/create";
    public static final String SIGN_TASK_CREATE_WITH_TEMPLATE = "POST /sign-task/create-with-template";
    public static final String SIGN_TASK_DOC_ADD = "POST /sign-task/doc/add";
    public static final String SIGN_TASK_DOC_DELETE = "POST /sign-task/doc/delete";
    public static final String SIGN_TASK_FIELD_ADD = "POST /sign-task/field/add";
    public static final String SIGN_TASK_FIELD_DELETE = "POST /sign-task/field/delete";
    public static final String SIGN_TASK_FIELD_FILL_VALUES = "POST /sign-task/field/fill-values";
    public static final String SIGN_TASK_ATTACH_ADD = "POST /sign-task/attach/add";
    public static final String SIGN_TASK_ATTACH_DELETE = "POST /sign-task/attach/delete";
    public static final String SIGN_TASK_ACTOR_ADD = "POST /sign-task/actor/add";
    public static final String SIGN_TASK_ACTOR_DELETE = "POST /sign-task/actor/delete";
    public static final String SIGN_TASK_START = "POST /sign-task/start";
    public static final String SIGN_TASK_CANCEL = "POST /sign-task/cancel";
    public static final String SIGN_TASK_DOC_FINALIZE = "POST /sign-task/doc-finalize";
    public static final String SIGN_TASK_BLOCK = "POST /sign-task/block";
    public static final String SIGN_TASK_UNBLOCK = "POST /sign-task/unblock";
    public static final String SIGN_TASK_URGE = "POST /sign-task/urge";
    public static final String SIGN_TASK_GET_DETAIL = "POST /sign-task/app/get-detail";
    public static final String SIGN_TASK_OWNER_GET_LIST = "POST /sign-task/owner/get-list";
    public static final String SIGN_TASK_OWNER_GET_DOWNLOAD_URL = "POST /sign-task/owner/get-download-url";
    public static final String SIGN_TASK_ACTOR_GET_URL = "POST /sign-task/actor/get-url";
    public static final String SIGN_TASK_CATALOG_LIST = "POST /sign-task-catalog/list";
    public static final String SIGN_TASK_FIELD_LIST = "POST /sign-task/field/list";
    public static final String SIGN_TASK_ACTOR_LIST = "POST /sign-task/actor/list";
    public static final String SIGN_TASK_GET_APPROVAL_INFO = "POST /sign-task/get-approval-info";
    public static final String SIGN_TASK_GET_BATCH_SIGN_URL = "POST /sign-task/get-batch-sign-url";
    public static final String SIGN_TASK_GET_EDIT_URL = "POST /sign-task/get-edit-url";
    public static final String SIGN_TASK_GET_PREVIEW_URL = "POST /sign-task/get-preview-url";
    public static final String GET_SIGN_TASK_DOWNLOAD_EVIDENCE_REPORT_URL = "POST /sign-task/evidence-report/get-download-url";
    public static final String SIGN_TASK_DELETE = "POST /sign-task/delete";
    public static final String SIGN_TASK_FINISH = "POST /sign-task/finish";
    public static final String SIGN_TASK_GET_BUSINESS_TYPE_LIST = "POST /sign-task/business-type/get-list";
    public static final String SIGN_TASK_ACTOR_GET_FACE_PICTURE = "POST /sign-task/actor/get-face-picture";
    public static final String SIGN_TASK_ABOLISH = "POST /sign-task/abolish";
    public static final String GET_SIGN_TASK_OWNER_SLICING_TICKET_ID = "POST /sign-task/owner/get-slicing-ticket-id";
    public static final String GET_SIGN_TASK_OWNER_PIC_DOWNLOAD_URL = "POST /sign-task/owner/get-pic-download-url";
    public static final String GET_AUDIO_VIDEO_DOWNLOAD_URL = "POST /sign-task/actor/get-audio-video-download-url";
    public static final String SIGN_TASK_ACTOR_V3_GET_URL = "POST /sign-task/actor/v3/get-url";
    public static final String SIGN_TASK_MODIFY_ACTOR = "POST /sign-task/actor/modify";
    public static final String SIGN_TASK_FIELD_SUBMIT = "POST /sign-task/field/submit";
    public static final String SIGN_TASK_FIELD_IGNORE = "POST /sign-task/ignore";
    public static final String SIGN_TASK_EXTENSION = "POST /sign-task/extension";
    public static final String SIGN_TASK_MESSAGE_REPORT_GET_DOWNLOAD_URL = "POST /sign-task/message-report/get-download-url";
    public static final String SIGN_TASK_GET_PREFILL_URL = "POST /sign-task/get-prefill-url";
    public static final String SIGN_TASK_GET_FILE = "POST /sign-task/owner/get-file";
    public static final String SIGN_TASK_APPLY_REPORT = "POST /sign-task/apply-report";
    public static final String SIGN_TASK_DOWNLOAD_REPORT = "POST /sign-task/download-report";
    public static final String SIGN_TASK_GET_CER_INFO = "POST /sign-task/actor/get-cer-info";

    /*EUIClient*/
    public static final String BILLING_GET_BILL_URL = "POST /billing/get-bill-url";
    public static final String APP_PAGE_RESOURCE_GET_URL = "POST /app-page-resource/get-url";
    public static final String USER_PAGE_RESOURCE_GET_URL = "POST /user-page-resource/get-url";
    public static final String USER_GET_AUTH_URL = "POST /user/get-auth-url";
    public static final String CORP_GET_AUTH_URL = "POST /corp/get-auth-url";

    /*OrgClient*/
    public static final String CORP_DEPT_GET_LIST = "POST /corp/dept/get-list";
    public static final String CORP_DEPT_GET_DETAIL = "POST /corp/dept/get-detail";
    public static final String CORP_DEPT_CREATE = "POST /corp/dept/create";
    public static final String CORP_DEPT_MODIFY = "POST /corp/dept/modify";
    public static final String CORP_DEPT_DELETE = "POST /corp/dept/delete";
    public static final String CORP_MEMBER_GET_LIST = "POST /corp/member/get-list";
    public static final String CORP_MEMBER_GET_DETAIL = "POST /corp/member/get-detail";
    public static final String CORP_MEMBER_CREATE = "POST /corp/member/create";
    public static final String CORP_MEMBER_ACTIVE_URL = "POST /corp/member/get-active-url";
    public static final String CORP_MEMBER_MODIFY = "POST /corp/member/modify";
    public static final String CORP_MEMBER_SET_DEPT = "POST /corp/member/set-dept";
    public static final String CORP_MEMBER_SET_STATUS = "POST /corp/member/set-status";
    public static final String CORP_MEMBER_DELETE = "POST /corp/member/delete";
    public static final String GET_ORG_MANAGER_URL = "POST /corp/organization/manage/get-url";
    public static final String GET_ORG_ENTITY_LIST = "POST /corp/entity/get-list";
    public static final String GET_ORG_ENTITY_MANAGE_URL = "POST /corp/entity/get-manage-url";


    /*SealClient*/
    public static final String SEAL_CREATE_BY_TEMPLATE = "POST /seal/create-by-template";
    public static final String SEAL_CREATE_BY_IMAGE = "POST /seal/create-by-image";
    public static final String SEAL_CREATE_LEGAL_REPRESENTATIVE_BY_TEMPLATE = "POST /seal/create-legal-representative-by-template";
    public static final String SEAL_CREATE_LEGAL_REPRESENTATIVE_BY_IMAGE = "POST /seal/create-legal-representative-by-image";
    public static final String SEAL_GET_LIST = "POST /seal/get-list";
    public static final String SEAL_GET_USER_LIST = "POST /seal/get-user-list";
    public static final String SEAL_GET_DETAIL = "POST /seal/get-detail";
    public static final String SEAL_GET_APPOINTED_URL = "POST /seal/manage/get-appointed-seal-url";
    public static final String GET_USER_SEAL_LIST = "POST /seal/user/get-list";
    public static final String SEAL_GET_CREATE_URL = "POST /seal/create/get-url";
    public static final String SEAL_GET_VERIFY_LIST = "POST /seal/verify/get-list";
    public static final String SEAL_MODIFY = "POST /seal/modify";
    public static final String SEAL_GET_GRANT_URL = "POST /seal/grant/get-url";
    public static final String SEAL_CANCEL_GRANT = "POST /seal/grant/cancel";
    public static final String SEAL_SET_STATUS = "POST /seal/set-status";
    public static final String SEAL_DELETE = "POST /seal/delete";
    public static final String SEAL_GET_MANAGE_URL = "POST /seal/manage/get-url";
    public static final String SEAL_GET_FREE_SIGN_URL = "POST /seal/free-sign/get-url";
    public static final String PERSONAL_SEAL_CREATE_BY_TEMPLATE = "POST /personal-seal/create-by-template";
    public static final String PERSONAL_SEAL_CREATE_BY_IMAGE = "POST /personal-seal/create-by-image";
    public static final String PERSONAL_SEAL_GET_LIST = "POST /personal-seal/get-list";
    public static final String PERSONAL_SEAL_GET_FREE_SIGN_URL = "POST /personal-seal/free-sign/get-url";
    public static final String CANCEL_SEAL_FREE_SIGN = "POST /seal/free-sign/cancel";
    public static final String CANCEL_PERSONAL_SEAL_FREE_SIGN = "POST /personal-seal/free-sign/cancel";
    public static final String PERSONAL_SEAL_GET_MANAGE_URL = "POST /personal-seal/manage/get-url";
    public static final String PERSONAL_SEAL_GET_CREATE_URL = "POST /personal-seal/create/get-url";
    public static final String PERSONAL_SEAL_DELETE = "POST /personal-seal/delete";
    public static final String SEAL_TAG_GET_LIST = "POST /seal/tag/get-list";

    /*ApproveClient*/
    public static final String APPROVAL_GET_URL = "POST /approval/get-url";
    public static final String APPROVAL_GET_LIST = "POST /approval/get-list";
    public static final String APPROVAL_GET_DETAIL = "POST /approval/get-detail";
    public static final String APPROVAL_FLOW_GET_LIST = "POST /approval-flow/get-list";
    public static final String APPROVAL_FLOW_GET_DETAIL = "POST /approval-flow/get-detail";

    /*AppClient*/
    public static final String GET_OPEN_ID_LIST = "POST /app/get-openId-list";

    /*CallBackClient*/
    public static final String GET_CALL_BACK_LIST = "POST /callback/get-list";

    /*ArchivesPerformanceClient*/
    public static final String ARCHIVES_PERFORMANCE_MODIFY = "POST /archives/performance/modify";
    public static final String ARCHIVES_PERFORMANCE_DELETE = "POST /archives/performance/delete";
    public static final String ARCHIVES_PERFORMANCE_LIST = "POST /archives/performance/list";
    public static final String CONTACT_ARCHIVED = "POST /archives/contact-archived";
    public static final String GET_ARCHIVED_LIST = "POST /archives/get-archived-list";
    public static final String GET_ARCHIVES_CATALOG_LIST = "POST /archives/catalog-list";
    public static final String GET_ARCHIVES_DETAIL = "POST /archives/get-archived-detail";
    public static final String GET_ARCHIVES_MANAGE_URL = "POST /archives/get-archives-url";

    /*DraftClient*/
    public static final String DRAFT_CREATE_URL = "POST /draft/create";
    public static final String DRAFT_GET_INVITE_URL = "POST /draft/get-invite-url";
    public static final String DRAFT_GET_EDIT_URL = "POST /draft/get-edit-url";
    public static final String DRAFT_GET_MANAGE_URL = "POST /draft/get-manage-url";
    public static final String DRAFT_DOC_FINALIZE_URL = "POST /draft/doc-finalize";
    public static final String DRAFT_GET_FINISHED_FILE_URL = "POST /draft/get-finished-file";


}

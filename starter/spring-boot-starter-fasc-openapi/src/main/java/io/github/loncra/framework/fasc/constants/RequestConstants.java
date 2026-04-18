package io.github.loncra.framework.fasc.constants;


/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class RequestConstants {

    private RequestConstants() {
    }


    /**
     * 企业控制台创建应用后得到的应用ID
     */
    public static final String APP_ID = "X-FASC-App-Id";

    public static final String EVENT = "X-FASC-Event";
    /**
     * FASC.openApi子版本号。如当前版本为：5.1
     */
    public static final String API_SUB_VERSION = "X-FASC-Api-SubVersion";

    public static final String CURRENT_SUB_VERSION = "5.1";
    /**
     * 签名算法类型:固定HMAC-SHA256
     */
    public static final String SIGN_TYPE = "X-FASC-Sign-Type";
    /**
     * 请求参数的签名值
     */
    public static final String SIGN = "X-FASC-Sign";
    /**
     * 时间戳(yyyy-MM-dd HH:mm:ss.sss)，时间戳必须是保证是当前时间，同时跟法大大这边的服务器时间正负不能相差5分钟
     */
    public static final String TIMESTAMP = "X-FASC-Timestamp";
    /**
     * 随机数(32位, 10分钟内不能重复请求)
     */
    public static final String NONCE = "X-FASC-Nonce";
    /**
     * 平台令牌,通过获取令牌接口返回
     */
    public static final String ACCESS_TOKEN = "X-FASC-AccessToken";
    /**
     * 请求参数的集合，除公共请求参数都必须放在这个参数中传递（除文件，字节流等）,json字符串.
     */
    public static final String DATA_KEY = "bizContent";

    /**
     *
     */
    public static final String GRANT_TYPE = "X-FASC-Grant-Type";

    public static final String FDD_REQEUST_ID = "X-FASC-Request-Id";


    /**
     * 默认授权类型
     **/
    public static final String CLIENT_CREDENTIAL = "client_credential";

    public static final String SUCCESS_CODE = "100000";

    public static final int SUCCESS_CODE_200 = 200;


    /**
     * 字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";

    /**
     * HTTP请求相关
     **/
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";

    public static final String EUI_TIMESTAMP = "timestamp";
    public static final String EUI_SIGNATURE = "signature";

    public static final String CALLBACK_OPEN_CORP_ID = "openCorpId";
    public static final String CALLBACK_CLIENT_CORP_ID = "clientCorpId";
}

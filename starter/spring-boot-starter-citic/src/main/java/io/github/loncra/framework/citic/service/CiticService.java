package io.github.loncra.framework.citic.service;

import com.citicbank.baselib.crypto.protocol.PKCS7Signature;
import com.citicbank.baselib.crypto.util.CryptUtil;
import com.citicbank.baselib.crypto.util.FileUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.loncra.framework.citic.CiticProperties;
import io.github.loncra.framework.citic.domain.CiticApiResult;
import io.github.loncra.framework.citic.domain.body.request.*;
import io.github.loncra.framework.citic.domain.body.response.*;
import io.github.loncra.framework.citic.domain.metadata.*;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.MetadataUtils;
import io.github.loncra.framework.commons.domain.metadata.TreeDescriptionMetadata;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.crypto.algorithm.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author maurice.chen
 */
public class CiticService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CiticService.class);

    public static final String DATE_FORMAT = "yyyyMMdd";

    public static final String TIME_FORMAT = "HHmmss";

    public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT;

    private final RestTemplate restTemplate;

    private final CiticProperties citicConfig;

    private final MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter;

    public CiticService(RestTemplate restTemplate, CiticProperties citicConfig, MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter) {
        this.restTemplate = restTemplate;
        this.citicConfig = citicConfig;
        this.mappingJackson2XmlHttpMessageConverter = mappingJackson2XmlHttpMessageConverter;
    }

    public UserRegistrationResponseBody userRegistration(UserRegistrationRequestBody body) {
        body.setTransCode("21000001");
        return executeApi(citicConfig.getBaseUrl(), body, UserRegistrationResponseBody.class);
    }

    public DownloadFileResponseBody downloadFile(DownloadFileRequestBody body) {
        body.setTransCode("21000007");
        return executeApi(citicConfig.getFileUploadUrl(), body, DownloadFileResponseBody.class);
    }

    public UserSsnResponseBody platformPayment(PlatformPaymentRequestBody body) {
        body.setTransCode("21000047");
        return executeApi(citicConfig.getBaseUrl(), body, UserSsnResponseBody.class);
    }

    public FileSignResponseMetadata queryElectronicReceipt(ElectronicReceiptRequestBody body) {
        body.setTransCode("21000011");
        return executeApi(citicConfig.getFileUploadUrl(), body, FileSignResponseMetadata.class);
    }

    public UpdateUserResponseBody updateUser(UpdateUserRequestBody body) {
        body.setTransCode("21000003");
        return executeApi(citicConfig.getBaseUrl(), body, UpdateUserResponseBody.class);
    }

    public SearchUserStatusResponseBody searchUserStatus(BasicUserIdRequestBody body) {
        body.setTransCode("22000001");
        return executeApi(citicConfig.getBaseUrl(), body, SearchUserStatusResponseBody.class);
    }

    public SimpleResponseMetadata bankCardOperation(BankCardRequestBody body) {
        body.setTransCode("21000024");
        return executeApi(citicConfig.getBaseUrl(), body, SimpleResponseMetadata.class);
    }

    public QueryUserTransactionStatusResponseBody queryUserTransactionStatus(QueryUserTransactionStatusRequestBody body) {
        body.setTransCode("21000010");
        return executeApi(citicConfig.getBaseUrl(), body, QueryUserTransactionStatusResponseBody.class);
    }

    public QueryBankCardResponseBody queryBankCard(QueryBankCardRequestBody body) {
        body.setTransCode("21000037");
        return executeApi(citicConfig.getBaseUrl(), body, QueryBankCardResponseBody.class);
    }

    public SignResponseMetadata setDefaultBankCard(DefaultBankCardRequestBody body) {
        body.setTransCode("21000025");
        return executeApi(citicConfig.getBaseUrl(), body, SignResponseMetadata.class);
    }

    public WithdrawalResponseBody withdrawal(WithdrawalRequestBody body) {
        body.setTransCode("21000014");
        return executeApi(citicConfig.getBaseUrl(), body, WithdrawalResponseBody.class);
    }

    public SearchUserBalanceResponseBody searchUserBalance(SearchUserBalanceRequestBody body) {
        body.setTransCode("22000006");
        return executeApi(citicConfig.getBaseUrl(), body, SearchUserBalanceResponseBody.class);
    }

    public SearchMerchantBalanceResponseBody searchMerchantBalance(SearchMerchantBalanceRequestBody body) {
        body.setTransCode("21000035");
        return executeApi(citicConfig.getBaseUrl(), body, SearchMerchantBalanceResponseBody.class);
    }

    public UploadFileResponseBody uploadFile(UploadFileRequestBody body) {
        body.setTransCode("21000031");
        return executeApi(citicConfig.getFileUploadUrl(), body, UploadFileResponseBody.class);
    }

    public UserSsnResponseBody readTimePayment(RealTimePaymentRequestBody body) {
        body.setTransCode("21000050");
        return executeApi(citicConfig.getBaseUrl(), body, UserSsnResponseBody.class);
    }

    public UserSsnResponseBody readTimeRefund(RealTimeRefundRequestBody body) {
        body.setTransCode("21000051");
        return executeApi(citicConfig.getBaseUrl(), body, UserSsnResponseBody.class);
    }

    public SearchUploadFileStatusResponseBody searchUploadFileStatus(SearchUploadFileStatusRequestBody body) {
        body.setTransCode("21000032");
        return executeApi(citicConfig.getBaseUrl(), body, SearchUploadFileStatusResponseBody.class);
    }

    public UserTransactionDetailsPageResponseBody userTransactionDetails(UserTransactionDetailsPageRequestBody body) {
        body.setTransCode("21000029");
        return executeApi(citicConfig.getBaseUrl(), body, UserTransactionDetailsPageResponseBody.class);
    }

    public MerchantTransactionDetailsPageResponseBody merchantTransactionDetails(MerchantTransactionDetailsPageRequestBody body) {
        body.setTransCode("21000039");
        return executeApi(citicConfig.getBaseUrl(), body, MerchantTransactionDetailsPageResponseBody.class);
    }

    public <T extends BasicMerchantMetadata, R extends BasicResponseMetadata> R executeApi(String url, T body, Class<R> responseType) {
        body.setMerchantId(citicConfig.getMerchantId());
        body.setReqSn(citicConfig.getMerchantId() + BasicRequestMetadata.getRequestSsnValue() + RandomStringUtils.secure().nextAlphanumeric(citicConfig.getRandomRequestSsnNumber()));
        sign(body);

        ObjectMapper objectMapper = mappingJackson2XmlHttpMessageConverter.getObjectMapper();

        HttpHeaders headers = getHttpHeaders(body);

        byte[] requestBody = SystemException.convertSupplier(() -> objectMapper.writeValueAsBytes(body));
        headers.setContentLength(requestBody.length);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "[中信银行 E 管家]:开始请求 {} 接口, 请求体为:{}, 请求头为:{}",
                    body.getTransCode(),
                    new String(requestBody),
                    headers
            );
        }

        HttpEntity<byte[]> entity = new HttpEntity<>(requestBody, headers);

        long startTime = System.currentTimeMillis();
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
        long endTime = System.currentTimeMillis();

        JavaType type = objectMapper.getTypeFactory().constructParametricType(CiticApiResult.class, responseType);
        CiticApiResult<R> result = SystemException.convertSupplier(() -> objectMapper.readValue(response.getBody(), type));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "[中信银行 E 管家]:请求 {} 接口结束, 用时:{} 毫秒, 响应结果为:{}, 序列化结果为:{}",
                    body.getTransCode(), (endTime - startTime),
                    SystemException.convertSupplier(() -> new String(Objects.requireNonNull(response.getBody()), StandardCharsets.UTF_8)),
                    SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(result))
            );
        }

        R data =  result.getData();
        if (data instanceof SignResponseMetadata signResponseMetadata) {
            SystemException.isTrue(StringUtils.isNotEmpty(signResponseMetadata.getSign()), "[中信银行 E 管家]:" + Objects.toString(data.getMessage(), result.getMessage()));
            Boolean verifyResult = SystemException.convertSupplier(
                    () -> verifySign(signResponseMetadata),
                    (e) -> new SystemException("[中信银行 E 管家]:验签出现异常", e)
            );
            SystemException.isTrue(Objects.nonNull(verifyResult) && verifyResult, "[中信银行 E 管家]:响应数据验签不通过");
        }

        SystemException.isTrue(citicConfig.getApiSuccessCodeValue().equals(data.getCode()), () -> new ErrorCodeException("[中信银行 E 管家]: 执行接口 [" + body.getTransCode() + "] 错误, " + data.getMessage(), data.getCode()));

        return data;
    }

    private <T extends BasicMerchantMetadata> HttpHeaders getHttpHeaders(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(BasicRequestMetadata.MERCHANT_NO_HEADER_NAME, body.getMerchantId());
        headers.add(BasicRequestMetadata.TRAN_CODE_HEADER_NAME, body.getTransCode());
        headers.add(BasicRequestMetadata.SERIAL_NO_HEADER_NAME, BasicRequestMetadata.getTransTimestampValue() + body.getTransCode());
        headers.add(BasicRequestMetadata.TRAN_TIMESTAMP_HEADER_NAME, BasicRequestMetadata.getTransTimestampValue());
        headers.add(BasicRequestMetadata.VERSION_HEADER_NAME, citicConfig.getApiVersionValue());
        headers.setContentType(new MediaType(MediaType.APPLICATION_XML, StandardCharsets.UTF_8));
        return headers;
    }

    public boolean verifySign(SignResponseMetadata body) throws Exception {
        String sign = body.getSign();
        String signString = getSignString(body);
        return verifySign(signString.getBytes(StandardCharsets.UTF_8), sign);
    }

    private void sign(BasicMerchantMetadata body) {
        String signString = getSignString(body);
        String sign = SystemException.convertSupplier(() ->
                                                              sign(
                                                                      signString.getBytes(StandardCharsets.UTF_8),
                                                                      citicConfig.getSignPassword(),
                                                                      citicConfig.getPrivateKeyPath(),
                                                                      citicConfig.getCertFilePath()
                                                              )
        );
        body.setSign(sign);
    }

    private String getSignString(Object o) {
        Map<String, Object> map = CastUtils.convertValue(o, CastUtils.MAP_TYPE_REFERENCE);
        List<String> values = new LinkedList<>();
        map
                .entrySet()
                .stream()
                .filter(e -> !citicConfig.getIgnoreSignProperties().contains(e.getKey()))
                .filter(e -> Objects.nonNull(e.getValue()))
                .filter(e -> StringUtils.isNotEmpty(e.getValue().toString()))
                .forEach(e -> values.add(e.getValue().toString()));

        StringBuilder signString = new StringBuilder();

        Collections.sort(values);
        values.forEach(signString::append);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "[中信银行 E 管家]:对 {} 数据进行排序，加签值为:{}",
                    SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(map)),
                    signString
            );
        }

        return signString.toString();
    }

    /**
     * 加签
     *
     * @param plaintext 明文报文 byte
     * @param password 发送方私钥文件密码
     * @param secretFile 发送方私钥文件名
     * @param certFile 发送方 cer证书文件名
     */
    private String sign(byte[] plaintext, String password, String secretFile, String certFile) throws Exception {
        char[] keyPassword = password.toCharArray();

        byte[] privateKey = FileUtil.read4file(secretFile);
        PrivateKey signerPrivatekey = CryptUtil.decryptPrivateKey(Base64.decode(privateKey), keyPassword);

        byte[] encodedCert = FileUtil.read4file(certFile);
        X509Certificate signerCertificate = CryptUtil.generateX509Certificate(Base64.decode(encodedCert));

        byte[] signature = PKCS7Signature.sign(plaintext, signerPrivatekey, signerCertificate, null, false);

        return new String(Base64.encode(signature));
    }

    /***
     * 验签
     * @param msg 待验签数据
     * @param sign 发送方生成的签名信息
     * @return 验签结果
     */
    public Boolean verifySign(byte[] msg, String sign) throws Exception {
        byte[] base64EncodedSenderCert = FileUtil.read4file(citicConfig.getPublicKeyPath());

        X509Certificate signerCertificate = CryptUtil.generateX509Certificate(Base64.decode(base64EncodedSenderCert));
        if (Objects.isNull(signerCertificate)) {
            return Boolean.FALSE;
        }
        PublicKey senderPubKey = signerCertificate.getPublicKey();
        return PKCS7Signature.verifyDetachedSignature(msg, Base64.decode(sign.getBytes()), senderPubKey);
    }

    public CiticProperties getCiticConfig() {
        return citicConfig;
    }

    public <T> List<T> convertBase64FileToEntity(String base64File, Class<T> targetClass) throws Exception {
        List<T> result = new LinkedList<>();
        TreeDescriptionMetadata metadata = Objects.requireNonNull(MetadataUtils.convertDescriptionMetadata(targetClass), "针对 " + targetClass + "找不到 @Description 注解");
        List<TreeDescriptionMetadata> fieldDesciptionList = CastUtils.cast(metadata.getChildren());

        byte[] s = Base64.decode(base64File);
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(s));
        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }

            byte[] contentBytes = zis.readAllBytes();
            String content = new String(contentBytes, StandardCharsets.UTF_8);
            String[] data = StringUtils.split(content, StringUtils.LF);
            for (String datum : data) {
                String[] line = StringUtils.splitByWholeSeparator(datum, UploadFileRequestBody.DEFAULT_SPLIT_SIMPLE);

                T entity = ClassUtils.newInstance(targetClass);
                for (int j = 0; j < line.length; j++) {
                    int finalIndex = j;
                    TreeDescriptionMetadata descriptionMetadata = fieldDesciptionList
                            .stream()
                            .filter(f -> f.getSort() == finalIndex)
                            .findFirst()
                            .orElseThrow(() -> new SystemException("找不到索引为" + finalIndex + "的字段描述"));
                    Field field = ReflectionUtils.findField(entity.getClass(), descriptionMetadata.getId());
                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, entity, CastUtils.cast(line[finalIndex], Class.forName(descriptionMetadata.getType())));
                    }
                }
                result.add(entity);
            }
        }

        zis.closeEntry();

        return result;
    }

}

package io.github.loncra.framework.fasc.utils.crypt;

import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.crypto.algorithm.SimpleByteSource;
import io.github.loncra.framework.fasc.FascProperties;
import io.github.loncra.framework.fasc.constants.RequestConstants;
import io.github.loncra.framework.fasc.utils.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class FddCryptUtil {


    private FddCryptUtil() {
    }

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    public static byte[] hmac256(
            byte[] key,
            String msg
    ) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes(UTF8));
    }

    public static String sha256Hex(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(s.getBytes(UTF8));
        return new SimpleByteSource(d).getHex();
    }

    /**
     * @param sortParam 排序后得参数字符串
     * @param timestamp 时间戳
     * @param appSecret 应用秘钥
     *
     * @return 签名值
     *
     * @throws Exception 异常
     */
    public static String sign(
            String sortParam,
            String timestamp,
            String appSecret
    ) throws Exception {
        //将排序后字符串转为sha256Hex
        String signText = sha256Hex(sortParam);
        // ************* 计算签名 *************
        byte[] secretSigning = hmac256((appSecret).getBytes(UTF8), timestamp);
        //计算后得到签名
        return new SimpleByteSource(hmac256(secretSigning, signText)).getHex();
    }


    public static String sortParameters(Map<String, String> parameters) {
        if (parameters.isEmpty()) {
            return null;
        }
        List<String> removeKeys = new ArrayList<>();
        for (Entry<String, String> entry : parameters.entrySet()) {
            if (StringUtil.isBlank(entry.getValue())) {
                removeKeys.add(entry.getKey());
            }
        }

        for (String key : removeKeys) {
            parameters.remove(key);
        }
        StringBuilder stringBuilder = new StringBuilder();
        SortedMap<String, String> paramMap = new TreeMap<>(parameters);
        int index = 0;
        for (Entry<String, String> entry : paramMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(HttpRequestParameterMapUtils.EQ).append(entry.getValue());
            index++;
            if (index != parameters.size()) {
                stringBuilder.append(HttpRequestParameterMapUtils.HTTP_AND);
            }
        }
        return stringBuilder.toString();
    }

    public static void verifyEventSign(
            ServletServerHttpRequest request,
            FascProperties fascProperties
    ) {
        String requestSign = request.getHeaders().getFirst(RequestConstants.SIGN);
        SystemException.isTrue(StringUtils.isNotEmpty(requestSign), "[法大大]: 签名数据不能为空");
        String timestamp = request.getHeaders().getFirst(RequestConstants.TIMESTAMP);
        SystemException.isTrue(StringUtils.isNotEmpty(timestamp), "[法大大]: 签名数时间戳不能为空");


        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(RequestConstants.APP_ID, request.getHeaders().getFirst(RequestConstants.APP_ID));
        paramMap.put(RequestConstants.SIGN_TYPE, request.getHeaders().getFirst(RequestConstants.SIGN_TYPE));
        paramMap.put(RequestConstants.TIMESTAMP, timestamp);
        paramMap.put(RequestConstants.NONCE, request.getHeaders().getFirst(RequestConstants.NONCE));
        paramMap.put(RequestConstants.EVENT, request.getHeaders().getFirst(RequestConstants.EVENT));
        paramMap.put(RequestConstants.DATA_KEY, request.getServletRequest().getParameter(RequestConstants.DATA_KEY));

        String signString = FddCryptUtil.sortParameters(paramMap);
        ;
        String sign = SystemException.convertSupplier(() -> FddCryptUtil.sign(signString, timestamp, fascProperties.getAccessToken().getSecretKey()));
        SystemException.isTrue(Strings.CS.equals(sign, requestSign), "[法大大]: 验签不通过");
    }

}


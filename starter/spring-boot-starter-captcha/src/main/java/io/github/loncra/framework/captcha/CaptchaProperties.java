package io.github.loncra.framework.captcha;

import io.github.loncra.framework.captcha.filter.CaptchaVerificationFilter;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.LinkedList;
import java.util.List;

/**
 * 验证码配置信息
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.captcha")
public class CaptchaProperties {

    /**
     * 默认的忽略拦截器参数名称
     */
    public static final String DEFAULT_IGNORE_INTERCEPTOR_PARAM_NAME = "ignoreInterceptor";

    /**
     * 默认的校验成功删除参数名称
     */
    public static final String DEFAULT_VERIFY_SUCCESS_DELETE_PARAM_NAME = "verifySuccessDelete";

    /**
     * 默认的校验 token 是否存在参数名称
     */
    public static final String DEFAULT_VERIFY_TOKEN_EXIST_PARAM_NAME = "verifyTokenExist";

    /**
     * 默认的 token 参数名称后缀
     */
    public static final String DEFAULT_TOKEN_PARAM_NAME_SUFFIX = "captchaToken";

    /**
     * 默认的验证码类型请求头名称
     */
    public static final String DEFAULT_CAPTCHA_TYPE_HEADER_NAME = "X-CAPTCHA-TYPE";

    /**
     * 默认的验证码类型参数名称
     */
    public static final String DEFAULT_CAPTCHA_TYPE_PARAM_NAME = "captchaType";

    /**
     * 验证码 token 缓存配置
     */
    private CacheProperties buildTokenCache = CacheProperties.of("loncra:framework:captcha:build-token:", TimeProperties.ofMinutes(5));
    /**
     * 拦截器 token 缓存配置
     */
    private CacheProperties interceptorTokenCache = CacheProperties.of("loncra:framework:captcha:interceptor-token:", TimeProperties.ofMinutes(5));

    /**
     * 获取拦截器参数名称，用于在生成 token 时判断一些逻辑执行操作,参考:{@link AbstractCaptchaService#generateToken(String, HttpServletRequest)}
     */
    private String ignoreInterceptorParamName = DEFAULT_IGNORE_INTERCEPTOR_PARAM_NAME;

    /**
     * 校验验证码成功是否删除参数名称，当提交改名称参数为 true 时，校验成功后会删除缓存的内容
     */
    private String verifySuccessDeleteParamName = DEFAULT_VERIFY_SUCCESS_DELETE_PARAM_NAME;

    /**
     * 校验 token 是否存在参数名称
     */
    private String verifyTokenExistParamName = DEFAULT_VERIFY_TOKEN_EXIST_PARAM_NAME;

    /**
     * 验证绑定 token 的参数后缀名
     */
    private String tokenParamNameSuffix = DEFAULT_TOKEN_PARAM_NAME_SUFFIX;

    /**
     * 是否永远验证成功后删除验证码
     */
    private boolean alwaysVerifySuccessDelete = true;

    /**
     * 默认验证码类型
     */
    private String defaultCaptchaType = "tianai";

    /**
     * 验证码类型请求头名称
     */
    private String captchaTypeHeaderName = DEFAULT_CAPTCHA_TYPE_HEADER_NAME;

    /**
     * 验证码类型请求参数名称
     */
    private String captchaTypeParamName = DEFAULT_CAPTCHA_TYPE_PARAM_NAME;

    /**
     * 必须要校验验证码通过后在执行的 url 集合
     */
    private List<String> verifyUrls = new LinkedList<>();

    /**
     * 匹配 url 时使用 {@link  org.springframework.util.AntPathMatcher} 作为匹配标准，改值会写入 {@link  org.springframework.util.AntPathMatcher#setCaseSensitive(boolean)} 中
     */
    private boolean filterAntPathMatcherCaseSensitive = true;

    /**
     * {@link CaptchaVerificationFilter} 的排序值
     */
    private int filterOrderValue = Ordered.HIGHEST_PRECEDENCE + 60;

    /**
     * 构造函数
     */
    public CaptchaProperties() {
    }

    /**
     * 获取验证码 token 缓存配置
     *
     * @return 缓存配置
     */
    public CacheProperties getBuildTokenCache() {
        return buildTokenCache;
    }

    /**
     * 设置验证码 token 缓存配置
     *
     * @param buildTokenCache 缓存配置
     */
    public void setBuildTokenCache(CacheProperties buildTokenCache) {
        this.buildTokenCache = buildTokenCache;
    }

    /**
     * 获取拦截器参数名称
     *
     * @return 拦截器参数名称
     */
    public String getIgnoreInterceptorParamName() {
        return ignoreInterceptorParamName;
    }

    /**
     * 设置拦截器参数名称
     *
     * @param ignoreInterceptorParamName 拦截器参数名称
     */
    public void setIgnoreInterceptorParamName(String ignoreInterceptorParamName) {
        this.ignoreInterceptorParamName = ignoreInterceptorParamName;
    }

    /**
     * 获取校验验证码成功是否删除参数名称
     *
     * @return 参数名称
     */
    public String getVerifySuccessDeleteParamName() {
        return verifySuccessDeleteParamName;
    }

    /**
     * 设置校验验证码成功是否删除参数名称
     *
     * @param verifySuccessDeleteParamName 参数名称
     */
    public void setVerifySuccessDeleteParamName(String verifySuccessDeleteParamName) {
        this.verifySuccessDeleteParamName = verifySuccessDeleteParamName;
    }

    /**
     * 获取校验 token 是否存在参数名称
     *
     * @return 参数名称
     */
    public String getVerifyTokenExistParamName() {
        return verifyTokenExistParamName;
    }

    /**
     * 设置校验 token 是否存在参数名称
     *
     * @param verifyTokenExistParamName 参数名称
     */
    public void setVerifyTokenExistParamName(String verifyTokenExistParamName) {
        this.verifyTokenExistParamName = verifyTokenExistParamName;
    }

    /**
     * 获取验证绑定 token 的参数后缀名
     *
     * @return 参数后缀名
     */
    public String getTokenParamNameSuffix() {
        return tokenParamNameSuffix;
    }

    /**
     * 设置验证绑定 token 的参数后缀名
     *
     * @param tokenParamNameSuffix 参数后缀名
     */
    public void setTokenParamNameSuffix(String tokenParamNameSuffix) {
        this.tokenParamNameSuffix = tokenParamNameSuffix;
    }

    /**
     * 获取默认验证码类型
     *
     * @return 默认验证码类型
     */
    public String getDefaultCaptchaType() {
        return defaultCaptchaType;
    }

    /**
     * 设置默认验证码类型
     *
     * @param defaultCaptchaType 默认验证码类型
     */
    public void setDefaultCaptchaType(String defaultCaptchaType) {
        this.defaultCaptchaType = defaultCaptchaType;
    }

    /**
     * 获取拦截器 token 缓存配置
     *
     * @return 缓存配置
     */
    public CacheProperties getInterceptorTokenCache() {
        return interceptorTokenCache;
    }

    /**
     * 设置拦截器 token 缓存配置
     *
     * @param interceptorTokenCache 缓存配置
     */
    public void setInterceptorTokenCache(CacheProperties interceptorTokenCache) {
        this.interceptorTokenCache = interceptorTokenCache;
    }

    /**
     * 获取验证码类型请求头名称
     *
     * @return 请求头名称
     */
    public String getCaptchaTypeHeaderName() {
        return captchaTypeHeaderName;
    }

    /**
     * 设置验证码类型请求头名称
     *
     * @param captchaTypeHeaderName 请求头名称
     */
    public void setCaptchaTypeHeaderName(String captchaTypeHeaderName) {
        this.captchaTypeHeaderName = captchaTypeHeaderName;
    }

    /**
     * 获取验证码类型请求参数名称
     *
     * @return 参数名称
     */
    public String getCaptchaTypeParamName() {
        return captchaTypeParamName;
    }

    /**
     * 设置验证码类型请求参数名称
     *
     * @param captchaTypeParamName 参数名称
     */
    public void setCaptchaTypeParamName(String captchaTypeParamName) {
        this.captchaTypeParamName = captchaTypeParamName;
    }

    /**
     * 获取必须要校验验证码通过后在执行的 url 集合
     *
     * @return URL 集合
     */
    public List<String> getVerifyUrls() {
        return verifyUrls;
    }

    /**
     * 设置必须要校验验证码通过后在执行的 url 集合
     *
     * @param verifyUrls URL 集合
     */
    public void setVerifyUrls(List<String> verifyUrls) {
        this.verifyUrls = verifyUrls;
    }

    /**
     * 是否匹配 url 时使用大小写敏感
     *
     * @return true 是，否则 false
     */
    public boolean isFilterAntPathMatcherCaseSensitive() {
        return filterAntPathMatcherCaseSensitive;
    }

    /**
     * 设置是否匹配 url 时使用大小写敏感
     *
     * @param filterAntPathMatcherCaseSensitive true 是，否则 false
     */
    public void setFilterAntPathMatcherCaseSensitive(boolean filterAntPathMatcherCaseSensitive) {
        this.filterAntPathMatcherCaseSensitive = filterAntPathMatcherCaseSensitive;
    }

    /**
     * 获取验证码验证过滤器的排序值
     *
     * @return 排序值
     */
    public int getFilterOrderValue() {
        return filterOrderValue;
    }

    /**
     * 设置验证码验证过滤器的排序值
     *
     * @param filterOrderValue 排序值
     */
    public void setFilterOrderValue(int filterOrderValue) {
        this.filterOrderValue = filterOrderValue;
    }

    /**
     * 是否永远验证成功后删除验证码
     *
     * @return true 是，否则 false
     */
    public boolean isAlwaysVerifySuccessDelete() {
        return alwaysVerifySuccessDelete;
    }

    /**
     * 设置是否永远验证成功后删除验证码
     *
     * @param alwaysVerifySuccessDelete true 是，否则 false
     */
    public void setAlwaysVerifySuccessDelete(boolean alwaysVerifySuccessDelete) {
        this.alwaysVerifySuccessDelete = alwaysVerifySuccessDelete;
    }
}

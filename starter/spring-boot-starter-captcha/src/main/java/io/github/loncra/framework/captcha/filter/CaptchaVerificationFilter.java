package io.github.loncra.framework.captcha.filter;

import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.commons.exception.SystemException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 验证码校验认证过滤器
 *
 * @author maurice.chen
 */
public class CaptchaVerificationFilter extends OncePerRequestFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(CaptchaVerificationFilter.class);

    private final CaptchaProperties captchaProperties;

    private final List<CaptchaVerificationService> captchaVerificationServices;

    private final List<CaptchaVerificationInterceptor> captchaVerificationInterceptors;

    private final AntPathMatcher matcher;

    public CaptchaVerificationFilter(
            CaptchaProperties captchaProperties,
            List<CaptchaVerificationService> captchaVerificationServices,
            List<CaptchaVerificationInterceptor> captchaVerificationInterceptors
    ) {
        this.captchaProperties = captchaProperties;
        this.captchaVerificationServices = captchaVerificationServices;
        this.captchaVerificationInterceptors = captchaVerificationInterceptors;

        matcher = new AntPathMatcher();
        matcher.setTrimTokens(false);
        matcher.setCaseSensitive(captchaProperties.isFilterAntPathMatcherCaseSensitive());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String url = request.getServletPath();
        if (captchaProperties.getVerifyUrls().stream().noneMatch(s -> matcher.match(s, url))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (CollectionUtils.isNotEmpty(captchaVerificationInterceptors)) {
            // 判断是否断言后为成功，如果是，不校验验证码，否则校验验证码。
            boolean success = this.captchaVerificationInterceptors.stream().allMatch(a -> a.preVerify(request, response));

            if (success) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {

            String type = getCaptchaType(request);
            Assert.isTrue(StringUtils.isNotEmpty(type), "验证码类型不能为空");

            CaptchaVerificationService captchaVerificationService = captchaVerificationServices
                    .stream()
                    .filter(c -> c.getType().contains(type))
                    .findFirst()
                    .orElseThrow(() -> new SystemException("找不到类型为 [" + type + "] 的验证码校验实现"));
            captchaVerificationService.verify(request);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("对 {} 请求校验验证码成功，请求参数为:{}", url, request.getParameterMap());
            }
            captchaVerificationInterceptors.forEach(a -> a.postVerify(request, response));
            if (getVerifySuccessDelete(request)) {
                captchaVerificationService.delete(request);
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            LOGGER.error("验证码校验失败", e);
            captchaVerificationInterceptors.forEach(a -> a.exceptionVerify(request, response, e));
        }
    }

    private String getCaptchaType(HttpServletRequest request) {
        String type = request.getParameter(captchaProperties.getCaptchaTypeParamName());
        if (StringUtils.isEmpty(type)) {
            type = request.getHeader(captchaProperties.getCaptchaTypeHeaderName());
        }

        return type;
    }

    private boolean getVerifySuccessDelete(HttpServletRequest request) {
        String verifySuccessDelete = request.getParameter(captchaProperties.getVerifySuccessDeleteParamName());

        if (StringUtils.isEmpty(verifySuccessDelete) && captchaProperties.isAlwaysVerifySuccessDelete()) {
            return true;
        }

        return BooleanUtils.toBoolean(verifySuccessDelete);
    }
}

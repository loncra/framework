package io.github.loncra.framework.captcha;

import io.github.loncra.framework.captcha.token.BuildToken;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * 代理的验证码服务
 *
 * @author maurice
 */
public class DelegateCaptchaService {

    /**
     * 验证码服务列表
     */
    private final List<CaptchaService> captchaServices;

    /**
     * 创建一个代理的验证码服务
     *
     * @param captchaServices 验证码服务列表
     */
    public DelegateCaptchaService(List<CaptchaService> captchaServices) {
        this.captchaServices = captchaServices;
    }

    /**
     * 根据 http servlet request 获取验证码服务
     *
     * @param request http servlet request
     *
     * @return 验证码服务
     */
    public CaptchaService getCaptchaServiceByRequest(HttpServletRequest request) {
        return captchaServices
                .stream()
                .filter(c -> c.isSupport(request))
                .findFirst()
                .orElseThrow(() -> new ServiceException("通过 http servlet request 找不到验证码服务"));
    }

    /**
     * 根据验证码类型获取验证码服务
     *
     * @param type 类型
     *
     * @return 验证码服务
     */
    public CaptchaService getCaptchaServiceByType(String type) {

        return captchaServices
                .stream()
                .filter(c -> c.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new ServiceException("找不到类型为 [" + type + "] 的验证码服务"));

    }

    /**
     * 生成 token
     *
     * @param type             验证码类型
     * @param deviceIdentified 设备唯一识别
     * @param request          http servlet request
     *
     * @return 绑定 token
     */
    public BuildToken generateToken(
            String type,
            String deviceIdentified,
            HttpServletRequest request
    ) {
        return getCaptchaServiceByType(type).generateToken(deviceIdentified, request);
    }

    /**
     * 验证 token
     *
     * @param request http servlet request
     *
     * @return 验证结果集
     */
    public RestResult<Map<String, Object>> verify(HttpServletRequest request) {

        CaptchaService captchaService = getCaptchaServiceByRequest(request);

        return captchaService.verify(request);
    }

    /**
     * 生成验证码
     *
     * @param request http servlet request
     *
     * @return 验证码结果
     *
     * @throws Exception 生成错误时抛出
     */
    public Object generateCaptcha(HttpServletRequest request) throws Exception {
        return getCaptchaServiceByRequest(request).generateCaptcha(request);
    }

    /**
     * 删除验证码
     *
     * @param request http servlet request
     *
     * @return 删除结果集
     */
    public RestResult<Map<String, Object>> delete(HttpServletRequest request) {
        return getCaptchaServiceByRequest(request).deleteCaptcha(request);
    }
}

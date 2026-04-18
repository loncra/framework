package io.github.loncra.framework.captcha;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 验证码解析器
 *
 * @author maurice.chen
 */
public interface CaptchaResolver {

    /**
     * POST 参数键
     */
    String POST_ARGS_KEY = "post";

    /**
     * 生成参数键
     */
    String GENERATE_ARGS_KEY = "generate";

    /**
     * 创建构造验证码元数据
     *
     * @param request http servlet request
     *
     * @return 构造验证码元数据
     */
    ConstructionCaptchaMetadata createConstructionCaptchaMeta(HttpServletRequest request);

    /**
     * 所属类型
     *
     * @return 类型
     */
    String getType();
}

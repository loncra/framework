package io.github.loncra.framework.captcha;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 构造验证码元数据
 *
 * @author maurice.chen
 */
public class ConstructionCaptchaMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = -2135082237130733550L;

    /**
     * 验证码类型
     */
    private String type;

    /**
     * 构造参数
     */
    private Map<String, Object> args;

    /**
     * 构造函数
     */
    public ConstructionCaptchaMetadata() {
    }

    /**
     * 构造函数
     *
     * @param type 验证码类型
     * @param args 构造参数
     */
    public ConstructionCaptchaMetadata(
            String type,
            Map<String, Object> args
    ) {
        this.type = type;
        this.args = args;
    }

    /**
     * 获取验证码类型
     *
     * @return 验证码类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置验证码类型
     *
     * @param type 验证码类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取构造参数
     *
     * @return 构造参数
     */
    public Map<String, Object> getArgs() {
        return args;
    }

    /**
     * 设置构造参数
     *
     * @param args 构造参数
     */
    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }
}

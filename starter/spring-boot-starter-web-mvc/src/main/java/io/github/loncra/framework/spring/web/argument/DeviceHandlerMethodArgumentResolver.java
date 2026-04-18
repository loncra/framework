package io.github.loncra.framework.spring.web.argument;

import io.github.loncra.framework.spring.web.device.DeviceUtils;
import nl.basjes.parse.useragent.UserAgent;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 设备参数解析器
 *
 * @author maurice
 */
public class DeviceHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断是否支持该参数，仅支持 UserAgent 类型的参数
     *
     * @param parameter 方法参数
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserAgent.class.isAssignableFrom(parameter.getParameterType());
    }

    /**
     * 解析参数并返回当前设备信息
     *
     * @param parameter             方法参数
     * @param modelAndViewContainer 模型和视图容器
     * @param request               Web 请求
     * @param binderFactory         数据绑定工厂
     *
     * @return 当前设备信息
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest request,
            WebDataBinderFactory binderFactory
    ) {
        return DeviceUtils.getCurrentDevice(request);
    }
}

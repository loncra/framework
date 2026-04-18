package io.github.loncra.framework.security.plugin;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 目标对象, 用于记录是方法级别的目标还是类级别的目标，等好区分如果构造插件内容。
 *
 * @author maurice.chen
 */
public class TargetObject {

    /**
     * 目标对象
     */
    private final Object target;

    /**
     * 方法列表
     */
    private final List<Method> methodList;

    /**
     * 构造函数
     *
     * @param target     目标对象
     * @param methodList 方法列表
     */
    public TargetObject(
            Object target,
            List<Method> methodList
    ) {
        this.methodList = methodList;
        this.target = target;
    }

    /**
     * 获取目标对象
     *
     * @return 目标对象
     */
    public Object getTarget() {
        return target;
    }

    /**
     * 获取方法列表
     *
     * @return 方法列表
     */
    public List<Method> getMethodList() {
        return methodList;
    }
}

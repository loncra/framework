package io.github.loncra.framework.fasc.utils.json;

import io.github.loncra.framework.fasc.bean.base.BaseRes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class ParameterizedTypeBaseRes implements ParameterizedType {
    Class clazz;

    public ParameterizedTypeBaseRes(Class clz) {
        clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {clazz};
    }

    @Override
    public Type getRawType() {
        return BaseRes.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}

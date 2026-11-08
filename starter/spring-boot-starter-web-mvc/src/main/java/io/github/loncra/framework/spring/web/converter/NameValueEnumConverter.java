package io.github.loncra.framework.spring.web.converter;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Objects;
import java.util.Set;

public final class NameValueEnumConverter implements GenericConverter {

    private static final Set<ConvertiblePair> CONVERTIBLE_TYPES = Set.of(
            new ConvertiblePair(String.class, NameValueEnum.class),
            new ConvertiblePair(String.class, NameEnum.class),
            new ConvertiblePair(String.class, ValueEnum.class)
    );

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_TYPES;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> enumType = targetType.getObjectType();
        String value = Objects.toString(source, null);
        Object result = Objects.isNull(value) ? null : resolve(enumType, value);
        if (Objects.isNull(result)) {
            throw new IllegalArgumentException("在类型 [" + enumType.getName() + "] 枚举里找不到值为 [" + value + "] 的类型");
        }
        return result;
    }

    private Object resolve(Class<?> enumType, String value) {
        if (NameValueEnum.class.isAssignableFrom(enumType)) {
            Object byValue = ofValue(enumType, value);
            return Objects.nonNull(byValue) ? byValue : ofName(enumType, value);
        }
        if (ValueEnum.class.isAssignableFrom(enumType)) {
            return ofValue(enumType, value);
        }
        if (NameEnum.class.isAssignableFrom(enumType)) {
            return ofName(enumType, value);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Enum<? extends ValueEnum<Object>> ofValue(Class<?> enumType, String value) {
        Class<Enum<? extends ValueEnum<Object>>> type = CastUtils.cast(enumType);
        return ValueEnum.<Object, Enum<? extends ValueEnum<Object>>>ofEnum(type, value, true);
    }

    @SuppressWarnings("unchecked")
    private static Enum<? extends NameEnum> ofName(Class<?> enumType, String value) {
        Class<Enum<? extends NameEnum>> type = CastUtils.cast(enumType);
        return NameEnum.ofEnum(type, value, true);
    }
}

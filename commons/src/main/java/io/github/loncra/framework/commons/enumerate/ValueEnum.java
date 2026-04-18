package io.github.loncra.framework.commons.enumerate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.EnumUtils;
import io.github.loncra.framework.commons.exception.EnumException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.exception.ValueEnumNotFoundException;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import io.github.loncra.framework.commons.jackson.deserializer.ValueEnumDeserializer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * 带有值的枚举接口
 *
 * @param <V> 值类型
 *
 * @author maurice.chen
 */
@JsonDeserialize(using = ValueEnumDeserializer.class)
public interface ValueEnum<V> {

    String METHOD_NAME = "getValue";

    String FIELD_NAME = "value";

    /**
     * 获取值
     *
     * @return 值
     */
    V getValue();

    /**
     * 将指定 ValueEnum 枚举类转换为 Map，key 为枚举常量名（{@link Enum#name()}），value 为 {@link #getValue()}。
     * <p>
     * 会排除：类或字段上标记了 {@link com.fasterxml.jackson.annotation.JsonIgnore} /
     * {@link com.fasterxml.jackson.annotation.JsonIgnoreProperties} 的枚举常量，以及 {@code ignore} 中指定的常量名。
     *
     * @param enumClass 实现 ValueEnum 的枚举类
     * @param ignore    要排除的枚举常量名（与 {@link Enum#name()} 一致）
     * @param <V>       值类型
     * @param <E>       枚举类型
     * @return key 为枚举 name，value 为 getValue() 的 Map，不会为 null
     */
    static <V, E extends Enum<? extends ValueEnum<V>>> Map<String, V> ofMap(Class<E> enumClass, String... ignore) {
        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);

        Set<String> ignoreValues = EnumUtils.getJsonIgnoreList(enumClass);
        if (ArrayUtils.isNotEmpty(ignore)) {
            ignoreValues.addAll(Set.of(ignore));
        }

        Map<String, V> result = new HashMap<>(constants.length);
        for (E o : constants) {
            if (ignoreValues.contains(o.name())) {
                continue;
            }
            ValueEnum<V> valueEnum = CastUtils.cast(o, ValueEnum.class);
            result.put(o.name(), valueEnum.getValue());
        }

        return result;
    }

    /**
     * 将指定 ValueEnum 枚举类转换为 {@link IdValueMetadata} 列表，每项 id 为枚举常量名，value 为 {@link #getValue()}。
     * <p>
     * 排除规则同 {@link #ofMap(Class, String...)}：JsonIgnore/JsonIgnoreProperties 及 {@code ignore} 中的常量名会被排除。
     *
     * @param enumClass 实现 ValueEnum 的枚举类
     * @param ignore    要排除的枚举常量名
     * @param <V>       值类型
     * @param <E>       枚举类型
     * @return IdValueMetadata 列表，不会为 null
     */
    static <V, E extends Enum<? extends ValueEnum<V>>> List<IdValueMetadata<String, V>> ofIdNameMetadata(Class<E> enumClass, String... ignore) {
        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);

        Set<String> ignoreValues = EnumUtils.getJsonIgnoreList(enumClass);
        if (ArrayUtils.isNotEmpty(ignore)) {
            ignoreValues.addAll(Set.of(ignore));
        }
        List<IdValueMetadata<String, V>> result = new ArrayList<>();
        for (E o : constants) {
            if (ignoreValues.contains(o.name())) {
                continue;
            }
            ValueEnum<V> valueEnum = CastUtils.cast(o, ValueEnum.class);
            result.add(IdValueMetadata.of(o.name(), valueEnum.getValue()));
        }

        return result;
    }

    /**
     * 根据 value 在指定枚举类中查找与之相等的枚举实例（静态方法，通用入口）
     * <p>
     * 使用方式：{@code ValueEnum.ofEnum(YesOrNo.class, 1)} 或 在枚举中定义
     * {@code public static YesOrNo ofEnum(Object value) { return ValueEnum.ofEnum(YesOrNo.class, value); }}
     * 后使用 {@code YesOrNo.ofEnum(1)}
     *
     * @param enumClass 实现 ValueEnum 的枚举类
     * @param value     要匹配的值（与各枚举的 getValue() 比较）
     * @param <E>       枚举类型
     * @return 匹配的枚举实例，找不到时抛出异常
     * @throws ValueEnumNotFoundException 当找不到与 value 相等的枚举时
     */
    static <V, E extends Enum<? extends ValueEnum<V>>> E ofEnum(Class<E> enumClass, Object value) {
        return ofEnum(enumClass, value, false);
    }

    /**
     * 根据 value 在指定枚举类中查找与之相等的枚举实例（静态方法，通用入口）
     *
     * @param enumClass     实现 ValueEnum 的枚举类
     * @param value         要匹配的值
     * @param ignoreNotFound true：找不到时返回 null；false：找不到时抛出 {@link ValueEnumNotFoundException}
     * @param <E>           枚举类型
     * @return 匹配的枚举实例；找不到且 ignoreNotFound 为 true 时返回 null
     * @throws ValueEnumNotFoundException 当找不到与 value 相等的枚举且 ignoreNotFound 为 false 时
     */
    static <V, E extends Enum<? extends ValueEnum<V>>> E ofEnum(Class<E> enumClass, Object value, boolean ignoreNotFound) {
        SystemException.isTrue(Objects.nonNull(value),() -> new EnumException(enumClass.getName() + " 中 value 为 null，无法匹配枚举项"));

        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);
        for (E constant : constants) {
            if (constant instanceof ValueEnum<?> ve && Objects.equals(ve.getValue(), value)) {
                return CastUtils.cast(constant);
            }
            if (constant.name().equals(value) || constant.toString().equals(value.toString())) {
                return CastUtils.cast(constant);
            }
        }

        if (value instanceof Number index && constants.length >= index.intValue()) {
            return CastUtils.cast(constants[index.intValue()]);
        }

        String msg = enumClass.getName() + " 中找不到值为: " + value + " 的枚举项，可选值: " + ofMap(enumClass);
        SystemException.isTrue(ignoreNotFound, () -> new ValueEnumNotFoundException(msg));

        return null;
    }

}

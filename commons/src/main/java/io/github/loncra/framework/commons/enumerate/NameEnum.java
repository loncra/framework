package io.github.loncra.framework.commons.enumerate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.EnumUtils;
import io.github.loncra.framework.commons.exception.EnumException;
import io.github.loncra.framework.commons.exception.NameEnumNotFoundException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.metadata.IdNameMetadata;
import io.github.loncra.framework.commons.jackson.deserializer.NameEnumDeserializer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Strings;

import java.util.*;

/**
 * 带有名称的枚举接口
 *
 * @author maurice.chen
 */
@JsonDeserialize(using = NameEnumDeserializer.class)
public interface NameEnum {

    String FIELD_NAME = "name";

    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 将指定 NameEnum 枚举类转换为 Map，key 为枚举常量名（{@link Enum#name()}），value 为 {@link #getName()}。
     * <p>
     * 会排除：类或字段上标记了 {@link com.fasterxml.jackson.annotation.JsonIgnore} /
     * {@link com.fasterxml.jackson.annotation.JsonIgnoreProperties} 的枚举常量，以及 {@code ignore} 中指定的常量名。
     *
     * @param enumClass 实现 NameEnum 的枚举类
     * @param ignore    要排除的枚举常量名（与 {@link Enum#name()} 一致）
     * @param <E>       枚举类型
     * @return key 为枚举 name，value 为 getName() 的 Map，不会为 null
     */
    static <E extends Enum<? extends NameEnum>> Map<String, String> ofMap(Class<E> enumClass, String... ignore) {
        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);

        Set<String> ignoreValues = EnumUtils.getJsonIgnoreList(enumClass);
        if (ArrayUtils.isNotEmpty(ignore)) {
            ignoreValues.addAll(Set.of(ignore));
        }

        Map<String, String> result = new HashMap<>(constants.length);
        for (E o : constants) {
            if (ignoreValues.contains(o.name())) {
                continue;
            }
            NameEnum nameEnum = CastUtils.cast(o, NameEnum.class);
            if (ignoreValues.contains(nameEnum.getName())) {
                continue;
            }
            result.put(o.name(), nameEnum.getName());
        }

        return result;
    }

    /**
     * 将指定 NameEnum 枚举类转换为 {@link IdNameMetadata} 列表，每项 id 为枚举常量名，name 为 {@link #getName()}。
     * <p>
     * 排除规则同 {@link #ofMap(Class, String...)}：JsonIgnore/JsonIgnoreProperties 及 {@code ignore} 中的常量名会被排除。
     *
     * @param enumClass 实现 NameEnum 的枚举类
     * @param ignore    要排除的枚举常量名
     * @param <E>       枚举类型
     * @return IdNameMetadata 列表，不会为 null
     */
    static <E extends Enum<? extends NameEnum>> List<IdNameMetadata> ofIdNameMetadata(Class<E> enumClass, String... ignore) {
        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);

        Set<String> ignoreValues = EnumUtils.getJsonIgnoreList(enumClass);
        if (ArrayUtils.isNotEmpty(ignore)) {
            ignoreValues.addAll(Set.of(ignore));
        }
        List<IdNameMetadata> result = new ArrayList<>();
        for (E o : constants) {
            if (ignoreValues.contains(o.name())) {
                continue;
            }
            NameEnum nameEnum = CastUtils.cast(o, NameEnum.class);
            if (ignoreValues.contains(nameEnum.getName())) {
                continue;
            }
            result.add(IdNameMetadata.of(o.name(), nameEnum.getName()));
        }

        return result;
    }

    /**
     * 根据 name 在指定枚举类中查找与之相同的枚举实例（静态方法，通用入口）
     * <p>
     * 使用方式：{@code NameEnum.ofEnum(Color.class, "红色")} 或 在枚举中定义
     * {@code public static Color ofEnum(String name) { return NameEnum.ofEnum(Color.class, name); }}
     * 后使用 {@code Color.ofEnum("红色")}
     *
     * @param enumClass 实现 NameEnum 的枚举类
     * @param name      要匹配的名称（与 getName() 或枚举常量 name() 比较）
     * @param <E>       枚举类型
     * @return 匹配的枚举实例，找不到时抛出异常
     * @throws NameEnumNotFoundException 当找不到与 name 相同的枚举时
     */
    static <E extends Enum<? extends NameEnum>> E ofEnum(Class<E> enumClass, String name) {
        return ofEnum(enumClass, name, false);
    }

    /**
     * 根据 name 在指定枚举类中查找与之相同的枚举实例（静态方法，通用入口）
     *
     * @param enumClass     实现 NameEnum 的枚举类
     * @param name          要匹配的名称
     * @param ignoreNotFound true：找不到时返回 null；false：找不到时抛出 {@link NameEnumNotFoundException}
     * @param <E>           枚举类型
     * @return 匹配的枚举实例；找不到且 ignoreNotFound 为 true 时返回 null
     * @throws NameEnumNotFoundException 当找不到与 name 相同的枚举且 ignoreNotFound 为 false 时
     */
    static <E extends Enum<? extends NameEnum>> E ofEnum(Class<E> enumClass, String name, boolean ignoreNotFound) {
        SystemException.isTrue(Objects.nonNull(name),() -> new EnumException(enumClass.getName() + " 中 name 为 null，无法匹配枚举项"));

        E[] constants = EnumUtils.checkThenReturnEnum(enumClass);
        for (E o : constants) {
            if (Objects.equals(o.name(), name) || Strings.CS.equals(o.toString(), name)) {
                return CastUtils.cast(o);
            }
            else if (o instanceof NameEnum nameEnum && Strings.CS.equals(nameEnum.getName(),name)) {
                return CastUtils.cast(o);
            }
        }

        String msg = enumClass.getName() + " 中找不到名称为: " + name + " 的枚举项，可选值: " + ofMap(enumClass);
        SystemException.isTrue(ignoreNotFound, () -> new NameEnumNotFoundException(msg));

        return null;
    }

}


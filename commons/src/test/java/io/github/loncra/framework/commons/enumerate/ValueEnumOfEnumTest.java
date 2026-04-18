package io.github.loncra.framework.commons.enumerate;

import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;
import io.github.loncra.framework.commons.exception.EnumException;
import io.github.loncra.framework.commons.exception.ValueEnumNotFoundException;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * ValueEnum#ofEnum 单元测试
 *
 * @author maurice.chen
 */
@DisplayName("ValueEnum.ofEnum 测试")
class ValueEnumOfEnumTest {

    /**
     * 仅实现 ValueEnum 的测试枚举；通过静态 ofEnum 可实现 {@code Status.ofEnum(1)} 得到 Status.ON
     */
    public enum Status implements ValueEnum<Integer> {
        ON(1),
        OFF(0);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        public static Status ofEnum(Object value) {
            return ValueEnum.ofEnum(Status.class, value);
        }

        public static Status ofEnum(Object value, boolean ignoreNotFound) {
            return ValueEnum.ofEnum(Status.class, value, ignoreNotFound);
        }
    }

    @Nested
    @DisplayName("静态 Status.ofEnum(value) - 推荐用法")
    class StaticStatusOfEnum {

        @Test
        @DisplayName("Status.ofEnum(1) 得到 Status.ON")
        void statusOfEnumByValue() {
            Assertions.assertSame(Status.ON, Status.ofEnum(1));
            Assertions.assertSame(Status.OFF, Status.ofEnum(0));
        }

        @Test
        @DisplayName("找不到时抛出 ValueEnumNotFoundException")
        void notFoundThrows() {
            Assertions.assertThrows(ValueEnumNotFoundException.class, () -> Status.ofEnum(999));
        }

        @Test
        @DisplayName("双参 ignoreNotFound")
        void statusOfEnumWithIgnoreNotFound() {
            Assertions.assertNull(Status.ofEnum(999, true));
            Assertions.assertThrows(ValueEnumNotFoundException.class, () -> Status.ofEnum(999, false));
        }
    }

    @Nested
    @DisplayName("ValueEnum.ofEnum(Class, value) - 静态通用入口")
    class StaticValueEnumOfEnum {

        @Test
        @DisplayName("ValueEnum.ofEnum(YesOrNo.class, 1) 得到 YesOrNo.Yes")
        void valueEnumOfEnumByClass() {
            YesOrNo yes = ValueEnum.ofEnum(YesOrNo.class, 1);
            Assertions.assertSame(YesOrNo.Yes, yes);
            Assertions.assertSame(YesOrNo.No, ValueEnum.ofEnum(YesOrNo.class, 0));
        }

        @Test
        @DisplayName("value 为 Integer 类型可匹配")
        void valueAsIntegerMatches() {
            YesOrNo yes = ValueEnum.ofEnum(YesOrNo.class, Integer.valueOf(1));
            Assertions.assertSame(YesOrNo.Yes, yes);
        }

        @Test
        @DisplayName("找不到时抛出 ValueEnumNotFoundException")
        void notFoundThrows() {
            Assertions.assertThrows(ValueEnumNotFoundException.class, () -> ValueEnum.ofEnum(YesOrNo.class, 999));
        }

        @Test
        @DisplayName("value 为 null 时行为")
        void nullValueBehavior() {
            Assertions.assertThrows(EnumException.class, () -> ValueEnum.ofEnum(YesOrNo.class, null, true));
            Assertions.assertThrows(EnumException.class, () -> ValueEnum.ofEnum(YesOrNo.class, null, false));
        }
    }

    @Nested
    @DisplayName("ValueEnum.ofMap 测试")
    class ValueEnumOfMap {

        @Test
        @DisplayName("ofMap(enumClass) 返回 name -> getValue() 的 Map")
        void ofMapReturnsNameToValue() {
            Map<String, Integer> map = ValueEnum.ofMap(Status.class);
            Assertions.assertEquals(2, map.size());
            Assertions.assertEquals(1, map.get("ON"));
            Assertions.assertEquals(0, map.get("OFF"));

            Map<String, Integer> yesNoMap = ValueEnum.ofMap(YesOrNo.class);
            Assertions.assertEquals(1, yesNoMap.get("Yes"));
            Assertions.assertEquals(0, yesNoMap.get("No"));
        }

        @Test
        @DisplayName("ofMap(enumClass, ignore...) 排除指定常量名")
        void ofMapWithIgnoreExcludesConstants() {
            Map<String, Integer> map = ValueEnum.ofMap(Status.class, "OFF");
            Assertions.assertEquals(1, map.size());
            Assertions.assertTrue(map.containsKey("ON"));
            Assertions.assertFalse(map.containsKey("OFF"));
        }
    }

    @Nested
    @DisplayName("ValueEnum.ofIdNameMetadata 测试")
    class ValueEnumOfIdNameMetadata {

        @Test
        @DisplayName("ofIdNameMetadata(enumClass) 返回 IdValueMetadata 列表")
        void ofIdNameMetadataReturnsList() {
            List<IdValueMetadata<String, Integer>> list = ValueEnum.ofIdNameMetadata(Status.class);
            Assertions.assertEquals(2, list.size());
            Assertions.assertTrue(list.stream().anyMatch(m -> "ON".equals(m.getId()) && Integer.valueOf(1).equals(m.getValue())));
            Assertions.assertTrue(list.stream().anyMatch(m -> "OFF".equals(m.getId()) && Integer.valueOf(0).equals(m.getValue())));
        }

        @Test
        @DisplayName("ofIdNameMetadata(enumClass, ignore...) 排除指定常量名")
        void ofIdNameMetadataWithIgnoreExcludesConstants() {
            List<IdValueMetadata<String, Integer>> list = ValueEnum.ofIdNameMetadata(Status.class, "OFF");
            Assertions.assertEquals(1, list.size());
            Assertions.assertEquals("ON", list.get(0).getId());
            Assertions.assertEquals(1, list.get(0).getValue());
        }
    }

}

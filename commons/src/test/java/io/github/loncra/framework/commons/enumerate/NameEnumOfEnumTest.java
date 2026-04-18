package io.github.loncra.framework.commons.enumerate;

import io.github.loncra.framework.commons.exception.EnumException;
import io.github.loncra.framework.commons.exception.NameEnumNotFoundException;
import io.github.loncra.framework.commons.id.metadata.IdNameMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * NameEnum#ofEnum 单元测试
 *
 * @author maurice.chen
 */
@DisplayName("NameEnum.ofEnum 测试")
class NameEnumOfEnumTest {

    /**
     * 测试用枚举；通过静态 ofEnum 可实现 {@code Color.ofEnum("红色")} 得到 Color.RED
     */
    public enum Color implements NameEnum {
        RED("红色"),
        GREEN("绿色"),
        BLUE("蓝色");

        private final String name;

        Color(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        /** 静态入口：直接通过类调用，如 Color.ofEnum("红色") */
        public static Color ofEnum(String name) {
            return NameEnum.ofEnum(Color.class, name);
        }

        public static Color ofEnum(String name, boolean ignoreNotFound) {
            return NameEnum.ofEnum(Color.class, name, ignoreNotFound);
        }
    }

    @Nested
    @DisplayName("静态 Color.ofEnum(name) - 推荐用法")
    class StaticColorOfEnum {

        @Test
        @DisplayName("Color.ofEnum(\"红色\") 得到 Color.RED")
        void colorOfEnumByName() {
            Color red = Color.ofEnum("红色");
            Assertions.assertSame(Color.RED, red);

            Assertions.assertSame(Color.GREEN, Color.ofEnum("绿色"));
            Assertions.assertSame(Color.BLUE, Color.ofEnum("蓝色"));
        }

        @Test
        @DisplayName("通过枚举常量名 name() 匹配")
        void colorOfEnumByConstantName() {
            Assertions.assertSame(Color.RED, Color.ofEnum("RED"));
            Assertions.assertSame(Color.BLUE, Color.ofEnum("BLUE"));
        }

        @Test
        @DisplayName("找不到时抛出 NameEnumNotFoundException")
        void notFoundThrows() {
            Assertions.assertThrows(NameEnumNotFoundException.class, () -> Color.ofEnum("未知"));
            Assertions.assertThrows(NameEnumNotFoundException.class, () -> Color.ofEnum(""));
        }
    }

    @Nested
    @DisplayName("NameEnum.ofEnum(Class, name) - 静态通用入口")
    class StaticNameEnumOfEnum {

        @Test
        @DisplayName("NameEnum.ofEnum(Color.class, \"红色\") 得到 Color.RED")
        void nameEnumOfEnumByClass() {
            Color red = NameEnum.ofEnum(Color.class, "红色");
            Assertions.assertSame(Color.RED, red);
        }

        @Test
        @DisplayName("双参 ignoreNotFound")
        void nameEnumOfEnumWithIgnoreNotFound() {
            Assertions.assertNull(NameEnum.ofEnum(Color.class, "未知", true));
            Assertions.assertThrows(NameEnumNotFoundException.class, () -> NameEnum.ofEnum(Color.class, "未知", false));
        }

        @Test
        @DisplayName("name 为 null 时行为")
        void nullNameBehavior() {
            Assertions.assertThrows(EnumException.class, () -> NameEnum.ofEnum(Color.class, null, true));
            Assertions.assertThrows(EnumException.class, () -> NameEnum.ofEnum(Color.class, null, false));
        }
    }

    @Nested
    @DisplayName("NameEnum.ofMap 测试")
    class NameEnumOfMap {

        @Test
        @DisplayName("ofMap(enumClass) 返回 name -> getName() 的 Map")
        void ofMapReturnsNameToGetName() {
            Map<String, String> map = NameEnum.ofMap(Color.class);
            Assertions.assertEquals(3, map.size());
            Assertions.assertEquals("红色", map.get("RED"));
            Assertions.assertEquals("绿色", map.get("GREEN"));
            Assertions.assertEquals("蓝色", map.get("BLUE"));
        }

        @Test
        @DisplayName("ofMap(enumClass, ignore...) 排除指定常量名")
        void ofMapWithIgnoreExcludesConstants() {
            Map<String, String> map = NameEnum.ofMap(Color.class, "BLUE");
            Assertions.assertEquals(2, map.size());
            Assertions.assertTrue(map.containsKey("RED"));
            Assertions.assertTrue(map.containsKey("GREEN"));
            Assertions.assertFalse(map.containsKey("BLUE"));
        }
    }

    @Nested
    @DisplayName("NameEnum.ofIdNameMetadata 测试")
    class NameEnumOfIdNameMetadata {

        @Test
        @DisplayName("ofIdNameMetadata(enumClass) 返回 IdNameMetadata 列表")
        void ofIdNameMetadataReturnsList() {
            List<IdNameMetadata> list = NameEnum.ofIdNameMetadata(Color.class);
            Assertions.assertEquals(3, list.size());
            Assertions.assertTrue(list.stream().anyMatch(m -> "RED".equals(m.getId()) && "红色".equals(m.getName())));
            Assertions.assertTrue(list.stream().anyMatch(m -> "GREEN".equals(m.getId()) && "绿色".equals(m.getName())));
            Assertions.assertTrue(list.stream().anyMatch(m -> "BLUE".equals(m.getId()) && "蓝色".equals(m.getName())));
        }

        @Test
        @DisplayName("ofIdNameMetadata(enumClass, ignore...) 排除指定常量名")
        void ofIdNameMetadataWithIgnoreExcludesConstants() {
            List<IdNameMetadata> list = NameEnum.ofIdNameMetadata(Color.class, "GREEN", "BLUE");
            Assertions.assertEquals(1, list.size());
            Assertions.assertEquals("RED", list.get(0).getId());
            Assertions.assertEquals("红色", list.get(0).getName());
        }
    }

}

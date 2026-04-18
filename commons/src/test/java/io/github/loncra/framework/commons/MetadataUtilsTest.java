package io.github.loncra.framework.commons;

import io.github.loncra.framework.commons.annotation.Description;
import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.commons.annotation.MetadataElements;
import io.github.loncra.framework.commons.domain.metadata.TreeDescriptionMetadata;
import io.github.loncra.framework.commons.tree.Tree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MetadataUtils 测试类
 *
 * @author maurice.chen
 */
public class MetadataUtilsTest {

    @Description(value = "测试类描述", name = "TestClass")
    public static class TestDescriptionClass {

        @Description(value = "名称字段", name = "name", sort = 1)
        private String name;

        @Description(value = "年龄字段", name = "age", sort = 2)
        private Integer age;

        @Description(value = "获取名称", name = "getName", sort = 1)
        public String getName() {
            return name;
        }

        @Description(value = "获取年龄", name = "getAge", sort = 2)
        public Integer getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    @Description(value = "嵌套测试类", name = "NestedTestClass")
    public static class NestedTestClass {

        @Description(value = "测试实体", name = "testEntity")
        private TestDescriptionClass testEntity;

        @Description(value = "标签列表", name = "tags")
        private List<String> tags;

        public TestDescriptionClass getTestEntity() {
            return testEntity;
        }

        public void setTestEntity(TestDescriptionClass testEntity) {
            this.testEntity = testEntity;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    @SuppressWarnings("unused")
    public static class NoDescriptionClass {
        private String name;
    }

    @Test
    public void testConvertDescriptionMetadataForClass() {
        // 测试有 @Description 注解的类
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(TestDescriptionClass.class);
        Assertions.assertNotNull(metadata);
        // TreeDescriptionMetadata.of() 第一个参数是 id，第二个参数是 name
        // 所以 description.name() 作为 id，description.value() 作为 name
        Assertions.assertEquals("测试类描述", metadata.getName());
        Assertions.assertEquals(TestDescriptionClass.class.getName(), metadata.getType());
        Assertions.assertEquals(Class.class.getName(), metadata.getSource());
        Assertions.assertNotNull(metadata.getChildren());
        Assertions.assertTrue(metadata.getChildren().size() > 0);

        // 测试没有 @Description 注解的类
        TreeDescriptionMetadata noDescriptionMetadata = MetadataUtils.convertDescriptionMetadata(NoDescriptionClass.class);
        Assertions.assertNull(noDescriptionMetadata);

        // 测试 null
        TreeDescriptionMetadata nullMetadata = MetadataUtils.convertDescriptionMetadata((Class<?>) null);
        Assertions.assertNull(nullMetadata);
    }

    @Test
    public void testConvertDescriptionMetadataForClassList() {
        List<Class<?>> classes = Arrays.asList(TestDescriptionClass.class, NoDescriptionClass.class, null);
        List<TreeDescriptionMetadata> metadataList = MetadataUtils.convertDescriptionMetadata(classes);

        Assertions.assertNotNull(metadataList);
        Assertions.assertEquals(1, metadataList.size()); // 只有 TestDescriptionClass 有 @Description 注解
        Assertions.assertEquals("TestClass", metadataList.get(0).getId());
    }

    @Test
    public void testConvertDescriptionMetadataForField() throws NoSuchFieldException {
        Field nameField = TestDescriptionClass.class.getDeclaredField("name");
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(nameField);

        Assertions.assertNotNull(metadata);
        Assertions.assertEquals("名称字段", metadata.getName());
        Assertions.assertEquals(String.class.getName(), metadata.getType());
        Assertions.assertEquals(Field.class.getName(), metadata.getSource());

        // 测试没有 @Description 注解的字段
        Field noDescriptionField = NoDescriptionClass.class.getDeclaredField("name");
        TreeDescriptionMetadata noDescriptionMetadata = MetadataUtils.convertDescriptionMetadata(noDescriptionField);
        Assertions.assertNull(noDescriptionMetadata);

        // 测试 null
        TreeDescriptionMetadata nullMetadata = MetadataUtils.convertDescriptionMetadata((Field) null);
        Assertions.assertNull(nullMetadata);
    }

    @Test
    public void testConvertDescriptionMetadataForMethod() throws NoSuchMethodException {
        Method getNameMethod = TestDescriptionClass.class.getMethod("getName");
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(getNameMethod);

        Assertions.assertNotNull(metadata);
        Assertions.assertEquals("获取名称", metadata.getName());
        Assertions.assertEquals(String.class.getName(), metadata.getType());
        Assertions.assertEquals(Method.class.getName(), metadata.getSource());

        // 测试没有 @Description 注解的方法
        Method noDescriptionMethod = NoDescriptionClass.class.getMethod("toString");
        TreeDescriptionMetadata noDescriptionMetadata = MetadataUtils.convertDescriptionMetadata(noDescriptionMethod);
        Assertions.assertNull(noDescriptionMetadata);

        // 测试 null
        TreeDescriptionMetadata nullMetadata = MetadataUtils.convertDescriptionMetadata((Method) null);
        Assertions.assertNull(nullMetadata);
    }

    @Test
    public void testConvertDescriptionMetadataWithNestedField() throws NoSuchFieldException {
        Field testEntityField = NestedTestClass.class.getDeclaredField("testEntity");
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(testEntityField);

        Assertions.assertNotNull(metadata);
        Assertions.assertEquals("testEntity", metadata.getId());
        Assertions.assertNotNull(metadata.getChildren());
        // 嵌套字段应该包含子字段
        Assertions.assertTrue(metadata.getChildren().size() > 0);
    }

    @Test
    public void testConvertDescriptionMetadataWithCollectionField() throws NoSuchFieldException {
        Field tagsField = NestedTestClass.class.getDeclaredField("tags");
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(tagsField);

        Assertions.assertNotNull(metadata);
        Assertions.assertEquals("tags", metadata.getId());
        // List<String> 是基本类型，不应该有子节点
        Assertions.assertTrue(metadata.getChildren().isEmpty());
    }

    @Test
    public void testConvertDescriptionMetadataSort() {
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(TestDescriptionClass.class);
        Assertions.assertNotNull(metadata);
        Assertions.assertNotNull(metadata.getChildren());

        // 验证子节点按 sort 排序
        List<TreeDescriptionMetadata> children = new ArrayList<>();
        for (Tree<String, TreeDescriptionMetadata> child : metadata.getChildren()) {
            children.add((TreeDescriptionMetadata) child);
        }
        for (int i = 0; i < children.size() - 1; i++) {
            Assertions.assertTrue(children.get(i).getSort() <= children.get(i + 1).getSort());
        }
    }

    @Test
    public void testConvertDescriptionMetadataWithDefaultName() {
        TreeDescriptionMetadata metadata = MetadataUtils.convertDescriptionMetadata(OnlyValueClass.class);
        Assertions.assertNotNull(metadata);
        // 如果没有指定 name，应该使用类名作为 id，value 作为 name
        Assertions.assertEquals("只有值的描述", metadata.getName());
    }

    @Description(value = "只有值的描述")
    static class OnlyValueClass {
    }

    // ========== parseMetadata 测试类 ==========

    @Metadata(key = "classKey1", value = "classValue1")
    @Metadata(key = "classKey2", value = "classValue2")
    public static class TestMetadataClass {

        @Metadata(key = "fieldKey1", value = "fieldValue1")
        private String field1;

        @MetadataElements({
                @Metadata(key = "fieldKey2", value = "fieldValue2"),
                @Metadata(key = "fieldKey3", value = "fieldValue3")
        })
        private String field2;

        @Metadata(key = "methodKey1", value = "methodValue1")
        public void method1() {
        }

        @MetadataElements({
                @Metadata(key = "methodKey2", value = "methodValue2"),
                @Metadata(key = "methodKey3", value = "methodValue3")
        })
        public void method2() {
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

    @MetadataElements({
            @Metadata(key = "elementsKey1", value = "elementsValue1"),
            @Metadata(key = "elementsKey2", value = "elementsValue2")
    })
    public static class TestMetadataElementsClass {

        @Metadata(key = "singleFieldKey", value = "singleFieldValue")
        private String singleField;

        public String getSingleField() {
            return singleField;
        }

        public void setSingleField(String singleField) {
            this.singleField = singleField;
        }
    }

    public static class NoMetadataClass {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    @Test
    public void testParseMetadataWithClassAnnotation() {
        Map<String, String> result = MetadataUtils.parseMetadata(TestMetadataClass.class);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        // 验证类上的注解
        Assertions.assertEquals("classValue1", result.get("classKey1"));
        Assertions.assertEquals("classValue2", result.get("classKey2"));

        // 验证字段上的注解
        Assertions.assertEquals("fieldValue1", result.get("fieldKey1"));
        Assertions.assertEquals("fieldValue2", result.get("fieldKey2"));
        Assertions.assertEquals("fieldValue3", result.get("fieldKey3"));

        // 验证方法上的注解
        Assertions.assertEquals("methodValue1", result.get("methodKey1"));
        Assertions.assertEquals("methodValue2", result.get("methodKey2"));
        Assertions.assertEquals("methodValue3", result.get("methodKey3"));
    }

    @Test
    public void testParseMetadataWithMetadataElementsAnnotation() {
        Map<String, String> result = MetadataUtils.parseMetadata(TestMetadataElementsClass.class);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        // 验证类上的 @MetadataElements 注解
        Assertions.assertEquals("elementsValue1", result.get("elementsKey1"));
        Assertions.assertEquals("elementsValue2", result.get("elementsKey2"));

        // 验证字段上的注解
        Assertions.assertEquals("singleFieldValue", result.get("singleFieldKey"));
    }

    @Test
    public void testParseMetadataWithNoMetadata() {
        Map<String, String> result = MetadataUtils.parseMetadata(NoMetadataClass.class);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseMetadataWithNull() {
        Map<String, String> result = MetadataUtils.parseMetadata(null);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseMetadataWithMixedAnnotations() {
        Map<String, String> result = MetadataUtils.parseMetadata(TestMetadataClass.class);

        Assertions.assertNotNull(result);

        // 验证所有注解都被收集
        int expectedSize = 8; // 2个类注解 + 3个字段注解 + 3个方法注解
        Assertions.assertEquals(expectedSize, result.size());

        // 验证所有键都存在
        Assertions.assertTrue(result.containsKey("classKey1"));
        Assertions.assertTrue(result.containsKey("classKey2"));
        Assertions.assertTrue(result.containsKey("fieldKey1"));
        Assertions.assertTrue(result.containsKey("fieldKey2"));
        Assertions.assertTrue(result.containsKey("fieldKey3"));
        Assertions.assertTrue(result.containsKey("methodKey1"));
        Assertions.assertTrue(result.containsKey("methodKey2"));
        Assertions.assertTrue(result.containsKey("methodKey3"));
    }

    @Test
    public void testParseMetadataOrder() {
        Map<String, String> result = MetadataUtils.parseMetadata(TestMetadataClass.class);

        Assertions.assertNotNull(result);

        // LinkedHashMap 应该保持插入顺序
        // 顺序应该是：类注解 -> 字段注解 -> 方法注解
        List<String> keys = new ArrayList<>(result.keySet());

        // 验证类注解在前面
        Assertions.assertTrue(keys.indexOf("classKey1") < keys.indexOf("fieldKey1"));
        Assertions.assertTrue(keys.indexOf("classKey2") < keys.indexOf("fieldKey1"));

        // 验证字段注解在方法注解前面
        Assertions.assertTrue(keys.indexOf("fieldKey1") < keys.indexOf("methodKey1"));
    }

    @Test
    public void testToMap() {
        // 测试正常情况
        Metadata[] metadatas = new Metadata[] {
                createMetadata("key1", "value1"),
                createMetadata("key2", "value2"),
                createMetadata("key3", "value3")
        };

        Map<String, String> result = MetadataUtils.toMap(metadatas);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("value1", result.get("key1"));
        Assertions.assertEquals("value2", result.get("key2"));
        Assertions.assertEquals("value3", result.get("key3"));

        // 测试 null 数组
        Map<String, String> nullResult = MetadataUtils.toMap(null);
        Assertions.assertNotNull(nullResult);
        Assertions.assertTrue(nullResult.isEmpty());

        // 测试空数组
        Map<String, String> emptyResult = MetadataUtils.toMap(new Metadata[0]);
        Assertions.assertNotNull(emptyResult);
        Assertions.assertTrue(emptyResult.isEmpty());

        // 测试包含 null 元素的数组
        Metadata[] withNulls = new Metadata[] {
                createMetadata("key1", "value1"),
                null,
                createMetadata("key2", "value2")
        };
        Map<String, String> withNullsResult = MetadataUtils.toMap(withNulls);
        Assertions.assertNotNull(withNullsResult);
        Assertions.assertEquals(2, withNullsResult.size());
        Assertions.assertEquals("value1", withNullsResult.get("key1"));
        Assertions.assertEquals("value2", withNullsResult.get("key2"));
    }

    /**
     * 创建 Metadata 注解的辅助方法（用于测试）
     */
    private Metadata createMetadata(
            String key,
            String value
    ) {
        return new Metadata() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Metadata.class;
            }

            @Override
            public String key() {
                return key;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

}


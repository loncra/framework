package io.github.loncra.framework.commons;

import io.github.loncra.framework.commons.annotation.IgnoreField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ObjectUtils 测试类
 *
 * @author maurice.chen
 */
public class ObjectUtilsTest {

    public static class TestEntity {
        private String name;
        private Integer age;
        private String email;
        private String phone;

        @IgnoreField
        private String secret;

        public TestEntity() {
        }

        public TestEntity(
                String name,
                Integer age,
                String email,
                String phone,
                String secret
        ) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.phone = phone;
            this.secret = secret;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class NestedEntity {
        private String id;
        private TestEntity entity;
        private List<String> tags;

        public NestedEntity() {
        }

        public NestedEntity(
                String id,
                TestEntity entity,
                List<String> tags
        ) {
            this.id = id;
            this.entity = entity;
            this.tags = tags;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public TestEntity getEntity() {
            return entity;
        }

        public void setEntity(TestEntity entity) {
            this.entity = entity;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    @Test
    public void testGetIgnoreField() {
        List<java.lang.reflect.Field> ignoreFields = ObjectUtils.getIgnoreField(TestEntity.class);
        Assertions.assertEquals(1, ignoreFields.size());
        Assertions.assertEquals("secret", ignoreFields.get(0).getName());

        // 测试没有 @IgnoreField 注解的类
        List<java.lang.reflect.Field> noIgnoreFields = ObjectUtils.getIgnoreField(String.class);
        Assertions.assertTrue(noIgnoreFields.isEmpty());
    }

    @Test
    public void testIsPrimitive() {
        // 测试基本类型包装类
        Assertions.assertTrue(ObjectUtils.isPrimitive(Boolean.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Byte.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Character.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Short.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Integer.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Long.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Float.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(Double.class));
        Assertions.assertTrue(ObjectUtils.isPrimitive(String.class));

        // 测试非基本类型
        Assertions.assertFalse(ObjectUtils.isPrimitive(Object.class));
        Assertions.assertFalse(ObjectUtils.isPrimitive(List.class));
        Assertions.assertFalse(ObjectUtils.isPrimitive(Map.class));
        Assertions.assertFalse(ObjectUtils.isPrimitive(TestEntity.class));
    }

    @Test
    public void testIgnoreObjectFieldToMap() {
        TestEntity entity = new TestEntity("张三", 25, "zhangsan@example.com", "13800138000", "secret123");

        List<String> properties = Arrays.asList("secret", "phone");
        Map<String, Object> result = ObjectUtils.ignoreObjectFieldToMap(entity, properties);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.containsKey("secret"));
        Assertions.assertFalse(result.containsKey("phone"));
        Assertions.assertTrue(result.containsKey("name"));
        Assertions.assertTrue(result.containsKey("age"));
        Assertions.assertTrue(result.containsKey("email"));
        Assertions.assertEquals("张三", result.get("name"));
        Assertions.assertEquals(25, result.get("age"));
    }

    @Test
    public void testIgnoreObjectFieldToMapWithNested() {
        TestEntity entity = new TestEntity("张三", 25, "zhangsan@example.com", "13800138000", "secret123");
        NestedEntity nested = new NestedEntity("1", entity, Arrays.asList("tag1", "tag2"));

        List<String> properties = Arrays.asList("entity.secret", "entity.phone");
        Map<String, Object> result = ObjectUtils.ignoreObjectFieldToMap(nested, properties);

        Assertions.assertNotNull(result);
        @SuppressWarnings("unchecked")
        Map<String, Object> entityMap = (Map<String, Object>) result.get("entity");
        Assertions.assertNotNull(entityMap);
        Assertions.assertFalse(entityMap.containsKey("secret"));
        Assertions.assertFalse(entityMap.containsKey("phone"));
    }

    @Test
    public void testDesensitizeObjectFieldToMap() {
        TestEntity entity = new TestEntity("张三", 25, "zhangsan@example.com", "13800138000", "secret123");

        List<String> properties = Arrays.asList("phone", "email");
        Map<String, Object> result = ObjectUtils.desensitizeObjectFieldToMap(entity, properties);

        Assertions.assertNotNull(result);
        String phone = (String) result.get("phone");
        String email = (String) result.get("email");

        // 脱敏后的值应该不等于原值
        Assertions.assertNotEquals("13800138000", phone);
        Assertions.assertNotEquals("zhangsan@example.com", email);
        // 脱敏后的值应该包含 * 或其他脱敏字符
        Assertions.assertTrue(phone.contains("*") || phone.length() < "13800138000".length());
    }

    @Test
    public void testDesensitizeObjectFieldToMapWithList() {
        NestedEntity nested = new NestedEntity("1",
                                               new TestEntity("张三", 25, "zhangsan@example.com", "13800138000", "secret123"),
                                               Arrays.asList("tag1", "tag2"));

        List<String> properties = List.of("tags");
        Map<String, Object> result = ObjectUtils.desensitizeObjectFieldToMap(nested, properties);

        Assertions.assertNotNull(result);
        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>) result.get("tags");
        Assertions.assertNotNull(tags);
        // 列表中的值应该被脱敏
        for (String tag : tags) {
            Assertions.assertTrue(tag.contains("*") || tag.length() < 4);
        }
    }

    @Test
    public void testIgnoreObjectFieldToMapWithEmptyProperties() {
        TestEntity entity = new TestEntity("张三", 25, "zhangsan@example.com", "13800138000", "secret123");

        List<String> properties = new ArrayList<>();
        Map<String, Object> result = ObjectUtils.ignoreObjectFieldToMap(entity, properties);

        Assertions.assertNotNull(result);
        // 没有指定要忽略的属性，所有属性都应该存在
        Assertions.assertTrue(result.containsKey("name"));
        Assertions.assertTrue(result.containsKey("age"));
        Assertions.assertTrue(result.containsKey("email"));
        Assertions.assertTrue(result.containsKey("phone"));
    }
}

